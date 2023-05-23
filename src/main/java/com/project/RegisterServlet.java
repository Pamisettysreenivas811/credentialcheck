package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		// retrieve the data from the request
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String address = req.getParameter("address");
		String phoneno = req.getParameter("phoneno");
		String password = req.getParameter("password");
		
		// connect with database and insert
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","9014527199");
			
			PreparedStatement ps = con.prepareStatement("insert into details values(?,?,?,?,?)");
			
			PreparedStatement ps1 = con.prepareStatement("select email from details where email=?");
			
			
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, phoneno);
			ps.setString(4, address);
			ps.setString(5, password);
			
			ps1.setString(1,email);
			
			ResultSet rs = ps1.executeQuery();
			if(rs.next())
			{
				resp.setContentType("text/html");
				
				PrintWriter pw = resp.getWriter();
				pw.print("Email id Already Exists");
				
				RequestDispatcher rd = req.getRequestDispatcher("register.html");
				rd.include(req, resp);
			}
			else
			{
				ps.executeUpdate();
				RequestDispatcher rd = req.getRequestDispatcher("login.html");
				rd.forward(req, resp);
				
			}
		
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		// redirecting the control to login.html page
		
	}

}
