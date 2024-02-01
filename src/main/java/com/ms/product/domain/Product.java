package com.ms.product.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Product {
	private int id;
	private int ownerId;
	private String name;
	private String company;
	private int price;
	private String imagePath;
	private String description;
	private String boughtDate;
	private boolean completed;
	private Date createdAt;
}
