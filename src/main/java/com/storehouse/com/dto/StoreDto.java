package com.storehouse.com.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StoreDto {
	private Long storeId;
	private String storeName;
	private String storeocation;
	private List<CustomerDto>customers;
	private List<DeliveryManDto>deliveryMans;
	private StoreManagerDto storeManager;
	private AdminDto admin;
}
