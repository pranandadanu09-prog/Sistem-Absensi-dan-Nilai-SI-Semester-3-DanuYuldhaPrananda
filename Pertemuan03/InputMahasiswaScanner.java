package Pertemuan03;

import java.util.Scanner;

public class InputMahasiswaScanner {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Nama     : ");
        String nama = sc.nextLine();

        System.out.print("NIM      : ");
        String nim = sc.nextLine();

        System.out.print("Prodi    : ");
        String prodi = sc.nextLine();

        System.out.print("Semester : ");
        int semester = sc.nextInt();

        System.out.print("Nilai    : ");
        int nilai = sc.nextInt();

        System.out.println("\n--- DATA MAHASISWA ---");
        System.out.println("Nama     : " + nama);
        System.out.println("NIM      : " + nim);
        System.out.println("Prodi    : " + prodi);
        System.out.println("Semester : " + semester);
        System.out.println("Nilai    : " + nilai);

        sc.close();
    }
} 
