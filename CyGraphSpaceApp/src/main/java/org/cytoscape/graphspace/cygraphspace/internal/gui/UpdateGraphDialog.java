package org.cytoscape.graphspace.cygraphspace.internal.gui;

//importing swing components
import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.JButton;

import org.cytoscape.graphspace.cygraphspace.internal.singletons.CyObjectManager;
import org.cytoscape.graphspace.cygraphspace.internal.singletons.Server;
import org.cytoscape.graphspace.cygraphspace.internal.task.UpdateGraphTask;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;
import org.json.JSONObject;
import java.util.ArrayList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class defines the UI for the UpdateGraph dialog
 * @author rishabh
 *
 */
@SuppressWarnings("serial")
public class UpdateGraphDialog extends JDialog {
	
	//UI component variables
	private JLabel hostValueLabel;
	private JLabel usernameValueLabel;
	private JLabel usernameLabel;
	private JPanel buttonsPanel;
	private JButton updateGraphButton;
	private JButton cancelButton;
	private GroupLayout groupLayout;
	private JLabel graphNameValue;
	private JLabel graphNameLabel;
    private JLabel updateMessage;

    private CyGraphSpaceResultPanel resultPanel;
	
	public UpdateGraphDialog(String graphName, JSONObject graphJSON, JSONObject styleJSON, boolean isGraphPublic, ArrayList<String> tags,
	        CyGraphSpaceResultPanel resultPanel) {
	    super(CyObjectManager.INSTANCE.getApplicationFrame(), "Update the graph/network on GraphSpace", ModalityType.APPLICATION_MODAL);

	    this.resultPanel = resultPanel;

		JLabel hostLabel = new JLabel("Host:");
		hostValueLabel = new JLabel("www.graphspace.org");
		usernameValueLabel = new JLabel("Anonymous");
		usernameLabel = new JLabel("Username:");
		buttonsPanel = new JPanel();
		graphNameLabel = new JLabel("Graph name:");
		graphNameValue = new JLabel("");
        updateMessage = new JLabel("<html>" + "You have already uploaded a graph with this name." 
                + "<br>" + "Would you like to update it?" + "</html>"); 

		groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(hostLabel)
								.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(usernameValueLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(hostValueLabel, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)))
						.addComponent(buttonsPanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(graphNameLabel, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(graphNameValue, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(updateMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(hostValueLabel)
						.addComponent(hostLabel))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(usernameLabel)
						.addComponent(usernameValueLabel))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(graphNameValue))
						.addComponent(graphNameLabel))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(updateMessage)
							//.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
							.addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		
		updateGraphButton = new JButton("Update");
		updateGraphButton.setEnabled(true);
		updateGraphButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateActionPerformed(graphJSON, styleJSON, isGraphPublic);
			}
		});
		buttonsPanel.add(updateGraphButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelActionPerformed(e);
			}
		});
		buttonsPanel.add(cancelButton);
		getContentPane().setLayout(groupLayout);
		populateFields(graphName);
		pack();
	}
	
	//populate dialog with user values
	private void populateFields(String graphName){
		if (Server.INSTANCE.getUsername() != null){
			usernameValueLabel.setText(Server.INSTANCE.getUsername());
		}
		if (Server.INSTANCE.getHost() != null){
			hostValueLabel.setText(Server.INSTANCE.getHost());
		}
		graphNameValue.setText(graphName);
	}

	//called when update button clicked
	private void updateActionPerformed(JSONObject graphJSON, JSONObject styleJSON, boolean isPublic){
        this.dispose();
        SynchronousTaskManager<?> synTaskMan = CyObjectManager.INSTANCE.getSynchrounousTaskManager();
        UpdateGraphTask updateGraphTask = new UpdateGraphTask(graphJSON, styleJSON, isPublic);
        updateGraphTask.setResultPanelEventListener(resultPanel);
        synTaskMan.execute(new TaskIterator(updateGraphTask));
	}
	
	//close the dialog box on cancel clicked
	private void cancelActionPerformed(ActionEvent e){
		this.dispose();
	}
}
