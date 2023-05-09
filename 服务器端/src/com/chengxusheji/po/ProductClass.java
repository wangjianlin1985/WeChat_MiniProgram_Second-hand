package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductClass {
    /*类别编号*/
    private Integer productClassId;
    public Integer getProductClassId(){
        return productClassId;
    }
    public void setProductClassId(Integer productClassId){
        this.productClassId = productClassId;
    }

    /*类别名称*/
    @NotEmpty(message="类别名称不能为空")
    private String productClassName;
    public String getProductClassName() {
        return productClassName;
    }
    public void setProductClassName(String productClassName) {
        this.productClassName = productClassName;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonProductClass=new JSONObject(); 
		jsonProductClass.accumulate("productClassId", this.getProductClassId());
		jsonProductClass.accumulate("productClassName", this.getProductClassName());
		return jsonProductClass;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}