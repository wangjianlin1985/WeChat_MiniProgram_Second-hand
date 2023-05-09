<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/productInfo.css" /> 

<div id="productInfo_manage"></div>
<div id="productInfo_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="productInfo_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="productInfo_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="productInfo_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="productInfo_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="productInfo_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="productInfoQueryForm" method="post">
			商品类别：<input class="textbox" type="text" id="productClassObj_productClassId_query" name="productClassObj.productClassId" style="width: auto"/>
			商品名称：<input type="text" class="textbox" id="productName" name="productName" style="width:110px" />
			价格区域：<input class="textbox" type="text" id="priceRegionObj_regionId_query" name="priceRegionObj.regionId" style="width: auto"/>
			成色：<input class="textbox" type="text" id="newOldLevelObj_levelId_query" name="newOldLevelObj.levelId" style="width: auto"/>
			区域：<input class="textbox" type="text" id="areaObj_areaId_query" name="areaObj.areaId" style="width: auto"/>
			发布人：<input class="textbox" type="text" id="userObj_user_name_query" name="userObj.user_name" style="width: auto"/>
			发布时间：<input type="text" id="addTime" name="addTime" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="productInfo_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="productInfoEditDiv">
	<form id="productInfoEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">商品编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="productInfo_productId_edit" name="productInfo.productId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">商品类别:</span>
			<span class="inputControl">
				<input class="textbox"  id="productInfo_productClassObj_productClassId_edit" name="productInfo.productClassObj.productClassId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">商品名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="productInfo_productName_edit" name="productInfo.productName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">价格区域:</span>
			<span class="inputControl">
				<input class="textbox"  id="productInfo_priceRegionObj_regionId_edit" name="productInfo.priceRegionObj.regionId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">商品价格:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="productInfo_price_edit" name="productInfo.price" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">成色:</span>
			<span class="inputControl">
				<input class="textbox"  id="productInfo_newOldLevelObj_levelId_edit" name="productInfo.newOldLevelObj.levelId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">区域:</span>
			<span class="inputControl">
				<input class="textbox"  id="productInfo_areaObj_areaId_edit" name="productInfo.areaObj.areaId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">商品图片:</span>
			<span class="inputControl">
				<img id="productInfo_productPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="productInfo_productPhoto" name="productInfo.productPhoto"/>
				<input id="productPhotoFile" name="productPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">联系人:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="productInfo_connectPerson_edit" name="productInfo.connectPerson" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="productInfo_connectPhone_edit" name="productInfo.connectPhone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">商品描述:</span>
			<span class="inputControl">
				<textarea id="productInfo_productDes_edit" name="productInfo.productDes" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">发布人:</span>
			<span class="inputControl">
				<input class="textbox"  id="productInfo_userObj_user_name_edit" name="productInfo.userObj.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">发布时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="productInfo_addTime_edit" name="productInfo.addTime" />

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="ProductInfo/js/productInfo_manage.js"></script> 
