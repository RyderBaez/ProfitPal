@WebServlet("/LoadItemsServlet")

public class LoadItemsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        response.setContentType("json/application");

        JsonObject responseJson = new JsonObject();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Assignment4?user=root&password=root");
            String query = "SELECT item, price FROM items WHERE username = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while(rs.next()){
                responseJson.addProperty(rs.getString("item"), rs.getDouble("price"));
            }
            response.getWriter().write(responseJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
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
        response.getWriter().write(responseJson.toString());
    }
}