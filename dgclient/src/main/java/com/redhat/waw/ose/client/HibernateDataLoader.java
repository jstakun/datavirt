package com.redhat.waw.ose.client;

import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;

import com.redhat.waw.ose.model.Customer;
import com.redhat.waw.ose.model.CustomerTransaction;

public class HibernateDataLoader {
	
	private static final int TRANSACTION_COUNT = 10;
	
	public static void main(String[] args) {
			
		Session session = HibernateUtil.getSessionFactory().openSession();
		  
		Query q = session.createQuery("From Customer ");
		
        List<Customer> resultList = q.list();
        System.out.println("num of customers:" + resultList.size());
        Random r = new Random();
        for (Customer next : resultList) {
            System.out.println("Loading customer: " + next.getCustomerid() + " " + next.getFirstname() + " " + next.getLastname());
            try {
            	//DataGridUtil.put(next.getCustomerid(), next);
            	//System.out.println("Customer loaded to cache");
            	
            	for (int i=0;i<TRANSACTION_COUNT;i++) {
            		CustomerTransaction t = new CustomerTransaction();
            		t.setTransactionDate(System.currentTimeMillis());
            		t.setTransactionid(next.getCustomerid() + t.getTransactionDate());
            		t.setCustomerid(next.getCustomerid());
            		t.setAmount(r.nextDouble() * 1000d);
            		DataGridUtil.put(t.getTransactionid(), t);
            		System.out.println("Transaction loaded to cache");
            	}
            } catch (Exception e) {
    			e.printStackTrace();
    		}
        }
        
        DataGridUtil.getStats();
        
        DataGridUtil.testSearch();       
	}
}
