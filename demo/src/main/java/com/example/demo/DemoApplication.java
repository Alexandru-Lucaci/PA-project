package com.example.demo;

import com.example.demo.database.ContDAO;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.SQLException;

@SpringBootApplication
@EnableSwagger2
public class DemoApplication {

	private static final String user="ALEX";
	private static final String password="tehnologiiweb";
	public  static HikariDataSource dataSource;
	public static void initDatabaseConnectionPool(){
		dataSource = new HikariDataSource();
		dataSource.setJdbcUrl("jdbc:oracle:thin:@//localhost:1521/XE");
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setAutoCommit(false);

	}

	public static void main(String[] args) {
		initDatabaseConnectionPool();
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("hello spring :D.");

	}

}
