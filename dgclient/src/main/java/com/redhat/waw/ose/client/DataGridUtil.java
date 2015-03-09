package com.redhat.waw.ose.client;

import java.util.Map;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

public class DataGridUtil {

	private static RemoteCache<String, Object> cache;
	
	private static RemoteCache<String, Object> getCache() {
		if (cache == null) {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.addServer()
			.host("localhost")
			.port(11222).port(11322);
			RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());
			
			//cacheManager.
			
			cache = cacheManager.getCache("default");
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
}
