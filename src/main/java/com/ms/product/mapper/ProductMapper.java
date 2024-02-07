package com.ms.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ms.product.domain.Product;

@Mapper
public interface ProductMapper {
	
    public int insertProductByMap(Product product);
    
    public int selectProductCount();
    public Product selectProductById(int id);
	public List<Product> selectProductList();
	public List<Product> selectProductListByKeyword(String keyword);
	public List<Product> selectLatestThreeProductList();
	
}
