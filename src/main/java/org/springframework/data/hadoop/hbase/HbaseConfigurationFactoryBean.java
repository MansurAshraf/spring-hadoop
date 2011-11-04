/*
 * Copyright 2011 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.hadoop.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.hadoop.configuration.ConfigurationFactoryBean;

/**
 * Factory for creating HBase specific configuration. By default also cleans up any connection associated with the current configuration.
 * 
 * 
 * @see HConnectionManager
 * @author Costin Leau
 */
public class HbaseConfigurationFactoryBean extends ConfigurationFactoryBean implements DisposableBean {

	private boolean deleteConnection = true;
	private boolean stopProxy = true;

	@Override
	protected void postProcessConfiguration(Configuration configuration) {
		HBaseConfiguration.addHbaseResources(configuration);
	}

	/**
	 * Indicates whether the potential connection created by this config is destroyed at shutdown (default).
	 * 
	 * @param deleteConnection The deleteConnection to set.
	 */
	public void setDeleteConnection(boolean deleteConnection) {
		this.deleteConnection = deleteConnection;
	}


	/**
	 * Indicates whether, when/if the associated connection is destroyed, whether the proxy is stopped or not. 
	 * 
	 * @param stopProxy The stopProxy to set.
	 */
	public void setStopProxy(boolean stopProxy) {
		this.stopProxy = stopProxy;
	}

	public void destroy() {
		if (deleteConnection) {
			HConnectionManager.deleteConnection(getObject(), stopProxy);
		}
	}
}