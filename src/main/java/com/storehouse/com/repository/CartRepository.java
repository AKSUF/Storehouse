package com.storehouse.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storehouse.com.entity.Account;
import com.storehouse.com.entity.Cart;
import com.storehouse.com.entity.User;

public interface CartRepository extends JpaRepository<Cart,Long> {




	

}
