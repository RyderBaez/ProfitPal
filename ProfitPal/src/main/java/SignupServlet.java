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

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		User user = new Gson.fromJson(request.getReader(), User.class);
		
		String username = user.username;
		String password = user.password;
		String email = user.email;
		int balance = user.balance;
		
		Gson gson = new Gson();
		
		if(username == null || username.isBlank() || password == null || password.isBlank() || email == null || email.isBlank()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "User info missing";
			pw.write(gson.toJson(error));
			pw.flush();
		}
		
		int userID = JDBCConnector.registerUser(username, password, email);
		
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
			pw.write(gson.toJson(error));
			pw.flush();
		}
		
	}
	public static int registerUser(String username, String password, String email) {
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ass4?user=rot&password=Test@12345");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM users WHERE email='" + email + "'");
			if(!rs.next()) {
				//no user with that email either
				rs.close();
				st.execute("INSERT INTO users(username, pass, email, balance) VALUES ('" + username + "', '" + password + "', '" + email + "', 50000)");
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
