$(function () {
	$.ajax({
		url : "NewOldLevel/" + $("#newOldLevel_levelId_edit").val() + "/update",
		type : "get",
		data : {
			//levelId : $("#newOldLevel_levelId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (newOldLevel, response, status) {
			$.messager.progress("close");
			if (newOldLevel) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#newOldLevelModifyButton").click(function(){ 
		if ($("#newOldLevelEditForm").form("validate")) {
			$("#newOldLevelEditForm").form({
			    url:"NewOldLevel/" +  $("#newOldLevel_levelId_edit").val() + "/update",
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
			$("#newOldLevelEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
