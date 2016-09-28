<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title>EPF Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="/computer-database-webapp/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/computer-database-webapp/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="topbar">
		<h1 class="fill">
			<a href="/computer-database-webapp/dashboard"> <spring:message code="label.appTitle" />
			</a>
		</h1>
		<span style="float: right"> <a href="?${!empty param.update ? 'update='.concat(param.update).concat('&') : ''}${!empty param.page ? 'page='.concat(param.page).concat('&') : ''}${!empty param.order ? 'order='.concat(param.order).concat('&') : ''}${!empty param.search ? 'search='.concat(param.search).concat('&') : ''}lang=en">English</a> | <a
			href="?${!empty param.update ? 'update='.concat(param.update).concat('&') : ''}${!empty param.page ? 'page='.concat(param.page).concat('&') : ''}${!empty param.order ? 'order='.concat(param.order).concat('&') : ''}${!empty param.search ? 'search='.concat(param.search).concat('&') : ''}lang=fr">Français</a>
		</span>
	</header>