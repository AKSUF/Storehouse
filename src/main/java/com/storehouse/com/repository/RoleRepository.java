package com.storehouse.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storehouse.com.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

}
