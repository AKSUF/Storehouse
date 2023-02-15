package com.storehouse.com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@ManyToOne
	@JoinColumn(name="producerId")
	private Producer producer;
	@ManyToOne
	@JoinColumn(name="storeId")
	private Store store;
	
}
