/*
 * This servlet displays the page that you vote on
 * 
 */
package edu.ccsu.cs416a2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Thi
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
        try{
            Connection connection = datasource.getConnection();
            
            String musictype = request.getParameter("musictype");
            //Add new musictype to DB
            if(musictype != null && musictype.length() > 0){  
                //int numvotes = Integer.parseInt(request.getParameter("numvotes"));
                String sql = "insert into votes(musictype, numvotes) values (?,?)";              
                PreparedStatement insertStatement = connection.prepareStatement(sql);
                insertStatement.setString(1,musictype);
                //int numvotes = numvote + 1; 
                insertStatement.setInt(2,0);
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
            
            //Shows data from the musictype column in the DB
            while(resultSet.next()){
                musictype = resultSet.getString("musictype");
                //out.println(musictype + "</br>");
                /*Don't need resultSet.getString("numvotes") on this servlet, but
                I was testing something out. Remove it later.
                */
                out.println(musictype + " " + resultSet.getString("numvotes") + "</br>");
            }
            
            out.println("</br>" + "Or add a new one" + "</br>");   
            
            out.println("<form action=\"StartPageServlet\" method=\"GET\">");
            out.println("<br/> New music type: <input type=\"textbox\" name=\"musictype\"/><br/>");
            out.println("<input type=\"submit\" value=\"Add type and vote\"/>");
            out.println("</form>");
 
            out.println("</body>");
            out.println("</html>");
            
            resultSet.close();
            readStatement.close();
            connection.close();
        }catch(Exception e){
            out.println("Error occurred " + e.getMessage());
        }finally{
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
