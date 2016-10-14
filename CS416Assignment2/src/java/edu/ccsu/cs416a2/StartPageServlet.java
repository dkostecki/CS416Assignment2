/* |--------------------------------------------------------|
 * |    This servlet displays the page that you vote on     |
 * |--------------------------------------------------------|
*/
package edu.ccsu.cs416a2;

import java.lang.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * author Thi & Daniel
 */
@WebServlet(name = "StartPageServlet", urlPatterns = {"/StartPageServlet"})
public class StartPageServlet extends HttpServlet {

    @Resource(name = "jdbc/HW2DB")
    private javax.sql.DataSource datasource;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Connection connection = datasource.getConnection();

            String musictype = request.getParameter("musictype");
            String numvotes = request.getParameter("numvotes");

            //testing updates for numvotes
            /*int numvotes = 0;
            if(request.getParameter("numvotes") != null)
            {
                numvotes = Integer.parseInt(request.getParameter("numvotes"));
            }
             */
            //String temp = request.getParameter("numvotes");
            //Integer numvotes = Integer.parseInt(temp);
            //Add new musictype to DB
            
            //This gives new musictype a value
            if (musictype != null && musictype.length() > 0) {
                //int numvotes = Integer.parseInt(request.getParameter("numvotes"));
                String sql = "insert into votes(musictype, numvotes) values (?,?)";
                PreparedStatement insertStatement = connection.prepareStatement(sql);
                insertStatement.setString(1, musictype);
                insertStatement.setInt(2, 1);
                int recordsAffected = insertStatement.executeUpdate();
                insertStatement.close();
            }
            
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartPageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("Vote what your favorite type of music is: " + "</br>");

            String readSQL = "select * from VOTES";
            PreparedStatement readStatement = connection.prepareStatement(readSQL);
            ResultSet resultSet = readStatement.executeQuery();

            //musictype and numvotes are put into arrays
            List<String> musicTypeArr = new ArrayList<>();           
            List<String> numVotes = new ArrayList<>();
            //---------------------------

            
            //form started here before the commit
            //Shows data from the musictype column in the DB
            
            //out.println("<form name = \"checkboxes\">");
            while (resultSet.next()) {
                musicTypeArr.add(resultSet.getString("musictype"));
                numVotes.add (resultSet.getString("numvotes"));
            
                //----
                //musictype = resultSet.getString("musictype");
                //numvotes = resultSet.getString("numvotes");
                //-----
                
                //numvotes = Integer.parseInt(resultSet.getString("numvotes"));
                //numvotes = resultSet.getInt("numvotes");
                //out.println(musictype + "</br>");
                
                //out.println("<input type=\"checkbox\" name=\"musictype\" value=musictype/>");
                //out.println(musictype + " " + numvotes + "</br>");
            }
            //out.println("</form>");

            //String sql = "UPDATE votes SET numvotes = numvotes + 1 WHERE musictype = '?'";

            out.println("<form action=\"StartPageServlet\" method=\"GET\">");
            
            
            for(int i=0; i<musicTypeArr.size(); i++){
                String checkboxVal ="\"" + (String)musicTypeArr.get(i) + "\"";
                out.println("<input type=\"checkbox\" name=\"checkbox\" value="
                        + checkboxVal + " />" + musicTypeArr.get(i) + "<br>");
            }
            
            
            
            String checkbox = request.getParameter("checkbox");
            if(checkbox != null){
                String selectVote = "select * from votes order by musictype";
                PreparedStatement selectStatement = connection.prepareStatement(selectVote);
                ResultSet resultSetV = selectStatement.executeQuery();
                
                
                int vote = 0;
                while(resultSetV.next()){
                    if(checkbox.equals(resultSetV.getString("musictype"))){
                        vote = resultSetV.getInt("numvotes");
                    }
                }
                
                //Adds 1 to previous votes
                String newVotes = Integer.toString(vote +1 );
                String updateVote = "update votes set numvotes=? where musictype=?";
                PreparedStatement updateStatement = connection.prepareStatement(updateVote);
                updateStatement.setString(1, newVotes );
                updateStatement.setString(2, checkbox);
                updateStatement.executeUpdate();
                updateStatement.close();              
            }
           
            
            
            out.println("<input type=\"submit\" name=\"sub\" value=\"Submit Vote\" onclick=\"location.href='Display.jsp'\"/><br/>");

            //If submit button is clicked, data will be displayed in jsp, but right now, it only shows the first musictype + its vote
            if (request.getParameter("sub") != null) {
                //If I don't have another String, PreparedStatement, ResultSet, it will only display the last the last musictype
                String readSubmitSQL = "select * from VOTES";
                PreparedStatement readSubStatement = connection.prepareStatement(readSubmitSQL);
                ResultSet resultSubmit = readSubStatement.executeQuery();
                
                //String[] and for loop to try to check checked checkboxes and pass the values to the database
                String[] check = request.getParameterValues("musictype");
                for (int i = 0; i < check.length; i++) {
                    
                    String[] checkVal = request.getParameterValues(check[i]);
                    
                    if(checkVal != null)
                    {
                        String sql = "UPDATE votes SET numvotes = numvotes + 1 WHERE musictype = ?)";
                        PreparedStatement insertStatement = connection.prepareStatement(sql);

                        musictype = resultSubmit.getString("musictype");
                        insertStatement.setString(1, musictype);

                    }
                    
                    
                    //This shows a bunch the arrays for musictype and numVotes in jsp
                    request.setAttribute("passedAttribute", musicTypeArr + " has " + numVotes); //numVotes shows an array of the votes //musictypes
                    request.getRequestDispatcher("Display.jsp").forward(request, response);
                }

            }

            out.println("<br/>Or add a new one<br/>");
            out.println("<br/> New music type: <input type=\"textbox\" name=\"musictype\"/><br/>");
            out.println("<input type=\"submit\" name=\"newSub\" value=\"Add type and vote\"/>");

            //This will only show the last musictype/votes from database
            if (request.getParameter("newSub") != null) {
                request.setAttribute("passedAttribute", musicTypeArr + " has " + numVotes);
                request.getRequestDispatcher("Display.jsp").forward(request, response);
            }
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");

            resultSet.close();
            readStatement.close();
            connection.close();
        } catch (Exception e) {
            out.println("Error occurred " + e.getMessage());
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}