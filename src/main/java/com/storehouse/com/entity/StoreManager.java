package com.storehouse.com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class StoreManager {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storemanagerId;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User userstoremanager;
	@OneToOne
	@JoinColumn(name = "storeId")
	private Store storemanager;

}
