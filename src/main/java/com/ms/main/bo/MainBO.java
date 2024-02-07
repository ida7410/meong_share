package com.ms.main.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.main.domain.Card;
import com.ms.product.bo.ProductBO;
import com.ms.product.domain.Product;
import com.ms.user.bo.UserBO;
import com.ms.user.domain.User;

@Service
public class MainBO {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private UserBO userBO;
	
	public Card getCardByProductId(int productId) {
		Product product = productBO.getProductById(productId);
		User user = userBO.getUserById(product.getOwnerId());
		
		Card card = new Card();
		card.setProduct(product);
		card.setUser(user);
		
		return card;
	}
	
	public List<Card> getCardByKeyword(String keyword) {
		List<Card> cardList = new ArrayList<>();
		List<Product> productList = productBO.getProductList(keyword);
		
		for (Product product : productList) {
			Card card = new Card();
			
			User user = userBO.getUserById(product.getOwnerId());
			
			card.setProduct(product);
			card.setUser(user);
			
			cardList.add(card);
		}
		
		Collections.reverse(cardList);
		
		return cardList;
	}
	
}
