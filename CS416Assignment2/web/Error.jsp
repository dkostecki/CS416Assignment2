<%-- 
   |------------------------------------------|
   |            JSP error handling            |
   |--------------------|---------------------|
   |    Thi & Daniel    |      10-16-16       |
   |--------------------|---------------------|

--%>

<%-- handle exceptions --%>
<%@ page isErrorPage="true" %>
<html>
    <head>
        <title>Error</title>
    </head>
    <body>
        <h1>Error</h1>
        <form>
            <%exception.printStackTrace(response.getWriter());%>
        </form>
    </body>
</html>