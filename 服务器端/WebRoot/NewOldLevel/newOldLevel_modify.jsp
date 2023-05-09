<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/newOldLevel.css" />
<div id="newOldLevel_editDiv">
	<form id="newOldLevelEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="newOldLevel_levelId_edit" name="newOldLevel.levelId" value="<%=request.getParameter("levelId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">新旧程度:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="newOldLevel_levelName_edit" name="newOldLevel.levelName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="newOldLevelModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/NewOldLevel/js/newOldLevel_modify.js"></script> 
