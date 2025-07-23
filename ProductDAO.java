package dao;

import models.Sale;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    public static void addSale(Sale sale) throws SQLException {
        String sql = "INSERT INTO sales (product_id, quantity_sold) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sale.getProductId());
            pstmt.setInt(2, sale.getQuantitySold());
            pstmt.executeUpdate();
        }
    }

    // DTO for Sale Summary (can be an inner class or separate file)
    public static class SaleSummaryDTO {
        public String productName;
        public int totalQuantitySold;
        public double totalRevenue;

        public SaleSummaryDTO(String productName, int totalQuantitySold, double totalRevenue) {
            this.productName = productName;
            this.totalQuantitySold = totalQuantitySold;
            this.totalRevenue = totalRevenue;
        }
    }

    public static List<SaleSummaryDTO> getSalesSummary() throws SQLException {
        List<SaleSummaryDTO> summary = new ArrayList<>();
        String sql = "SELECT p.name AS product_name, SUM(s.quantity_sold) AS total_qty_sold, " +
                     "SUM(s.quantity_sold * p.price) AS total_revenue " +
                     "FROM sales s JOIN products p ON s.product_id = p.id " +
                     "GROUP BY p.name ORDER BY total_revenue DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                summary.add(new SaleSummaryDTO(
                    rs.getString("product_name"),
                    rs.getInt("total_qty_sold"),
                    rs.getDouble("total_revenue")
                ));
            }
        }
        return summary;
    }
}
