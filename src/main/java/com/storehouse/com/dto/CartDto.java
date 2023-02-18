package com.storehouse.com.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CartDto {
	private long cartId;
	private List<CartItemDto>cartItems;
	private CustomerDto customer;
}
