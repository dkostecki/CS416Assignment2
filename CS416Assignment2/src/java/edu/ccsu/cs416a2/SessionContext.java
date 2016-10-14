/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ccsu.cs416a2;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thi
 */
@WebServlet(name = "SessionContext", urlPatterns = {"/SessionContext"})
public class SessionContext extends HttpServlet {
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
            
        //String count = (String)request.getAttribute("passSession");
            
        //Session
        HttpSession session = request.getSession();
        Integer sessionVotes = (Integer)session.getAttribute("sessionVotes");
        if (sessionVotes == null){
            sessionVotes = 0;
            session.setAttribute("sessionVotes", sessionVotes);
        }else{
            sessionVotes = new Integer(sessionVotes.intValue()+1);
        }
        session.setAttribute("sessionVotes", sessionVotes);
        out.println("I have voted " + sessionVotes + " times.");
        
        
        //Context
        ServletContext context = request.getServletContext();
        Integer contextVotes = (Integer)context.getAttribute("contextVotes");
        if (contextVotes == null){
            contextVotes = 0;
            context.setAttribute("contextVotes", contextVotes);
        }else{
            contextVotes = new Integer(contextVotes.intValue()+1);
        }
        context.setAttribute("contextVotes", contextVotes);
        out.println("All users since the server started have voted " + contextVotes + " times.</br>");    
    
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
