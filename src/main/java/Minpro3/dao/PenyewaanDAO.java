
package Minpro3.dao;

import Minpro3.Database.DatabaseConnection;
import Minpro3.Model.Penyewaan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PenyewaanDAO {

    public ArrayList<Penyewaan> getAll() {

        ArrayList<Penyewaan> daftarPenyewaan = new ArrayList<>();

        String sql = "SELECT * FROM penyewaan";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Penyewaan penyewaan = new Penyewaan(
                        rs.getString("id_penyewaan"),
                        rs.getString("id_pelanggan"),
                        rs.getString("id_alat"),
                        rs.getInt("lama_sewa"),
                        rs.getDouble("total_harga")
                );

                daftarPenyewaan.add(penyewaan);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Gagal membaca data penyewaan: " + e.getMessage()
            );
        }

        return daftarPenyewaan;
    }

    public boolean tambah(Penyewaan penyewaan, double diskon) {

        String sql = "INSERT INTO penyewaan "
                + "(id_penyewaan, id_pelanggan, id_alat, "
                + "lama_sewa, diskon, total_harga) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, penyewaan.getIdPenyewaan());
            ps.setString(2, penyewaan.getIdPelanggan());
            ps.setString(3, penyewaan.getIdAlat());
            ps.setInt(4, penyewaan.getLamaSewa());
            ps.setDouble(5, diskon);
            ps.setDouble(6, penyewaan.getTotalHarga());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Gagal menambah penyewaan: " + e.getMessage()
            );
            return false;
        }
    }

    public boolean tambah(Penyewaan penyewaan) {
        return tambah(penyewaan, 0);
    }

    public boolean ubah(Penyewaan penyewaan, double diskon) {

        String sql = "UPDATE penyewaan SET "
                + "id_pelanggan = ?, id_alat = ?, "
                + "lama_sewa = ?, diskon = ?, total_harga = ? "
                + "WHERE id_penyewaan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, penyewaan.getIdPelanggan());
            ps.setString(2, penyewaan.getIdAlat());
            ps.setInt(3, penyewaan.getLamaSewa());
            ps.setDouble(4, diskon);
            ps.setDouble(5, penyewaan.getTotalHarga());
            ps.setString(6, penyewaan.getIdPenyewaan());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Gagal mengubah penyewaan: " + e.getMessage()
            );
            return false;
        }
    }

    public boolean ubah(Penyewaan penyewaan) {
        return ubah(penyewaan, 0);
    }

    public boolean hapus(String idPenyewaan) {

        String sql = "DELETE FROM penyewaan "
                + "WHERE id_penyewaan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPenyewaan);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Gagal menghapus penyewaan: " + e.getMessage()
            );
            return false;
        }
    }

    public Penyewaan cariById(String idPenyewaan) {

        String sql = "SELECT * FROM penyewaan "
                + "WHERE id_penyewaan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPenyewaan);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Penyewaan(
                            rs.getString("id_penyewaan"),
                            rs.getString("id_pelanggan"),
                            rs.getString("id_alat"),
                            rs.getInt("lama_sewa"),
                            rs.getDouble("total_harga")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Gagal mencari penyewaan: " + e.getMessage()
            );
        }

        return null;
    }
}