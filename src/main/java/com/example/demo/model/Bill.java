package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "bill")
@Inheritance(strategy = InheritanceType.JOINED)
public class Bill {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "date")
	private Date date;
	
	@Transient
	private long total;
	
	@OneToMany(mappedBy = "bill")
	private List<Ticket> tickets; 
	
	@ManyToOne
	@JoinColumn(name = "customerid")
	private Customer customer;
	
	@OneToMany(mappedBy = "bill")
	private List<BillService> billservice;
	
	@ManyToMany
	@JoinTable(name = "bill_gift",
			joinColumns = @JoinColumn(name="billid",referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name="giftid", referencedColumnName = "id"))
	private List<Gift> gifts;
	
}
