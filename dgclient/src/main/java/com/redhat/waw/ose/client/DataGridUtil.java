package com.redhat.waw.ose.client;

import java.util.List;
import java.util.Map;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import com.redhat.waw.ose.model.Customer;
import com.redhat.waw.ose.model.CustomerMarshaller;
import com.redhat.waw.ose.model.CustomerTransaction;
import com.redhat.waw.ose.model.CustomerTransactionMarshaller;

public class DataGridUtil {

	private static String host = "localhost";
	private static int portOffset = 100;
	//private static String containerName = "clustered";
	private static String cacheName = "default";
	
	private static final String PROTOBUF_DEFINITION_RESOURCE = "/protony/customer.proto";
	
	private static RemoteCache<String, Object> cache;
	
	
	private static RemoteCache<String, Object> getCache() {
		if (cache == null) {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.addServer()
			.host(host)
			.port(11222).port(11222 + portOffset)
			.marshaller(new ProtoStreamMarshaller());
			RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());
			
			try {
				SerializationContext ctx = ProtoStreamMarshaller.getSerializationContext(cacheManager);
				ctx.registerProtoFiles(FileDescriptorSource.fromResources(PROTOBUF_DEFINITION_RESOURCE));
				ctx.registerMarshaller(new CustomerMarshaller());
				ctx.registerMarshaller(new CustomerTransactionMarshaller());				
				
				RemoteCache<String, String> metadataCache = cacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
				metadataCache.put(PROTOBUF_DEFINITION_RESOURCE, ProtobufSchemaRegister.readResource(PROTOBUF_DEFINITION_RESOURCE));
				String errors = metadataCache.get(ProtobufMetadataManagerConstants.ERRORS_KEY_SUFFIX);
				if (errors != null) {
					throw new IllegalStateException("Some Protobuf schema files contain errors:\n" + errors);
				}
			} catch (Exception e) {
    			e.printStackTrace();
    		}
			
			cache = cacheManager.getCache(cacheName);
		}
		return cache;
	}
	
	public static void put(String key, Object value) {
		getCache().put(key, value);
	}
	
	public static void getStats() {
		Map<String, String> stats = getCache().stats().getStatsMap();
		for (Map.Entry<String, String> stat : stats.entrySet()) {
			System.out.println(stat.getKey() + ": " + stat.getValue());
		}
	}
	
	public static void testSearch() {
		QueryFactory<Query> qf = Search.getQueryFactory(getCache());
		
		Query customerQuery = qf.from(Customer.class)
		.having("customerid").like("CST01010").toBuilder()
				.build();
		List<Customer> results = customerQuery.list();
		System.out.println("Found " + results.size() + " customers");
		
		Query transactionQuery = qf.from(CustomerTransaction.class)
		.having("customerid").like("CST01010").toBuilder()
				.build();
		List<CustomerTransaction> transactions = transactionQuery.list();
		System.out.println("Found " + transactions.size() + " transactions");
	}
	
	/*public void execDistTask(DistributedCallable task) {
		DistributedExecutorService des = new DefaultExecutorService(getCache());
	    long start = System.currentTimeMillis();
	    List<Future<Long>> results = des.submitEverywhere(task);
	    int count = 0;
	    for (Future<Long> f : results) {
	    	System.out.println("Node size: " + f.get());
	        count += f.get();
	    }
	    System.out.println("Cache size: " + count);
	}*/
}
