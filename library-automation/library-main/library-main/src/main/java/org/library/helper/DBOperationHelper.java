package org.library.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBOperationHelper {

	private static DBOperationHelper instance = null;

	private DBOperationHelper() {
	}

	public static DBOperationHelper getInstance() {
		if (instance == null) {
			synchronized (DBOperationHelper.class) {
				instance = new DBOperationHelper();
			}
		}
		return instance;
	}

	private String sqlDosyasiOku(String fileName) throws IOException {
		try (InputStream is = DBOperationHelper.class.getClassLoader().getResourceAsStream("sqlSorgulari/" + fileName);
		     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		}
	}

	private void sorguCalistir(String sql, Object... params) {
		System.out.println("Veritabanı işlemi başlatılıyor: " + sql);
		try (Connection conn = DBConnectionHelper.getInstance().getConnection();
		     PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

			for (int i = 0; i < params.length; i++) {
				preparedStatement.setObject(i + 1, params[i]);
				System.out.println("Parametre " + (i + 1) + ": " + params[i]);
			}

			int affectedRows = preparedStatement.executeUpdate();
			System.out.println("Etkilenen satır sayısı: " + affectedRows);
		} catch (SQLException e) {
			System.out.println("SQL Hatası: " + e.getMessage());
		}
		System.out.println("Veritabanı işlemi tamamlandı.");
	}

	public void dosyadanSorguCalistir(String sqlFilePath, Object... params) {
		try {
			String sql = sqlDosyasiOku(sqlFilePath);
			sorguCalistir(sql, params);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public <T> List<T> listeSorgusuCalistir(String sqlFilePath, ResultSetHandler<T> handler, Object... params) {

		List<T> results = new ArrayList<>();
		String sql = null;
		try {
			sql = sqlDosyasiOku(sqlFilePath);
			System.out.println("SQL Sorgusu: " + sql);
		} catch (IOException e) {
			System.out.println("SQL dosyası okuma hatası " + e.getMessage());
			return results;
		}

		try (Connection conn = DBConnectionHelper.getInstance().getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(sql)) {

			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
				System.out.println("Parametre " + (i + 1) + ": " + params[i]);
			}

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					results.add(handler.handle(rs));
				}
			}
		} catch (SQLException e) {
			System.out.println("Veritabanı sorgusu çalıştırma hatası " + e.getMessage());
		}

		return results;
	}

	public interface ResultSetHandler<T> {
		T handle(ResultSet rs) throws SQLException;
	}
}
