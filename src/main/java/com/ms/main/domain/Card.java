package com.ms.main.domain;

import com.ms.product.domain.Product;
import com.ms.user.domain.User;

import lombok.Data;

@Data
public class Card {
	private Product product;
	private User user;
	private int likeCount;
	private int recommendCount;
	private String liked;
}
