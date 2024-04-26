import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
	
		Gson gson = new Gson();
		
		if(password == null || password.isBlank() || email == null || email.isBlank() || firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "User info missing";
			pw.write(gson.toJson(error));
			pw.flush();
		}
		
		int userID = registerUser(password, email);
		
		if(userID == -1) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "Username is taken";
			pw.write(gson.toJson(error));
			pw.flush();
		}else if(userID == -2) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "Email is already registered";
			pw.write(gson.toJson(error));
			pw.flush();
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
			pw.write("User successfully registered");
			pw.flush();
		}
		
	}
	public static int registerUser(String password, String email) throws UnsupportedEncodingException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		int userID = -1;
		
		try {
			//TODO: change this to your SQL password when testing
			String encodedPassword = URLEncoder.encode("nR81&U1P1v}E", "UTF-8");
			String url = "jdbc:mysql://localhost/homework4?user=root&password=" + encodedPassword;
			conn = DriverManager.getConnection(url);
			
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM users WHERE email='" + email + "'");
			if(!rs.next()) {
				//no user with that email either
				rs.close();
				st.execute("INSERT INTO users(pass, email) VALUES ('" + password + "', '" + email + "'");
				rs = st.executeQuery("SELECT LAST_INSERT_ID()");
				rs.next();
				userID = rs.getInt(1);
			}else {
				userID = -2;
			}
		}catch(SQLException sqle) {
			System.out.println("SQLException in registerUser. ");
			sqle.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(st != null) {
					st.close();
				}
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return userID;
	}
	
}
