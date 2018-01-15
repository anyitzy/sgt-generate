<%@ page contentType="text/html;charset=UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<html>
<head>
<title>赊购通-代码生成器</title>
<meta name="decorator" content="default" />
<%-- <c:set var="ctxStatic" value="${pageContext.request.contextPath}"/> --%>
<script
	src="${pageContext.request.contextPath}/static/js/jquery-1.8.3.min.js"></script>
<script
	src="${pageContext.request.contextPath}/static/js/jqLoad/jquery-ui-jqLoding.js?basePath=${pageContext.request.contextPath}"></script>
<script type="text/javascript">
	$(function() {

	});

	function generator() {

		var tableName = $.trim($("#tableName").val());
		if (tableName == "") {
			alert("表名不能为空！");
			return false;
		}

		var entityName = $.trim($("#entityName").val());
		if (entityName == "") {
			alert("实体类名不能为空！");
			return false;
		}

		var packageNamePre = $.trim($("#packageNamePre").val());
		if (packageNamePre == "") {
			alert("包名前缀不能为空！");
			return false;
		}

		var databaseName = $.trim($("#databaseName").val());
		if (databaseName == "") {
			alert("数据库名称不能为空！");
			return false;
		}

		var ipName = $.trim($("#ipName").val());
		if (ipName == "") {
			alert("数据库IP地址不能为空！");
			return false;
		}

		var portName = $.trim($("#portName").val());
		if (portName == "") {
			alert("数据库端口不能为空！");
			return false;
		}

		var userName = $.trim($("#userName").val());
		if (userName == "") {
			alert("数据库用户名不能为空！");
			return false;
		}

		var passWord = $.trim($("#passWord").val());
		if (passWord == "") {
			alert("数据库密码不能为空！");
			return false;
		}

		var outUrl = $.trim($("#outUrl").val());
		if (outUrl == "") {
			alert("输出路径不能为空！");
			return false;
		}
		
		var authorName = $.trim($("#authorName").val());
		if (authorName == "") {
			alert("注释签名不能为空！");
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/GeneratesServlet",
			cache : false,
			data : {
				tableName : tableName,
				entityName : entityName,
				packageNamePre : packageNamePre,
				databaseName : databaseName,
				ipName : ipName,
				portName : portName,
				userName : userName,
				outUrl : outUrl,
				authorName : authorName,
				passWord : passWord
			},
			success : function(data) {
				if (data.code != null && data.code == "200") {
					alert("生成成功！");
				} else {
					alert("生成失败！");
				}
			},
			beforeSend : function() {
				// async:false,那就不好使了
				//弹出遮罩层，加载处理效果
				$.fn.jqLoading({
					height : 100,
					width : 240,
					text : "正在加载中，请耐心等待...."
				});
			},
			complete : function(XMLHttpRequest, textStatus) {
				//如果超时就处理 ，指定要跳转的页面
				//取消遮罩层效果
				$.fn.jqLoading("destroy");
			},
			error : function(e) {
				alert(e);
			}
		});
	}
</script>
<body>
<div align="center">
	<h1>赊购通---代码生成器</h1>
	<%-- 实体类，dao，service的包名，应该根据表名输入，主动填充，可以二次修改（后期待优化） --%>
	<table>
		<tr>
			<td><label for="txtname"><font color="red">*</font>表名：</label></td>
			<td><input type="text" id="tableName" name="tableName" size="50"
				value="qshop_product" size="20">(eg:qshop_product)</td>
		</tr>
		<tr>
			<td><label for="txtname"><font color="red">*</font>实体类名：</label></td>
			<td><input type="text" id="entityName" name="entityName" size="50"
				value="Account">(eg:Account)</td>
		</tr>
		<tr>
			<td><label for="txtname"><font color="red">*</font>包名前缀：</label></td>
			<td><input type="text" id="packageNamePre" size="50"
				name="packageNamePre" value="com.yryz.qstone.modules.product">(eg:com.yryz.qstone.modules.product)</td>
		</tr>

		<tr>
			<td><label for="txtname"><font color="red">*</font>输出代码路径：</label></td>
			<td><input type="text" id="outUrl" size="50"
				name="outUrl" value="D:/daimashengchengqi">(eg:D:/daimashengchengqi)</td>
		</tr>

		<tr>
			<td><label for="txtname"><font color="red">*</font>注释签名：</label></td>
			<td><input type="text" id="authorName" size="50"
				name="authorName" value="wangsenyong">(eg:wangsenyong)</td>
		</tr>
		
		<tr>
			<td><label for="txtname"><font color="red">*</font>数据库名称：</label></td>
			<td><input type="text" id="databaseName" size="50"
			name="databaseName" value="qshop">(eg:qshop)</td>
		</tr>

		<tr>
			<td><label for="txtname"><font color="red">*</font>数据库IP地址：</label></td>
			<td><input type="text" id="ipName" size="50"
			name="ipName" value="192.168.30.37">(eg:192.168.30.37)</td>
		</tr>

		<tr>
			<td><label for="txtname"><font color="red">*</font>数据库端口：</label></td>
			<td><input type="text" id="portName" size="50"
			name="portName" value="8066">(eg:8066)</td>
		</tr>
		<tr>
			<td><label for="txtname"><font color="red">*</font>数据库用户名：</label></td>
			<td><input type="text" id="userName" size="50"
			name="userName" value="mycat-activeii">(eg:mycat-activeii)</td>
		</tr>
		<tr>
			<td><label for="txtname"><font color="red">*</font>数据库密码：</label></td>
			<td><input type="text" id="passWord" size="50"
			name="passWord" value="123qwe">(eg:123qwe)</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td colspan=2><input type="button" value="生成代码" onclick="generator();"
			style="font-size: 50px;"></td>
		</tr>
	</table>

</div>
</body>
</html>
