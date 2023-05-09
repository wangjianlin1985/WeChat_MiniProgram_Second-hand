package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AreaInfo {
    /*记录编号*/
    private Integer areaId;
    public Integer getAreaId(){
        return areaId;
    }
    public void setAreaId(Integer areaId){
        this.areaId = areaId;
    }

    /*区域名称*/
    @NotEmpty(message="区域名称不能为空")
    private String areaName;
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonAreaInfo=new JSONObject(); 
		jsonAreaInfo.accumulate("areaId", this.getAreaId());
		jsonAreaInfo.accumulate("areaName", this.getAreaName());
		return jsonAreaInfo;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}