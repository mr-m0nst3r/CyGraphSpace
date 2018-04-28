package org.cytoscape.graphspace.cygraphspace.internal.task;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.cytoscape.graphspace.cygraphspace.internal.singletons.Server;
import org.cytoscape.graphspace.cygraphspace.internal.util.MessageConfig;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.json.JSONObject;

/**
 * Task class that posts graph to the GraphSpace and update related status
 */
public class PostGraphTask extends AbstractTask {
    private JSONObject graphJSON;
    private JSONObject styleJSON; 
    private boolean isGraphPublic;
    private TaskMonitor taskMonitor;

    public PostGraphTask(JSONObject graphJSON, JSONObject styleJSON, boolean isGraphPublic) {
        this.graphJSON = graphJSON;
        this.styleJSON = styleJSON;
        this.isGraphPublic = isGraphPublic;
    }

    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {

        this.taskMonitor = taskMonitor;
        taskMonitor.setTitle(MessageConfig.POST_GRAPH_TASK_TITLE);
        taskMonitor.setStatusMessage(MessageConfig.POST_GRAPH_TASK_STATUS_IN_PROGRESS);

        // run task in background
        new Thread() {
            public void run() {
                postGraph();
            }
        }.start();
    }

    private void postGraph() {
        try {
            postGraph(graphJSON, styleJSON, isGraphPublic, null);
        } catch (Exception e1) {
            taskMonitor.setStatusMessage(MessageConfig.POST_GRAPH_TASK_STATUS_FAIL);
            JOptionPane.showMessageDialog(null, MessageConfig.POST_GRAPH_TASK_DIALOG_FAIL, 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        taskMonitor.setStatusMessage(MessageConfig.POST_GRAPH_TASK_STATUS_SUCCESS);
        JOptionPane.showMessageDialog(null, MessageConfig.POST_GRAPH_TASK_DIALOG_SUCCESS, 
                "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    //post the current network to GraphSpace
    private void postGraph(JSONObject graphJSON, JSONObject styleJSON, boolean isGraphPublic, ArrayList<String> tagsList) throws Exception{
        Server.INSTANCE.postGraph(graphJSON, styleJSON, isGraphPublic, tagsList);
    }
}