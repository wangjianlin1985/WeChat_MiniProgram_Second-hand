$(function () {
	$("#productInfo_productClassObj_productClassId").combobox({
	    url:'ProductClass/listAll',
	    valueField: "productClassId",
	    textField: "productClassName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#productInfo_productClassObj_productClassId").combobox("getData"); 
            if (data.length > 0) {
                $("#productInfo_productClassObj_productClassId").combobox("select", data[0].productClassId);
            }
        }
	});
	$("#productInfo_productName").validatebox({
		required : true, 
		missingMessage : '请输入商品名称',
	});

	$("#productInfo_priceRegionObj_regionId").combobox({
	    url:'PriceRegion/listAll',
	    valueField: "regionId",
	    textField: "regionName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#productInfo_priceRegionObj_regionId").combobox("getData"); 
            if (data.length > 0) {
                $("#productInfo_priceRegionObj_regionId").combobox("select", data[0].regionId);
            }
        }
	});
	$("#productInfo_price").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入商品价格',
		invalidMessage : '商品价格输入不对',
	});

	$("#productInfo_newOldLevelObj_levelId").combobox({
	    url:'NewOldLevel/listAll',
	    valueField: "levelId",
	    textField: "levelName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#productInfo_newOldLevelObj_levelId").combobox("getData"); 
            if (data.length > 0) {
                $("#productInfo_newOldLevelObj_levelId").combobox("select", data[0].levelId);
            }
        }
	});
	$("#productInfo_areaObj_areaId").combobox({
	    url:'AreaInfo/listAll',
	    valueField: "areaId",
	    textField: "areaName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#productInfo_areaObj_areaId").combobox("getData"); 
            if (data.length > 0) {
                $("#productInfo_areaObj_areaId").combobox("select", data[0].areaId);
            }
        }
	});
	$("#productInfo_connectPerson").validatebox({
		required : true, 
		missingMessage : '请输入联系人',
	});

	$("#productInfo_connectPhone").validatebox({
		required : true, 
		missingMessage : '请输入联系电话',
	});

	$("#productInfo_productDes").validatebox({
		required : true, 
		missingMessage : '请输入商品描述',
	});

	$("#productInfo_userObj_user_name").combobox({
	    url:'UserInfo/listAll',
	    valueField: "user_name",
	    textField: "realname",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#productInfo_userObj_user_name").combobox("getData"); 
            if (data.length > 0) {
                $("#productInfo_userObj_user_name").combobox("select", data[0].user_name);
            }
        }
	});
	$("#productInfo_addTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	//单击添加按钮
	$("#productInfoAddButton").click(function () {
		//验证表单 
		if(!$("#productInfoAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#productInfoAddForm").form({
			    url:"ProductInfo/add",
			    onSubmit: function(){
					if($("#productInfoAddForm").form("validate"))  { 
	                	$.messager.progress({
							text : "正在提交数据中...",
						}); 
	                	return true;
	                } else {
	                    return false;
	                }
			    },
			    success:function(data){
			    	$.messager.progress("close");
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#productInfoAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#productInfoAddForm").submit();
		}
	});

	//单击清空按钮
	$("#productInfoClearButton").click(function () { 
		$("#productInfoAddForm").form("clear"); 
	});
});
