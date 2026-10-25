# TodoApp — Daftar Tugas Mahasiswa

**Nama:** Sri Zulaini  
**NIM:** F1D02410096
**Mata Kuliah:** Latihan Materi GUI - 5C  

---

## 1. Deskripsi Aplikasi

TodoApp adalah aplikasi desktop berbasis Java Swing untuk membantu mahasiswa mencatat tugas kuliah. Pengguna dapat menambahkan tugas beserta tingkat prioritasnya, menempatkan tugas di urutan paling atas, menandai tugas sebagai selesai, dan menghapus tugas dari daftar.

## 2. Fitur Aplikasi

- Menambahkan tugas kuliah dengan prioritas Tinggi, Sedang, atau Rendah.
- Menempatkan tugas baru di urutan paling atas melalui opsi centang.
- Menampilkan tugas dalam daftar yang dapat digulir.
- Menandai tugas yang dipilih sebagai selesai.
- Menghapus tugas yang dipilih.
- Menampilkan jumlah tugas dan tugas yang sedang dipilih pada label status.
- Menampilkan peringatan jika nama tugas kosong.
- Menampilkan konfirmasi sebelum aplikasi ditutup.

## 3. Teknologi

- Bahasa pemrograman: Java
- Library GUI: Java Swing (`javax.swing`) dan AWT (`java.awt`)
- JDK: Java Development Kit 8 atau lebih baru

## 4. Komponen GUI

### Top-Level Container

- `JFrame`: class `TodoApp` meng-*extend* `JFrame` sehingga menjadi jendela utama yang menampung seluruh komponen lain.

### Intermediate Components

- `JPanel`: mengelompokkan komponen berdasarkan bagian tampilan, yaitu `panelAtas` (judul), `panelInput` (form tugas baru), `panelTengah` (gabungan form dan daftar), `panelTombol`, dan `panelBawah` (tombol aksi dan status).
- `JScrollPane`: membungkus `JList` sehingga daftar dapat digulir ketika tugas bertambah banyak.

### Atomic Components

- `JLabel`: menampilkan judul, keterangan input, dan status jumlah tugas.
- `JTextField`: menerima nama tugas.
- `JComboBox`: memilih prioritas tugas.
- `JCheckBox`: memilih apakah tugas ditempatkan di paling atas.
- `JButton`: menjalankan aksi Tambah, Tandai Selesai, dan Hapus.
- `JList`: menampilkan daftar tugas, dengan data dikelola oleh `DefaultListModel`.
- `JOptionPane`: menampilkan dialog peringatan dan konfirmasi.

## 5. Layout Manager

### BorderLayout

Digunakan pada `JFrame` utama, `panelAtas`, `panelTengah`, dan `panelBawah`. Tampilan dibagi menjadi area NORTH (judul), CENTER (form dan daftar), dan SOUTH (tombol dan status). Area CENTER otomatis melebar mengikuti ukuran jendela.

### GridLayout

Digunakan pada `panelInput` dengan susunan 3 baris dan 2 kolom, sehingga label dan komponen input sejajar rapi dengan ukuran sel yang seragam.

### FlowLayout

Digunakan pada `panelTombol` untuk menyusun tombol Tandai Selesai dan Hapus secara berurutan dan rata tengah.

## 6. Event Handling

Aplikasi menggunakan empat interface event listener.

### ActionListener

Didaftarkan pada `btnTambah`, `btnTandai`, dan `btnHapus`. Method `actionPerformed(ActionEvent e)` membedakan sumber event dengan `e.getSource()`, lalu memanggil `tambahTugas()`, `tandaiSelesai()`, atau `hapusTugas()`.

### ListSelectionListener

Didaftarkan pada `listTugas`. Method `valueChanged(ListSelectionEvent e)` memperbarui label status dengan tugas yang sedang dipilih. Pengecekan `getValueIsAdjusting()` mencegah event diproses dua kali.

### KeyListener

Didaftarkan pada `txtTugas`. Method `keyPressed(KeyEvent e)` menambahkan tugas ketika tombol Enter ditekan. Method `keyReleased` dan `keyTyped` dibiarkan kosong karena tidak digunakan.

### WindowListener (melalui WindowAdapter)

Didaftarkan pada `JFrame` menggunakan *anonymous inner class*. Method `windowClosing(WindowEvent e)` menampilkan dialog konfirmasi sebelum aplikasi ditutup. `WindowAdapter` dipakai agar tidak perlu meng-*override* seluruh method `WindowListener`.

Class `TodoApp` mengimplementasikan `ActionListener`, `ListSelectionListener`, dan `KeyListener` secara langsung.

## 7. Cara Menjalankan

Pastikan JDK telah terpasang dan terminal berada di folder utama proyek.

Kompilasi:

```bash
javac -d out src/TodoApp.java
```

Jalankan:

```bash
java -cp out TodoApp
```

## 8. Struktur Proyek

```text
TodoApp/
├── src/
│   └── TodoApp.java
├── screenshots/
│   ├── 1-tampilan-awal.png
│   ├── 2-tambah-tugas.png
│   ├── 3-tugas-selesai.png
│   ├── 4-validasi-kosong.png
│   └── 5-konfirmasi-keluar.png
├── README.md
└── .gitignore
```

## 9. Penyimpanan Data

Data tugas disimpan sementara di memori selama aplikasi berjalan. Data tidak bertahan setelah aplikasi ditutup.

## 10. Dokumentasi Screenshot

### Tampilan Awal

![Tampilan awal TodoApp](screenshots/1-tampilan-awal.png)

### Menambah Tugas

![Daftar tugas dengan beberapa prioritas](screenshots/2-tambah-tugas.png)

### Tugas Ditandai Selesai

![Tugas dengan awalan Selesai](screenshots/3-tugas-selesai.png)

### Validasi Input

![Peringatan ketika nama tugas kosong](screenshots/4-validasi-kosong.png)

### Konfirmasi Keluar

![Dialog konfirmasi saat menutup aplikasi](screenshots/5-konfirmasi-keluar.png)