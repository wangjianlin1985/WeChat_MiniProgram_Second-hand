var productInfo_manage_tool = null; 
$(function () { 
	initProductInfoManageTool(); //建立ProductInfo管理对象
	productInfo_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#productInfo_manage").datagrid({
		url : 'ProductInfo/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "productId",
		sortOrder : "desc",
		toolbar : "#productInfo_manage_tool",
		columns : [[
			{
				field : "productId",
				title : "商品编号",
				width : 70,
			},
			{
				field : "productClassObj",
				title : "商品类别",
				width : 140,
			},
			{
				field : "productName",
				title : "商品名称",
				width : 140,
			},
			{
				field : "price",
				title : "商品价格",
				width : 70,
			},
			{
				field : "newOldLevelObj",
				title : "成色",
				width : 140,
			},
			{
				field : "areaObj",
				title : "区域",
				width : 140,
			},
			{
				field : "productPhoto",
				title : "商品图片",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "connectPerson",
				title : "联系人",
				width : 140,
			},
			{
				field : "connectPhone",
				title : "联系电话",
				width : 140,
			},
			{
				field : "userObj",
				title : "发布人",
				width : 140,
			},
			{
				field : "addTime",
				title : "发布时间",
				width : 140,
			},
		]],
	});

	$("#productInfoEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#productInfoEditForm").form("validate")) {
					//验证表单 
					if(!$("#productInfoEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#productInfoEditForm").form({
						    url:"ProductInfo/" + $("#productInfo_productId_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#productInfoEditDiv").dialog("close");
			                        productInfo_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#productInfoEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#productInfoEditDiv").dialog("close");
				$("#productInfoEditForm").form("reset"); 
			},
		}],
	});
});

function initProductInfoManageTool() {
	productInfo_manage_tool = {
		init: function() {
			$.ajax({
				url : "ProductClass/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#productClassObj_productClassId_query").combobox({ 
					    valueField:"productClassId",
					    textField:"productClassName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{productClassId:0,productClassName:"不限制"});
					$("#productClassObj_productClassId_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "PriceRegion/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#priceRegionObj_regionId_query").combobox({ 
					    valueField:"regionId",
					    textField:"regionName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{regionId:0,regionName:"不限制"});
					$("#priceRegionObj_regionId_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "NewOldLevel/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#newOldLevelObj_levelId_query").combobox({ 
					    valueField:"levelId",
					    textField:"levelName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{levelId:0,levelName:"不限制"});
					$("#newOldLevelObj_levelId_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "AreaInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#areaObj_areaId_query").combobox({ 
					    valueField:"areaId",
					    textField:"areaName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{areaId:0,areaName:"不限制"});
					$("#areaObj_areaId_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "UserInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#userObj_user_name_query").combobox({ 
					    valueField:"user_name",
					    textField:"realname",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{user_name:"",realname:"不限制"});
					$("#userObj_user_name_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#productInfo_manage").datagrid("reload");
		},
		redo : function () {
			$("#productInfo_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#productInfo_manage").datagrid("options").queryParams;
			queryParams["productClassObj.productClassId"] = $("#productClassObj_productClassId_query").combobox("getValue");
			queryParams["productName"] = $("#productName").val();
			queryParams["priceRegionObj.regionId"] = $("#priceRegionObj_regionId_query").combobox("getValue");
			queryParams["newOldLevelObj.levelId"] = $("#newOldLevelObj_levelId_query").combobox("getValue");
			queryParams["areaObj.areaId"] = $("#areaObj_areaId_query").combobox("getValue");
			queryParams["userObj.user_name"] = $("#userObj_user_name_query").combobox("getValue");
			queryParams["addTime"] = $("#addTime").datebox("getValue"); 
			$("#productInfo_manage").datagrid("options").queryParams=queryParams; 
			$("#productInfo_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#productInfoQueryForm").form({
			    url:"ProductInfo/OutToExcel",
			});
			//提交表单
			$("#productInfoQueryForm").submit();
		},
		remove : function () {
			var rows = $("#productInfo_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var productIds = [];
						for (var i = 0; i < rows.length; i ++) {
							productIds.push(rows[i].productId);
						}
						$.ajax({
							type : "POST",
							url : "ProductInfo/deletes",
							data : {
								productIds : productIds.join(","),
							},
							beforeSend : function () {
								$("#productInfo_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#productInfo_manage").datagrid("loaded");
									$("#productInfo_manage").datagrid("load");
									$("#productInfo_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#productInfo_manage").datagrid("loaded");
									$("#productInfo_manage").datagrid("load");
									$("#productInfo_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#productInfo_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "ProductInfo/" + rows[0].productId +  "/update",
					type : "get",
					data : {
						//productId : rows[0].productId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (productInfo, response, status) {
						$.messager.progress("close");
						if (productInfo) { 
							$("#productInfoEditDiv").dialog("open");
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
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
