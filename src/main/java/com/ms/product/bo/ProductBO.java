package com.ms.product.bo;

import java.util.ArrayList;
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
        
        Product product = new Product();
        product.setOwnerId(ownerId);
        product.setName(name);
        product.setCompany(company);
        product.setPrice(price);
		product.setImagePath(imagePath);
		product.setDescription(description);
		product.setBoughtDate(boughtDate);
        
        productMapper.insertProductByMap(product);
        int insertedProductId = product.getId();
        return insertedProductId;
	}
	
	public int getProductCount(String keyword) {
		return productMapper.selectProductCount(keyword);
	}

	public Product getProductById(int id) {
		Product product = productMapper.selectProductById(id);
		return product;
	}

	public List<Product> getProductListByOwnerId(int ownerId) {
		return productMapper.selectProductListByOwnerId(ownerId);
	}
	
	public List<Product> getProductList(String keyword, int skip, int limit) {
		List<Product> productList = null;
		if (keyword != null) {
			productList = productMapper.selectProductListByKeyword(keyword, skip, limit);
		}
		else {			
			productList = productMapper.selectProductList(skip, limit);
		}
		return productList;
	}
	
	public List<Product> getProductListByOwnerIdOrKeyword(Integer ownerId, String keyword,  int skip, int limit) {
		return productMapper.selectProductListByOwnerIdOrKeyword(ownerId, keyword, skip, limit);
	}
	
	public List<Product> getLatestThreeProductList() {
		List<Product> productList = productMapper.selectLatestThreeProductList();
		return productList;
	}
	
	public List<Product> getThreeRandomProductList() {
		int totalProductCount = productMapper.selectProductCount(null);
		List<Product> productList = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) {
			int randomNumber = (int) (Math.random() * totalProductCount + 1);
			
			Product product = productMapper.selectProductById(randomNumber);
			productList.add(product);
		}
		
		return productList;
	}
	
	
	
}
