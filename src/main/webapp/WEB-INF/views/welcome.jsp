<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="home" align="center" style="height: 100%; width: 100%;">欢迎，${user.realName }</div>
<script type="text/javascript">
yepnope("${ctx}/resources/swfupload/css/icons.css");
yepnope("${ctx}/resources/swfupload/swfupload.js");
yepnope("${ctx}/resources/swfupload/uploaderPanel.js");

</script>