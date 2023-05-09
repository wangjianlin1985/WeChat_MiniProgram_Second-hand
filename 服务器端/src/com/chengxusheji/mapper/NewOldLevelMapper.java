package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.NewOldLevel;

public interface NewOldLevelMapper {
	/*添加新旧程度信息*/
	public void addNewOldLevel(NewOldLevel newOldLevel) throws Exception;

	/*按照查询条件分页查询新旧程度记录*/
	public ArrayList<NewOldLevel> queryNewOldLevel(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有新旧程度记录*/
	public ArrayList<NewOldLevel> queryNewOldLevelList(@Param("where") String where) throws Exception;

	/*按照查询条件的新旧程度记录数*/
	public int queryNewOldLevelCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条新旧程度记录*/
	public NewOldLevel getNewOldLevel(int levelId) throws Exception;

	/*更新新旧程度记录*/
	public void updateNewOldLevel(NewOldLevel newOldLevel) throws Exception;

	/*删除新旧程度记录*/
	public void deleteNewOldLevel(int levelId) throws Exception;

}
