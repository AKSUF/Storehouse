package com.storehouse.com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CartItemDto {
	private Long cartitemId;
	private ProductDto product;
	private CartDto cart;
}
