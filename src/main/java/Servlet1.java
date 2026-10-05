import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("uname") == null) {
			response.sendRedirect("index.html?e=2");
			return;
		}

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<br>"+"Welcome " + session.getAttribute("uname") + "<br>");
		out.print("<a href='Servlet2'>visit</a>  ");
		
		out.print("<script>window.addEventListener('pageshow', function(e){ if (e.persisted) location.reload(); });</script>");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String n = request.getParameter("userName");

		if (n == null || !n.trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,30}")) {
			response.sendRedirect("index.html?e=1");
			return;
		}

		HttpSession old = request.getSession(false);
		if (old != null) {
			old.invalidate();
		}

		HttpSession session = request.getSession(true);
		session.setAttribute("uname", n.trim());

		response.sendRedirect("Servlet1");
	}
}