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
	<title>Flipbasket - Home page</title>
	
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
	<table width="100%">
		<tr>
			<td width="50%"><h1>Flipbasket</h1></td>
			<td width="50%" align="right">Welcome <%= session.getAttribute(UserDO.DisplayAttributes.firstName.toString()) %>!<br>[<a href="/logout">Logout</a>]</td>
		</tr>
	</table>
	<b>New order</b>
	<form name="skl_form" class="form-inline" role="form" method="post" action="/order">
		<div class="form-group">
			<label class="" for="Details">Details &nbsp;&nbsp;</label> <input type="text" name="<%= OrderDO.DisplayAttributes.details.toString() %>" class="form-control">
			<label class="" for="Amount">Amount &nbsp;&nbsp;</label> <input type="text" name="<%= OrderDO.DisplayAttributes.amount.toString() %>" class="form-control">
			<label class="" for="Tax">Tax &nbsp;&nbsp;</label> <input type="text" name="<%= OrderDO.DisplayAttributes.tax.toString() %>" class="form-control">
			<br><a href="#" id="run_button" class="btn btn-success" onclick="skl_form.submit();">Create</a>
			<%
				String message = (String) request.getAttribute(OrderDO.DisplayAttributes.orderMsg.toString());
				if(message != null) {
			%>
				&nbsp;<%= message %>
			<%
				}
				
				List<OrderDO> orders = (List<OrderDO>) request.getAttribute(OrderDO.DisplayAttributes.orderList.toString());
				if(orders!=null && orders.size()>0) {
			%>
					<br><h2>Past orders</h2><hr>
			<%
					for(OrderDO order : orders) {
			%>
					<b><%= order.getOrderNum() %></b> worth <%= order.getAmount() %> ordered on <%= order.getCreatedOn() %>
					<br>&nbsp;&nbsp;&nbsp;<%= order.getDetails() %><br><br>
			<%
					}
				}
				else {
			%> 
				No past orders found for you! Why don't you get something for yourself?
			<%
				}
			%>
		</div>
	</form>
	<br>
</body>

</html>
