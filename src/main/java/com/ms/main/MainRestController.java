package com.ms.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.like.bo.LikeBO;
import com.ms.main.bo.MainBO;
import com.ms.main.domain.Card;
import com.ms.main.domain.Criteria;
import com.ms.main.domain.PageMaker;
import com.ms.product.bo.ProductBO;
import com.ms.user.bo.UserBO;
import com.ms.user.domain.User;

@RestController
public class MainRestController {
	
	@Autowired
	private MainBO mainBO;
	
	@Autowired
	private ProductBO productBO;
	
	@GetMapping("/get-card-list")
	public List<Card> getCardList(String userLoginId, String keyword, Integer page, boolean completed) {
		if (page == null) {
			page = 1;
		}
		
		int totalCount = productBO.getProductCount(null, false);

		Criteria cri = new Criteria();
		cri.setPage(page);
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		
		List<Card> cardList = mainBO.getCardByUserLoginIdOrKeyword(userLoginId, null, (int)page, cri, completed);
		
		return cardList;
	}
	
}
