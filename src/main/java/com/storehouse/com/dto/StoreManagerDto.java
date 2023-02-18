package com.storehouse.com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StoreManagerDto {
	private Long storemanagerId;
	private UserDto userstoremanager;
	private StoreDto storemanager;
}
