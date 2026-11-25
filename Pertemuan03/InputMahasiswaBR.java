package Pertemuan03;
import java.io.*;

public class InputMahasiswaBR {
    public static void main(String[] args) throws IOException {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Nama     : ");
        String nama = br.readLine();

        System.out.print("NIM      : ");
        String nim = br.readLine();

        System.out.print("Prodi    : ");
        String prodi = br.readLine();

        System.out.print("Semester : ");
        int semester = Integer.parseInt(br.readLine());

        System.out.print("Nilai    : ");
        int nilai = Integer.parseInt(br.readLine());

        System.out.println("\n--- DATA MAHASISWA ---");
        System.out.println("Nama     : " + nama);
        System.out.println("NIM      : " + nim);
        System.out.println("Prodi    : " + prodi);
        System.out.println("Semester : " + semester);
        System.out.println("Nilai    : " + nilai);
    }
}