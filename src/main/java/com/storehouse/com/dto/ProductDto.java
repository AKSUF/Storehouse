package com.storehouse.com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductDto {
	private Long productId;
	private String productName;
	private int price;
	private ProducerDto producer;
	private StoreDto store;
}
