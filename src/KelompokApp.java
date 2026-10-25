import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KelompokApp extends JFrame implements ActionListener, ItemListener {

    // ===== Palet warna: hijau pastel dan kuning pastel =====
    private static final Color HIJAU_LATAR   = new Color(0xF1F8E9);
    private static final Color HIJAU_PASTEL  = new Color(0xC8E6C9);
    private static final Color HIJAU_TUA     = new Color(0x2E7D32);
    private static final Color KUNING_MUDA   = new Color(0xFFFDE7);
    private static final Color KUNING_PASTEL = new Color(0xFFF59D);
    private static final Color KUNING_TUA    = new Color(0xF9A825);
    private static final Color TEKS_GELAP    = new Color(0x33432F);

    private static final String HASIL_AWAL =
            "Hasil pembagian kelompok akan tampil di sini.";

    // Atomic Components
    private JTextArea txtNama, txtHasil;
    private JComboBox<Integer> cbJumlah;
    private JLabel lblInfo, lblStatus;
    private JButton btnAcak, btnReset;

    public KelompokApp() {
        // Top Level Container: JFrame (class ini sendiri)
        setTitle("Pengacak Kelompok");
        setSize(760, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(HIJAU_LATAR);

        initComponents();

        // Event Handling: WindowListener lewat WindowAdapter (anonymous class)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int pilihan = JOptionPane.showConfirmDialog(KelompokApp.this,
                        "Yakin ingin keluar dari aplikasi?",
                        "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (pilihan == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    private void initComponents() {
        // Layout Manager utama JFrame: BorderLayout
        setLayout(new BorderLayout(10, 10));

        // ===== Panel Atas (Intermediate Component) - BorderLayout.NORTH =====
        JPanel panelAtas = new JPanel(new GridLayout(2, 1));
        panelAtas.setBackground(HIJAU_PASTEL);
        panelAtas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, KUNING_PASTEL),
                BorderFactory.createEmptyBorder(10, 0, 8, 0)));
        JLabel lblJudul = new JLabel("Pengacak Kelompok", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 24));
        lblJudul.setForeground(HIJAU_TUA);
        JLabel lblSub = new JLabel("Bagi anggota menjadi kelompok secara acak dan adil",
                SwingConstants.CENTER);
        lblSub.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSub.setForeground(TEKS_GELAP);
        panelAtas.add(lblJudul);
        panelAtas.add(lblSub);
        add(panelAtas, BorderLayout.NORTH);

        // ===== Panel Tengah (Intermediate Component) - GridLayout 1 x 2 =====
        JPanel panelTengah = new JPanel(new GridLayout(1, 2, 10, 0));
        panelTengah.setBackground(HIJAU_LATAR);
        panelTengah.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panelTengah.add(buatPanelInput());
        panelTengah.add(buatPanelHasil());
        add(panelTengah, BorderLayout.CENTER);

        // ===== Panel Bawah (Intermediate Component) - BorderLayout.SOUTH =====
        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setBackground(HIJAU_LATAR);

        // Panel tombol memakai FlowLayout
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        panelTombol.setBackground(HIJAU_LATAR);
        btnAcak = buatTombol("Acak", HIJAU_PASTEL, HIJAU_TUA);
        btnReset = buatTombol("Reset", KUNING_PASTEL, KUNING_TUA);
        btnAcak.addActionListener(this); // ActionListener
        btnReset.addActionListener(this);
        panelTombol.add(btnAcak);
        panelTombol.add(btnReset);

        lblStatus = new JLabel("Siap digunakan.", SwingConstants.CENTER);
        lblStatus.setForeground(HIJAU_TUA);
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 12));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        panelBawah.add(panelTombol, BorderLayout.CENTER);
        panelBawah.add(lblStatus, BorderLayout.SOUTH);
        add(panelBawah, BorderLayout.SOUTH);
    }

    /** Panel kiri: kotak nama dan pilihan jumlah kelompok. */
    private JPanel buatPanelInput() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(KUNING_PASTEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                buatBorderJudul("Daftar Nama (satu nama per baris)", KUNING_TUA),
                BorderFactory.createEmptyBorder(4, 6, 6, 6)));

        txtNama = new JTextArea();
        txtNama.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNama.setMargin(new Insets(6, 8, 6, 8));
        JScrollPane scrollNama = new JScrollPane(txtNama);

        // Pilihan jumlah kelompok (FlowLayout)
        JPanel panelJumlah = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        panelJumlah.setBackground(KUNING_PASTEL);
        JLabel lblJumlah = new JLabel("Jumlah kelompok:");
        lblJumlah.setFont(new Font("Arial", Font.BOLD, 12));
        lblJumlah.setForeground(TEKS_GELAP);
        cbJumlah = new JComboBox<>(new Integer[]{2, 3, 4, 5, 6});
        cbJumlah.setBackground(Color.WHITE);
        cbJumlah.addItemListener(this); // ItemListener
        panelJumlah.add(lblJumlah);
        panelJumlah.add(cbJumlah);

        lblInfo = new JLabel("Akan dibagi menjadi 2 kelompok");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInfo.setForeground(HIJAU_TUA);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 2, 0));

        JPanel panelBawahInput = new JPanel(new GridLayout(2, 1));
        panelBawahInput.setBackground(KUNING_PASTEL);
        panelBawahInput.add(panelJumlah);
        panelBawahInput.add(lblInfo);

        panel.add(scrollNama, BorderLayout.CENTER);
        panel.add(panelBawahInput, BorderLayout.SOUTH);
        return panel;
    }

    /** Panel kanan: hasil pembagian (hanya baca). */
    private JPanel buatPanelHasil() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HIJAU_LATAR);

        txtHasil = new JTextArea(HASIL_AWAL);
        txtHasil.setEditable(false);
        txtHasil.setLineWrap(true);
        txtHasil.setWrapStyleWord(true);
        txtHasil.setBackground(KUNING_MUDA);
        txtHasil.setForeground(TEKS_GELAP);
        txtHasil.setFont(new Font("Arial", Font.PLAIN, 14));
        txtHasil.setMargin(new Insets(6, 8, 6, 8));

        JScrollPane scrollHasil = new JScrollPane(txtHasil);
        scrollHasil.setBorder(buatBorderJudul("Hasil Pembagian", HIJAU_TUA));
        panel.add(scrollHasil, BorderLayout.CENTER);
        return panel;
    }

    // ---------- Helper tampilan ----------

    /** Membuat tombol pastel dengan garis tepi yang lebih tua agar tetap terlihat jelas. */
    private JButton buatTombol(String teks, Color warna, Color garis) {
        JButton tombol = new JButton(teks);
        tombol.setBackground(warna);
        tombol.setForeground(TEKS_GELAP);
        tombol.setFont(new Font("Arial", Font.BOLD, 13));
        tombol.setFocusPainted(false);
        tombol.setOpaque(true);
        tombol.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(garis, 2),
                BorderFactory.createEmptyBorder(6, 24, 6, 24)));
        tombol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return tombol;
    }

    /** Membuat border berjudul dengan warna garis tertentu. */
    private TitledBorder buatBorderJudul(String judul, Color warnaGaris) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(warnaGaris, 2), judul);
        border.setTitleColor(TEKS_GELAP);
        border.setTitleFont(new Font("Arial", Font.BOLD, 12));
        return border;
    }

    // ---------- Logika ----------

    private void acak() {
        // Ambil nama dari kotak teks, abaikan baris kosong
        List<String> daftar = new ArrayList<>();
        for (String baris : txtNama.getText().split("\n")) {
            String nama = baris.trim();
            if (!nama.isEmpty()) {
                daftar.add(nama);
            }
        }
        int jumlah = (Integer) cbJumlah.getSelectedItem();

        // Validasi input
        if (daftar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan minimal satu nama!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (daftar.size() < jumlah) {
            JOptionPane.showMessageDialog(this,
                    "Jumlah nama (" + daftar.size() + ") kurang dari jumlah kelompok ("
                            + jumlah + ")!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Acak urutan nama, lalu bagikan satu per satu secara bergiliran
        Collections.shuffle(daftar);
        List<List<String>> kelompok = new ArrayList<>();
        for (int i = 0; i < jumlah; i++) {
            kelompok.add(new ArrayList<>());
        }
        for (int i = 0; i < daftar.size(); i++) {
            kelompok.get(i % jumlah).add(daftar.get(i));
        }

        // Susun teks hasil
        StringBuilder hasil = new StringBuilder();
        for (int i = 0; i < jumlah; i++) {
            List<String> anggota = kelompok.get(i);
            hasil.append("Kelompok ").append(i + 1)
                    .append(" (").append(anggota.size()).append(" orang)\n");
            for (int j = 0; j < anggota.size(); j++) {
                hasil.append("  ").append(j + 1).append(". ").append(anggota.get(j)).append("\n");
            }
            hasil.append("\n");
        }
        txtHasil.setText(hasil.toString());
        txtHasil.setCaretPosition(0);
        lblStatus.setText(daftar.size() + " nama dibagi menjadi " + jumlah + " kelompok.");
    }

    private void reset() {
        txtNama.setText("");
        cbJumlah.setSelectedIndex(0);
        lblInfo.setText("Akan dibagi menjadi 2 kelompok");
        txtHasil.setText(HASIL_AWAL);
        lblStatus.setText("Siap digunakan.");
        txtNama.requestFocus();
    }

    // ---------- Event Handling: ActionListener ----------
    @Override
    public void actionPerformed(ActionEvent e) {
        Object sumber = e.getSource();
        if (sumber == btnAcak) {
            acak();
        } else if (sumber == btnReset) {
            reset();
        }
    }

    // ---------- Event Handling: ItemListener ----------
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            lblInfo.setText("Akan dibagi menjadi " + cbJumlah.getSelectedItem() + " kelompok");
        }
    }

    public static void main(String[] args) {
        // Look and Feel lintas-platform agar warna tampil sama di Windows, macOS, dan Linux
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            // abaikan, pakai tampilan bawaan
        }
        SwingUtilities.invokeLater(() -> new KelompokApp().setVisible(true));
    }
}