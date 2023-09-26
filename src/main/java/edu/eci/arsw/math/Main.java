/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author hcadavid
 */
public class Main {

    public static void main(String a[]) {

        // System.out.println(bytesToHex(PiDigits.getDigits(1, 10, 8, "hilos 1")));
        System.out.println(bytesToHex(PiDigits.getDigits(101, 1000, 25, "hilos 2")));
        // System.out.println(bytesToHex(PiDigits.getDigits(1001, 10000, 50, "hilos 3")));
        // System.out.println(bytesToHex(PiDigits.getDigits(10001, 100000, 65, "hilos 4")));
        // System.out.println(bytesToHex(PiDigits.getDigits(100001, 1000000, 70, "hilos 5")));
        // System.out.println(bytesToHex(PiDigits.getDigits(1001, 1000000, 50, "hilos 4")));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                
                Thread.sleep(5000);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            PiDigits.sayStop();

            System.out.println("Presione Enter para continuar...");
            try {
                br.readLine();
                PiDigits.continuar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexChars.length; i = i + 2) {
            // sb.append(hexChars[i]);
            sb.append(hexChars[i + 1]);
        }
        return sb.toString();
    }

}
