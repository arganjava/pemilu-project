package com.journaldev.bootifulmongodb.spreadsheet;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.journaldev.bootifulmongodb.model.Tps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SheetsQuickstart {
    public static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static final String TOKENS_DIRECTORY_PATH = "tokens";
    public static final Object[] COLUMN= new Object[]{"Generate At", "Provinsi","Kota", "Kecamatan", "Kelurahan", "No TPS", "Jumlah Pemilih",
    "Jumlah Pengguna", "Total Suara", "Suara Tidak Sah", "Suara Sah", "JOKMA", "PAS", "JOKMA+PAS", "Mismatch", "images"};

    public static final String spreadsheetId = "1TB1c6pCqdxOifkQGuOyRZTz3V7nMGWDSJxeicywvomg";
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    public static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    public static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1TB1c6pCqdxOifkQGuOyRZTz3V7nMGWDSJxeicywvomg";
        final String range = "DKI JAKARTA!A1:D";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        System.out.println(service.getBaseUrl());

        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s, %s\n", row.get(0), row.get(3));
            }
        }
    }

    public static int createHeaderColumn(String provinsi)throws IOException, GeneralSecurityException{
        String range = provinsi+"!A1:Z";
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        // Build a new authorized API client service.
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, SheetsQuickstart.JSON_FACTORY, SheetsQuickstart.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(SheetsQuickstart.APPLICATION_NAME)
                .build();

        List<List<Object>> values = Arrays.asList(
                Arrays.asList(SheetsQuickstart.COLUMN)
        );

        ValueRange body = new ValueRange()
                .setValues(values);
        UpdateValuesResponse response =
                service.spreadsheets().values().update(SheetsQuickstart.spreadsheetId, range, body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();
        System.out.printf("%d cells updated.", response.getUpdatedCells());

        System.out.println(service.getBaseUrl());

        return response.getUpdatedCells();
    }

    public static int generateDataRows(String provinsi, List<Tps> tpsList)throws IOException, GeneralSecurityException{
        String range = provinsi+"!A2:Z";
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        // Build a new authorized API client service.
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, SheetsQuickstart.JSON_FACTORY, SheetsQuickstart.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(SheetsQuickstart.APPLICATION_NAME)
                .build();
        List<List<Object>> values = new ArrayList<>();

        for(Tps tps : tpsList){
            String imageString = Arrays.asList(tps.getImages()).stream().collect(Collectors.joining(" , "));
            Object[] rows = new Object[]{tps.getGenerateAt(), tps.getProvinsi(), tps.getKota(), tps.getKecamatan(), tps.getKelurahan(), tps.getNama(), tps.getPemilihJumlah(),
                    tps.getPenggunaJumlah(), tps.getSuaraTotal(), tps.getSuaraTidakSah(), tps.getSuaraSah(), tps.getChart().get_21(), tps.getChart().get_22(), tps.getChart().totalChart(), tps.getSuaraSah() - tps.getChart().totalChart(), imageString};
            values.add(Arrays.asList(rows));
        }
        ValueRange body = new ValueRange()
                .setValues(values);
        UpdateValuesResponse response =
                service.spreadsheets().values().update(SheetsQuickstart.spreadsheetId, range, body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();
        System.out.printf("%d cells updated.", response.getUpdatedCells());

        System.out.println(service.getBaseUrl());

        return response.getUpdatedCells();
    }


}