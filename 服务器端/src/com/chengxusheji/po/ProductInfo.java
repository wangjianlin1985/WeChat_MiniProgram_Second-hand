package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductInfo {
    /*商品编号*/
    private Integer productId;
    public Integer getProductId(){
        return productId;
    }
    public void setProductId(Integer productId){
        this.productId = productId;
    }

    /*商品类别*/
    private ProductClass productClassObj;
    public ProductClass getProductClassObj() {
        return productClassObj;
    }
    public void setProductClassObj(ProductClass productClassObj) {
        this.productClassObj = productClassObj;
    }

    /*商品名称*/
    @NotEmpty(message="商品名称不能为空")
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*价格区域*/
    private PriceRegion priceRegionObj;
    public PriceRegion getPriceRegionObj() {
        return priceRegionObj;
    }
    public void setPriceRegionObj(PriceRegion priceRegionObj) {
        this.priceRegionObj = priceRegionObj;
    }

    /*商品价格*/
    @NotNull(message="必须输入商品价格")
    private Float price;
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    /*成色*/
    private NewOldLevel newOldLevelObj;
    public NewOldLevel getNewOldLevelObj() {
        return newOldLevelObj;
    }
    public void setNewOldLevelObj(NewOldLevel newOldLevelObj) {
        this.newOldLevelObj = newOldLevelObj;
    }

    /*区域*/
    private AreaInfo areaObj;
    public AreaInfo getAreaObj() {
        return areaObj;
    }
    public void setAreaObj(AreaInfo areaObj) {
        this.areaObj = areaObj;
    }

    /*商品图片*/
    private String productPhoto;
    public String getProductPhoto() {
        return productPhoto;
    }
    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    private String productPhotoUrl;
    public void setProductPhotoUrl(String productPhotoUrl) {
		this.productPhotoUrl = productPhotoUrl;
	}
	public String getProductPhotoUrl() {
		return  SessionConsts.BASE_URL + productPhoto;
	}
    /*联系人*/
    @NotEmpty(message="联系人不能为空")
    private String connectPerson;
    public String getConnectPerson() {
        return connectPerson;
    }
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }

    /*联系电话*/
    @NotEmpty(message="联系电话不能为空")
    private String connectPhone;
    public String getConnectPhone() {
        return connectPhone;
    }
    public void setConnectPhone(String connectPhone) {
        this.connectPhone = connectPhone;
    }

    /*商品描述*/
    @NotEmpty(message="商品描述不能为空")
    private String productDes;
    public String getProductDes() {
        return productDes;
    }
    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    /*发布人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*发布时间*/
    @NotEmpty(message="发布时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonProductInfo=new JSONObject(); 
		jsonProductInfo.accumulate("productId", this.getProductId());
		jsonProductInfo.accumulate("productClassObj", this.getProductClassObj().getProductClassName());
		jsonProductInfo.accumulate("productClassObjPri", this.getProductClassObj().getProductClassId());
		jsonProductInfo.accumulate("productName", this.getProductName());
		jsonProductInfo.accumulate("priceRegionObj", this.getPriceRegionObj().getRegionName());
		jsonProductInfo.accumulate("priceRegionObjPri", this.getPriceRegionObj().getRegionId());
		jsonProductInfo.accumulate("price", this.getPrice());
		jsonProductInfo.accumulate("newOldLevelObj", this.getNewOldLevelObj().getLevelName());
		jsonProductInfo.accumulate("newOldLevelObjPri", this.getNewOldLevelObj().getLevelId());
		jsonProductInfo.accumulate("areaObj", this.getAreaObj().getAreaName());
		jsonProductInfo.accumulate("areaObjPri", this.getAreaObj().getAreaId());
		jsonProductInfo.accumulate("productPhoto", this.getProductPhoto());
		jsonProductInfo.accumulate("connectPerson", this.getConnectPerson());
		jsonProductInfo.accumulate("connectPhone", this.getConnectPhone());
		jsonProductInfo.accumulate("productDes", this.getProductDes());
		jsonProductInfo.accumulate("userObj", this.getUserObj().getRealname());
		jsonProductInfo.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonProductInfo.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonProductInfo;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}