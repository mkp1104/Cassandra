<!-- 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
    import="com.skl.app.entity.*,
    		java.util.List
    	   "
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Flipbasket</title>
	
	<!-- Bootstrap itself -->
	<link href="/assets/css/bootstrap.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="/assets/css/font-awesome.css">
	<style type="text/css" media="screen">
		body {
			overflow: show;
			padding: 20px;
		}
	</style>
	
</head>

<body>
	<h1>Flipbasket</h1>
	<form name="skl_form" class="form-inline" role="form" method="post" action="/login">
		<div class="form-group">
			<label class="" for="Login">Login &nbsp;&nbsp;</label> <input type="text" name="<%= UserDO.FetchAttributes.loginId.toString() %>" class="form-control">
			<label class="" for="Password">Password &nbsp;&nbsp;</label> <input type="password" name="<%= UserDO.FetchAttributes.password.toString() %>" class="form-control">
		</div>
		<br><br>
		<a href="#" id="run_button" class="btn btn-success" onclick="skl_form.submit();">Go</a>
	</form>
</body>

</html>
