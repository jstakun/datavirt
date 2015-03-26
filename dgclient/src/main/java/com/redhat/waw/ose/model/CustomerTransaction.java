package com.redhat.waw.ose.model;

public class CustomerTransaction implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String transactionid;
	
	private String customerid;
	
	private double amount;
	
	private long transactionDate;
	
	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(long transactionDate) {
		this.transactionDate = transactionDate;
	}

}
