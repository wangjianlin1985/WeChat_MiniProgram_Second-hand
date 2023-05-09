package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.ProductClass;
import com.chengxusheji.po.PriceRegion;
import com.chengxusheji.po.NewOldLevel;
import com.chengxusheji.po.AreaInfo;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.ProductInfo;

import com.chengxusheji.mapper.ProductInfoMapper;
@Service
public class ProductInfoService {

	@Resource ProductInfoMapper productInfoMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加商品信息记录*/
    public void addProductInfo(ProductInfo productInfo) throws Exception {
    	productInfoMapper.addProductInfo(productInfo);
    }

    /*按照查询条件分页查询商品信息记录*/
    public ArrayList<ProductInfo> queryProductInfo(ProductClass productClassObj,String productName,PriceRegion priceRegionObj,NewOldLevel newOldLevelObj,AreaInfo areaObj,UserInfo userObj,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != productClassObj && productClassObj.getProductClassId()!= null && productClassObj.getProductClassId()!= 0)  where += " and t_productInfo.productClassObj=" + productClassObj.getProductClassId();
    	if(!productName.equals("")) where = where + " and t_productInfo.productName like '%" + productName + "%'";
    	if(null != priceRegionObj && priceRegionObj.getRegionId()!= null && priceRegionObj.getRegionId()!= 0)  where += " and t_productInfo.priceRegionObj=" + priceRegionObj.getRegionId();
    	if(null != newOldLevelObj && newOldLevelObj.getLevelId()!= null && newOldLevelObj.getLevelId()!= 0)  where += " and t_productInfo.newOldLevelObj=" + newOldLevelObj.getLevelId();
    	if(null != areaObj && areaObj.getAreaId()!= null && areaObj.getAreaId()!= 0)  where += " and t_productInfo.areaObj=" + areaObj.getAreaId();
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_productInfo.userObj='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) where = where + " and t_productInfo.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return productInfoMapper.queryProductInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<ProductInfo> queryProductInfo(ProductClass productClassObj,String productName,PriceRegion priceRegionObj,NewOldLevel newOldLevelObj,AreaInfo areaObj,UserInfo userObj,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != productClassObj && productClassObj.getProductClassId()!= null && productClassObj.getProductClassId()!= 0)  where += " and t_productInfo.productClassObj=" + productClassObj.getProductClassId();
    	if(!productName.equals("")) where = where + " and t_productInfo.productName like '%" + productName + "%'";
    	if(null != priceRegionObj && priceRegionObj.getRegionId()!= null && priceRegionObj.getRegionId()!= 0)  where += " and t_productInfo.priceRegionObj=" + priceRegionObj.getRegionId();
    	if(null != newOldLevelObj && newOldLevelObj.getLevelId()!= null && newOldLevelObj.getLevelId()!= 0)  where += " and t_productInfo.newOldLevelObj=" + newOldLevelObj.getLevelId();
    	if(null != areaObj && areaObj.getAreaId()!= null && areaObj.getAreaId()!= 0)  where += " and t_productInfo.areaObj=" + areaObj.getAreaId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_productInfo.userObj='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) where = where + " and t_productInfo.addTime like '%" + addTime + "%'";
    	return productInfoMapper.queryProductInfoList(where);
    }
    
    
    /*按照查询条件查询所有记录*/
    public ArrayList<ProductInfo> queryNewProductInfo() throws Exception  { 
     	String where = "where 1=1"; 
    	return productInfoMapper.queryNewProductInfoList();
    }
    

    /*查询所有商品信息记录*/
    public ArrayList<ProductInfo> queryAllProductInfo()  throws Exception {
        return productInfoMapper.queryProductInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(ProductClass productClassObj,String productName,PriceRegion priceRegionObj,NewOldLevel newOldLevelObj,AreaInfo areaObj,UserInfo userObj,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(null != productClassObj && productClassObj.getProductClassId()!= null && productClassObj.getProductClassId()!= 0)  where += " and t_productInfo.productClassObj=" + productClassObj.getProductClassId();
    	if(!productName.equals("")) where = where + " and t_productInfo.productName like '%" + productName + "%'";
    	if(null != priceRegionObj && priceRegionObj.getRegionId()!= null && priceRegionObj.getRegionId()!= 0)  where += " and t_productInfo.priceRegionObj=" + priceRegionObj.getRegionId();
    	if(null != newOldLevelObj && newOldLevelObj.getLevelId()!= null && newOldLevelObj.getLevelId()!= 0)  where += " and t_productInfo.newOldLevelObj=" + newOldLevelObj.getLevelId();
    	if(null != areaObj && areaObj.getAreaId()!= null && areaObj.getAreaId()!= 0)  where += " and t_productInfo.areaObj=" + areaObj.getAreaId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_productInfo.userObj='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) where = where + " and t_productInfo.addTime like '%" + addTime + "%'";
        recordNumber = productInfoMapper.queryProductInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取商品信息记录*/
    public ProductInfo getProductInfo(int productId) throws Exception  {
        ProductInfo productInfo = productInfoMapper.getProductInfo(productId);
        return productInfo;
    }

    /*更新商品信息记录*/
    public void updateProductInfo(ProductInfo productInfo) throws Exception {
        productInfoMapper.updateProductInfo(productInfo);
    }

    /*删除一条商品信息记录*/
    public void deleteProductInfo (int productId) throws Exception {
        productInfoMapper.deleteProductInfo(productId);
    }

    /*删除多条商品信息信息*/
    public int deleteProductInfos (String productIds) throws Exception {
    	String _productIds[] = productIds.split(",");
    	for(String _productId: _productIds) {
    		productInfoMapper.deleteProductInfo(Integer.parseInt(_productId));
    	}
    	return _productIds.length;
    }
}
