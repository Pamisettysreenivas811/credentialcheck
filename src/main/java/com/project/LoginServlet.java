package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String name = req.getParameter("emails");
		String password = req.getParameter("pass");
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","9014527199");
			
			PreparedStatement ps = con.prepareStatement("select email,password from details where email=? and password=?");
			
			ps.setString(1,name);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				if(rs.getString(1).equalsIgnoreCase(name) && rs.getString(2).equals(password))
				{
					RequestDispatcher rd = req.getRequestDispatcher("home.html");
					rd.forward(req, resp);
				}
				else
				{
					resp.setContentType("text/html");
					
					PrintWriter pw = resp.getWriter();
					pw.print("Incorrect username or password");
					
					RequestDispatcher rd = req.getRequestDispatcher("login.html");
					rd.include(req, resp);
					
				}
				
			}
			else
			{
				resp.setContentType("text/html");
				
				PrintWriter pw = resp.getWriter();
				pw.print("Incorrect username or password");
				
				RequestDispatcher rd = req.getRequestDispatcher("login.html");
				rd.include(req, resp);
		
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
