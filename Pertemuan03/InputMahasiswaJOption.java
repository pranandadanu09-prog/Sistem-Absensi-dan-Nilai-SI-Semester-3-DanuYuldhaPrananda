package Pertemuan03;
import javax.swing.JOptionPane;

public class InputMahasiswaJOption {
    public static void main(String[] args) {

        String nama = JOptionPane.showInputDialog("Masukkan Nama:");
        String nim = JOptionPane.showInputDialog("Masukkan NIM:");
        String prodi = JOptionPane.showInputDialog("Masukkan Prodi:");
        
        int semester = Integer.parseInt(
            JOptionPane.showInputDialog("Masukkan Semester:")
        );

        int nilai = Integer.parseInt(
            JOptionPane.showInputDialog("Masukkan Nilai:")
        );

        String hasil =
            "--- DATA MAHASISWA ---\n" +
            "Nama     : " + nama + "\n" +
            "NIM      : " + nim + "\n" +
            "Prodi    : " + prodi + "\n" +
            "Semester : " + semester + "\n" +
            "Nilai    : " + nilai;

        JOptionPane.showMessageDialog(null, hasil);
    }
}