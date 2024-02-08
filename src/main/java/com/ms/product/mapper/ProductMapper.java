package com.ms.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.product.domain.Product;

@Mapper
public interface ProductMapper {
	
    public int insertProductByMap(Product product);
    
    public int selectProductCount();
    
    public Product selectProductById(int id);
    
    public List<Product> selectProductListByOwnerId(int ownerId);
    
	public List<Product> selectProductList(
			@Param("skip") int skip, 
			@Param("limit") int limit);
	
	public List<Product> selectProductListByKeyword(
			@Param("keyword") String keyword,
			@Param("skip") int skip, 
			@Param("limit") int limit);
	
	public List<Product> selectLatestThreeProductList();
	
}
