<%--
  Created by IntelliJ IDEA.
  User: kurt
  Date: 2014/10/2
  Time: 4:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ page import="cn.edu.tsinghua.cess.deployment.entity.DeployMode"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Deployment Status</title>
</head>
<body>
	<b>This server has been deployed as following:</b>
	<br>

	<table border="0">
		<tbody>
			<tr>
				<th>Deployment Mode:</th>
				<td>${deployment.mode}</td>
			</tr>
			<tr>
				<th>Node Name:</th>
				<td>${deployment.nodeName}</td>
			</tr>
			<tr>
				<th>Node Ip:</th>
				<td>${deployment.nodeIp}</td>
			</tr>
			<tr>
				<th>Node Port:</th>
				<td>${deployment.nodePort}</td>
			</tr>
			<tr>
				<th>Root Path:</th>
				<td>${deployment.rootPath}</td>
			</tr>
			<c:if test="${deployment.mode eq 'DISTRIBUTED_WORKER'}">
				<tr>
					<th>Central Node Ip:</th>
					<td>${deployment.centralNodeIp}</td>
				</tr>
				<tr>
					<th>Central Node Port:</th>
					<td>${deployment.centralNodePort}</td>
				</tr>
				<tr>
					<th>Central Node Root Path:</th>
					<td>${deployment.centralNodeRootPath}</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<br>
	<c:if test="${deployment.mode eq 'DISTRIBUTED_CENTRAL'}">
		<table border="0">
			<tr>
				<th colspan="4" align="left">
					Registered WorkerNode(s)
				</th>
			<tr>
			<tr>
				<th>Node Name</th>
				<th>Node Ip</th>
				<th>Node Port</th>
				<th>Root Path</th>
			</tr>

			<c:forEach items="${workerNodeList}" var="workerNode">
				<tr>
					<td>${workerNode.name}</td>
					<td>${workerNode.ip}  </td>
					<td>${workerNode.port}</td>
					<td>${workerNode.rootPath}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>
