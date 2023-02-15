package com.storehouse.com.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table
@Getter
@Setter

public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long storeId;
private String storeName;
private String storeocation;
@ManyToMany(mappedBy = "stores")
private List<Customer>customers;
@OneToMany(mappedBy = "store")
private List<DeliveryMan>deliveryMans;

@OneToOne(mappedBy = "storemanager")
private StoreManager storeManager;
@ManyToOne
private Admin admin;
}
