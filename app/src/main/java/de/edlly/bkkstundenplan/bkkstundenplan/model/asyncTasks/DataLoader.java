package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class DataLoader {
    private String jsonData;
    private String error;

    String getError() {
        return error;
    }

    String getJsonData() {
        return jsonData;
    }

    void getJsonData(String uri) {
        try {
            URL url = new URL(uri);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            String contenType = httpURLConnection.getContentType();
            if ("application/json".equals(contenType)) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();

                jsonData = stringBuilder.toString();
            } else {
                error = "Fehler beim laden der Daten";
            }
        } catch (IOException e) {
            error = e.getMessage();
        }
    }
}
