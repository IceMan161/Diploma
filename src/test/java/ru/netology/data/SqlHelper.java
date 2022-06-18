package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                System.getProperty("db.url"),
                System.getProperty("db.login"),
                System.getProperty("db.password")
        );
    }

    public static void cleanDB() {
        val debitPayment = "DELETE FROM payment_entity";
        val creditRequest = "DELETE FROM credit_request_entity";
        val order = "DELETE FROM order_entity";
        val runner = new QueryRunner();
        try (val connection = getConnection();
        ) {
            runner.update(connection, debitPayment);
            runner.update(connection, creditRequest);
            runner.update(connection, order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getStatusDebitPaymentEntity() {
        try (val connection = getConnection();
             val countStmt = connection.createStatement()) {
            val sql = "SELECT status FROM payment_entity;";
            val resultSet = countStmt.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStatusCreditRequestEntity() {
        try (val connection = getConnection();
             val countStmt = connection.createStatement()) {
            val sql = "SELECT status FROM credit_request_entity;";
            val resultSet = countStmt.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
