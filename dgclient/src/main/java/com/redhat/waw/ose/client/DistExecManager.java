package com.redhat.waw.ose.client;

import java.io.Serializable;
import java.util.Set;

import org.infinispan.Cache;
import org.infinispan.distexec.DistributedCallable;

public class DistExecManager {

	public static void main(String[] args) {
		// TODO not supported yet with HotRod
	}
	
	private class Counter implements DistributedCallable, Serializable {

		private transient Cache<String, Long> cache;
		
		@Override
		public Object call() throws Exception {
			Set<String> keys = cache.keySet();
			return keys.size();
		}

		@Override
		public void setEnvironment(Cache cache, Set set) {
			this.cache = cache;			
		}
		
	}

}
