package Minpro3;

import java.util.Scanner;
import Minpro3.controller.PenyewaanController;
import Minpro3.Model.AlatMusik;
import Minpro3.Model.AlatMusikAkustik;
import Minpro3.Model.AlatMusikElektrik;
import Minpro3.Model.Pelanggan;
import Minpro3.Model.Penyewaan;
import Minpro3.View.MenuView;

public class Minpro3PenyewaanAlatMusik {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        MenuView view = new MenuView(input);
        PenyewaanController controller = new PenyewaanController();

        int pilihan = 0;

        do {
            view.tampilkanMenuUtama();

            try {
                pilihan = view.inputMenu();

                switch (pilihan) {

                    case 1:
                        menuAlat(view, controller);
                        break;

                    case 2:
                        menuPelanggan(view, controller);
                        break;

                    case 3:
                        menuPenyewaan(view, controller);
                        break;

                    case 4:
                        view.tampilkanPesan("Program selesai.");
                        break;

                    default:
                        view.tampilkanError(
                                "Pilihan menu tidak tersedia."
                        );
                }

            } catch (NumberFormatException e) {
                view.tampilkanError(
                        "Input harus berupa angka."
                );
            }

        } while (pilihan != 4);

        input.close();
    }

    public static void menuAlat(
            MenuView view,
            PenyewaanController controller) {

        int pilihan = 0;

        do {
            view.tampilkanMenuAlat();

            try {
                pilihan = view.inputMenu();

                switch (pilihan) {

                    case 1:
                        controller.lihatAlat();
                        break;

                    case 2:
                        tambahAlat(view, controller);
                        break;

                    case 3:
                        ubahAlat(view, controller);
                        break;

                    case 4:
                        hapusAlat(view, controller);
                        break;

                    case 5:
                        break;

                    default:
                        view.tampilkanError(
                                "Pilihan menu tidak tersedia."
                        );
                }

            } catch (NumberFormatException e) {
                view.tampilkanError(
                        "Input harus berupa angka."
                );
                pilihan = 0;
            }

        } while (pilihan != 5);
    }


    public static void tambahAlat(
            MenuView view,
            PenyewaanController controller) {

        try {

            System.out.println("\n===== TAMBAH ALAT MUSIK =====");

            String id = view.inputString(
                    "ID Alat: "
            );

            if (id.trim().isEmpty()) {
                view.tampilkanError(
                        "ID alat tidak boleh kosong."
                );
                return;
            }

            if (controller.cariAlat(id) != null) {
                view.tampilkanError(
                        "ID alat sudah digunakan."
                );
                return;
            }

            String nama = view.inputString(
                    "Nama Alat: "
            );

            String jenis = view.inputString(
                    "Jenis Alat: "
            );

            double harga = view.inputDouble(
                    "Harga Sewa: "
            );

            if (harga <= 0) {
                view.tampilkanError(
                        "Harga sewa harus lebih dari 0."
                );
                return;
            }

            System.out.println("\nJenis Alat:");
            System.out.println("1. Akustik");
            System.out.println("2. Elektrik");

            int pilihanJenis = view.inputInt(
                    "Pilih jenis: "
            );

            AlatMusik alat;

            if (pilihanJenis == 1) {

                String bahan = view.inputString(
                        "Bahan: "
                );

                alat = new AlatMusikAkustik(
                        id,
                        nama,
                        jenis,
                        harga,
                        "Ready",
                        bahan
                );

            } else if (pilihanJenis == 2) {

                int daya = view.inputInt(
                        "Daya (Watt): "
                );

                if (daya <= 0) {
                    view.tampilkanError(
                            "Daya harus lebih dari 0."
                    );
                    return;
                }

                alat = new AlatMusikElektrik(
                        id,
                        nama,
                        jenis,
                        harga,
                        "Ready",
                        daya
                );

            } else {

                view.tampilkanError(
                        "Jenis alat tidak tersedia."
                );
                return;
            }

            if (controller.tambahAlat(alat)) {

                view.tampilkanPesan(
                        "Data alat berhasil ditambahkan."
                );

            } else {

                view.tampilkanError(
                        "Data alat gagal ditambahkan."
                );
            }

        } catch (NumberFormatException e) {

            view.tampilkanError(
                    "Input angka tidak valid."
            );
        }
    }


    public static void ubahAlat(
            MenuView view,
            PenyewaanController controller) {

        try {

            System.out.println("\n===== UBAH ALAT MUSIK =====");

            String id = view.inputString(
                    "ID Alat: "
            );

            AlatMusik alat = controller.cariAlat(id);

            if (alat == null) {

                view.tampilkanError(
                        "ID alat tidak ditemukan."
                );
                return;
            }

            String nama = view.inputString(
                    "Nama baru: "
            );

            String jenis = view.inputString(
                    "Jenis baru: "
            );

            double harga = view.inputDouble(
                    "Harga sewa baru: "
            );

            if (harga <= 0) {

                view.tampilkanError(
                        "Harga sewa harus lebih dari 0."
                );
                return;
            }

            String status = view.inputString(
                    "Status baru (Ready/Disewa): "
            );

            if (!status.equalsIgnoreCase("Ready")
                    && !status.equalsIgnoreCase("Disewa")) {

                view.tampilkanError(
                        "Status hanya boleh Ready atau Disewa."
                );
                return;
            }

            if (controller.ubahAlat(
                    id,
                    nama,
                    jenis,
                    harga,
                    status)) {

                view.tampilkanPesan(
                        "Data alat berhasil diubah."
                );

            } else {

                view.tampilkanError(
                        "Data alat gagal diubah."
                );
            }

        } catch (NumberFormatException e) {

            view.tampilkanError(
                    "Input angka tidak valid."
            );
        }
    }

    public static void hapusAlat(
            MenuView view,
            PenyewaanController controller) {

        System.out.println("\n===== HAPUS ALAT MUSIK =====");

        String id = view.inputString(
                "ID Alat yang ingin dihapus: "
        );

        AlatMusik alat = controller.cariAlat(id);

        if (alat == null) {

            view.tampilkanError(
                    "ID alat tidak ditemukan."
            );
            return;
        }

        if (controller.hapusAlat(id)) {

            view.tampilkanPesan(
                    "Data alat berhasil dihapus."
            );

        } else {

            view.tampilkanError(
                    "Data alat gagal dihapus."
            );
        }
    }

    public static void menuPelanggan(
            MenuView view,
            PenyewaanController controller) {

        int pilihan = 0;

        do {
            view.tampilkanMenuPelanggan();

            try {
                pilihan = view.inputMenu();

                switch (pilihan) {

                    case 1:
                        controller.lihatPelanggan();
                        break;

                    case 2:
                        tambahPelanggan(view, controller);
                        break;

                    case 3:
                        ubahPelanggan(view, controller);
                        break;

                    case 4:
                        hapusPelanggan(view, controller);
                        break;

                    case 5:
                        break;

                    default:
                        view.tampilkanError(
                                "Pilihan menu tidak tersedia."
                        );
                }

            } catch (NumberFormatException e) {

                view.tampilkanError(
                        "Input harus berupa angka."
                );
                pilihan = 0;
            }

        } while (pilihan != 5);
    }

    public static void tambahPelanggan(
            MenuView view,
            PenyewaanController controller) {

        System.out.println("\n===== TAMBAH PELANGGAN =====");

        String id = view.inputString(
                "ID Pelanggan: "
        );

        if (id.trim().isEmpty()) {

            view.tampilkanError(
                    "ID pelanggan tidak boleh kosong."
            );
            return;
        }

        if (controller.cariPelanggan(id) != null) {

            view.tampilkanError(
                    "ID pelanggan sudah digunakan."
            );
            return;
        }

        String nama = view.inputString(
                "Nama Pelanggan: "
        );

        String noTelepon = view.inputNomorTelepon(
                "No. Telepon: "
        );

        String alamat = view.inputString(
                "Alamat: "
        );

        Pelanggan pelanggan = new Pelanggan(
                id,
                nama,
                noTelepon,
                alamat
        );

        if (controller.tambahPelanggan(pelanggan)) {

            view.tampilkanPesan(
                    "Data pelanggan berhasil ditambahkan."
            );

        } else {

            view.tampilkanError(
                    "Data pelanggan gagal ditambahkan."
            );
        }
    }

    public static void ubahPelanggan(
            MenuView view,
            PenyewaanController controller) {

        System.out.println("\n===== UBAH PELANGGAN =====");

        String id = view.inputString(
                "ID Pelanggan: "
        );

        Pelanggan pelanggan =
                controller.cariPelanggan(id);

        if (pelanggan == null) {

            view.tampilkanError(
                    "ID pelanggan tidak ditemukan."
            );
            return;
        }

        String nama = view.inputString(
                "Nama baru: "
        );

        String noTelepon = view.inputNomorTelepon(
        "No. Telepon baru: "
        );


        String alamat = view.inputString(
                "Alamat baru: "
        );

        if (controller.ubahPelanggan(
                id,
                nama,
                noTelepon,
                alamat)) {

            view.tampilkanPesan(
                    "Data pelanggan berhasil diubah."
            );

        } else {

            view.tampilkanError(
                    "Data pelanggan gagal diubah."
            );
        }
    }

    public static void hapusPelanggan(
            MenuView view,
            PenyewaanController controller) {

        System.out.println("\n===== HAPUS PELANGGAN =====");

        String id = view.inputString(
                "ID Pelanggan yang ingin dihapus: "
        );

        if (controller.hapusPelanggan(id)) {

            view.tampilkanPesan(
                    "Data pelanggan berhasil dihapus."
            );

        } else {

            view.tampilkanError(
                    "ID pelanggan tidak ditemukan."
            );
        }
    }

    public static void menuPenyewaan(
            MenuView view,
            PenyewaanController controller) {

        int pilihan = 0;

        do {
            view.tampilkanMenuPenyewaan();

            try {
                pilihan = view.inputMenu();

                switch (pilihan) {

                    case 1:
                        controller.lihatPenyewaan();
                        break;

                    case 2:
                        tambahPenyewaan(view, controller);
                        break;

                    case 3:
                        ubahPenyewaan(view, controller);
                        break;

                    case 4:
                        hapusPenyewaan(view, controller);
                        break;

                    case 5:
                        break;

                    default:
                        view.tampilkanError(
                                "Pilihan menu tidak tersedia."
                        );
                }

            } catch (NumberFormatException e) {

                view.tampilkanError(
                        "Input harus berupa angka."
                );
                pilihan = 0;
            }

        } while (pilihan != 5);
    }

    public static void tambahPenyewaan(
            MenuView view,
            PenyewaanController controller) {

        try {

            System.out.println(
                    "\n===== TAMBAH PENYEWAAN ====="
            );

            String idPenyewaan = view.inputString(
                    "ID Penyewaan: "
            );

            if (controller.cariPenyewaan(idPenyewaan)
                    != null) {

                view.tampilkanError(
                        "ID penyewaan sudah digunakan."
                );
                return;
            }

            String idPelanggan = view.inputString(
                    "ID Pelanggan: "
            );

            if (controller.cariPelanggan(idPelanggan)
                    == null) {

                view.tampilkanError(
                        "ID pelanggan tidak ditemukan."
                );
                return;
            }

            String idAlat = view.inputString(
                    "ID Alat: "
            );

            AlatMusik alat =
                    controller.cariAlat(idAlat);

            if (alat == null) {

                view.tampilkanError(
                        "ID alat tidak ditemukan."
                );
                return;
            }

            if (!alat.getStatus()
                    .equalsIgnoreCase("Ready")) {

                view.tampilkanError(
                        "Alat sedang disewa."
                );
                return;
            }

            int lamaSewa = view.inputInt(
                    "Lama Sewa (hari): "
            );

            if (lamaSewa <= 0) {

                view.tampilkanError(
                        "Lama sewa harus lebih dari 0."
                );
                return;
            }

            double diskon = view.inputDouble(
                    "Diskon (%): "
            );

            if (diskon < 0 || diskon > 100) {

                view.tampilkanError(
                        "Diskon harus antara 0 sampai 100."
                );
                return;
            }

            boolean berhasil =
                    controller.tambahPenyewaan(
                            idPenyewaan,
                            idPelanggan,
                            idAlat,
                            lamaSewa,
                            diskon
                    );

            if (berhasil) {

                Penyewaan penyewaan =
                        controller.cariPenyewaan(
                                idPenyewaan
                        );

                view.tampilkanPesan(
                        "Penyewaan berhasil ditambahkan."
                );

                System.out.println(
                        "Total Harga : Rp"
                        + penyewaan.getTotalHarga()
                );

            } else {

                view.tampilkanError(
                        "Penyewaan gagal ditambahkan."
                );
            }

        } catch (NumberFormatException e) {

            view.tampilkanError(
                    "Input angka tidak valid."
            );
        }
    }

    public static void ubahPenyewaan(
            MenuView view,
            PenyewaanController controller) {

        try {

            System.out.println(
                    "\n===== UBAH PENYEWAAN ====="
            );

            String id = view.inputString(
                    "ID Penyewaan: "
            );

            Penyewaan penyewaan =
                    controller.cariPenyewaan(id);

            if (penyewaan == null) {

                view.tampilkanError(
                        "ID penyewaan tidak ditemukan."
                );
                return;
            }

            String idPelanggan = view.inputString(
                    "ID Pelanggan baru: "
            );

            if (controller.cariPelanggan(idPelanggan)
                    == null) {

                view.tampilkanError(
                        "ID pelanggan tidak ditemukan."
                );
                return;
            }

            String idAlat = view.inputString(
                    "ID Alat baru: "
            );

            if (controller.cariAlat(idAlat) == null) {

                view.tampilkanError(
                        "ID alat tidak ditemukan."
                );
                return;
            }

            int lamaSewa = view.inputInt(
                    "Lama Sewa baru: "
            );

            if (lamaSewa <= 0) {

                view.tampilkanError(
                        "Lama sewa harus lebih dari 0."
                );
                return;
            }

            if (controller.ubahPenyewaan(
                    id,
                    idPelanggan,
                    idAlat,
                    lamaSewa)) {

                view.tampilkanPesan(
                        "Data penyewaan berhasil diubah."
                );

            } else {

                view.tampilkanError(
                        "Data penyewaan gagal diubah. "
                        + "Pastikan alat tersedia."
                );
            }

        } catch (NumberFormatException e) {

            view.tampilkanError(
                    "Input angka tidak valid."
            );
        }
    }

    public static void hapusPenyewaan(
            MenuView view,
            PenyewaanController controller) {

        System.out.println(
                "\n===== HAPUS PENYEWAAN ====="
        );

        String id = view.inputString(
                "ID Penyewaan yang ingin dihapus: "
        );

        if (controller.hapusPenyewaan(id)) {

            view.tampilkanPesan(
                    "Data penyewaan berhasil dihapus."
            );

        } else {

            view.tampilkanError(
                    "ID penyewaan tidak ditemukan."
            );
        }
    }
}