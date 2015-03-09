package com.redhat.waw.ose.client;

import com.redhat.waw.ose.model.Customer;

public class SimpleDataLoader {

	public static void loadData(int first, int limit) {
		
		System.out.println("Starting the Hotrod Client\n");

			
		for (int i = first; i< first+limit ;i++) {
			Customer c = new Customer();
			c.setCustomerid("cst" + i);
			c.setFirstname("John" + i);
			DataGridUtil.put("cst" + i, c);		
		}
		
		System.out.println("Loaded " + limit + " records into the Data Grid\n");
		
		DataGridUtil.getStats();
	}
	
	public static void main(String[] args) {
		loadData(11, 1);
	}
	
}
