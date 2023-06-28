package kr.ac.kopo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet({"/book/list", "/book/add", "/book/update", "/book/delete"}) //전통적으로 값을 여러개 넣을땐 { ,  ,  ,  , }로 표시해줌.
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		if(uri.endsWith("/list")) {	
			ArrayList<Book> list = new ArrayList<Book>();
			
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "madang", "madang");
				Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT * FROM book");
				while(rs.next()) {
					Book item = new Book();
					item.setBookid(rs.getInt(1));
					item.setBookname(rs.getString(2));
					item.setPublisher(rs.getString(3));
					item.setPrice(rs.getInt(4));
					
					list.add(item);
				}
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			request.setAttribute("list", list);
			
			request.getRequestDispatcher("/WEB-INF/book/list.jsp").forward(request, response);
			
		} else if(uri.endsWith("/add")) {
			request.getRequestDispatcher("/WEB-INF/book/add.jsp").forward(request, response);
			
		} else if(uri.endsWith("/update")) {
			String bookid = request.getParameter("bookid");
			
			Book item = new Book();
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "madang", "madang");
				
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE bookid=?"); 
				
				stmt.setString(1, bookid);
				
				ResultSet rs = stmt.executeQuery(); //쿼리 실행해서 rs의 값을 얻음.
				
				if(rs.next()) { //값을 꺼내오고... 세팅
				
				item.setBookid(rs.getInt(1));
				item.setBookname(rs.getString(2));
				item.setPublisher(rs.getString(3));
				item.setPrice(rs.getInt(4));
				}
				
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			request.setAttribute("item", item); 
			
			request.getRequestDispatcher("/WEB-INF/book/update.jsp").forward(request, response);
			
		} else if(uri.endsWith("/delete")) {
			String bookid = request.getParameter("bookid");
			int result = 0;
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "madang", "madang");
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM book WHERE bookid = ?"); 
				stmt.setString(1, bookid);
				result = stmt.executeUpdate();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("result", result);
			request.getRequestDispatcher("/WEB-INF/book/delete.jsp").forward(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		
		if(uri.endsWith("/add")) {
			request.setCharacterEncoding("UTF-8"); //폰트 깨지지 않게 인코딩 추가.

			String bookid = request.getParameter("bookid");
			String bookname = request.getParameter("bookname");
			String publisher = request.getParameter("publisher");
			String price = request.getParameter("price"); //request.getParameter로 소환하면 모두 String으로 되기 때문에
																				// 필요시 형변환 해야함.
			int result = 0;
			
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "madang", "madang");
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?)"); 
				stmt.setString(1, bookid);
				stmt.setString(2, bookname);
				stmt.setString(3, publisher);
				stmt.setString(4, price);
				result = stmt.executeUpdate();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("result", result);
			request.getRequestDispatcher("/WEB-INF/book/add_apply.jsp").forward(request, response);
			
		} else if(uri.endsWith("/update")) {
			request.setCharacterEncoding("UTF-8"); 

			String bookid = request.getParameter("bookid");
			String bookname = request.getParameter("bookname");
			String publisher = request.getParameter("publisher");
			String price = request.getParameter("price"); 
			int result = 0;
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "madang", "madang");
				PreparedStatement stmt = conn.prepareStatement("UPDATE book  SET bookname=?, publisher=?, price=? WHERE bookid=?"); 
				
				stmt.setString(4, bookid);
				stmt.setString(1, bookname);
				stmt.setString(2, publisher);
				stmt.setString(3, price);
				
				result = stmt.executeUpdate();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("result", result);
			request.getRequestDispatcher("/WEB-INF/book/update_apply.jsp").forward(request, response);
			
		}
	}

}
