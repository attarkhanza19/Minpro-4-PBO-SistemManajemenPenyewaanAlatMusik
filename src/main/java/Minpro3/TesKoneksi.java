package Minpro3;

import Minpro3.Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TesKoneksi {

    public static void main(String[] args) {

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Koneksi database berhasil!");

        } catch (SQLException e) {
            System.out.println("Koneksi database gagal!");
            System.out.println("Pesan error: " + e.getMessage());
        }
    }
}