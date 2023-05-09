package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Announce {
    /*公告编号*/
    private Integer announceId;
    public Integer getAnnounceId(){
        return announceId;
    }
    public void setAnnounceId(Integer announceId){
        this.announceId = announceId;
    }

    /*公告标题*/
    @NotEmpty(message="公告标题不能为空")
    private String announceTitle;
    public String getAnnounceTitle() {
        return announceTitle;
    }
    public void setAnnounceTitle(String announceTitle) {
        this.announceTitle = announceTitle;
    }

    /*公告内容*/
    @NotEmpty(message="公告内容不能为空")
    private String announceContent;
    public String getAnnounceContent() {
        return announceContent;
    }
    public void setAnnounceContent(String announceContent) {
        this.announceContent = announceContent;
    }

    /*发布日期*/
    @NotEmpty(message="发布日期不能为空")
    private String announceDate;
    public String getAnnounceDate() {
        return announceDate;
    }
    public void setAnnounceDate(String announceDate) {
        this.announceDate = announceDate;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonAnnounce=new JSONObject(); 
		jsonAnnounce.accumulate("announceId", this.getAnnounceId());
		jsonAnnounce.accumulate("announceTitle", this.getAnnounceTitle());
		jsonAnnounce.accumulate("announceContent", this.getAnnounceContent());
		jsonAnnounce.accumulate("announceDate", this.getAnnounceDate().length()>19?this.getAnnounceDate().substring(0,19):this.getAnnounceDate());
		return jsonAnnounce;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}