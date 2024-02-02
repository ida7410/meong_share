package com.ms.product.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ms.common.FileManagerService;
import com.ms.product.domain.Product;
import com.ms.product.mapper.ProductMapper;

@Service
public class ProductBO {
	
	@Autowired
	private ProductMapper productMapper;
    
    @Autowired
    private FileManagerService fileManagerService;

	public int addProduct(int ownerId, String ownerLoginId, String name, String company, int price,
							MultipartFile productImageFile, String description, String boughtDate) {
		
        String imagePath = fileManagerService.saveFile(ownerLoginId, productImageFile);
        int insertedProductId = productMapper.insertProduct(ownerId, name, company, price, imagePath, description, boughtDate);
        return insertedProductId;
	}

	public Product getProductById(int id) {
		Product product = productMapper.selectProductById(id);
		return product;
	}
	
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
