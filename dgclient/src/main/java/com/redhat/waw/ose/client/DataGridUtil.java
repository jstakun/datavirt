package com.redhat.waw.ose.client;

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
			.port(11322);
			RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());
			cache = cacheManager.getCache("default");
		}
		return cache;
	}
	
	public static void put(String key, Object value) {
		getCache().put(key, value);
	}
}
