package com.storehouse.com.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeliveryDto {
	private Long deliverId;
	private Integer delivery_number;
	private String status;
	private String delivery_address;
	@JsonIgnore
	private UserDto user;
	@JsonIgnore
	private ProductDto product;
	@JsonIgnore
	private  DeliveryManDto deliveryMan;
	@JsonIgnore
	private OrdersDto orderdeliver;
	
}
