package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Weeks;

public class LoadWeeks extends AsyncTask<LoadWeeks.LoadWeeksParam, Long, Weeks> {
    private IloadWeeks listener;
    private Context context;

    public LoadWeeks(IloadWeeks listener, Context context) {
        this.listener = listener;
        this.context = context;
    }


    @Override
    protected Weeks doInBackground(LoadWeeksParam... loadWeeksParams) {
        Weeks weeks = null;
        try {
            URL url = new URL(UriStatics.getWeeksUrlAll());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            String contenType = conn.getContentType();

            if ("application/json".equals(contenType)) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();


                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();

                String data = stringBuilder.toString();
                weeks = new Gson().fromJson(data, Weeks.class);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (LoadWeeksParam weeksParam : loadWeeksParams) {

            // auslesen des Json Array

            // schreiben des Json in classe

        }

        return weeks;

    }

    @Override
    protected void onPostExecute(final Weeks weeks) {
        listener.onLoaderWeeksCompleted(weeks);
    }

    public interface IloadWeeks {
        void onLoaderWeeksCompleted(Weeks classes);
    }

    public class LoadWeeksParam {
        public LoadWeeksParam(String week) {
            this.week = week;
        }

        private String week;

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }
}