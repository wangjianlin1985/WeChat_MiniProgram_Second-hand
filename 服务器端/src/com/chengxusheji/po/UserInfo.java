package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserInfo {
    /*手机用户名*/
    @NotEmpty(message="手机用户名不能为空")
    private String user_name;
    public String getUser_name(){
        return user_name;
    }
    public void setUser_name(String user_name){
        this.user_name = user_name;
    }

    /*登陆密码*/
    @NotEmpty(message="登陆密码不能为空")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*姓名*/
    private String realname;
    public String getRealname() {
        return realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /*性别*/
    @NotEmpty(message="性别不能为空")
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*出生日期*/
    @NotEmpty(message="出生日期不能为空")
    private String bornDate;
    public String getBornDate() {
        return bornDate;
    }
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    /*用户qq*/
    private String qq;
    public String getQq() {
        return qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }

    /*家庭地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*用户邮箱*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*用户头像*/
    private String myPhoto;
    public String getMyPhoto() {
        return myPhoto;
    }
    public void setMyPhoto(String myPhoto) {
        this.myPhoto = myPhoto;
    }

    private String myPhotoUrl;
    public void setMyPhotoUrl(String myPhotoUrl) {
		this.myPhotoUrl = myPhotoUrl;
	}
	public String getMyPhotoUrl() {
		return  SessionConsts.BASE_URL + myPhoto;
	}
    /*附加信息*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonUserInfo=new JSONObject(); 
		jsonUserInfo.accumulate("user_name", this.getUser_name());
		jsonUserInfo.accumulate("password", this.getPassword());
		jsonUserInfo.accumulate("realname", this.getRealname());
		jsonUserInfo.accumulate("sex", this.getSex());
		jsonUserInfo.accumulate("bornDate", this.getBornDate().length()>19?this.getBornDate().substring(0,19):this.getBornDate());
		jsonUserInfo.accumulate("qq", this.getQq());
		jsonUserInfo.accumulate("address", this.getAddress());
		jsonUserInfo.accumulate("email", this.getEmail());
		jsonUserInfo.accumulate("myPhoto", this.getMyPhoto());
		jsonUserInfo.accumulate("memo", this.getMemo());
		return jsonUserInfo;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}