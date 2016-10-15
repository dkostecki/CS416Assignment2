<%-- 
   |------------------------------------------|
   |          Jsp displaying results          |
   |--------------------|---------------------|
   |    Thi & Daniel    |      10-16-16       |
   |--------------------|---------------------|

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Results</title>
        
    </head>
    <body>
        
        <%-- display from scriptlet --%>
        <p id="out"></p>
        
        <%-- remove for final version --%>
        <p id="sessionOut"></p>
        <p id="contextOut"></p>
        <p id="testing"></p>
        
        <%-- scriptlet to handle passed data from DisplayServlet --%>
        <script type="text/javascript">
            var session = '${passSession}';
            var context = '${passContext}';
            var test = '${test}';
        <%-- Everything above is Thi's experiment--%>    
            var text = "";
            var x = [];
            var y = [];
            
            //passed from DisplayServlet
            x = '${passedAttribute}';
            y = '${passedAttribute2}';
            
            //remove brackets
            var music = x.replace("[","");
            var votes = y.replace("[","");
            
            var music = music.replace("]","");
            var votes = votes.replace("]","");
            
            //remove commas
            var music = music.split(",");
            var votes = votes.split(",");
            
            //loop through music
            for(var i = 0; i < music.length; i++)
            {
                text = text + music[i] + " has " + votes[i] + " votes" + "<br>";
            }
            
            //pass text back to html 
            document.getElementById("out").innerHTML = text;
            
            <%-- Everything below is Thi's experiment--%>  
            document.getElementById("sessionOut").innerHTML = session;
            document.getElementById("contextOut").innerHTML = context;
            document.getElementById("testing").innerHTML = test;
        </script>
    </body>
</html>