package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Phones;
import database.DatabaseConn;

public class DetaSer extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DetaSer() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sql;
		String phoneID=request.getParameter("phoneID");
		HttpSession session=request.getSession();
		Phones phone = new Phones();
		DatabaseConn conn = new DatabaseConn();
		sql="select * from phones where phoneID='"+phoneID+"'";
		try {
		if(conn.getFreeConnection()){
			if(conn.getStatement()){
				ResultSet rs=conn.getResult(sql);				
					if(rs.next()){
						phone.setPhoneID(rs.getString("phoneID"));
						phone.setPname(rs.getString("pname"));
						phone.setPrice(rs.getDouble("price"));
						phone.setKinds(rs.getInt("kinds"));
						phone.setImg(rs.getString("img"));
						phone.setDescrib(rs.getString("describ"));
					}
				session.setAttribute("deta",phone);
				response.sendRedirect("details.jsp");
			}
			}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}finally{
				conn.closeResultSet();
				conn.closeStatement();
				conn.closeConnection();
		}	
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
