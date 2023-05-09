<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String user_name = (String)request.getAttribute("user_name"); //手机用户名查询关键字
    String realname = (String)request.getAttribute("realname"); //姓名查询关键字
    String bornDate = (String)request.getAttribute("bornDate"); //出生日期查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>用户信息查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>UserInfo/frontlist">用户信息信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>UserInfo/userInfo_frontAdd.jsp" style="display:none;">添加用户信息</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<userInfoList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		UserInfo userInfo = userInfoList.get(i); //获取到用户信息对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>UserInfo/<%=userInfo.getUser_name() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=userInfo.getMyPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		手机用户名:<%=userInfo.getUser_name() %>
			     	</div>
			     	<div class="field">
	            		姓名:<%=userInfo.getRealname() %>
			     	</div>
			     	<div class="field">
	            		性别:<%=userInfo.getSex() %>
			     	</div>
			     	<div class="field">
	            		出生日期:<%=userInfo.getBornDate() %>
			     	</div>
			     	<div class="field">
	            		用户qq:<%=userInfo.getQq() %>
			     	</div>
			     	<div class="field">
	            		用户邮箱:<%=userInfo.getEmail() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>UserInfo/<%=userInfo.getUser_name() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="userInfoEdit('<%=userInfo.getUser_name() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="userInfoDelete('<%=userInfo.getUser_name() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

			<div class="row">
				<div class="col-md-12">
					<nav class="pull-left">
						<ul class="pagination">
							<li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<%
								int startPage = currentPage - 5;
								int endPage = currentPage + 5;
								if(startPage < 1) startPage=1;
								if(endPage > totalPage) endPage = totalPage;
								for(int i=startPage;i<=endPage;i++) {
							%>
							<li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
							<%  } %> 
							<li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						</ul>
					</nav>
					<div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>用户信息查询</h1>
		</div>
		<form name="userInfoQueryForm" id="userInfoQueryForm" action="<%=basePath %>UserInfo/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="user_name">手机用户名:</label>
				<input type="text" id="user_name" name="user_name" value="<%=user_name %>" class="form-control" placeholder="请输入手机用户名">
			</div>
			<div class="form-group">
				<label for="realname">姓名:</label>
				<input type="text" id="realname" name="realname" value="<%=realname %>" class="form-control" placeholder="请输入姓名">
			</div>
			<div class="form-group">
				<label for="bornDate">出生日期:</label>
				<input type="text" id="bornDate" name="bornDate" class="form-control"  placeholder="请选择出生日期" value="<%=bornDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="userInfoEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;用户信息信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="userInfoEditForm" id="userInfoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="userInfo_user_name_edit" class="col-md-3 text-right">手机用户名:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="userInfo_user_name_edit" name="userInfo.user_name" class="form-control" placeholder="请输入手机用户名" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="userInfo_password_edit" class="col-md-3 text-right">登陆密码:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_password_edit" name="userInfo.password" class="form-control" placeholder="请输入登陆密码">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_realname_edit" class="col-md-3 text-right">姓名:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_realname_edit" name="userInfo.realname" class="form-control" placeholder="请输入姓名">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_sex_edit" class="col-md-3 text-right">性别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_sex_edit" name="userInfo.sex" class="form-control" placeholder="请输入性别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_bornDate_edit" class="col-md-3 text-right">出生日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date userInfo_bornDate_edit col-md-12" data-link-field="userInfo_bornDate_edit" data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="userInfo_bornDate_edit" name="userInfo.bornDate" size="16" type="text" value="" placeholder="请选择出生日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_qq_edit" class="col-md-3 text-right">用户qq:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_qq_edit" name="userInfo.qq" class="form-control" placeholder="请输入用户qq">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_address_edit" class="col-md-3 text-right">家庭地址:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_address_edit" name="userInfo.address" class="form-control" placeholder="请输入家庭地址">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_email_edit" class="col-md-3 text-right">用户邮箱:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_email_edit" name="userInfo.email" class="form-control" placeholder="请输入用户邮箱">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_myPhoto_edit" class="col-md-3 text-right">用户头像:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="userInfo_myPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="userInfo_myPhoto" name="userInfo.myPhoto"/>
			    <input id="myPhotoFile" name="myPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_memo_edit" class="col-md-3 text-right">附加信息:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_memo_edit" name="userInfo.memo" class="form-control" placeholder="请输入附加信息">
			 </div>
		  </div>
		</form> 
	    <style>#userInfoEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxUserInfoModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.userInfoQueryForm.currentPage.value = currentPage;
    document.userInfoQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.userInfoQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.userInfoQueryForm.currentPage.value = pageValue;
    documentuserInfoQueryForm.submit();
}

/*弹出修改用户信息界面并初始化数据*/
function userInfoEdit(user_name) {
	$.ajax({
		url :  basePath + "UserInfo/" + user_name + "/update",
		type : "get",
		dataType: "json",
		success : function (userInfo, response, status) {
			if (userInfo) {
				$("#userInfo_user_name_edit").val(userInfo.user_name);
				$("#userInfo_password_edit").val(userInfo.password);
				$("#userInfo_realname_edit").val(userInfo.realname);
				$("#userInfo_sex_edit").val(userInfo.sex);
				$("#userInfo_bornDate_edit").val(userInfo.bornDate);
				$("#userInfo_qq_edit").val(userInfo.qq);
				$("#userInfo_address_edit").val(userInfo.address);
				$("#userInfo_email_edit").val(userInfo.email);
				$("#userInfo_myPhoto").val(userInfo.myPhoto);
				$("#userInfo_myPhotoImg").attr("src", basePath +　userInfo.myPhoto);
				$("#userInfo_memo_edit").val(userInfo.memo);
				$('#userInfoEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除用户信息信息*/
function userInfoDelete(user_name) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "UserInfo/deletes",
			data : {
				user_names : user_name,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#userInfoQueryForm").submit();
					//location.href= basePath + "UserInfo/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交用户信息信息表单给服务器端修改*/
function ajaxUserInfoModify() {
	$.ajax({
		url :  basePath + "UserInfo/" + $("#userInfo_user_name_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#userInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#userInfoQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse > a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*出生日期组件*/
    $('.userInfo_bornDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

