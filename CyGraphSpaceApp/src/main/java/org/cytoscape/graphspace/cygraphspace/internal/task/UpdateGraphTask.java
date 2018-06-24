package org.cytoscape.graphspace.cygraphspace.internal.task;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.cytoscape.graphspace.cygraphspace.internal.gui.ResultPanelEvent;
import org.cytoscape.graphspace.cygraphspace.internal.gui.ResultPanelEventListener;
import org.cytoscape.graphspace.cygraphspace.internal.singletons.Server;
import org.cytoscape.graphspace.cygraphspace.internal.util.MessageConfig;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.json.JSONObject;

/**
 * Task class that update graph to the GraphSpace and update related status
 */
public class UpdateGraphTask extends AbstractTask {
    private JSONObject graphJSON;
    private JSONObject styleJSON; 
    private boolean isGraphPublic;
    private TaskMonitor taskMonitor;
    private ResultPanelEventListener listener;

    public UpdateGraphTask(JSONObject graphJSON, JSONObject styleJSON, boolean isGraphPublic) {
        this.graphJSON = graphJSON;
        this.styleJSON = styleJSON;
        this.isGraphPublic = isGraphPublic;
        this.listener = null;
    }

    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {

        this.taskMonitor = taskMonitor;
        taskMonitor.setTitle(MessageConfig.UPDATE_GRAPH_TASK_TITLE);
        taskMonitor.setStatusMessage(MessageConfig.UPDATE_GRAPH_TASK_STATUS_IN_PROGRESS);

        // run task in background
        new Thread() {
            public void run() {
                postGraph();
            }
        }.start();
    }

    private void postGraph() {
        int panelIndex = -1;
        try {
            if (listener != null)
                panelIndex = listener.postGraphEvent(
                        new ResultPanelEvent(graphJSON.getJSONObject("data").getString("name"),MessageConfig.TASK_IN_PROGRESS));

            updateGraph(graphJSON, styleJSON, isGraphPublic, null);
        } catch (Exception e1) {
            taskMonitor.setStatusMessage(MessageConfig.UPDATE_GRAPH_TASK_STATUS_FAIL);
            JOptionPane.showMessageDialog(null, 
                    MessageConfig.UPDATE_GRAPH_TASK_DIALOG_FAIL, "Error", JOptionPane.ERROR_MESSAGE);

            if (listener != null)
                listener.updateGraphStatusEvent(
                        new ResultPanelEvent(panelIndex, "", MessageConfig.TASK_FAIL));

            return;
        }

        if (listener != null)
            listener.updateGraphStatusEvent(
                    new ResultPanelEvent(panelIndex, "", MessageConfig.TASK_COMPLETE));

        taskMonitor.setStatusMessage(MessageConfig.UPDATE_GRAPH_TASK_STATUS_SUCCESS);
        JOptionPane.showMessageDialog(null, 
                MessageConfig.UPDATE_GRAPH_TASK_DIALOG_SUCCESS, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    //post the current network to GraphSpace
    private void updateGraph(JSONObject graphJSON, JSONObject styleJSON, boolean isPublic, ArrayList<String> tagsList) throws Exception{
        String name = graphJSON.getJSONObject("data").getString("name");
        Server.INSTANCE.updateGraph(name, graphJSON, isPublic, tagsList);
    }

    public void setResultPanelEventListener(ResultPanelEventListener listener) {
        this.listener = listener;
    }
}
