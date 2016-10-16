/*  |------------------------------------------|
 *  |    Passes session and context to HTML    |
 *  |--------------------|---------------------|
 *  |    Thi & Daniel    |      10-16-16       |
 *  |--------------------|---------------------|
 */
package edu.ccsu.cs416a2;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SessionContext", urlPatterns = {"/SessionContext"})
public class SessionContext extends HttpServlet {

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

        //Gets sessionVotes from StartPageServlet through getAttribute("sessionVotes);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Integer sessionVotes = (Integer) session.getAttribute("sessionVotes");
        if (sessionVotes == null) {
            sessionVotes = 0;
        }

        ServletContext context = request.getServletContext();
        Integer contextVotes = (Integer) context.getAttribute("contextVotes");
        if (contextVotes == null) {
            contextVotes = 0;
        }

        out.println("I have voted " + sessionVotes + " times.<br/>");
        out.println("All users since the server started have voted " + contextVotes + " times.");
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
