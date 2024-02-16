package com.study.product.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.study.product.config.DBConfig;
import com.study.product.config.DBConnectionMgr;
import com.study.product.vo.Product;

public class ProductDao {
	
	private static ProductDao instance;
	private DBConnectionMgr pool;
		
	private ProductDao() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static ProductDao getInstance() {
		if(instance == null) {
			instance = new ProductDao();
		}
		return instance;
	}
	
	public List<Product> getProductList() {
		List<Product> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			String sql = "select * from product_tb";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Product product = Product.builder()
						.productId(rs.getInt(1))
						.name(rs.getString(2))
						.price(rs.getInt(3))
						.size(rs.getString(4))
						.build();
				
				list.add(product);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return list;
	}

	public Product findProductByName(String name) {
		Product product = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = pool.getConnection(); 
			String sql = "select * from product_tb where product_name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = Product.builder()
						.productId(rs.getInt(1))
						.name(rs.getString(2))
						.price(rs.getInt(3))
						.size(rs.getString(4))
						.build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return product;
	}
	
	public int saveProduct(Product product) {
		int successCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			String sql = "insert into product_tb(product_name, product_price, product_size) values(?, ?, ?)";
//			String sql = "insert into product_tb values(0, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, product.getName());
			pstmt.setInt(2, product.getPrice());
			pstmt.setString(3, product.getSize());
			successCount = pstmt.executeUpdate(); 
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				product.setProductId(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return successCount; 
	}
	
}
