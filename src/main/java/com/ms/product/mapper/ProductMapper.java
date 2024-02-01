package com.ms.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ms.product.domain.Product;

@Mapper
public interface ProductMapper {
	
	public List<Product> selectProductList();
	public List<Product> selectProductListByKeyword(String keyword);
	
}
