package com.journaldev.bootifulmongodb.spreadsheet;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class CreateOperation {


    public  static  String[] WILAYAHS = new String[]{"ACEH", "SUMATERA UTARA", "SUMATERA BARAT", "RIAU" ,"JAMBI","SUMATERA SELATAN",
    "BENGKULU", "LAMPUNG", "KEPULAUAN BANGKA BELITUNG", "KEPULAUAN RIAU", "DKI JAKARTA", "JAWA BARAT",
            "JAWA TENGAH", "DAERAH ISTIMEWA YOGYAKARTA", "JAWA TIMUR", "BANTEN", "BALI", "NUSA TENGGARA BARAT",
            "NUSA TENGGARA TIMUR", "KALIMANTAN BARAT", "KALIMANTAN TENGAH", "KALIMANTAN SELATAN", "KALIMANTAN TIMUR",
    "SULAWESI UTARA", "SULAWESI TENGAH", "SULAWESI SELATAN", "SULAWESI TENGGARA", "GORONTALO", "SULAWESI BARAT",
    "MALUKU", "MALUKU UTARA", "PAPUA", "PAPUA BARAT", "KALIMANTAN UTARA"};



    public static void main(String... args) throws IOException, GeneralSecurityException {
        for (String wilayah : WILAYAHS){
            SheetsQuickstart.createHeaderColumn(wilayah);
        }
    }


}
