
package Minpro3.controller;

import java.util.ArrayList;

import Minpro3.dao.AlatMusikDAO;
import Minpro3.dao.PelangganDAO;
import Minpro3.dao.PenyewaanDAO;

import Minpro3.Model.AlatMusik;
import Minpro3.Model.DapatDitampilkan;
import Minpro3.Model.Pelanggan;
import Minpro3.Model.Penyewaan;

public class PenyewaanController {

    private final AlatMusikDAO alatDAO;
    private final PelangganDAO pelangganDAO;
    private final PenyewaanDAO penyewaanDAO;

    public PenyewaanController() {
        alatDAO = new AlatMusikDAO();
        pelangganDAO = new PelangganDAO();
        penyewaanDAO = new PenyewaanDAO();
    }

    public ArrayList<AlatMusik> getDaftarAlat() {
        return alatDAO.getAll();
    }

    public ArrayList<Pelanggan> getDaftarPelanggan() {
        return pelangganDAO.getAll();
    }

    public ArrayList<Penyewaan> getDaftarPenyewaan() {
        return penyewaanDAO.getAll();
    }

    public boolean tambahAlat(AlatMusik alat) {

        if (alat == null) {
            return false;
        }

        if (cariAlat(alat.getIdAlat()) != null) {
            return false;
        }

        return alatDAO.tambah(alat);
    }

    public boolean ubahAlat(
            String id,
            String nama,
            String jenis,
            double harga,
            String status) {

        AlatMusik alat = cariAlat(id);

        if (alat == null) {
            return false;
        }

        if (nama == null || nama.trim().isEmpty()
                || jenis == null || jenis.trim().isEmpty()
                || harga <= 0
                || status == null
                || status.trim().isEmpty()) {
            return false;
        }

        alat.setNamaAlat(nama);
        alat.setJenisAlat(jenis);
        alat.setHargaSewa(harga);
        alat.setStatus(status);

        return alatDAO.ubah(alat);
    }

    public boolean hapusAlat(String id) {

        if (cariAlat(id) == null) {
            return false;
        }

        return alatDAO.hapus(id);
    }

    public boolean tambahPelanggan(Pelanggan pelanggan) {

        if (pelanggan == null) {
            return false;
        }

        if (cariPelanggan(pelanggan.getIdPelanggan()) != null) {
            return false;
        }

        return pelangganDAO.tambah(pelanggan);
    }

    public boolean ubahPelanggan(
            String id,
            String nama,
            String noTelepon,
            String alamat) {

        Pelanggan pelanggan = cariPelanggan(id);

        if (pelanggan == null) {
            return false;
        }

        if (nama == null || nama.trim().isEmpty()
                || noTelepon == null || noTelepon.trim().isEmpty()
                || alamat == null || alamat.trim().isEmpty()) {
            return false;
        }

        pelanggan.setNamaPelanggan(nama);
        pelanggan.setNoTelepon(noTelepon);
        pelanggan.setAlamat(alamat);

        return pelangganDAO.ubah(pelanggan);
    }

    public boolean hapusPelanggan(String id) {

        if (cariPelanggan(id) == null) {
            return false;
        }

        return pelangganDAO.hapus(id);
    }

    public boolean tambahPenyewaan(
            String idPenyewaan,
            String idPelanggan,
            String idAlat,
            int lamaSewa) {

        return tambahPenyewaan(
                idPenyewaan,
                idPelanggan,
                idAlat,
                lamaSewa,
                0
        );
    }

    public boolean tambahPenyewaan(
            String idPenyewaan,
            String idPelanggan,
            String idAlat,
            int lamaSewa,
            double diskon) {

        if (idPenyewaan == null || idPenyewaan.trim().isEmpty()) {
            return false;
        }

        if (cariPenyewaan(idPenyewaan) != null) {
            return false;
        }

        Pelanggan pelanggan = cariPelanggan(idPelanggan);
        AlatMusik alat = cariAlat(idAlat);

        if (pelanggan == null || alat == null) {
            return false;
        }

        if (!alat.getStatus().equalsIgnoreCase("Ready")) {
            return false;
        }

        if (lamaSewa <= 0) {
            return false;
        }

        if (diskon < 0 || diskon > 100) {
            return false;
        }

        double totalHarga;

        if (diskon == 0) {
            totalHarga = alat.hitungTotal(
                    alat.getHargaSewa(),
                    lamaSewa
            );
        } else {
            totalHarga = alat.hitungTotal(
                    alat.getHargaSewa(),
                    lamaSewa,
                    diskon
            );
        }

        Penyewaan penyewaan = new Penyewaan(
                idPenyewaan,
                idPelanggan,
                idAlat,
                lamaSewa,
                totalHarga
        );

        if (!penyewaanDAO.tambah(penyewaan, diskon)) {
            return false;
        }

        alat.setStatus("Disewa");

        if (!alatDAO.ubah(alat)) {
            System.out.println(
                    "Penyewaan tersimpan, tetapi status alat "
                    + "gagal diperbarui."
            );
        }

        return true;
    }

