import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
    		
	        String jsonResponse = gson.toJson(resultMap);
	        System.out.println(jsonResponse);
	        response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
    }
}
