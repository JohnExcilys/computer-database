<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title>EPF Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="topbar">
		<h1 class="fill">
			<a href="dashboard"> <spring:message code="label.appTitle" />
			</a>
		</h1>
		<span style="float: right"> <a href="?lang=en">English</a> | <a
			href="?lang=fr">Français</a>
		</span>
	</header>