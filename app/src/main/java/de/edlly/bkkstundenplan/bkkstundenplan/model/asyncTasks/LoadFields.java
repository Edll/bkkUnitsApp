package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Fields;

public class LoadFields extends AsyncTask<LoadFields.LoadWeeksParam, Long, Fields> {
    private IloadFields listener;
    private Context context;

    public LoadFields(IloadFields listener, Context context) {
        this.listener = listener;
        this.context = context;
    }


    @Override
    protected Fields doInBackground(LoadWeeksParam... loadWeeksParams) {
        Fields fields = null;
        try {
            URL url = new URL(UriStatics.getFieldUrl("1", "1"));
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

                fields = new Gson().fromJson(data, Fields.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (LoadWeeksParam weeksParam : loadWeeksParams) {

            // auslesen des Json Array

            // schreiben des Json in classe

        }

        return fields;

    }

    @Override
    protected void onPostExecute(final Fields weeks) {

        listener.onLoaderHoursCompleted(weeks);

    }

    public interface IloadFields {
        void onLoaderHoursCompleted(Fields fields);
    }

    public class LoadWeeksParam {
        private String fieldId;
        private String classId;

        public String getClassId() {
            return classId;
        }

        public String getFieldId() {
            return fieldId;
        }
    }
}