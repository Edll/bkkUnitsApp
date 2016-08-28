package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Classes;

public class LoadClasses extends AsyncTask<LoadClasses.LoadClassesParam, Long, Classes> {
    private IloadClasses listener;
    private Context context;


    public LoadClasses(Context context, IloadClasses listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected Classes doInBackground(LoadClassesParam... params) {
        Classes classes = null;
        try {
            URL url = new URL("http://10.0.2.2/bkk/jsonoutput.php?class=all");
            // URL url = new URL("http://www.edlly.de/bkk/jsonoutput.php?class=all");
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

                classes = new Gson().fromJson(data, Classes.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (LoadClassesParam weeksParam : params) {

            // auslesen des Json Array

            // schreiben des Json in classe

        }

        return classes;
    }


    @Override
    protected void onPostExecute(final Classes classes) {
        super.onPostExecute(classes);
        listener.onLoaderClassesCompleted(classes);
    }

    public interface IloadClasses {
        void onLoaderClassesCompleted(Classes classes);
    }

    public class LoadClassesParam {
        private String classe;
        private Integer weekId;

        public String getClasse() {
            return classe;
        }

        public void setClasse(String classe) {
            this.classe = classe;
        }

        public Integer getWeekId() {
            return weekId;
        }

        public void setWeekId(Integer weekId) {
            this.weekId = weekId;
        }
    }
}
