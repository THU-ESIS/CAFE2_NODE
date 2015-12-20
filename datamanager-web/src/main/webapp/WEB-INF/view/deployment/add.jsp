<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<body>

<form action="deployment/deploy" method="post">
    <input type="radio" name="mode" value="DISTRIBUTED_CENTRAL" /> Distributed Central <br />
    <input type="radio" name="mode" value="DISTRIBUTED_WORKER" /> Distributed Worker <br />
    <input type="radio" name="mode" value="LOCAL" /> Local <br />
    Node Name:<input type="text" name="nodeName" /><br />
    Node Ip:<input type="text" name="nodeIp" /><br />
    Node Port:<input type="text" name="nodePort" /><br />
    Root Path:<input type="text" name="rootPath" /><br />

    Central Node Ip:<input type="text" name="centralNodeIp" /><br />
    Central Node Port:<input type="text" name="centralNodePort" /><br />
    Central Node Root Path:<input type="text" name="centralNodeRootPath" /><br />
    <input type="submit" value="submit">
</form>
</body>
</html>