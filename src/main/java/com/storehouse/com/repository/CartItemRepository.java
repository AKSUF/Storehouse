package com.storehouse.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storehouse.com.entity.Cart;
import com.storehouse.com.entity.CartItem;
import com.storehouse.com.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{

	CartItem findByCartAndProduct(Cart cart, Product product);

}
