package org.graphspace.javaclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphspace.javaclient.model.GSGraphMetaData;
import org.json.JSONArray;
import org.json.JSONObject;

public class CyGraphSpaceClient{
	
	String host;
	String username;
	String password;
	Client client;
	final String defaultHost = "www.graphspace.org";
	
	public CyGraphSpaceClient(String host, String username, String password){
		this.host = host;
		this.username = username;
		this.password = password;
		client = new Client();
		client.authenticate(this.host, this.username, this.password);
	}
	
	public CyGraphSpaceClient(String username, String password){
		this.host = this.defaultHost;
		this.username = username;
		this.password = password;
		client = new Client();
		client.authenticate(this.host, this.username, this.password);
	}
	
	public ArrayList<GSGraphMetaData> graphJSONListToMetaDataArray(JSONObject graphsJSONObject){
		ArrayList<GSGraphMetaData> graphMetaDataList = new ArrayList<GSGraphMetaData>();
		JSONArray graphsArray = graphsJSONObject.getJSONObject("body").getJSONArray("array").getJSONObject(0).getJSONArray("graphs");
		for (Object graphObject : graphsArray){
			JSONObject graph = (JSONObject) graphObject;
			String name = graph.getString("name");
			int id = graph.getInt("id");
			String ownedBy = graph.getString("owner_email");
			int access = graph.getInt("is_public");
			GSGraphMetaData graphMetaData = new GSGraphMetaData(name, id, ownedBy, access);
			graphMetaDataList.add(graphMetaData);
		}
		return graphMetaDataList;
	}
	
	public ArrayList<GSGraphMetaData> getGraphMetaDataList(boolean myGraphs, boolean publicGraphs, boolean sharedGraphs) throws Exception{
		
		ArrayList<GSGraphMetaData> graphList = new ArrayList<GSGraphMetaData>();
		
		if (myGraphs){
			int limit = 20;
			int offset = 0;
			graphList.addAll(graphJSONListToMetaDataArray(client.getMyGraphs(limit, offset)));
		}
		
		if (publicGraphs){
			int limit = 20;
			int offset = 0;
			graphList.addAll(graphJSONListToMetaDataArray(client.getPublicGraphs(limit, offset)));
		}
		
		//TODO: handle shared graphs
//		if (sharedGraphs){
//			
//		}
		
		return graphList;
	}
	
	public JSONObject getGraphById(String id) throws Exception{
		return client.getGraphById(id);
	}
	
	public JSONObject getGraphByName(String name) throws Exception{
		return client.getGraph(name);
	}
	
	public void postGraph(JSONObject graph) throws Exception{
		String str = client.postGraph(graph).toString();
		System.out.println(str);
	}
	
	public JSONObject updateGraph(String name, String ownerEmail, JSONObject graphJSON, boolean isGraphPublic) throws Exception{
		return client.updateGraph(name, ownerEmail, graphJSON, isGraphPublic);
	}
	
	public JSONObject updateGraph(String name, JSONObject graphJSON, boolean isGraphPublic) throws Exception{
		String ownerEmail = this.username;
		return client.updateGraph(name, ownerEmail, graphJSON, isGraphPublic);
	}
}