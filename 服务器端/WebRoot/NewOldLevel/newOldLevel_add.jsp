<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/newOldLevel.css" />
<div id="newOldLevelAddDiv">
	<form id="newOldLevelAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">新旧程度:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="newOldLevel_levelName" name="newOldLevel.levelName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="newOldLevelAddButton" class="easyui-linkbutton">添加</a>
			<a id="newOldLevelClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/NewOldLevel/js/newOldLevel_add.js"></script> 
