package com.ms.product.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.product.domain.Product;
import com.ms.product.mapper.ProductMapper;

@Service
public class ProductBO {
	
	@Autowired
	private ProductMapper productMapper;
	
	public List<Product> getProductList(String keyword) {
		List<Product> productList = null;
		if (keyword != null) {
			productList = productMapper.selectProductListByKeyword(keyword);
			
		}
		else {			
			productList = productMapper.selectProductList();
		}
		return productList;
	}
	
}
