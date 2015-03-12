package com.redhat.waw.ose.client;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.redhat.waw.ose.model.Customer;

public class HibernateDataLoader {
	
	public static void main(String[] args) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		  
		Query q = session.createQuery("From Customer ");
        
        List<Customer> resultList = q.list();
        System.out.println("num of customers:" + resultList.size());
        for (Customer next : resultList) {
            System.out.println("Loading customer: " + next.getCustomerid() + " " +next.getFirstname() + " " + next.getLastname());
            DataGridUtil.put(next.getCustomerid(), next);
            System.out.println("Customer loaded to cache");
        }
        DataGridUtil.getStats();
	}
}
