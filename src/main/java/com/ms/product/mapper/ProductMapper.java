package com.ms.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.product.domain.Product;

@Mapper
public interface ProductMapper {
	
    public void insertProduct(
            @Param("ownerId") int ownerId, 
            @Param("name") String name, 
            @Param("company") String company, 
            @Param("price") int price,
            @Param("imagePath") String imagePath, 
            @Param("description") String description, 
            @Param("boughtDate") String boughtDate);
    
	public List<Product> selectProductList();
	public List<Product> selectProductListByKeyword(String keyword);
	
}
