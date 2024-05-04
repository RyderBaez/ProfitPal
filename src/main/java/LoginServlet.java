// only need to validate that an email exists

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String password = request.getParameter("password");
		String email = request.getParameter("email");
		password = hashPassword(password);

		Gson gson = new Gson();
		
		//No null checking is necessary; done on frontend
		
		int userID = loginUser(password, email);
		
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
	public static int loginUser(String password, String email) throws UnsupportedEncodingException {
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
			String encodedPassword = URLEncoder.encode("root", "UTF-8");
			String url = "jdbc:mysql://localhost/ProfitPal?user=root&password=" + encodedPassword;
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM ProfitPal.Users WHERE email='" + email + "' AND password='" + password + "'");
			if(rs.next()) {
				userID = rs.getInt("userID");
			}
		}catch(SQLException sqle) {
			System.out.println("SQLException in LoginServlet. ");
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
	private static String hashPassword(String password) {
	    try {
	    	System.out.println("hashing fct reached");
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(password.getBytes("UTF-8"));
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : hash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }
	        System.out.println("new password: " + hexString.toString());
	        return hexString.toString();
	    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}