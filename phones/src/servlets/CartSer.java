package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Orders;
import bean.Phones;
import database.DatabaseConn;

public class CartSer extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CartSer() {
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

		doPost(request,response);
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


		HttpSession session = request.getSession();
		List<Orders> list = new ArrayList<Orders>();
		String sql;
		if(session.getAttribute("memberID")==null){
			session.setAttribute("error", "����û�е�¼�����ȵ�¼��");
			response.sendRedirect("login.jsp");
		}else{
			DatabaseConn conn = new DatabaseConn();
			String memberID=session.getAttribute("memberID").toString();
			sql="select * from orders,phones where done='���ﳵ' and memberID='"+memberID+"' and orders.phoneID=phones.phoneID";
			//System.out.println(sql);
			try {
			if(conn.getFreeConnection()){
				if(conn.getStatement()){
					ResultSet rs=conn.getResult(sql);					
						while(rs.next()){
							double price=rs.getDouble("price");
							Orders order = new Orders();
							order.setOrderID(rs.getInt("orderID"));
							order.setMemberID(rs.getString("memberID"));
							order.setPhoneID(rs.getString("phoneID"));
							order.setNum(rs.getInt("num"));
							order.setImg(rs.getString("img"));
							order.setPrice(price);
							list.add(order);
						}
						session.setAttribute("cartList", list);
			            response.sendRedirect("cart.jsp");
				  }
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
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
