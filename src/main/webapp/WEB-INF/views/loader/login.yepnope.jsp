<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Authority</title>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/commons/yepnope.jsp"%>
<script type="text/javascript">
	yepnope({
		load : "${ctx}/resources/js/login.js"
	});
</script>
</head>
<body class="x-border-layout-ct" style="position: static; overflow: hidden;">
	<table id="logo-table">
		<tr>
			<td align="center" height="65"><a href="${ctx}"><img src="${ctx}/resources/images/box_title_bg.gif"></img> </a>
			</td>
		</tr>
	</table>
	<div id="login-win-div"></div>
</body>
</html>
