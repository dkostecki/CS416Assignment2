/*  |------------------------------------------|
 *  |      Servlet displaying voting page      |
 *  |--------------------|---------------------|
 *  |    Thi & Daniel    |      10-16-16       |
 *  |--------------------|---------------------|
 */
package edu.ccsu.cs416a2;

import java.lang.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "StartPageServlet", urlPatterns = {"/StartPageServlet"})
public class StartPageServlet extends HttpServlet {

    private int testingGlobal = 1; //I put testingGlobal at the bottom of processRequest

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartPageServlet</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("Vote what your favorite type of music is: " + "</br>");
            
            Connection connection = datasource.getConnection();
            String readSQL = "select * from VOTES";
            PreparedStatement readStatement = connection.prepareStatement(readSQL);
            ResultSet nullSet = readStatement.executeQuery();
            
            //Inserts Pop and Rock to a null DB
            if(!nullSet.next()){
                String sql = "INSERT INTO votes(musictype,numvotes) VALUES(?,?)";
                PreparedStatement insertStatement = connection.prepareStatement(sql);
                insertStatement.setString(1, "Pop");
                insertStatement.setInt(2, 0);
                insertStatement.executeUpdate();
                insertStatement.close();

                String sql2 = "INSERT INTO votes(musictype,numvotes) VALUES(?,?)";
                PreparedStatement insertStatement2 = connection.prepareStatement(sql2);
                insertStatement2.setString(1, "Rock");
                insertStatement2.setInt(2, 0);
                insertStatement2.executeUpdate();
                insertStatement2.close();
            }
            nullSet.close();
            
            //musictype and numvotes are put into arrays
            List<String> musicTypeArr = new ArrayList<>();
            List<String> numVotes = new ArrayList<>();
            
            ResultSet resultSet = readStatement.executeQuery();
            //Adds data from DB to ArrayList
            while (resultSet.next()) {
                musicTypeArr.add(resultSet.getString("musictype"));
                numVotes.add(resultSet.getString("numvotes"));
            }
            resultSet.close();
            
            out.println("<form action=\"StartPageServlet\" method=\"GET\">");

            //Populate checkboxes
            for (int i = 0; i < musicTypeArr.size(); i++) {
                String checkboxVal = "\"" + (String) musicTypeArr.get(i) + "\"";
                out.println("<input type=\"checkbox\" name=\"checkbox\" value="
                        + checkboxVal + " />" + musicTypeArr.get(i) + "<br>");
            }

            out.println("<input type=\"submit\" name=\"sub\" value=\"Submit Vote\"/><br/>");
            
            //Data will be displayed in jsp if submit button is clicked
            if (request.getParameter("sub") != null){
                //String[] and for loop to try to check checked checkboxes and pass the values to the database
                String[] check = request.getParameterValues("checkbox");
                
                if (check != null){
                    //Stores selected checkboxes into checkVal
                    List<String> checkVal = Arrays.asList(check);
                    ArrayList<String> selectedTypes = new ArrayList<String>();

                    //updateableList contains all selected music types
                    selectedTypes.addAll(checkVal);

                    //loops as many times as the total number of selected checkboxes
                    for (int i = 0; i < selectedTypes.size(); i++) {
                        String readSubmitSQL = "select * from VOTES";
                        PreparedStatement readSubStatement = connection.prepareStatement(readSubmitSQL);
                        ResultSet resultSubmit = readSubStatement.executeQuery();

                        //numvotes increments 1 for each selected music types
                        while (resultSubmit.next()) {
                            if (selectedTypes.get(i).equals(resultSubmit.getString("musictype"))) {
                                String sql = "UPDATE votes SET numvotes = numvotes + 1 WHERE musictype = ?";
                                PreparedStatement updateStatement = connection.prepareStatement(sql);

                                String musictype = resultSubmit.getString("musictype");
                                updateStatement.setString(1, musictype);
                                updateStatement.executeUpdate();
                                updateStatement.close();
                            }
                        } 
                        resultSubmit.close();
                        readSubStatement.close();
                    }
                }   
            }
            
            out.println("<br/>Or add a new one<br/>");
            out.println("<br/> New music type: <input type=\"textbox\" name=\"musictype\"/><br/>");
            out.println("<input type=\"submit\" name=\"newSub\" value=\"Add type and vote\"/>");
            
            //Variable name for the submit button for adding new music type
            String newSub = request.getParameter("newSub");
            
            //Stores the input from textfield into string musictype
            String newMusicType = request.getParameter("musictype");
            
            String rSQL = "select * from VOTES";
            PreparedStatement rStatement = connection.prepareStatement(rSQL);
            ResultSet rSet = rStatement.executeQuery();
            
            boolean same = false;
            //This gives new musictype a value
            if ((newMusicType != null) && (newMusicType.length() > 0)) {
                while (rSet.next()) {
                    if((newMusicType.equals(rSet.getString("musictype")))){
                        same = true;
                    }
                }
                if(same == false){
                    out.println("<br>No matches with list.<br>");
                    String sql = "insert into votes(musictype, numvotes) values (?,?)";
                    PreparedStatement insertStatement = connection.prepareStatement(sql);
                    insertStatement.setString(1, newMusicType);
                    insertStatement.setInt(2, 1);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }else out.println("<br>That music type is already in the list. Please select from above.");
            }

            //Go to DisplayServlet
            if ((request.getParameter("sub") != null || ( (request.getParameter("newSub")!= null) &&(same == false)))) {
                //Session count
                HttpSession session = request.getSession();
                Integer sessionVotes = (Integer) session.getAttribute("sessionVotes");

                if (sessionVotes == null) {
                    sessionVotes = 1;
                    session.setAttribute("sessionVotes", sessionVotes);
                } else {
                    sessionVotes = new Integer(sessionVotes.intValue() + 1);
                }
                session.setAttribute("sessionVotes", sessionVotes);

                //Context count
                ServletContext context = request.getServletContext();
                Integer contextVotes = (Integer) context.getAttribute("contextVotes");
                if (contextVotes == null) {
                    contextVotes = 1;
                    context.setAttribute("contextVotes", contextVotes);
                } else {
                    contextVotes = new Integer(contextVotes.intValue() + 1);
                }
                context.setAttribute("contextVotes", contextVotes);

                //Passes sessionVotes and contextVotes to DisplayServlet
                response.sendRedirect("DisplayServlet"); 
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
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
