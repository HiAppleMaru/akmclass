package kr.ac.kopo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ClacServlet
 */
@WebServlet("/ClacServlet")
public class ClacServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClacServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String button = request.getParameter("button");
		
		HttpSession session = request.getSession();
		
		Calc calc = (Calc) session.getAttribute("calc");
		if(calc == null) {
			calc = new Calc();
			session.setAttribute("calc", calc);			
		}
		
		calc.onButton(button);
		
		request.setAttribute("display", calc.getDisplay());
		
		request.getRequestDispatcher("/WEB-INF/views/calc.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
