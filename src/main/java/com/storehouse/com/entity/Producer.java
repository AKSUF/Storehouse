package com.storehouse.com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter

public class Producer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long producerId;
private String producerName;
private String producerLocation;
}
