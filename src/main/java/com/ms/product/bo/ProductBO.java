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
	
	public int getProductCount(String keyword, boolean completed) {
		return productMapper.selectProductCount(keyword, completed);
	}

	public Product getProductById(int id) {
		Product product = productMapper.selectProductById(id);
		return product;
	}
	
	public List<Product> getProductListByOwnerIdOrKeyword(Integer ownerId, String keyword,  int skip, Integer limit, boolean completed) {
		return productMapper.selectProductListByOwnerIdOrKeyword(ownerId, keyword, skip, limit, completed);
	}
	
	public List<Product> getLatestThreeProductList() {
		List<Product> productList = productMapper.selectLatestThreeProductList();
		return productList;
	}
	
	public List<Product> getThreeRandomProductList() {
		int totalProductCount = productMapper.selectProductCount(null, false);
		if (totalProductCount == 0) {
			return null;
		}
		
		List<Product> incompleteproductList = productMapper.selectProductListByCompleted(false);
		
		List<Product> productList = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) {
			int randomNumber = (int) (Math.random() * totalProductCount);
			
			Product product = incompleteproductList.get(randomNumber);
			productList.add(product);
		}
		
		return productList;
	}
	
	public void updateProductCopmletedByProductId(int productId, int buyerId) {
		productMapper.updateProductCopmletedByProductId(productId, buyerId);
	}
	
}
