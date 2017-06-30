package org.cytoscape.graphspace.cygraphspace.internal.gui;

import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.commons.io.IOUtils;
import org.cytoscape.graphspace.cygraphspace.internal.singletons.CyObjectManager;
import org.cytoscape.graphspace.cygraphspace.internal.singletons.Server;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.TaskIterator;
import org.json.JSONObject;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.awt.event.ActionEvent;

public class PostGraphDialog extends JDialog {
	private JTextField hostField;
	private JTextField usernameField;
	private JTextField graphNameField;
	private JLabel usernameLabel;
	private JLabel graphNameLabel;
	private JPanel buttonsPanel;
	private JButton postGraphButton;
	private JButton cancelButton;
	public PostGraphDialog(Frame parent) {
		this.setTitle("Export Graphs to GraphSpace");
		
		JLabel hostLabel = new JLabel("Host");
		
		hostField = new JTextField();
		hostField.setColumns(10);
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		
		graphNameField = new JTextField();
		graphNameField.setColumns(10);
		
		usernameLabel = new JLabel("Username");
		
		graphNameLabel = new JLabel("Graph Name");
		
		buttonsPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(hostLabel)
								.addComponent(usernameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(hostField, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
								.addComponent(usernameField)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(graphNameLabel)
							.addGap(6)
							.addComponent(graphNameField))
						.addComponent(buttonsPanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(hostField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(hostLabel))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(usernameLabel))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(graphNameLabel)
						.addComponent(graphNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		
		postGraphButton = new JButton("Export");
		postGraphButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportActionPerformed(e);
			}
		});
		buttonsPanel.add(postGraphButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelActionPerformed(e);
			}
		});
		buttonsPanel.add(cancelButton);
		getContentPane().setLayout(groupLayout);
		populateFields();
		pack();
	}
	
	private void populateFields(){
		if (Server.INSTANCE.getUsername() != null){
			usernameField.setText(Server.INSTANCE.getUsername());
		}
		if (Server.INSTANCE.getHost() != null){
			hostField.setText(Server.INSTANCE.getHost());
		}
	}
	
	private void exportActionPerformed(ActionEvent evt){
		try{
			File tempFile = File.createTempFile("CyGraphSpace", ".cyjs");
			CyNetwork network = CyObjectManager.INSTANCE.getApplicationManager().getCurrentNetwork();
			TaskIterator ti = CyObjectManager.INSTANCE.getExportNetworkTaskFactory().createTaskIterator(network, tempFile);
			CyObjectManager.INSTANCE.getTaskManager().execute(ti);
			InputStream is = new FileInputStream(tempFile);
            String graphJSONString = IOUtils.toString(is);
            JSONObject graphJSON = new JSONObject(graphJSONString);   
			Server.INSTANCE.postGraph(graphJSON);
			this.dispose();
		}
		catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog((Component)evt.getSource(), "Could not post graph", "Error", JOptionPane.ERROR_MESSAGE);
			this.dispose();
		}
	}
	
	private void cancelActionPerformed(ActionEvent e){
		this.dispose();
	}
	
}