/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 */

package com.skl.cassandra.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;

public class CassandraClient {
	
	//key to get the object from servlet context. Alternatively this class can be a spring bean as well
	public static final String CASSANDRA_DAO = "cassandra_dao";
	
	private static final Logger logger = Logger.getLogger(CassandraClient.class.getName());

	private String clusterNodes;
	private String schema;
	private String uid;
	private String pwd;
	
	private Cluster cluster;	//Thread safe - maintain one per app server
	private Session session;	//Thread safe - maintain one per KS

	
	public CassandraClient() {
		
	}
	
	public CassandraClient(String clusterNodes, String schema, String uid, String pwd) throws UnknownHostException {
		this.clusterNodes = clusterNodes;
		this.schema = schema;
		this.uid = uid;
		this.pwd = pwd;
		initialize();
	}	
	
	public void initialize() throws UnknownHostException {
		logger.info("Initializing CassandraClient with " + clusterNodes + " " + schema + " " + uid);
		List<InetAddress> contactPoints = new ArrayList<InetAddress>();
		String[] tokens = StringUtils.split(clusterNodes);
		for(String token : tokens) {
			contactPoints.add(InetAddress.getByName(token));
		}
		
		if(StringUtils.isNotEmpty(uid)) {
			cluster = Cluster.builder()
						.addContactPoints(contactPoints)
						//Retry if we get a read/write timeout; CL downgrade policy can be an issue
						.withRetryPolicy(DefaultRetryPolicy.INSTANCE)
						//Wait exponentially 5 min (1000*60*5) ms base; max 1hr; trying to connect to a dead node
						.withReconnectionPolicy(new ExponentialReconnectionPolicy(300000, 3600000))
						//Do round robin in one DC only, inefficient if all nodes are considered
						//.withLoadBalancingPolicy(new DCAwareRoundRobinPolicy("DC1"))
						//Required if your cluster has authentication enabled
						.withCredentials(uid, pwd)
						//Compress the payload as supported by the binary protocol (LZ4, SNAPPY or NONE)
						//.withCompression(Compression.LZ4)
						.build();
		} else {
			cluster = Cluster.builder()
					.addContactPoints(contactPoints)
					//Retry if we get a read/write timeout; CL downgrade policy can be an issue
					.withRetryPolicy(DefaultRetryPolicy.INSTANCE)
					//Wait exponentially 5 min (1000*60*5) ms base; max 1hr; trying to connect to a dead node
					.withReconnectionPolicy(new ExponentialReconnectionPolicy(300000, 3600000))
					//Do round robin in one DC only, inefficient if all nodes are considered
					//.withLoadBalancingPolicy(new DCAwareRoundRobinPolicy("DC1"))
					//Compress the payload as supported by the binary protocol (LZ4, SNAPPY or NONE)
					//.withCompression(Compression.LZ4)
					.build();
		}
		session = cluster.connect(schema);
		printMetadata();
	}
	
	
	public void printMetadata() {
		StringBuilder sb = new StringBuilder();
		Metadata metadata = cluster.getMetadata();
		sb.append("Cassandra client initialized: " + this + "\n");
		sb.append("Connected to cluster: " + metadata.getClusterName() + "\n");
		sb.append("Session object is: " + session + "\n");
		for (Host host : metadata.getAllHosts()) {
			sb.append("Datacenter: " + host.getDatacenter() + " Host: " + host.getAddress() + "; Rack: " + host.getRack() + "\n");
		}
		logger.info(sb.toString());
	}
	
	public void destroy() {
		logger.info("Cassandra cluster shutdown initiated ...");
		session.close();
		cluster.close();
		logger.info("Cassandra session and cluster closed");
		logger.info("If you get a message and exception regarding memory leak from tomcat, please see https://datastax-oss.atlassian.net/browse/JAVA-647");
	}	

	public Session getSession(){
		return session;
	}

	public String getClusterNodes() {
		return clusterNodes;
	}

	public void setClusterNodes(String clusterNodes) {
		this.clusterNodes = clusterNodes;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
