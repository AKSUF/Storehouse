package com.storehouse.com.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;

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
@OneToOne
@JoinColumn(name="user_id")
@JsonIgnore
private User user;

@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "producer")
private List<Product>products;

}
