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
        <%--
        <p><% out.println(request.getAttribute("passedAttribute")); %> has <% out.println(request.getAttribute("passedAttribute2")); %> votes<br/></p>
        --%>
        
        <p id="out"></p>
        
        <script type="text/javascript">
            
            var text = "";
            var x = [];
            var y = [];
            x = '${passedAttribute}';
            y = '${passedAttribute2}';
            
            var music = x.replace("[","");
            var votes = y.replace("[","");
            
            var music = music.replace("]","");
            var votes = votes.replace("]","");
            
            var music = music.split(",");
            var votes = votes.split(",");
            
            
            for(var i = 0; i < music.length; i++)
            {
                text = text + music[i] + " has " + votes[i] + " votes" + "<br>";
                //out.println(<p>out.println(music[i] + " has " + votes[i] + " votes");<br/></p>);
            }
            document.getElementById("out").innerHTML = text;
        </script>
    </body>
</html>