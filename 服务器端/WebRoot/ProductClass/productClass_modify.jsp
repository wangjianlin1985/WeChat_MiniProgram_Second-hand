<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/productClass.css" />
<div id="productClass_editDiv">
	<form id="productClassEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">类别编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="productClass_productClassId_edit" name="productClass.productClassId" value="<%=request.getParameter("productClassId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">类别名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="productClass_productClassName_edit" name="productClass.productClassName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="productClassModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/ProductClass/js/productClass_modify.js"></script> 
