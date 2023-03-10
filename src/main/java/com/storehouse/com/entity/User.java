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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;
	private String name;
	private String detail;
	private String phone_number;
	private String gender;
	private String profile_image;
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", referencedColumnName = "account_id")
	private Account account;
	@OneToOne
	@JoinColumn(name="producerId")
	private Producer producer;
	@OneToOne
	@JoinColumn(name="customerId")
	private Customer customer;
	@OneToOne(mappedBy = "userdelivryman")
	private DeliveryMan deliveryMan;
	
	@OneToOne(mappedBy = "userstoremanager")
	private StoreManager storeManager;
	@OneToOne(mappedBy = "adminuser")
	private Admin admin;
	
}
