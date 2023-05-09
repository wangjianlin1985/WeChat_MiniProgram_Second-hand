$(function () {
	$.ajax({
		url : "ProductClass/" + $("#productClass_productClassId_edit").val() + "/update",
		type : "get",
		data : {
			//productClassId : $("#productClass_productClassId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (productClass, response, status) {
			$.messager.progress("close");
			if (productClass) { 
				$("#productClass_productClassId_edit").val(productClass.productClassId);
				$("#productClass_productClassId_edit").validatebox({
					required : true,
					missingMessage : "请输入类别编号",
					editable: false
				});
				$("#productClass_productClassName_edit").val(productClass.productClassName);
				$("#productClass_productClassName_edit").validatebox({
					required : true,
					missingMessage : "请输入类别名称",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#productClassModifyButton").click(function(){ 
		if ($("#productClassEditForm").form("validate")) {
			$("#productClassEditForm").form({
			    url:"ProductClass/" +  $("#productClass_productClassId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#productClassEditForm").form("validate"))  {
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
			$("#productClassEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
