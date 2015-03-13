package com.redhat.waw.ose.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.MalformedURLException;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class ProtobufSchemaRegister {

	private static final String PROTOBUF_DEFINITION_RESOURCE = "/protony/customer.proto";
	
	public static void register(String serverHost, int serverJmxPort, String cacheContainerName) throws MalformedURLException, IOException, MalformedObjectNameException, InstanceNotFoundException, MBeanException, ReflectionException {
			String schemaFileName = "customer.proto";     // The name of the schema file
			String schemaFileContents = readResource(PROTOBUF_DEFINITION_RESOURCE); // The Protobuf schema file contents

			JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL("service:jmx:remoting-jmx://" + serverHost + ":" + serverJmxPort));
			MBeanServerConnection jmxConnection = jmxConnector.getMBeanServerConnection();

			ObjectName protobufMetadataManagerObjName = new ObjectName("jboss.infinispan:type=RemoteQuery,name=" + 
			ObjectName.quote(cacheContainerName) + ",component=ProtobufMetadataManager");

			jmxConnection.invoke(protobufMetadataManagerObjName, "registerProtofile", new Object[]{schemaFileName, schemaFileContents}, 
				                     new String[]{String.class.getName(), String.class.getName()});
			jmxConnector.close();
	}
	
	protected static String readResource(String resourcePath) throws IOException {
		InputStream is = DataGridUtil.class.getResourceAsStream(resourcePath);
		try {
			final Reader reader = new InputStreamReader(is, "UTF-8");
			StringWriter writer = new StringWriter();
			char[] buf = new char[1024];
			int len;
			while ((len = reader.read(buf)) != -1) {
				writer.write(buf, 0, len);
			}
			return writer.toString();
		} finally {
			is.close();
		}
	}
}
