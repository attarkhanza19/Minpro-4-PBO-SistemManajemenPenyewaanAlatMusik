package Minpro3.dao;

import Minpro3.Database.DatabaseConnection;
import Minpro3.Model.AlatMusik;
import Minpro3.Model.AlatMusikAkustik;
import Minpro3.Model.AlatMusikElektrik;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class AlatMusikDAO {

    public ArrayList<AlatMusik> getAll() {

        ArrayList<AlatMusik> daftarAlat = new ArrayList<>();

        String sql = "SELECT * FROM alat_musik";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String id = rs.getString("id_alat");
                String nama = rs.getString("nama_alat");
                String jenis = rs.getString("jenis_alat");
                double harga = rs.getDouble("harga_sewa");
                String status = rs.getString("status");
                String bahan = rs.getString("bahan");
                int daya = rs.getInt("daya");

                if (bahan != null) {
                    daftarAlat.add(new AlatMusikAkustik(
                            id, nama, jenis, harga, status, bahan
                    ));
                } else {
                    daftarAlat.add(new AlatMusikElektrik(
                            id, nama, jenis, harga, status, daya
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal membaca data alat musik: "
                    + e.getMessage());
        }

        return daftarAlat;
    }

    public boolean tambah(AlatMusik alat) {

        String sql = "INSERT INTO alat_musik "
                + "(id_alat, nama_alat, jenis_alat, harga_sewa, "
                + "status, bahan, daya) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, alat.getIdAlat());
            ps.setString(2, alat.getNamaAlat());
            ps.setString(3, alat.getJenisAlat());
            ps.setDouble(4, alat.getHargaSewa());
            ps.setString(5, alat.getStatus());

            if (alat instanceof AlatMusikAkustik) {
                AlatMusikAkustik akustik = (AlatMusikAkustik) alat;

                ps.setString(6, akustik.getBahan());
                ps.setNull(7, Types.INTEGER);

            } else if (alat instanceof AlatMusikElektrik) {
                AlatMusikElektrik elektrik = (AlatMusikElektrik) alat;

                ps.setNull(6, Types.VARCHAR);
                ps.setInt(7, elektrik.getDaya());

            } else {
                throw new SQLException("Jenis alat musik tidak dikenali.");
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menambah alat: " + e.getMessage());
            return false;
        }
    }

    public boolean ubah(AlatMusik alat) {

        String sql = "UPDATE alat_musik SET nama_alat = ?, "
                + "jenis_alat = ?, harga_sewa = ?, status = ?, "
                + "bahan = ?, daya = ? WHERE id_alat = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, alat.getNamaAlat());
            ps.setString(2, alat.getJenisAlat());
            ps.setDouble(3, alat.getHargaSewa());
            ps.setString(4, alat.getStatus());

            if (alat instanceof AlatMusikAkustik) {
                AlatMusikAkustik akustik = (AlatMusikAkustik) alat;

                ps.setString(5, akustik.getBahan());
                ps.setNull(6, Types.INTEGER);

            } else if (alat instanceof AlatMusikElektrik) {
                AlatMusikElektrik elektrik = (AlatMusikElektrik) alat;

                ps.setNull(5, Types.VARCHAR);
                ps.setInt(6, elektrik.getDaya());

            } else {
                throw new SQLException("Jenis alat musik tidak dikenali.");
            }

            ps.setString(7, alat.getIdAlat());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal mengubah alat: " + e.getMessage());
            return false;
        }
    }

    public boolean hapus(String idAlat) {

        String sql = "DELETE FROM alat_musik WHERE id_alat = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idAlat);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus alat: " + e.getMessage());
            return false;
        }
    }
}