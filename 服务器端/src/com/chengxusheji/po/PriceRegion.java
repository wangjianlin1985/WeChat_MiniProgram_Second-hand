package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PriceRegion {
    /*记录编号*/
    private Integer regionId;
    public Integer getRegionId(){
        return regionId;
    }
    public void setRegionId(Integer regionId){
        this.regionId = regionId;
    }

    /*价格区间*/
    @NotEmpty(message="价格区间不能为空")
    private String regionName;
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonPriceRegion=new JSONObject(); 
		jsonPriceRegion.accumulate("regionId", this.getRegionId());
		jsonPriceRegion.accumulate("regionName", this.getRegionName());
		return jsonPriceRegion;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}