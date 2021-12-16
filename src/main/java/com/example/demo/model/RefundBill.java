package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "refundbill")
public class RefundBill extends Bill{
	private long fine;
	private String note;
	
	
	public RefundBill(long fine, String note) {
		super();
		this.fine = fine;
		this.note = note;
	}


	public RefundBill() {
		super();
	}


	public long getFine() {
		return fine;
	}


	public void setFine(long fine) {
		this.fine = fine;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}
	
	
}
