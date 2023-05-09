package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Announce;

import com.chengxusheji.mapper.AnnounceMapper;
@Service
public class AnnounceService {

	@Resource AnnounceMapper announceMapper;
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

    /*添加公告信息记录*/
    public void addAnnounce(Announce announce) throws Exception {
    	announceMapper.addAnnounce(announce);
    }

    /*按照查询条件分页查询公告信息记录*/
    public ArrayList<Announce> queryAnnounce(String announceTitle,String announceDate,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!announceTitle.equals("")) where = where + " and t_announce.announceTitle like '%" + announceTitle + "%'";
    	if(!announceDate.equals("")) where = where + " and t_announce.announceDate like '%" + announceDate + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return announceMapper.queryAnnounce(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Announce> queryAnnounce(String announceTitle,String announceDate) throws Exception  { 
     	String where = "where 1=1";
    	if(!announceTitle.equals("")) where = where + " and t_announce.announceTitle like '%" + announceTitle + "%'";
    	if(!announceDate.equals("")) where = where + " and t_announce.announceDate like '%" + announceDate + "%'";
    	return announceMapper.queryAnnounceList(where);
    }

    /*查询所有公告信息记录*/
    public ArrayList<Announce> queryAllAnnounce()  throws Exception {
        return announceMapper.queryAnnounceList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String announceTitle,String announceDate) throws Exception {
     	String where = "where 1=1";
    	if(!announceTitle.equals("")) where = where + " and t_announce.announceTitle like '%" + announceTitle + "%'";
    	if(!announceDate.equals("")) where = where + " and t_announce.announceDate like '%" + announceDate + "%'";
        recordNumber = announceMapper.queryAnnounceCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取公告信息记录*/
    public Announce getAnnounce(int announceId) throws Exception  {
        Announce announce = announceMapper.getAnnounce(announceId);
        return announce;
    }

    /*更新公告信息记录*/
    public void updateAnnounce(Announce announce) throws Exception {
        announceMapper.updateAnnounce(announce);
    }

    /*删除一条公告信息记录*/
    public void deleteAnnounce (int announceId) throws Exception {
        announceMapper.deleteAnnounce(announceId);
    }

    /*删除多条公告信息信息*/
    public int deleteAnnounces (String announceIds) throws Exception {
    	String _announceIds[] = announceIds.split(",");
    	for(String _announceId: _announceIds) {
    		announceMapper.deleteAnnounce(Integer.parseInt(_announceId));
    	}
    	return _announceIds.length;
    }
}
