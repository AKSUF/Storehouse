package com.storehouse.com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.storehouse.com.dto.StoreDto;

public interface StoresService {

	StoreDto createStore(@Valid StoreDto storeDto, String jwtFromRequest);

	StoreDto updateStore(@Valid StoreDto storeDto, Long storeId, HttpServletRequest request);

	void deleteStore(Long storeId, String token);

	List<StoreDto> getAllCatagory(String token);

	StoreDto getStore(Long storeId, String token);

	StoreDto getStoreById(Long storeId);

	StoreDto updateImage(StoreDto storeDto, String jwtFromRequest, Long storeId);

	

	
}
