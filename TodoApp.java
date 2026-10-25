import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class TodoApp extends JFrame
        implements ActionListener, ListSelectionListener, KeyListener {

    private static final Color HIJAU_LATAR   = new Color(0xF1F8E9);
    private static final Color HIJAU_PASTEL  = new Color(0xC8E6C9);
    private static final Color HIJAU_PILIH   = new Color(0xA5D6A7);
    private static final Color HIJAU_TUA     = new Color(0x2E7D32);
    private static final Color KUNING_MUDA   = new Color(0xFFFDE7);
    private static final Color KUNING_PASTEL = new Color(0xFFF59D);
    private static final Color KUNING_TUA    = new Color(0xF9A825);
    private static final Color TEKS_GELAP    = new Color(0x33432F);
    private static final Color WARNA_TINGGI  = new Color(198, 40, 40);
    private static final Color WARNA_SEDANG  = new Color(239, 108, 0);
    private static final Color WARNA_RENDAH  = new Color(46, 125, 50);
    private static final Color WARNA_SELESAI = new Color(150, 150, 150);

    private JLabel lblJudul, lblTugas, lblPrioritas, lblStatus;
    private JTextField txtTugas;
    private JComboBox<String> cbPrioritas;
    private JCheckBox chkPalingAtas;
    private JButton btnTambah, btnTandai, btnHapus;
    private JList<String> listTugas;
    private DefaultListModel<String> model;

    public TodoApp() {
        setTitle("Daftar Tugas Mahasiswa");
        setSize(500, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        initComponents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int pilihan = JOptionPane.showConfirmDialog(TodoApp.this,
                        "Yakin ingin keluar dari aplikasi?",
                        "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (pilihan == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(HIJAU_LATAR);

        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.setBackground(HIJAU_PASTEL);
        panelAtas.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, KUNING_PASTEL));
        lblJudul = new JLabel("Daftar Tugas Kuliah", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 22));
        lblJudul.setForeground(HIJAU_TUA);
        lblJudul.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));
        panelAtas.add(lblJudul, BorderLayout.CENTER);
        add(panelAtas, BorderLayout.NORTH);

        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 8));
        panelInput.setBackground(KUNING_PASTEL);
        panelInput.setBorder(BorderFactory.createCompoundBorder(
                buatBorderJudul("Tambah Tugas Baru", KUNING_TUA),
                BorderFactory.createEmptyBorder(4, 6, 6, 6)));

        lblTugas = new JLabel("Nama tugas:");
        lblTugas.setForeground(TEKS_GELAP);
        txtTugas = new JTextField();
        txtTugas.setBackground(Color.WHITE);
        txtTugas.addKeyListener(this);

        lblPrioritas = new JLabel("Prioritas:");
        lblPrioritas.setForeground(TEKS_GELAP);
        cbPrioritas = new JComboBox<>(new String[]{"Tinggi", "Sedang", "Rendah"});
        cbPrioritas.setBackground(Color.WHITE);

        chkPalingAtas = new JCheckBox("Taruh di paling atas");
        chkPalingAtas.setBackground(KUNING_PASTEL);
        chkPalingAtas.setForeground(TEKS_GELAP);
        btnTambah = buatTombol("Tambah", HIJAU_PASTEL, HIJAU_TUA);
        btnTambah.addActionListener(this);

        panelInput.add(lblTugas);
        panelInput.add(txtTugas);
        panelInput.add(lblPrioritas);
        panelInput.add(cbPrioritas);
        panelInput.add(chkPalingAtas);
        panelInput.add(btnTambah);

        model = new DefaultListModel<>();
        listTugas = new JList<>(model);
        listTugas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listTugas.setBackground(KUNING_MUDA);
        listTugas.setCellRenderer(new TugasRenderer());
        listTugas.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(listTugas);
        scrollPane.setBackground(HIJAU_LATAR);
        scrollPane.setBorder(buatBorderJudul("Daftar Tugas", HIJAU_TUA));

        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));
        panelTengah.setBackground(HIJAU_LATAR);
        panelTengah.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panelTengah.add(panelInput, BorderLayout.NORTH);
        panelTengah.add(scrollPane, BorderLayout.CENTER);
        add(panelTengah, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setBackground(HIJAU_LATAR);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelTombol.setBackground(HIJAU_LATAR);
        btnTandai = buatTombol("Tandai Selesai", HIJAU_PASTEL, HIJAU_TUA);
        btnHapus = buatTombol("Hapus", KUNING_PASTEL, KUNING_TUA);
        btnTandai.addActionListener(this);
        btnHapus.addActionListener(this);
        panelTombol.add(btnTandai);
        panelTombol.add(btnHapus);

        lblStatus = new JLabel("Total tugas: 0");
        lblStatus.setForeground(HIJAU_TUA);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 12));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(0, 10, 8, 10));

        panelBawah.add(panelTombol, BorderLayout.CENTER);
        panelBawah.add(lblStatus, BorderLayout.SOUTH);
        add(panelBawah, BorderLayout.SOUTH);
    }

    private JButton buatTombol(String teks, Color warna, Color garis) {
        JButton tombol = new JButton(teks);
        tombol.setBackground(warna);
        tombol.setForeground(TEKS_GELAP);
        tombol.setFont(new Font("Arial", Font.BOLD, 12));
        tombol.setFocusPainted(false);
        tombol.setOpaque(true);
        tombol.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(garis, 2),
                BorderFactory.createEmptyBorder(5, 14, 5, 14)));
        tombol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return tombol;
    }

    private TitledBorder buatBorderJudul(String judul, Color warnaGaris) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(warnaGaris, 2), judul);
        border.setTitleColor(TEKS_GELAP);
        border.setTitleFont(new Font("Arial", Font.BOLD, 12));
        return border;
    }

    private static class TugasRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            String teks = String.valueOf(value);
            label.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            label.setFont(new Font("Arial", Font.PLAIN, 13));

            if (isSelected) {
                label.setBackground(HIJAU_PILIH);
                label.setForeground(Color.BLACK);
            } else {
                label.setBackground(KUNING_MUDA);
                if (teks.startsWith("(Selesai)")) {
                    label.setForeground(WARNA_SELESAI);
                } else if (teks.contains("[Tinggi]")) {
                    label.setForeground(WARNA_TINGGI);
                } else if (teks.contains("[Sedang]")) {
                    label.setForeground(WARNA_SEDANG);
                } else if (teks.contains("[Rendah]")) {
                    label.setForeground(WARNA_RENDAH);
                }
            }
            return label;
        }
    }

    private void tambahTugas() {
        String nama = txtTugas.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tugas tidak boleh kosong!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String item = "[" + cbPrioritas.getSelectedItem() + "] " + nama;
        if (chkPalingAtas.isSelected()) {
            model.add(0, item);
        } else {
            model.addElement(item);
        }
        txtTugas.setText("");
        txtTugas.requestFocus();
        perbaruiStatus();
    }

    private void tandaiSelesai() {
        int i = listTugas.getSelectedIndex();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Pilih tugas terlebih dahulu!");
            return;
        }
        String item = model.get(i);
        if (!item.startsWith("(Selesai) ")) {
            model.set(i, "(Selesai) " + item);
        }
    }

    private void hapusTugas() {
        int i = listTugas.getSelectedIndex();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Pilih tugas yang akan dihapus!");
            return;
        }
        model.remove(i);
        perbaruiStatus();
    }

    private void perbaruiStatus() {
        lblStatus.setText("Total tugas: " + model.size());
    }

    // ---------- Event Handling: ActionListener ----------
    @Override
    public void actionPerformed(ActionEvent e) {
        Object sumber = e.getSource();
        if (sumber == btnTambah) {
            tambahTugas();
        } else if (sumber == btnTandai) {
            tandaiSelesai();
        } else if (sumber == btnHapus) {
            hapusTugas();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String terpilih = listTugas.getSelectedValue();
            if (terpilih != null) {
                lblStatus.setText("Dipilih: " + terpilih);
            } else {
                perbaruiStatus();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            tambahTugas();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
        }
        SwingUtilities.invokeLater(() -> new TodoApp().setVisible(true));
    }
}