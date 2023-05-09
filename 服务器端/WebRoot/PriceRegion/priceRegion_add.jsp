<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/priceRegion.css" />
<div id="priceRegionAddDiv">
	<form id="priceRegionAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">价格区间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="priceRegion_regionName" name="priceRegion.regionName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="priceRegionAddButton" class="easyui-linkbutton">添加</a>
			<a id="priceRegionClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/PriceRegion/js/priceRegion_add.js"></script> 
