package com.mycompany.data.dataAccess.Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    final private String url;
    final private String user;
    final private String password;

    public DatabaseConnection() {
        this.url = "jdbc:postgresql://tek-mmmi-db0a.tek.c.sdu.dk:5432/si3_2019_group_2_db";
        this.user = "si3_2019_group_2";
        this.password = "did3+excises";
    }

    private Connection connect() throws SQLException, ClassNotFoundException {
        System.out.println("succes");
        return DriverManager.getConnection(url, user, password);

    }

    private PreparedStatement prepareStatement(String query, Object... values) throws SQLException, ClassNotFoundException {
        Connection con = connect();

        PreparedStatement statement = con.prepareStatement(query);

        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
        return statement;
    }

        public int queryUpdate(String query, Object... values) {
        int affectedRows = 0;
        try (PreparedStatement statement = prepareStatement(query, values)) {

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return affectedRows;
        }
    }
    
    public static void main(String[] args) {
        DatabaseConnection conn = new DatabaseConnection();
        try {
            conn.connect();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}