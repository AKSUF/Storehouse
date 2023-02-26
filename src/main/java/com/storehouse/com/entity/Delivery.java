package com.storehouse.com.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter

public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deliverId;
	private Integer delivery_number;
	private String status;
	private String delivery_address;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	@JsonIgnore
	private User user;
	 private int quantity;
	private Double totalPrice;
	  private String paymentIntentId;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", referencedColumnName = "productId")
	private Product product;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "delivery")
	private  DeliveryMan deliveryMan;
	
	@OneToOne
	@JoinColumn(name="orderId")
	private Orders orderdeliver;
	
}
