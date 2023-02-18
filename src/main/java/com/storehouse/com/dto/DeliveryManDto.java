package com.storehouse.com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeliveryManDto {
	private Long deliverymanId;
	private UserDto userdelivryman;
	private StoreDto store;
}
