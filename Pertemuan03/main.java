package Pertemuan03;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.sql.*;

public class main {
    // Palet Warna Modern Sesuai Desain Baru
    private static final Color HEADER_NAVY_DARK = new Color(10, 21, 56);
    private static final Color HEADER_INDIGO = new Color(28, 52, 125);
    private static final Color ACCENT_BLUE = new Color(37, 99, 235);
    private static final Color ACCENT_BLUE_LIGHT = new Color(59, 130, 246);
    private static final Color DANGER_RED = new Color(215, 25, 70);
    private static final Color DANGER_RED_DARK = new Color(159, 18, 57);
    private static final Color RESET_DARK = new Color(30, 48, 74);
    private static final Color RESET_LIGHT = new Color(56, 78, 110);
    private static final Color BG_LIGHT = new Color(238, 242, 247);
    private static final Color TEXT_DARK = new Color(15, 23, 42);
    private static final Color BORDER_COLOR = new Color(186, 198, 214);
    private static final Color ICON_BG = new Color(235, 240, 247);
    private static final Color TABLE_HEADER_BG = new Color(15, 27, 49);
    private static final Color ZEBRA_EVEN = Color.WHITE;
    private static final Color ZEBRA_ODD = new Color(233, 239, 246);
    private static final Color GRID_LINE = new Color(195, 205, 218);

    // Tipe Ikon
    private static final int ICON_USER = 1;
    private static final int ICON_ID = 2;
    private static final int ICON_BOOK = 3;
    private static final int ICON_CALENDAR = 4;
    private static final int ICON_STAR = 5;
    private static final int ICON_CHECK = 6;
    private static final int ICON_REFRESH = 7;
    private static final int ICON_TRASH = 8;

    // Data Mahasiswa
    private static final Map<String, String> DATA_MAHASISWA = new LinkedHashMap<>();
    static {
        DATA_MAHASISWA.put("-- Pilih Mahasiswa --", "");
        DATA_MAHASISWA.put("Rifa Fauzi", "2595124001");
        DATA_MAHASISWA.put("Muhammad Andi Irfansyah", "2595124002");
        DATA_MAHASISWA.put("Waheeda Zahrotuz Zakiya", "2595124003");
        DATA_MAHASISWA.put("Ira Sofiyanna", "2595124004");
        DATA_MAHASISWA.put("Livia Eka Linggajati", "2595124005");
        DATA_MAHASISWA.put("Fallih Ahmad Ahsan Khuluqi", "2595124006");
        DATA_MAHASISWA.put("Brilian Nur Iqbal", "2595124007");
        DATA_MAHASISWA.put("Danu Yuldha Prananda", "2595124008");
        DATA_MAHASISWA.put("Zahrotul Muyassaroh", "2595124009");
        DATA_MAHASISWA.put("Jerry Andika Saputra", "2595124010");
        DATA_MAHASISWA.put("Fernando Wasim Dzul Karom", "2595124011");
        DATA_MAHASISWA.put("Qurrah A'yuni Al-Habibah", "2595124012");
        DATA_MAHASISWA.put("Ahmad Rafa", "2595124016");
        DATA_MAHASISWA.put("Uroydhatul Khasanah", "2595124017");
        DATA_MAHASISWA.put("Muhammad Hasan Nasrulloh", "2595124018");
        DATA_MAHASISWA.put("Jessica Keyla Putri Susanto", "2595124020");
    }

    // Data Mata Kuliah
    private static final String[] MATA_KULIAH = {
        "-- Pilih Mata Kuliah --",
        "Desain dan Manajemen Jaringan (3 SKS)",
        "Perencanaan Kewirausahaan (3 SKS)",
        "Bahasa Arab (2 SKS)",
        "Basis Data (3 SKS)",
        "Analisis dan Desain Sistem Informasi (3 SKS)",
        "Aljabar Linier dan Matriks (3 SKS)",
        "Pemrograman Berorientasi Objek (3 SKS)",
        "Keamanan Sistem Informasi (3 SKS)"
    };