    public boolean ubahPenyewaan(
            String id,
            String idPelanggan,
            String idAlat,
            int lamaSewa) {

        Penyewaan penyewaan = cariPenyewaan(id);

        if (penyewaan == null) {
            return false;
        }

        Pelanggan pelanggan = cariPelanggan(idPelanggan);
        AlatMusik alatBaru = cariAlat(idAlat);

        if (pelanggan == null || alatBaru == null) {
            return false;
        }

        if (lamaSewa <= 0) {
            return false;
        }

        String idAlatLama = penyewaan.getIdAlat();
        boolean gantiAlat =
                !idAlatLama.equalsIgnoreCase(idAlat);

        if (gantiAlat
                && !alatBaru.getStatus().equalsIgnoreCase("Ready")) {
            return false;
        }

        AlatMusik alatLama = cariAlat(idAlatLama);

        double totalHarga = alatBaru.hitungTotal(
                alatBaru.getHargaSewa(),
                lamaSewa
        );

        penyewaan.setIdPelanggan(idPelanggan);
        penyewaan.setIdAlat(idAlat);
        penyewaan.setLamaSewa(lamaSewa);
        penyewaan.setTotalHarga(totalHarga);

        if (!penyewaanDAO.ubah(penyewaan)) {
            return false;
        }

        if (gantiAlat) {
            if (alatLama != null) {
                alatLama.setStatus("Ready");
                alatDAO.ubah(alatLama);
            }

            alatBaru.setStatus("Disewa");
            alatDAO.ubah(alatBaru);
        }

        return true;
    }

    public boolean hapusPenyewaan(String id) {

        Penyewaan penyewaan = cariPenyewaan(id);

        if (penyewaan == null) {
            return false;
        }

        if (!penyewaanDAO.hapus(id)) {
            return false;
        }

        AlatMusik alat = cariAlat(penyewaan.getIdAlat());

        if (alat != null) {
            alat.setStatus("Ready");
            alatDAO.ubah(alat);
        }

        return true;
    }

    public void lihatAlat() {

        System.out.println("\n===== DAFTAR ALAT MUSIK =====");

        ArrayList<AlatMusik> daftarAlat = getDaftarAlat();

        if (daftarAlat.isEmpty()) {
            System.out.println("Belum ada data alat musik.");
            return;
        }

        for (AlatMusik alat : daftarAlat) {
            if (alat instanceof DapatDitampilkan) {
                DapatDitampilkan data = (DapatDitampilkan) alat;
                data.tampilkanData();
            }

            System.out.println("----------------------------");
        }
    }

    public void lihatPelanggan() {

        System.out.println("\n===== DAFTAR PELANGGAN =====");

        ArrayList<Pelanggan> daftarPelanggan = getDaftarPelanggan();

        if (daftarPelanggan.isEmpty()) {
            System.out.println("Belum ada data pelanggan.");
            return;
        }

        for (Pelanggan pelanggan : daftarPelanggan) {
            pelanggan.tampilkanData();
            System.out.println("----------------------------");
        }
    }

    public void lihatPenyewaan() {

        System.out.println("\n===== DAFTAR PENYEWAAN =====");

        ArrayList<Penyewaan> daftarPenyewaan = getDaftarPenyewaan();

        if (daftarPenyewaan.isEmpty()) {
            System.out.println("Belum ada data penyewaan.");
            return;
        }

        for (Penyewaan penyewaan : daftarPenyewaan) {
            penyewaan.tampilkanData();
            System.out.println("----------------------------");
        }
    }

    public AlatMusik cariAlat(String id) {

        if (id == null) {
            return null;
        }

        for (AlatMusik alat : alatDAO.getAll()) {
            if (alat.getIdAlat().equalsIgnoreCase(id)) {
                return alat;
            }
        }

        return null;
    }

    public Pelanggan cariPelanggan(String id) {

        if (id == null) {
            return null;
        }

        return pelangganDAO.cariById(id);
    }

    public Penyewaan cariPenyewaan(String id) {

        if (id == null) {
            return null;
        }

        return penyewaanDAO.cariById(id);
    }
}