package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Weeks;

public  class LoadWeeks  extends AsyncTask<LoadWeeks.LoadWeeksParam, Long, Weeks> {
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
           // URL url = new URL("http://10.0.2.2/eclipse/bkkUnits/application/jsonoutput.php?week=all");
            URL url = new URL("http://www.edlly.de/bkk/jsonoutput.php?week=all");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            String contenType = conn.getContentType();

            if ("application/json".equals(contenType)) {
                conn.setRequestMethod("GET");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();


                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();

                String data = stringBuilder.toString();
                Log.w("test", "doInBackground: " + data);
                weeks = new Gson().fromJson(data, Weeks.class);
            //    Log.w("test", "doInBackground: " + weeks.getList().get(0).toString());
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
        Log.w("test", "onPostClasses" + weeks);
        listener.onLoaderWeeksCompleted(weeks);


    }

    public class LoadWeeksParam {
        private String week;

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }

    public interface IloadWeeks {
        void onLoaderWeeksCompleted(Weeks classes);
    }
}