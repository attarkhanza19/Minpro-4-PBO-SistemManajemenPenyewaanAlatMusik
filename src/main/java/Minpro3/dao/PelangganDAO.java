package Minpro3.dao;

import Minpro3.Database.DatabaseConnection;
import Minpro3.Model.Pelanggan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PelangganDAO {

    public ArrayList<Pelanggan> getAll() {

        ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();

        String sql = "SELECT * FROM pelanggan";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pelanggan pelanggan = new Pelanggan(
                        rs.getString("id_pelanggan"),
                        rs.getString("nama_pelanggan"),
                        rs.getString("no_telepon"),
                        rs.getString("alamat")
                );

                daftarPelanggan.add(pelanggan);
            }

        } catch (SQLException e) {
            System.out.println("Gagal membaca data pelanggan: "
                    + e.getMessage());
        }

        return daftarPelanggan;
    }

    public boolean tambah(Pelanggan pelanggan) {

        String sql = "INSERT INTO pelanggan "
                + "(id_pelanggan, nama_pelanggan, no_telepon, alamat) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pelanggan.getIdPelanggan());
            ps.setString(2, pelanggan.getNamaPelanggan());
            ps.setString(3, pelanggan.getNoTelepon());
            ps.setString(4, pelanggan.getAlamat());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menambah pelanggan: "
                    + e.getMessage());
            return false;
        }
    }

    public boolean ubah(Pelanggan pelanggan) {

        String sql = "UPDATE pelanggan SET nama_pelanggan = ?, "
                + "no_telepon = ?, alamat = ? "
                + "WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pelanggan.getNamaPelanggan());
            ps.setString(2, pelanggan.getNoTelepon());
            ps.setString(3, pelanggan.getAlamat());
            ps.setString(4, pelanggan.getIdPelanggan());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal mengubah pelanggan: "
                    + e.getMessage());
            return false;
        }
    }

    // DELETE: Menghapus pelanggan berdasarkan ID
    public boolean hapus(String idPelanggan) {

        String sql = "DELETE FROM pelanggan WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPelanggan);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus pelanggan: "
                    + e.getMessage());
            return false;
        }
    }

    public Pelanggan cariById(String idPelanggan) {

        String sql = "SELECT * FROM pelanggan WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPelanggan);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Pelanggan(
                            rs.getString("id_pelanggan"),
                            rs.getString("nama_pelanggan"),
                            rs.getString("no_telepon"),
                            rs.getString("alamat")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal mencari pelanggan: "
                    + e.getMessage());
        }

        return null;
    }
}