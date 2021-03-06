/*
 * Copyright (c) 2014, the Cytoscape Consortium and the Regents of the University of California
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.cytoscape.graphspace.cygraphspace.internal.singletons;

import java.io.File;
import java.util.Properties;

import javax.swing.JFrame;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.CyVersion;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.group.CyGroupManager;
import org.cytoscape.io.util.StreamUtil;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.property.CyProperty;
import org.cytoscape.task.read.LoadNetworkFileTaskFactory;
import org.cytoscape.task.read.LoadVizmapFileTaskFactory;
import org.cytoscape.task.write.ExportNetworkTaskFactory;
import org.cytoscape.task.write.ExportNetworkViewTaskFactory;
import org.cytoscape.task.write.ExportVizmapTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.presentation.RenderingEngineManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.swing.DialogTaskManager;

//utility variables and methods to maintain Cytoscape singleton properties
public enum CyObjectManager {
	
    INSTANCE;

    private File configDir;
    public CySwingAppAdapter adapter;
    private CyApplicationManager applicationManager;
    private CyNetworkTableManager networkTableManager;
	private LoadNetworkFileTaskFactory loadNetworkFileTaskFactory;
	private ExportNetworkViewTaskFactory exportNetworkViewTaskFactory;
	private ExportNetworkTaskFactory exportNetworkTaskFactory;
	private CyTableManager tableManager;
	private CySwingApplication desktop;
	private LoadVizmapFileTaskFactory loadVizmapFileTaskFactory;
	private ExportVizmapTaskFactory exportVizmapTaskFactory;
    private CyNetworkFactory cyNetworkFactory;
	private CyNetworkManager cyNetworkManager;
	private CyRootNetworkManager cyRootNetworkManager;
	private CyProperty<Properties> cyProperties;

	public void setCyApplicationManager(CyApplicationManager applicationManager) {
	    this.applicationManager = applicationManager;
	}

	public void setCyProperties(CyProperty<Properties> cyProperties) {
		this.cyProperties = cyProperties;
	}

	public CyProperty<Properties> getCyProperties(){
		return this.cyProperties;
	}
	
	public void setCyNetworkFactory(CyNetworkFactory cyNetworkFactory) {
		this.cyNetworkFactory = cyNetworkFactory;
	}
	
	public CyNetworkFactory getCyNetworkFactory() {
		return this.cyNetworkFactory;
	}
	
	public void setCyNetworkManager(CyNetworkManager cyNetworkManager) {
		this.cyNetworkManager = cyNetworkManager;
	}
	
	public CyNetworkManager getCyNetworkManager() {
		return this.cyNetworkManager;
	}
	
	public void setCyRootNetworkManager(CyRootNetworkManager cyRootNetworkManager) {
		this.cyRootNetworkManager = cyRootNetworkManager;
	}
	
	public CyRootNetworkManager getRootCyNetworkManager() {
		return this.cyRootNetworkManager;
	}
	
    public File getConfigDir(){
        return configDir;
    }
    
    public void setConfigDir(File configDir){
        this.configDir = configDir;
    }
    
    public void setCySwingAppAdapter(CySwingAppAdapter appAdapter){
        this.adapter = appAdapter;
    }
    
    public void setLoadNetworkFileTaskFactory(LoadNetworkFileTaskFactory loadNetworkFileTaskFactory){
    	this.loadNetworkFileTaskFactory = loadNetworkFileTaskFactory;
    }
    
    public void setExportNetworkTaskFactory(ExportNetworkTaskFactory exportNetworkTaskFactory){
    	this.exportNetworkTaskFactory = exportNetworkTaskFactory;
    }

    public void setExportNetworkViewTaskFactory(ExportNetworkViewTaskFactory exportNetworkViewTaskFactory){
        this.exportNetworkViewTaskFactory = exportNetworkViewTaskFactory;
    }

    public void setExportVizmapTaskFactory(ExportVizmapTaskFactory exportVizmapTaskFactory){
    	this.exportVizmapTaskFactory = exportVizmapTaskFactory;
    }
    
    public void setLoadVizmapTaskFactory(LoadVizmapFileTaskFactory loadVizmapFileTaskFactory){
    	this.loadVizmapFileTaskFactory = loadVizmapFileTaskFactory;
    }

    public CyVersion getCyVersion(){
    	return adapter.getCyVersion();
    }
    
    public StreamUtil getStreamUtil(){
    	return adapter.getStreamUtil();
    }
    
    public CyRootNetworkManager getRootNetworkManager(){
    	return adapter.getCyRootNetworkManager();
    }
    
    public CyApplicationManager getApplicationManager(){
    	return this.applicationManager;
    }
    
    public CyNetworkFactory getNetworkFactory(){
        return adapter.getCyNetworkFactory();
    }
    
    public CyNetworkManager getNetworkManager(){
        return adapter.getCyNetworkManager();
    }
    
    public CyNetworkViewFactory getNetworkViewFactory(){
        return adapter.getCyNetworkViewFactory();
    }
    
    public CyNetworkViewManager getNetworkViewManager(){
        return adapter.getCyNetworkViewManager();
    }

    public LoadNetworkFileTaskFactory getLoadNetworkFileTaskFactory(){
    	return this.loadNetworkFileTaskFactory;
    }
    
    public LoadVizmapFileTaskFactory getLoadVizmapFileTaskFactory(){
    	return this.loadVizmapFileTaskFactory;
    }
    
    public ExportVizmapTaskFactory getExportVizmapTaskFactory(){
    	return this.exportVizmapTaskFactory;
    }
    
    public ExportNetworkTaskFactory getExportNetworkTaskFactory(){
    	return this.exportNetworkTaskFactory;
    }

    public ExportNetworkViewTaskFactory getExportNetworkViewTaskFactory(){
        return this.exportNetworkViewTaskFactory;
    }

    public VisualLexicon getDefaultVisualLexicon(){
    	return adapter.getRenderingEngineManager().getDefaultVisualLexicon();
    }
    public JFrame getApplicationFrame(){
    	return adapter.getCySwingApplication().getJFrame();
    }
    
    public DialogTaskManager getTaskManager(){
    	return this.adapter.getDialogTaskManager();
    }

    public SynchronousTaskManager<?> getSynchrounousTaskManager() {
        return this.adapter.getCyServiceRegistrar().getService(SynchronousTaskManager.class);
    }

    public VisualMappingManager getVisualMappingManager() {
    	return adapter.getVisualMappingManager();
    }
    
    public CyGroupManager getCyGroupManager(){
    	return adapter.getCyGroupManager();
    }
    
    public VisualStyleFactory getVisualStyleFactory(){
    	return adapter.getVisualStyleFactory();
    }
    
    public VisualMappingFunctionFactory getVisualMappingFunctionContinuousFactory() {
    	return adapter.getVisualMappingFunctionContinuousFactory();
    }
    
    public VisualMappingFunctionFactory getVisualMappingFunctionDiscreteFactory(){
    	return adapter.getVisualMappingFunctionDiscreteFactory();
    }
    
    public VisualMappingFunctionFactory getVisualMappingFunctionPassthroughFactory(){
    	return adapter.getVisualMappingFunctionPassthroughFactory();
    }
    
    public RenderingEngineManager getRenderingEngineManager(){
    	return adapter.getRenderingEngineManager();
    }

	public void setNetworkTableManager(CyNetworkTableManager networkTableManager){
		this.networkTableManager = networkTableManager;
	}
	
	public void setTableManager(CyTableManager tableManager){
		this.tableManager = tableManager;
	}
	
    public CyNetworkTableManager getNetworkTableManager(){
    	return networkTableManager;
    }
    
    public CyTableManager getTableManager(){
    	return this.tableManager;
    }
    
    public CyNetwork getCurrentNetwork(){
        return applicationManager == null ? null : applicationManager.getCurrentNetwork();
    }

    public CyNetworkView getCurrentNetworkView(){
        return applicationManager == null ? null : applicationManager.getCurrentNetworkView();
    }
    
    public void setCySwingApplition(CySwingApplication desktop){
    	this.desktop = desktop;
    }
    
    public CySwingApplication getCySwingApplition(){
    	return this.desktop;
    }
}
