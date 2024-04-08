package org.library.helper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionHelper {
	private static final String PROPERTIES_FILE = "db.properties";
	private static DBConnectionHelper instance;
	private Connection connection;

	private DBConnectionHelper() {
		veritabaninaBaglan();
	}

	private void veritabaninaBaglan() {
		Properties props = new Properties();
		try (InputStream fis = DBConnectionHelper.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			props.load(fis);
		} catch (IOException e) {
			throw new RuntimeException("Veritabanı ayar dosyası yüklenemedi", e);
		}

		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String password = props.getProperty("db.password");

		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new RuntimeException("Veritabanına bağlanılamadı", e);
		}
	}

	public static synchronized DBConnectionHelper getInstance() {
		if (instance == null) {
			instance = new DBConnectionHelper();
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			if(connection == null || connection.isClosed()){
				veritabaninaBaglan();
			}
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return connection;
	}
}
