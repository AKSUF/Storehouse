package com.storehouse.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storehouse.com.entity.Store;

public interface StoreRepository extends JpaRepository<Store,Long> {

}
