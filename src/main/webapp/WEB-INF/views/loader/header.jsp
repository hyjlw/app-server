<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>header</title>
<script>
	// calculate difference between zero and window.onload
	var now = new Date();
	window.onload = function() {
		document.getElementById("ms").innerHTML = (new Date()).getTime() - now.getTime();
	};
</script>
<script type="text/javascript" src="${ctx}/resources/loader/head.min.js"></script>
<script>
	head.js("${ctx}/resources/extjs/adapter/ext/ext-base.js")
	.js("${ctx}/resources/extjs/ext-all-debug.js")
	.js("${ctx}/resources/extjs/ext-lang-zh_CN.js")
	.js("${ctx}/resources/extjs/ux/ExtMD5.js")
	.js("${ctx}/resources/js/share.js");
</script>
<style>
#time {
	font-size: 150px;
	color: #888;
}
</style>
</head>

<body>
<img src="${ctx}/resources/images/box_title_bg.gif"></img>
<img src="${ctx}/resources/images/Anynote-s.png"></img>
<img src="${ctx}/resources/images/Anynote.png"></img>
<br />
<br />
<div id="time">
		<span id="ms"></span> ms
</div>
</body>
</html>