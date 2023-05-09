package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.PriceRegion;

public interface PriceRegionMapper {
	/*添加价格区间信息*/
	public void addPriceRegion(PriceRegion priceRegion) throws Exception;

	/*按照查询条件分页查询价格区间记录*/
	public ArrayList<PriceRegion> queryPriceRegion(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有价格区间记录*/
	public ArrayList<PriceRegion> queryPriceRegionList(@Param("where") String where) throws Exception;

	/*按照查询条件的价格区间记录数*/
	public int queryPriceRegionCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条价格区间记录*/
	public PriceRegion getPriceRegion(int regionId) throws Exception;

	/*更新价格区间记录*/
	public void updatePriceRegion(PriceRegion priceRegion) throws Exception;

	/*删除价格区间记录*/
	public void deletePriceRegion(int regionId) throws Exception;

}
