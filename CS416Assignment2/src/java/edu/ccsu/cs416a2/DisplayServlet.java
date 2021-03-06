/*  |------------------------------------------|
 *  |          Servlet passing to JSP          |
 *  |--------------------|---------------------|
 *  |    Thi & Daniel    |      10-16-16       |
 *  |--------------------|---------------------|
 */
//pass voting results to JSP for display
package edu.ccsu.cs416a2;

import java.lang.*;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            Connection connection = datasource.getConnection();

            String readSQL = "select * from VOTES";
            PreparedStatement readStatement = connection.prepareStatement(readSQL);
            ResultSet resultSet = readStatement.executeQuery();

            //lists to hold musictype and numvotes
            List<String> music = new ArrayList<>();
            List<String> votes = new ArrayList<>();

            //populate lists
            while (resultSet.next()) {
                music.add(resultSet.getString("musictype"));
                votes.add(resultSet.getString("numvotes"));
            }

            //pass lists to Display.jsp
            request.setAttribute("passedAttribute", music);
            request.setAttribute("passedAttribute2", votes);
            request.getRequestDispatcher("Display.jsp").forward(request, response);

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
