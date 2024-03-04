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
    
	// ------- CREATE -------
    
    /***
     * Create a product
     * @param ownerId
     * @param ownerLoginId
     * @param name
     * @param company
     * @param price
     * @param productImageFile
     * @param description
     * @param boughtDate
     * @return
     */
	public int addProduct(int ownerId, String ownerLoginId, String name, String company, int price,
							MultipartFile productImageFile, String description, String boughtDate) {
		
		// save file and get the path of image
        String imagePath = fileManagerService.saveFile(ownerLoginId, productImageFile);
        
        // make Product temporarily
        Product product = new Product();
        product.setOwnerId(ownerId);
        product.setName(name);
        product.setCompany(company);
        product.setPrice(price);
		product.setImagePath(imagePath);
		product.setDescription(description);
		product.setBoughtDate(boughtDate);
        
		// insert product
        productMapper.insertProductByMap(product);
        
        // get the primary key of inserted product
        int insertedProductId = product.getId();
        
        return insertedProductId;
	}
	
	
	// ------- READ -------
	
	/***
	 * Get the number of product where keyword is included and completed is matching
	 * @param keyword
	 * @param completed
	 * @return
	 */
	public int getProductCountByKeywordCompleted(String keyword, boolean completed) {
		return productMapper.selectProductCountByKeywordCompleted(keyword, completed);
	}
	
	/***
	 * Get product by product id
	 * @param id
	 * @return
	 */
	public Product getProductById(int id) {
		Product product = productMapper.selectProductById(id);
		return product;
	}
	
	/***
	 * Get the list of product by owner id or keyword
	 * @param ownerId
	 * @param keyword
	 * @param skip
	 * @param limit
	 * @param completed
	 * @return
	 */
	public List<Product> getProductListByOwnerIdOrKeyword(Integer ownerId, String keyword,  int skip, Integer limit, boolean completed) {
		return productMapper.selectProductListByOwnerIdOrKeyword(ownerId, keyword, skip, limit, completed);
	}
	
	/***
	 * Get the list of three latest uploaded product
	 * @return
	 */
	public List<Product> getLatestThreeProductList() {
		List<Product> productList = productMapper.selectLatestThreeProductList();
		return productList;
	}
	
	/***
	 * Get three random product list
	 * @return
	 */
	public List<Product> getThreeRandomProductList() {
		
		// get the total number of product which is not traded yet
		int totalProductCount = productMapper.selectProductCountByKeywordCompleted(null, false);
		if (totalProductCount == 0) { // if none
			return null;
		}
		
		// get the incompleted product list
		List<Product> incompleteproductList = productMapper.selectProductListByCompleted(false);
		
		// get the random three product
		List<Product> productList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			
			// random number to use as an index
			int randomNumber = (int) (Math.random() * totalProductCount);
			
			// get the product at the index
			Product product = incompleteproductList.get(randomNumber);
			
			// add into the list
			productList.add(product);
		}
		
		return productList;
	}
	
	
	// ------- UPDATE -------
	
	/***
	 * Update product as completed
	 * @param productId
	 * @param buyerId
	 */
	public void updateProductCopmletedByProductId(int productId, int buyerId) {
		productMapper.updateProductCopmletedByProductId(productId, buyerId);
	}
	
}
