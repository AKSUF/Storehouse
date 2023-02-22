package com.storehouse.com.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private String image;
	private ProducerDto producer;
	private StoreDto store;
	private String category;
	private UserDto user;
	private List<DeliveryDto> delivery;
	private Set<CommentDto> comments = new HashSet<>();
}
