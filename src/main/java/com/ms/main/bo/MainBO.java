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
	
	private static final int CARD_LIMIT = 8;
	
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
	
	public List<Card> getCardByUserLoginId(String userLoginId, int page, int prevProductId) {
		List<Card> cardList = new ArrayList<>();
		
		User user = userBO.getUserByLoginId(userLoginId);
		int userId = user.getId();
		List<Product> userProductList = productBO.getProductListByOwnerId(userId);
		
		for (Product product : userProductList) {
			Card card = new Card();
			
			card.setProduct(product);
			card.setUser(user);
			
			cardList.add(card);
		}
		
		Collections.reverse(cardList);
		
		return cardList;
	}
	
	public List<Card> getCardByKeyword(String keyword, int page, Criteria cri) {
		List<Card> cardList = new ArrayList<>();
		
		int skip = (page - 1) * cri.getPerPageNum();
		
		List<Product> productList = productBO.getProductList(keyword, skip, cri.getPerPageNum());
		
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
	
	
	public List<Card> getCardByUserLoginIdOrKeyword(String userLoginId, String keyword, int page, Criteria cri) {
		Integer userId = null;
		if (userLoginId != null) {
			userId = userBO.getUserByLoginId(userLoginId).getId();
		}
		
		List<Card> cardList = new ArrayList<>();
		
		int skip = (page - 1) * cri.getPerPageNum();
		
		
		List<Product> productList = productBO.getProductListByOwnerIdOrKeyword(userId, keyword, skip, cri.getPerPageNum());
		
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
