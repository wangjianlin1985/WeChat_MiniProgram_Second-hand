<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/priceRegion.css" />
<div id="priceRegion_editDiv">
	<form id="priceRegionEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="priceRegion_regionId_edit" name="priceRegion.regionId" value="<%=request.getParameter("regionId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">价格区间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="priceRegion_regionName_edit" name="priceRegion.regionName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="priceRegionModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/PriceRegion/js/priceRegion_modify.js"></script> 
