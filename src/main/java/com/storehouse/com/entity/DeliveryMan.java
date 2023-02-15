package com.storehouse.com.entity;

import javax.persistence.Entity;
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
@Table

public class DeliveryMan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deliverymanId;
	@OneToOne
	@JoinColumn(name="user_id")
	private User userdelivryman;
	@ManyToOne
	@JoinColumn(name="storeId")
	private Store store;
	
	
}
