package com.ms.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.product.domain.Product;

@Mapper
public interface ProductMapper {
	
	// ------- CREATE -------
	
	/***
	 * Insert product
	 * @param product
	 * @return
	 */
    public int insertProductByMap(Product product);
    
    
	// ------- READ -------
    
    /***
     * Select the number of product by keyword and completed
     * @param keyword
     * @param completed
     * @return
     */
    public int selectProductCountByKeywordCompleted(
    		@Param("keyword") String keyword,
    		@Param("completed") boolean completed);
    
    /***
     * Select the product by product id
     * @param id
     * @return
     */
    public Product selectProductById(int id);
    
    /***
     * Select the list of product by owner id or keyword
     * @param ownerId
     * @param keyword
     * @param skip
     * @param limit
     * @param completed
     * @return
     */
	public List<Product> selectProductListByOwnerIdOrKeyword(
			@Param("ownerId") Integer ownerId,
			@Param("keyword") String keyword,
			@Param("skip") int skip, 
			@Param("limit") Integer limit,
			@Param("completed") boolean completed);
	
	/***
	 * Select the latest three product
	 * @return
	 */
	public List<Product> selectLatestThreeProductList();
	
	/***
	 * Select the product which are not traded yet
	 * @param completed
	 * @return
	 */
	public List<Product> selectProductListByCompleted(boolean completed);
	
	
	// ------- UPDATE -------
	
	/***
	 * Update the product as completed
	 * @param productId
	 * @param buyerId
	 */
	public void updateProductCopmletedByProductId(
			@Param("productId") int productId, 
			@Param("buyerId") int buyerId);
	
}
