<%-- 
    Document   : Display
    Created on : Oct 11, 2016, 8:46:42 PM
    Author     : Daniel & Thi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Results</title>
        
    </head>
    <body>
        
        <p><% out.println(request.getAttribute("passedAttribute")); %> has <% out.println(request.getAttribute("passedAttribute2")); %> votes<br/></p>
        
    </body>
</html>