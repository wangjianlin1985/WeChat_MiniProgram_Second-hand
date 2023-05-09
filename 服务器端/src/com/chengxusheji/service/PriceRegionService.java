package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.PriceRegion;

import com.chengxusheji.mapper.PriceRegionMapper;
@Service
public class PriceRegionService {

	@Resource PriceRegionMapper priceRegionMapper;
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

    /*添加价格区间记录*/
    public void addPriceRegion(PriceRegion priceRegion) throws Exception {
    	priceRegionMapper.addPriceRegion(priceRegion);
    }

    /*按照查询条件分页查询价格区间记录*/
    public ArrayList<PriceRegion> queryPriceRegion(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return priceRegionMapper.queryPriceRegion(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<PriceRegion> queryPriceRegion() throws Exception  { 
     	String where = "where 1=1";
    	return priceRegionMapper.queryPriceRegionList(where);
    }

    /*查询所有价格区间记录*/
    public ArrayList<PriceRegion> queryAllPriceRegion()  throws Exception {
        return priceRegionMapper.queryPriceRegionList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = priceRegionMapper.queryPriceRegionCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取价格区间记录*/
    public PriceRegion getPriceRegion(int regionId) throws Exception  {
        PriceRegion priceRegion = priceRegionMapper.getPriceRegion(regionId);
        return priceRegion;
    }

    /*更新价格区间记录*/
    public void updatePriceRegion(PriceRegion priceRegion) throws Exception {
        priceRegionMapper.updatePriceRegion(priceRegion);
    }

    /*删除一条价格区间记录*/
    public void deletePriceRegion (int regionId) throws Exception {
        priceRegionMapper.deletePriceRegion(regionId);
    }

    /*删除多条价格区间信息*/
    public int deletePriceRegions (String regionIds) throws Exception {
    	String _regionIds[] = regionIds.split(",");
    	for(String _regionId: _regionIds) {
    		priceRegionMapper.deletePriceRegion(Integer.parseInt(_regionId));
    	}
    	return _regionIds.length;
    }
}
