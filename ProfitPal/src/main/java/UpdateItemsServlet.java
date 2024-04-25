@WebServlet("/UpdateItemsServlet")
public class UpdateItemsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void UpdateItems(String username, String[] items, String[] prices) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Assignment4?user=root&password=root");
            for(int i = 0; i < items.length; i++){
                String item = items[i];
                double price = Double.parseDouble(prices[i]);
                String query = "UPDATE items SET price = ? WHERE username = ? AND item = ?";
                ps = conn.prepareStatement(query);
                ps.setDouble(1, price);
                ps.setString(2, username);
                ps.setString(3, item);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String[] items = request.getParameterValues("items");
        String[] prices = request.getParameterValues("prices");

        UpdateItems(username, items, prices);
}