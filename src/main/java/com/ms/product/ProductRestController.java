package com.ms.product;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms.product.bo.ProductBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/product")
@RestController
public class ProductRestController {
	
	@Autowired
	private ProductBO productBO;
	
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("name") String name, 
			@RequestParam("company") String company, 
			@RequestParam("price") int price,
			@RequestParam("productImageFile") MultipartFile productImageFile, 
			@RequestParam("description") String description, 
			@RequestParam("boughtDate") String boughtDate,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		int ownerId = (int)session.getAttribute("id");
		String ownerLoginId = (String)session.getAttribute("loginId");
		
		productBO.addProduct(ownerId, ownerLoginId, name, company, price, productImageFile, description, boughtDate);
		
		return result;
	}
	
}
