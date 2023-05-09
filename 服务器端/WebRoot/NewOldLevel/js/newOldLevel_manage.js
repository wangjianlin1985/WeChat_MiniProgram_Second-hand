var newOldLevel_manage_tool = null; 
$(function () { 
	initNewOldLevelManageTool(); //建立NewOldLevel管理对象
	newOldLevel_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#newOldLevel_manage").datagrid({
		url : 'NewOldLevel/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "levelId",
		sortOrder : "desc",
		toolbar : "#newOldLevel_manage_tool",
		columns : [[
			{
				field : "levelId",
				title : "记录编号",
				width : 70,
			},
			{
				field : "levelName",
				title : "新旧程度",
				width : 140,
			},
		]],
	});

	$("#newOldLevelEditDiv").dialog({
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
				if ($("#newOldLevelEditForm").form("validate")) {
					//验证表单 
					if(!$("#newOldLevelEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#newOldLevelEditForm").form({
						    url:"NewOldLevel/" + $("#newOldLevel_levelId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#newOldLevelEditForm").form("validate"))  {
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
			                        $("#newOldLevelEditDiv").dialog("close");
			                        newOldLevel_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#newOldLevelEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#newOldLevelEditDiv").dialog("close");
				$("#newOldLevelEditForm").form("reset"); 
			},
		}],
	});
});

function initNewOldLevelManageTool() {
	newOldLevel_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#newOldLevel_manage").datagrid("reload");
		},
		redo : function () {
			$("#newOldLevel_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#newOldLevel_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#newOldLevelQueryForm").form({
			    url:"NewOldLevel/OutToExcel",
			});
			//提交表单
			$("#newOldLevelQueryForm").submit();
		},
		remove : function () {
			var rows = $("#newOldLevel_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var levelIds = [];
						for (var i = 0; i < rows.length; i ++) {
							levelIds.push(rows[i].levelId);
						}
						$.ajax({
							type : "POST",
							url : "NewOldLevel/deletes",
							data : {
								levelIds : levelIds.join(","),
							},
							beforeSend : function () {
								$("#newOldLevel_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#newOldLevel_manage").datagrid("loaded");
									$("#newOldLevel_manage").datagrid("load");
									$("#newOldLevel_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#newOldLevel_manage").datagrid("loaded");
									$("#newOldLevel_manage").datagrid("load");
									$("#newOldLevel_manage").datagrid("unselectAll");
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
			var rows = $("#newOldLevel_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "NewOldLevel/" + rows[0].levelId +  "/update",
					type : "get",
					data : {
						//levelId : rows[0].levelId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (newOldLevel, response, status) {
						$.messager.progress("close");
						if (newOldLevel) { 
							$("#newOldLevelEditDiv").dialog("open");
							$("#newOldLevel_levelId_edit").val(newOldLevel.levelId);
							$("#newOldLevel_levelId_edit").validatebox({
								required : true,
								missingMessage : "请输入记录编号",
								editable: false
							});
							$("#newOldLevel_levelName_edit").val(newOldLevel.levelName);
							$("#newOldLevel_levelName_edit").validatebox({
								required : true,
								missingMessage : "请输入新旧程度",
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
