$(function () {
	$.ajax({
		url : "ProductInfo/" + $("#productInfo_productId_edit").val() + "/update",
		type : "get",
		data : {
			//productId : $("#productInfo_productId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (productInfo, response, status) {
			$.messager.progress("close");
			if (productInfo) { 
				$("#productInfo_productId_edit").val(productInfo.productId);
				$("#productInfo_productId_edit").validatebox({
					required : true,
					missingMessage : "请输入商品编号",
					editable: false
				});
				$("#productInfo_productClassObj_productClassId_edit").combobox({
					url:"ProductClass/listAll",
					valueField:"productClassId",
					textField:"productClassName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#productInfo_productClassObj_productClassId_edit").combobox("select", productInfo.productClassObjPri);
						//var data = $("#productInfo_productClassObj_productClassId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#productInfo_productClassObj_productClassId_edit").combobox("select", data[0].productClassId);
						//}
					}
				});
				$("#productInfo_productName_edit").val(productInfo.productName);
				$("#productInfo_productName_edit").validatebox({
					required : true,
					missingMessage : "请输入商品名称",
				});
				$("#productInfo_priceRegionObj_regionId_edit").combobox({
					url:"PriceRegion/listAll",
					valueField:"regionId",
					textField:"regionName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#productInfo_priceRegionObj_regionId_edit").combobox("select", productInfo.priceRegionObjPri);
						//var data = $("#productInfo_priceRegionObj_regionId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#productInfo_priceRegionObj_regionId_edit").combobox("select", data[0].regionId);
						//}
					}
				});
				$("#productInfo_price_edit").val(productInfo.price);
				$("#productInfo_price_edit").validatebox({
					required : true,
					validType : "number",
					missingMessage : "请输入商品价格",
					invalidMessage : "商品价格输入不对",
				});
				$("#productInfo_newOldLevelObj_levelId_edit").combobox({
					url:"NewOldLevel/listAll",
					valueField:"levelId",
					textField:"levelName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#productInfo_newOldLevelObj_levelId_edit").combobox("select", productInfo.newOldLevelObjPri);
						//var data = $("#productInfo_newOldLevelObj_levelId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#productInfo_newOldLevelObj_levelId_edit").combobox("select", data[0].levelId);
						//}
					}
				});
				$("#productInfo_areaObj_areaId_edit").combobox({
					url:"AreaInfo/listAll",
					valueField:"areaId",
					textField:"areaName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#productInfo_areaObj_areaId_edit").combobox("select", productInfo.areaObjPri);
						//var data = $("#productInfo_areaObj_areaId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#productInfo_areaObj_areaId_edit").combobox("select", data[0].areaId);
						//}
					}
				});
				$("#productInfo_productPhoto").val(productInfo.productPhoto);
				$("#productInfo_productPhotoImg").attr("src", productInfo.productPhoto);
				$("#productInfo_connectPerson_edit").val(productInfo.connectPerson);
				$("#productInfo_connectPerson_edit").validatebox({
					required : true,
					missingMessage : "请输入联系人",
				});
				$("#productInfo_connectPhone_edit").val(productInfo.connectPhone);
				$("#productInfo_connectPhone_edit").validatebox({
					required : true,
					missingMessage : "请输入联系电话",
				});
				$("#productInfo_productDes_edit").val(productInfo.productDes);
				$("#productInfo_productDes_edit").validatebox({
					required : true,
					missingMessage : "请输入商品描述",
				});
				$("#productInfo_userObj_user_name_edit").combobox({
					url:"UserInfo/listAll",
					valueField:"user_name",
					textField:"realname",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#productInfo_userObj_user_name_edit").combobox("select", productInfo.userObjPri);
						//var data = $("#productInfo_userObj_user_name_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#productInfo_userObj_user_name_edit").combobox("select", data[0].user_name);
						//}
					}
				});
				$("#productInfo_addTime_edit").datetimebox({
					value: productInfo.addTime,
					required: true,
					showSeconds: true,
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#productInfoModifyButton").click(function(){ 
		if ($("#productInfoEditForm").form("validate")) {
			$("#productInfoEditForm").form({
			    url:"ProductInfo/" +  $("#productInfo_productId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#productInfoEditForm").form("validate"))  {
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#productInfoEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
