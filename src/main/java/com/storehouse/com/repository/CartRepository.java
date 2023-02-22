package com.storehouse.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storehouse.com.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {

}
