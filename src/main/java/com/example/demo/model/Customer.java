package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "personid")
public class Customer extends Person {
	@Column(name = "address")
	private String address;
	
	@Column(name = "joineddate")
	private Date joinedDate;
	
	@OneToOne
	@JoinColumn(name = "membercardid")
	private MemberCard memberCard;
	
	@OneToMany(mappedBy = "customer")
	private List<Bill> bills;
	
	public Customer() {
		super();
	}

	public Customer(String name, Date dateofbirth, String phone, String email, String address, Date joinedDate,
			MemberCard memberCard) {
		super(name, dateofbirth, phone, email);
		this.address = address;
		this.joinedDate = joinedDate;
		this.memberCard = memberCard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public MemberCard getMemberCard() {
		return memberCard;
	}

	public void setMemberCard(MemberCard memberCard) {
		this.memberCard = memberCard;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	
	
	
}
