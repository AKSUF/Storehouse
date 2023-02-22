package com.storehouse.com.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table
@Getter
@Setter

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long productId;
	private String productName;
	private int price;
	private String image;
	private String status;
	
	private String productdesc;
	private String category;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "producerId", referencedColumnName = "producerId")
	private Producer producer;
	
	@ManyToOne
	@JoinColumn(name="storeId")
	private Store store;
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "product")
	private List<Delivery> delivery;
	
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
	
}