    private static final String[] STATUS_ABSENSI = {
        "-- Pilih Status Kehadiran --", "Hadir", "Izin", "Sakit", "Alpha"
    };

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(main::buildDashboard);
    }

    private static void buildDashboard() {
        JFrame frame = new JFrame("Sistem Akademik - S1 Sistem Informasi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1240, 770);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_LIGHT);

        // Header Modern dengan Efek Geometris & Garis Emas
        mainPanel.add(createModernHeader(), BorderLayout.NORTH);

        // Dashboard Content (Split side-by-side)
        JPanel contentPanel = new JPanel(new BorderLayout(22, 0));
        contentPanel.setBackground(BG_LIGHT);
        contentPanel.setBorder(new EmptyBorder(20, 22, 22, 22));

        // Setup Model Tabel
        String[] columns = {"NIM", "Nama Mahasiswa", "Mata Kuliah", "Kehadiran", "Nilai Angka", "Nilai Huruf"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // Panel Kiri: Form Input (Aksen Biru di Atas Kartu)
        JPanel leftPanel = createFormPanel(frame, tableModel);
        contentPanel.add(leftPanel, BorderLayout.WEST);

        // Panel Kanan: Tabel Data (Aksen Merah-Rose di Atas Kartu)
        JPanel rightPanel = createTablePanel(frame, tableModel);
        contentPanel.add(rightPanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private static String konversiNilaiHuruf(int nilai) {
        if (nilai >= 85) return "A";
        if (nilai >= 75) return "B";
        if (nilai >= 65) return "C";
        if (nilai >= 50) return "D";
        return "E";
    }

    private static JPanel createModernHeader() {
        // Panel Header dengan Background Gradasi & Pola Geometris
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // Gradasi Dasar Navy ke Indigo
                GradientPaint bgGrad = new GradientPaint(0, 0, HEADER_NAVY_DARK, w, h, HEADER_INDIGO);
                g2.setPaint(bgGrad);
                g2.fillRect(0, 0, w, h);

                // Pola Geometris Segitiga / Poligon Halus
                g2.setColor(new Color(255, 255, 255, 12));
                g2.fillPolygon(new int[]{w - 650, w - 420, w - 520}, new int[]{0, 0, h}, 3);
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillPolygon(new int[]{w - 420, w - 180, w - 310}, new int[]{0, 0, h}, 3);
                g2.setColor(new Color(59, 130, 246, 28));
                g2.fillPolygon(new int[]{w - 280, w, w - 120}, new int[]{h, 0, h}, 3);
                g2.setColor(new Color(255, 255, 255, 10));
                g2.fillPolygon(new int[]{w - 180, w, w}, new int[]{0, 0, h}, 3);

                g2.dispose();
            }
        };
        header.setBorder(new EmptyBorder(16, 26, 16, 26));

        JPanel branding = new JPanel(new BorderLayout(18, 0));
        branding.setOpaque(false);
        branding.add(createHeaderLogoLabel(), BorderLayout.WEST);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("UNIVERSITAS HASYIM ASY'ARI");
        title.setFont(new Font("Segoe UI", Font.BOLD, 23));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Dashboard Akademik - Input Absensi & Nilai (Semester III)");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(203, 213, 225));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        branding.add(titlePanel, BorderLayout.CENTER);
        header.add(branding, BorderLayout.WEST);

        JPanel container = new JPanel(new BorderLayout());
        container.add(header, BorderLayout.CENTER);

        // Garis Aksen Emas Bergradasi (Glowing Gold Bar)
        JPanel goldAccent = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();
                LinearGradientPaint goldGrad = new LinearGradientPaint(
                    0, 0, w, 0,
                    new float[]{0.0f, 0.35f, 0.5f, 0.65f, 1.0f},
                    new Color[]{
                        new Color(161, 120, 28),
                        new Color(234, 195, 75),
                        new Color(254, 240, 138),
                        new Color(234, 195, 75),
                        new Color(161, 120, 28)
                    }
                );
                g2.setPaint(goldGrad);
                g2.fillRect(0, 0, w, h);
                g2.dispose();
            }
        };
        goldAccent.setPreferredSize(new Dimension(0, 5));
        container.add(goldAccent, BorderLayout.SOUTH);

        return container;
    }

    private static JLabel createHeaderLogoLabel() {
        JLabel logo = new JLabel();
        logo.setPreferredSize(new Dimension(78, 78));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setVerticalAlignment(SwingConstants.CENTER);
        logo.setOpaque(false);

        ImageIcon icon = loadHeaderLogo();
        if (icon != null) {
            Image scaled = icon.getImage().getScaledInstance(72, 72, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(scaled));
        } else {
            logo.setText("UNH");
            logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
            logo.setForeground(Color.WHITE);
        }
        return logo;
    }

    private static ImageIcon loadHeaderLogo() {
        String[] paths = {
            "logo-unhasy.png",
            "/logo-unhasy.png",
            "/Pertemuan03/logo-unhasy.png",
            "Pertemuan03/logo-unhasy.png",
            "C:\\PBO\\Pertemuan03\\logo-unhasy.png",
            "C:\\PBO\\logo-unhasy.png"
        };

        for (String path : paths) {
            URL resource = main.class.getResource(path);
            if (resource != null) {
                return new ImageIcon(resource);
            }
        }

        File[] candidates = {
            new File(System.getProperty("user.dir"), "logo-unhasy.png"),
            new File(System.getProperty("user.dir"), "Pertemuan03/logo-unhasy.png"),
            new File("C:\\PBO\\Pertemuan03\\logo-unhasy.png"),
            new File("C:\\PBO\\logo-unhasy.png")
        };

        for (File candidate : candidates) {
            if (candidate.isFile()) {
                return new ImageIcon(candidate.getAbsolutePath());
            }
        }

        return null;
    }

    private static JPanel createFormPanel(JFrame frame, DefaultTableModel tableModel) {
        CardPanel formContainer = new CardPanel(ACCENT_BLUE);
        formContainer.setLayout(new BorderLayout());
        formContainer.setPreferredSize(new Dimension(410, 0));

        JLabel formTitle = new JLabel("Form Input Data");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 19));
        formTitle.setForeground(TEXT_DARK);
        formTitle.setBorder(new EmptyBorder(4, 0, 18, 0));
        formContainer.add(formTitle, BorderLayout.NORTH);

        JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        fields.setOpaque(false);

        JComboBox<String> comboNama = createComboBox(DATA_MAHASISWA.keySet().toArray(new String[0]));
        JTextField textNim = createInnerTextField();
        textNim.setEditable(false);
        textNim.setBackground(Color.WHITE);
        JComboBox<String> comboMatkul = createComboBox(MATA_KULIAH);
        JComboBox<String> comboAbsensi = createComboBox(STATUS_ABSENSI);
        JTextField textNilai = createInnerTextField();

        // Auto-fill NIM
        comboNama.addActionListener(e -> {
            String selected = (String) comboNama.getSelectedItem();
            if (selected != null && DATA_MAHASISWA.containsKey(selected)) {
                textNim.setText(DATA_MAHASISWA.get(selected));
            }
        });

        addFieldGroup(fields, "Nama Mahasiswa", wrapWithIcon(ICON_USER, comboNama));
        addFieldGroup(fields, "Nomor Induk Mahasiswa (NIM)", wrapWithIcon(ICON_ID, textNim));
        addFieldGroup(fields, "Mata Kuliah", wrapWithIcon(ICON_BOOK, comboMatkul));
        addFieldGroup(fields, "Status Kehadiran", wrapWithIcon(ICON_CALENDAR, comboAbsensi));
        addFieldGroup(fields, "Nilai Akhir (0-100)", wrapWithIcon(ICON_STAR, textNilai));

        formContainer.add(fields, BorderLayout.CENTER);

        // Tombol Simpan & Reset
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 14, 0));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(new EmptyBorder(16, 0, 2, 0));

        JButton btnSimpan = createGradientButton("Simpan", ACCENT_BLUE_LIGHT, ACCENT_BLUE, ICON_CHECK);
        JButton btnReset = createGradientButton("Reset", RESET_LIGHT, RESET_DARK, ICON_REFRESH);
        btnPanel.add(btnSimpan);
        btnPanel.add(btnReset);
        formContainer.add(btnPanel, BorderLayout.SOUTH);

        btnSimpan.addActionListener(e -> {
            if (comboNama.getSelectedIndex() == 0
                    || comboMatkul.getSelectedIndex() == 0
                    || comboAbsensi.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Lengkapi semua dropdown pilihan!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            final int nilai;
            try {
                nilai = Integer.parseInt(textNilai.getText().trim());
                if (nilai < 0 || nilai > 100) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Masukkan nilai angka 0-100 yang valid!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String nim = textNim.getText();
            String nama = (String) comboNama.getSelectedItem();
            String matkul = (String) comboMatkul.getSelectedItem();
            String kehadiran = (String) comboAbsensi.getSelectedItem();
            String nilaiHuruf = konversiNilaiHuruf(nilai);

            int existingRow = -1;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (nim.equals(String.valueOf(tableModel.getValueAt(i, 0)))
                        && matkul.equals(String.valueOf(tableModel.getValueAt(i, 2)))) {
                    existingRow = i;
                    break;
                }
            }

            if (existingRow >= 0) {
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Data untuk mahasiswa \"" + nama + "\" pada mata kuliah\n\""
                        + matkul + "\" sudah ada.\nApakah Anda ingin memperbarui data tersebut?",
                    "Data Duplikat Ditemukan",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.setValueAt(kehadiran, existingRow, 3);
                    tableModel.setValueAt(nilai, existingRow, 4);
                    tableModel.setValueAt(nilaiHuruf, existingRow, 5);
                    JOptionPane.showMessageDialog(frame, "Data berhasil diperbarui!");
                }
                return;
            }

            
String sql = "INSERT INTO nilai_mahasiswa (nim, nama_mahasiswa, mata_kuliah, kehadiran, nilai_angka, nilai_huruf) "
           + "VALUES (?, ?, ?, ?, ?, ?)";

try (Connection conn = KoneksiDB.getConnection();
     PreparedStatement ps = conn.prepareStatement(sql)) {

    ps.setString(1, nim);
    ps.setString(2, nama);
    ps.setString(3, matkul);
    ps.setString(4, kehadiran);
    ps.setInt(5, nilai);
    ps.setString(6, nilaiHuruf);
    ps.executeUpdate();

    tableModel.addRow(new Object[]{nim, nama, matkul, kehadiran, nilai, nilaiHuruf});
    JOptionPane.showMessageDialog(frame, "Data berhasil masuk ke MySQL!");
} catch (SQLException ex) {
    JOptionPane.showMessageDialog(frame, "Gagal menyimpan ke MySQL: " + ex.getMessage());
}
        });

        btnReset.addActionListener(e -> {
            comboNama.setSelectedIndex(0);
            textNim.setText("");
            comboMatkul.setSelectedIndex(0);
            comboAbsensi.setSelectedIndex(0);
            textNilai.setText("");
        });

        return formContainer;
    }

    private static JPanel createTablePanel(JFrame frame, DefaultTableModel tableModel) {
        CardPanel tableContainer = new CardPanel(DANGER_RED);
        tableContainer.setLayout(new BorderLayout(0, 16));

        JPanel topBar = new JPanel(new BorderLayout(12, 0));
        topBar.setOpaque(false);

        JLabel tableTitle = new JLabel("Daftar Nilai Mahasiswa");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tableTitle.setForeground(HEADER_NAVY_DARK);
        topBar.add(tableTitle, BorderLayout.WEST);

        JButton btnHapus = createGradientButton(
            "Hapus Baris Terpilih", new Color(225, 45, 91), DANGER_RED_DARK, ICON_TRASH
        );
        btnHapus.setPreferredSize(new Dimension(205, 40));
        topBar.add(btnHapus, BorderLayout.EAST);
        tableContainer.add(topBar, BorderLayout.NORTH);

        JTable table = new JTable(tableModel);
        table.setRowHeight(42);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_DARK);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setGridColor(GRID_LINE);
        table.setSelectionBackground(new Color(207, 225, 255));
        table.setSelectionForeground(TEXT_DARK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableHeader.setBackground(new Color(233, 239, 246));
        tableHeader.setForeground(TEXT_DARK);
        tableHeader.setOpaque(true);
        tableHeader.setPreferredSize(new Dimension(0, 44));
        tableHeader.setReorderingAllowed(false);
        TableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(SwingConstants.LEFT);
                setBorder(new EmptyBorder(0, 6, 0, 6));
            }

            @Override
            public Component getTableCellRendererComponent(
                    JTable source, Object value, boolean selected, boolean focused,
                    int row, int column) {
                Component headerCell = super.getTableCellRendererComponent(
                    source, value, selected, focused, row, column
                );
                headerCell.setFont(new Font("Segoe UI", Font.BOLD, 13));
                headerCell.setForeground(TEXT_DARK);
                headerCell.setBackground(new Color(233, 239, 246));
                return headerCell;
            }
        };
        tableHeader.setDefaultRenderer(headerRenderer);

        TableColumnModel columns = table.getColumnModel();
        int[] columnWidths = {82, 140, 112, 106, 108, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            columns.getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        columns.getColumn(1).setMinWidth(130);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable source, Object value, boolean selected, boolean focused, int row, int column) {
                Component cell = super.getTableCellRendererComponent(
                    source, value, selected, focused, row, column
                );
                setBorder(new EmptyBorder(0, 9, 0, 9));
                if (!selected) {
                    cell.setBackground(row % 2 == 0 ? ZEBRA_EVEN : ZEBRA_ODD);
                    cell.setForeground(TEXT_DARK);
                }
                return cell;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            columns.getColumn(i).setCellRenderer(renderer);
        }

        btnHapus.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Pilih baris pada tabel yang ingin dihapus terlebih dahulu!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String nama = String.valueOf(tableModel.getValueAt(selectedRow, 1));
            String matkul = String.valueOf(tableModel.getValueAt(selectedRow, 2));
            int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Yakin ingin menghapus data \"" + nama + "\" untuk mata kuliah\n\""
                    + matkul + "\"?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        return tableContainer;
    }

    private static void addFieldGroup(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(7));
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
    }

    private static JPanel wrapWithIcon(int iconType, JComponent field) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        wrapper.setPreferredSize(new Dimension(340, 42));
        wrapper.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));

        IconBadge badge = new IconBadge(iconType);
        badge.setPreferredSize(new Dimension(40, 40));
        wrapper.add(badge, BorderLayout.WEST);

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(280, 40));
        field.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        if (field instanceof JComboBox<?>) {
            ((JComboBox<?>) field).setBackground(Color.WHITE);
        }
        wrapper.add(field, BorderLayout.CENTER);
        return wrapper;
    }

    private static JTextField createInnerTextField() {
        JTextField field = new JTextField();
        field.setForeground(TEXT_DARK);
        field.setCaretColor(ACCENT_BLUE);
        return field;
    }

    private static JComboBox<String> createComboBox(String[] options) {
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setForeground(TEXT_DARK);
        combo.setBackground(Color.WHITE);
        combo.setFocusable(false);
        return combo;
    }

    private static JButton createGradientButton(
            String text, Color lightColor, Color darkColor, int iconType) {
        JButton button = new JButton(text, new DrawnIcon(iconType, Color.WHITE));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(8);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(9, 14, 9, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(0, 42));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent component) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                JButton source = (JButton) component;
                Color top = source.getModel().isPressed() ? darkColor : lightColor;
                Color bottom = source.getModel().isPressed() ? lightColor.darker() : darkColor;
                g2.setPaint(new GradientPaint(0, 0, top, 0, component.getHeight(), bottom));
                g2.fill(new RoundRectangle2D.Double(0, 0, component.getWidth(), component.getHeight(), 12, 12));
                g2.dispose();
                super.paint(g, component);
            }
        });
        return button;
    }

    private static class CardPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private final Color accentColor;

        CardPanel(Color accentColor) {
            this.accentColor = accentColor;
            setOpaque(false);
            setBorder(new EmptyBorder(19, 20, 20, 20));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();

            g2.setColor(new Color(30, 45, 65, 34));
            g2.fill(new RoundRectangle2D.Double(3, 5, width - 6, height - 8, 18, 18));
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Double(1, 1, width - 4, height - 7, 16, 16));
            g2.setColor(accentColor);
            g2.fill(new RoundRectangle2D.Double(1, 1, width - 4, 8, 12, 12));
            g2.fillRect(1, 5, width - 4, 4);
            g2.setColor(BORDER_COLOR);
            g2.setStroke(new BasicStroke(1));
            g2.draw(new RoundRectangle2D.Double(1, 1, width - 4, height - 7, 16, 16));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class IconBadge extends JComponent {
        private static final long serialVersionUID = 1L;
        private final int iconType;

        IconBadge(int iconType) {
            this.iconType = iconType;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(ICON_BG);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            g2.setColor(new Color(71, 91, 119));
            paintIconShape(g2, iconType, getWidth() / 2, getHeight() / 2, 1.7f);
            g2.dispose();
        }
    }

    private static class DrawnIcon implements Icon {
        private final int iconType;
        private final Color color;

        DrawnIcon(int iconType, Color color) {
            this.iconType = iconType;
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return 18;
        }

        @Override
        public int getIconHeight() {
            return 18;
        }

        @Override
        public void paintIcon(Component component, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            paintIconShape(g2, iconType, x + 9, y + 9, 1.5f);
            g2.dispose();
        }
    }

    private static void paintIconShape(Graphics2D g2, int iconType, int centerX, int centerY, float stroke) {
        g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        switch (iconType) {
            case ICON_USER:
                g2.drawOval(centerX - 4, centerY - 8, 8, 8);
                g2.draw(new RoundRectangle2D.Double(centerX - 8, centerY + 1, 16, 10, 8, 8));
                break;
            case ICON_ID:
                g2.drawRoundRect(centerX - 9, centerY - 7, 18, 14, 3, 3);
                g2.drawOval(centerX - 6, centerY - 4, 4, 4);
                g2.drawLine(centerX - 7, centerY + 3, centerX - 1, centerY + 3);
                g2.drawLine(centerX + 2, centerY - 3, centerX + 6, centerY - 3);
                g2.drawLine(centerX + 2, centerY, centerX + 6, centerY);
                break;
            case ICON_BOOK:
                g2.drawRoundRect(centerX - 8, centerY - 7, 16, 14, 2, 2);
                g2.drawLine(centerX, centerY - 6, centerX, centerY + 6);
                break;
            case ICON_CALENDAR:
                g2.drawRoundRect(centerX - 8, centerY - 6, 16, 14, 3, 3);
                g2.drawLine(centerX - 8, centerY - 2, centerX + 8, centerY - 2);
                g2.drawLine(centerX - 4, centerY - 9, centerX - 4, centerY - 4);
                g2.drawLine(centerX + 4, centerY - 9, centerX + 4, centerY - 4);
                break;
            case ICON_STAR:
                Path2D star = new Path2D.Double();
                for (int i = 0; i < 10; i++) {
                    double angle = -Math.PI / 2 + i * Math.PI / 5;
                    double radius = i % 2 == 0 ? 9 : 4;
                    double x = centerX + Math.cos(angle) * radius;
                    double y = centerY + Math.sin(angle) * radius;
                    if (i == 0) star.moveTo(x, y);
                    else star.lineTo(x, y);
                }
                star.closePath();
                g2.draw(star);
                break;
            case ICON_CHECK:
                g2.drawRoundRect(centerX - 7, centerY - 8, 14, 16, 3, 3);
                g2.drawLine(centerX - 4, centerY, centerX - 1, centerY + 3);
                g2.drawLine(centerX - 1, centerY + 3, centerX + 5, centerY - 4);
                break;
            case ICON_REFRESH:
                g2.drawArc(centerX - 7, centerY - 7, 14, 14, 40, 280);
                g2.drawLine(centerX + 4, centerY - 7, centerX + 8, centerY - 7);
                g2.drawLine(centerX + 8, centerY - 7, centerX + 8, centerY - 3);
                break;
            case ICON_TRASH:
                g2.drawLine(centerX - 7, centerY - 6, centerX + 7, centerY - 6);
                g2.drawLine(centerX - 3, centerY - 9, centerX + 3, centerY - 9);
                g2.drawRoundRect(centerX - 5, centerY - 5, 10, 13, 2, 2);
                g2.drawLine(centerX - 2, centerY - 2, centerX - 2, centerY + 5);
                g2.drawLine(centerX + 2, centerY - 2, centerX + 2, centerY + 5);
                break;
            default:
                break;
        }
    }
}