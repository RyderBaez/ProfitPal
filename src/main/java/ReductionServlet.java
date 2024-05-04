import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/ReductionServlet")

public class ReductionServlet extends HttpServlet {
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
	        Budget budget = gson.fromJson(xmlData, Budget.class);
	        
	        String user = budget.getUser();
	        double grocery = budget.getGrocery();
	        double gas = budget.getGas();
	        double restaurant = budget.getRestaurant();
	        double shopping = budget.getShopping();
	        
	        double newGrocery = 0.0;
	        double newGas = 0.0;
	        double newRestaurant = 0.0;
	        double newShopping = 0.0;
	        
	        double gasChange = -1;
	        double groceryChange = 1;
		    double restaurantChange = 0.0;
		 	double shoppingChange = 0.0;
    
	        if(shopping > restaurant) {
	        	newRestaurant = Double.parseDouble(String.format("%.2f", (restaurant * .9)));
	        	newShopping = Double.parseDouble(String.format("%.2f", (shopping * .95)));
	        	restaurantChange = -10.0;
	        	shoppingChange = -5.0;
	        } else {
	        	newRestaurant = Double.parseDouble(String.format("%.2f", (restaurant * .95)));
	        	newShopping = Double.parseDouble(String.format("%.2f", (shopping * .9)));
	        	restaurantChange = -5.0;
	        	shoppingChange = -10.0;
	        }
	        
	        newGrocery = Double.parseDouble(String.format("%.2f", (grocery * 1.01)));
	        newGas = Double.parseDouble(String.format("%.2f", (gas * .99)));
	        
	        double monetaryGasReduction =  Double.parseDouble(String.format("%.2f", (gas - newGas))); 
	        double monetaryRestaurantReduction = Double.parseDouble(String.format("%.2f", (restaurant - newRestaurant)));  
	        double monetaryShoppingReduction = Double.parseDouble(String.format("%.2f", (shopping - newShopping)));  
	        double monetaryGroceryReduction = Double.parseDouble(String.format("%.2f", (grocery - newGrocery)));  
	        
	        double totalSaved = monetaryGasReduction + monetaryRestaurantReduction + monetaryShoppingReduction + monetaryGroceryReduction;
	        Random rand = new Random();
	        //Change to size of vector
	        int index = rand.nextInt(3);
	        //Add in order of price.
	        Vector<String> stockVec = new Vector<String>();
	        stockVec.add("PLTR");
	        stockVec.add("SKYW");
	        stockVec.add("TSLA");
	        double price = getStockPrice(stockVec.get(index), gson);
	        boolean hasEnough = true;
	        while(price > totalSaved) {
	        	index--;
	        	if(index < 0) {
	        		break;
	        	}
	        	price = getStockPrice(stockVec.get(index), gson);
	        }
	        
	        budget.setGas(newGas);
	        budget.setRestaurant(newRestaurant);
	        budget.setShopping(newShopping);
	        budget.setGrocery(newGrocery);
	        
	        Map<String, Object> resultMap = new HashMap<>();
	        resultMap.put("budget", budget);
	        resultMap.put("monetaryGasReduction", monetaryGasReduction);
	        resultMap.put("monetaryRestaurantReduction", monetaryRestaurantReduction);
	        resultMap.put("monetaryShoppingReduction", monetaryShoppingReduction);
	        resultMap.put("monetaryGroceryReduction", monetaryGroceryReduction);
	        resultMap.put("gasChange", gasChange);
	        resultMap.put("groceryChange", groceryChange);
	        resultMap.put("restaurantChange", restaurantChange);
	        resultMap.put("shoppingChange", shoppingChange);
	        resultMap.put("totalSaved", totalSaved);
	        if(index >= 0) {
	        	resultMap.put("ticker", stockVec.get(index));
	        }
	        else {
	        	resultMap.put("ticker",  "NONE");
	        }
	        
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
				
				rs = st.executeQuery("SELECT * FROM ProfitPal.BudgetItems WHERE username='" + user + "'");
				if(!rs.next()) {
					rs.close();
					System.out.println("in here1");
//					st.execute("INSERT INTO ProfitPal.Users(email, password) VALUES ('" + email + "', '" + password + "')");

					st.execute("INSERT INTO ProfitPal.BudgetItems(username, category, spending) VALUES ('" + user + "', '" + grocery_ + "', '" + grocery + "')");
					st.execute("INSERT INTO ProfitPal.BudgetItems(username, category, spending) VALUES ('" + user + "', '" + gas_ + "', '" + gas + "')");
					st.execute("INSERT INTO ProfitPal.BudgetItems(username, category, spending) VALUES ('" + user + "', '" + shopping_ + "', '" + shopping + "')");
					st.execute("INSERT INTO ProfitPal.BudgetItems(username, category, spending) VALUES ('" + user + "', '" + restaurant_ + "', '" + restaurant + "')");
				}else {
					st.execute("UPDATE ProfitPal.BudgetItems SET spending='" + grocery + "' WHERE username='" + user + "' AND category='" + grocery_ + "'");
					st.execute("UPDATE ProfitPal.BudgetItems SET spending='" + gas + "' WHERE username='" + user + "' AND category='" + gas_ + "'");
					st.execute("UPDATE ProfitPal.BudgetItems SET spending='" + restaurant + "' WHERE username='" + user + "' AND category='" + restaurant_ + "'");
					st.execute("UPDATE ProfitPal.BudgetItems SET spending='" + shopping + "' WHERE username='" + user + "' AND category='" + shopping_ + "'");
				}
				
//				st.executeUpdate("SELECT * FROM ProfitPal.Users WHERE email='" + email + "' AND password='" + password + "'");
				
				
			}catch(SQLException sqle) {
				System.out.println("SQLException in ReductionServlet.");
				sqle.printStackTrace();
			}
    		
	        String jsonResponse = gson.toJson(resultMap);
	        System.out.println(jsonResponse);
	        response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
    }
    private static double getStockPrice(String ticker, Gson gson) throws IOException {
		URL url = new URL(String.format("https://finnhub.io/api/v1/quote?symbol=%s&token=%s", ticker,
				"cnvouq9r01qmeb8u3pqgcnvouq9r01qmeb8u3pr0"));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String json = in.readLine();
		StockPrice stockPrice = gson.fromJson(json, StockPrice.class);
		return stockPrice.getC();
	}
}