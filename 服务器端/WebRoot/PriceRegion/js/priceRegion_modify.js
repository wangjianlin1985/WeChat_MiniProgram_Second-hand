$(function () {
	$.ajax({
		url : "PriceRegion/" + $("#priceRegion_regionId_edit").val() + "/update",
		type : "get",
		data : {
			//regionId : $("#priceRegion_regionId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (priceRegion, response, status) {
			$.messager.progress("close");
			if (priceRegion) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#priceRegionModifyButton").click(function(){ 
		if ($("#priceRegionEditForm").form("validate")) {
			$("#priceRegionEditForm").form({
			    url:"PriceRegion/" +  $("#priceRegion_regionId_edit").val() + "/update",
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
			$("#priceRegionEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
