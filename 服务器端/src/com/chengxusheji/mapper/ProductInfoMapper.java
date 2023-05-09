package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.ProductInfo;

public interface ProductInfoMapper {
	/*添加商品信息信息*/
	public void addProductInfo(ProductInfo productInfo) throws Exception;

	/*按照查询条件分页查询商品信息记录*/
	public ArrayList<ProductInfo> queryProductInfo(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有商品信息记录*/
	public ArrayList<ProductInfo> queryProductInfoList(@Param("where") String where) throws Exception;
	
	/*按照查询条件查询最新5条商品信息记录*/
	public ArrayList<ProductInfo> queryNewProductInfoList() throws Exception;
	

	/*按照查询条件的商品信息记录数*/
	public int queryProductInfoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条商品信息记录*/
	public ProductInfo getProductInfo(int productId) throws Exception;

	/*更新商品信息记录*/
	public void updateProductInfo(ProductInfo productInfo) throws Exception;

	/*删除商品信息记录*/
	public void deleteProductInfo(int productId) throws Exception;

}
