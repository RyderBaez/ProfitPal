import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/LoadItemsServlet")

public class LoadItemsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	StringBuilder xmlStringBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            while ((line = reader.readLine()) != null) {
                xmlStringBuilder.append(line);
            }
        } catch (Exception e) {
            throw new ServletException("Error reading XML content", e);
        }

        // Convert StringBuilder to String
        String xmlData = xmlStringBuilder.toString();
		System.out.println(xmlData);
		
		Gson gson = new Gson();
        Update update = gson.fromJson(xmlData, Update.class);
    	
        String user = update.getUser();
        
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		System.out.println("user");
		System.out.println(user);
		
		try {
			String encodedPassword = URLEncoder.encode("nR81&U1P1v}E", "UTF-8");
			String url = "jdbc:mysql://localhost/ProfitPal?user=root&password=" + encodedPassword;
			conn = DriverManager.getConnection(url);
			
			st = conn.createStatement();
			
			String grocery_ = "grocery";
			String gas_ = "gas";
			String shopping_ = "shopping";
			String restaurant_ = "restaurant";
			
			rs = st.executeQuery("SELECT spending FROM ProfitPal.BudgetItems WHERE username='" + user + "' AND category='" + grocery_ + "'");
			Double grocery = 0.0;
			if(rs.next()) {
				grocery = Double.parseDouble(String.format("%.2f", (rs.getDouble("spending"))));
			}
			
			rs = st.executeQuery("SELECT spending FROM ProfitPal.BudgetItems WHERE username='" + user + "' AND category='" + gas_ + "'");
			Double gas = 0.0;
			if(rs.next()) {
				gas = Double.parseDouble(String.format("%.2f", (rs.getDouble("spending"))));
			}
			
			rs = st.executeQuery("SELECT spending FROM ProfitPal.BudgetItems WHERE username='" + user + "' AND category='" + shopping_ + "'");
			Double shopping = 0.0;
			if(rs.next()) {
				shopping = Double.parseDouble(String.format("%.2f", (rs.getDouble("spending"))));
			}
			
			rs = st.executeQuery("SELECT spending FROM ProfitPal.BudgetItems WHERE username='" + user + "' AND category='" + restaurant_ + "'");
			Double restaurant = 0.0;
			if(rs.next()) {
				restaurant = Double.parseDouble(String.format("%.2f", (rs.getDouble("spending"))));
			}

			Map<String, Double> resultMap = new HashMap<>();
	        resultMap.put("gas", gas);
	        resultMap.put("grocery", grocery);
	        resultMap.put("shopping", shopping);
	        resultMap.put("restaurant", restaurant);
	        System.out.println(resultMap);
	        String jsonResponse = gson.toJson(resultMap);
	        response.setContentType("application/json");
            response.getWriter().write(jsonResponse);			
			
		}catch(SQLException sqle) {
			System.out.println("SQLException in LoadItemsServlet.");
			sqle.printStackTrace();
		}
		
    }
}