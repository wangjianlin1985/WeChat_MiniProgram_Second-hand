var priceRegion_manage_tool = null; 
$(function () { 
	initPriceRegionManageTool(); //建立PriceRegion管理对象
	priceRegion_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#priceRegion_manage").datagrid({
		url : 'PriceRegion/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "regionId",
		sortOrder : "desc",
		toolbar : "#priceRegion_manage_tool",
		columns : [[
			{
				field : "regionId",
				title : "记录编号",
				width : 70,
			},
			{
				field : "regionName",
				title : "价格区间",
				width : 140,
			},
		]],
	});

	$("#priceRegionEditDiv").dialog({
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
				if ($("#priceRegionEditForm").form("validate")) {
					//验证表单 
					if(!$("#priceRegionEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#priceRegionEditForm").form({
						    url:"PriceRegion/" + $("#priceRegion_regionId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#priceRegionEditForm").form("validate"))  {
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
			                        $("#priceRegionEditDiv").dialog("close");
			                        priceRegion_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#priceRegionEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#priceRegionEditDiv").dialog("close");
				$("#priceRegionEditForm").form("reset"); 
			},
		}],
	});
});

function initPriceRegionManageTool() {
	priceRegion_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#priceRegion_manage").datagrid("reload");
		},
		redo : function () {
			$("#priceRegion_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#priceRegion_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#priceRegionQueryForm").form({
			    url:"PriceRegion/OutToExcel",
			});
			//提交表单
			$("#priceRegionQueryForm").submit();
		},
		remove : function () {
			var rows = $("#priceRegion_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var regionIds = [];
						for (var i = 0; i < rows.length; i ++) {
							regionIds.push(rows[i].regionId);
						}
						$.ajax({
							type : "POST",
							url : "PriceRegion/deletes",
							data : {
								regionIds : regionIds.join(","),
							},
							beforeSend : function () {
								$("#priceRegion_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#priceRegion_manage").datagrid("loaded");
									$("#priceRegion_manage").datagrid("load");
									$("#priceRegion_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#priceRegion_manage").datagrid("loaded");
									$("#priceRegion_manage").datagrid("load");
									$("#priceRegion_manage").datagrid("unselectAll");
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
			var rows = $("#priceRegion_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "PriceRegion/" + rows[0].regionId +  "/update",
					type : "get",
					data : {
						//regionId : rows[0].regionId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (priceRegion, response, status) {
						$.messager.progress("close");
						if (priceRegion) { 
							$("#priceRegionEditDiv").dialog("open");
							$("#priceRegion_regionId_edit").val(priceRegion.regionId);
							$("#priceRegion_regionId_edit").validatebox({
								required : true,
								missingMessage : "请输入记录编号",
								editable: false
							});
							$("#priceRegion_regionName_edit").val(priceRegion.regionName);
							$("#priceRegion_regionName_edit").validatebox({
								required : true,
								missingMessage : "请输入价格区间",
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
