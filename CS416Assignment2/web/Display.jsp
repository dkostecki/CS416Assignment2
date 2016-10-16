<%-- 
   |------------------------------------------|
   |          JSP displaying results          |
   |--------------------|---------------------|
   |    Thi & Daniel    |      10-16-16       |
   |--------------------|---------------------|

--%>

<%-- display voting results --%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="Error.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Results</title>
    </head>
    <body>
        <%-- display from scriptlet --%>
        <p id="out"></p>

        <%-- scriptlet to handle passed data from DisplayServlet --%>
        <script type="text/javascript">
            var text = "";
            var x = [];
            var y = [];

            //passed from DisplayServlet
            x = '${passedAttribute}';
            y = '${passedAttribute2}';

            //remove brackets
            var music = x.replace("[", "");
            var votes = y.replace("[", "");

            var music = music.replace("]", "");
            var votes = votes.replace("]", "");

            //remove commas
            var music = music.split(",");
            var votes = votes.split(",");

            //loop through music
            for (var i = 0; i < music.length; i++)
            {
                text = text + music[i] + " has " + votes[i] + " votes" + "<br>";
            }

            //pass text back to html 
            document.getElementById("out").innerHTML = text;
        </script>
    </body>
</html>