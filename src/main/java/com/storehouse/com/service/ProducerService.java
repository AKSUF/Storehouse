package com.storehouse.com.service;

import java.util.List;

import javax.validation.Valid;

import com.storehouse.com.dto.ProductDto;
import com.storehouse.com.entity.Product;

public interface ProducerService {

	Product addProduct(@Valid ProductDto productDto, String jwtFromRequest);

	ProductDto updateaddedProduct(@Valid ProductDto productDto, String jwtFromRequest, Long productId);

	ProductDto addnewProduct(ProductDto productDto, Long userId, Long storeId, String token);

	ProductDto getProductById(Long productId);

	List<ProductDto> getProductByUser(Long userId, String jwtFromRequest);

	List<ProductDto> getProductAsStore(Long storeId, String token);

	

}
