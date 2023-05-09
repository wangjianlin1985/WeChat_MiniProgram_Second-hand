package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Announce;

public interface AnnounceMapper {
	/*添加公告信息信息*/
	public void addAnnounce(Announce announce) throws Exception;

	/*按照查询条件分页查询公告信息记录*/
	public ArrayList<Announce> queryAnnounce(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有公告信息记录*/
	public ArrayList<Announce> queryAnnounceList(@Param("where") String where) throws Exception;

	/*按照查询条件的公告信息记录数*/
	public int queryAnnounceCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条公告信息记录*/
	public Announce getAnnounce(int announceId) throws Exception;

	/*更新公告信息记录*/
	public void updateAnnounce(Announce announce) throws Exception;

	/*删除公告信息记录*/
	public void deleteAnnounce(int announceId) throws Exception;

}
