package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.data.DbUtils.PaymentEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {

    private static String url = System.getProperty("datasource.url");
    private static String user = "app";
    private static String password = "pass";

    public DbHelper() {
    }

    @SneakyThrows
    public static void cleanData() {
        QueryRunner runner = new QueryRunner();
        String cleanCreditRequest = "DELETE FROM credit_request_entity;";
        String cleanPayment = "DELETE FROM payment_entity;";
        String cleanOrder = "DELETE FROM order_entity;";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            runner.update(conn, cleanCreditRequest);
            runner.update(conn, cleanPayment);
            runner.update(conn, cleanOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static PaymentEntity payData() {
        QueryRunner runner = new QueryRunner();
        String reqStatus = "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;";


        PaymentEntity payData = new PaymentEntity();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            payData = runner.query(conn, reqStatus, new BeanHandler<>(PaymentEntity.class));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payData;
    }

}