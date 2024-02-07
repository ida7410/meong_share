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
		
		
		Integer ownerId = (Integer)session.getAttribute("userId");
		String ownerLoginId = (String)session.getAttribute("userLoginId");
		
		if (ownerId == null) {
			result.put("code", 300);
			result.put("error_message", "세션이 만료되었습니다. 다시 로그인해주세요.");
			return result;
		}
		
		int insertedProductId = productBO.addProduct(ownerId, ownerLoginId, name, company, price, 
													productImageFile, description, boughtDate);
		
		result.put("code", 200);
		result.put("result", "success");
		result.put("insertedProductId", insertedProductId);
		
		return result;
	}
	
}
