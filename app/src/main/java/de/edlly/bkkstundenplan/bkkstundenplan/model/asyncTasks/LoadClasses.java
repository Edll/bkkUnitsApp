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

import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Classes;

public class LoadClasses extends AsyncTask<LoadClassesParam, Long, Classes> {
    private IloadClasses listener;
    private Context context;


    public LoadClasses(Context context, IloadClasses listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected Classes doInBackground(LoadClassesParam... classesParams) {
        Classes classes = null;

        for (LoadClassesParam classesParam : classesParams) {

            Log.w("test", "Load Class ClasseId: " + classesParam.getClassId());
            Log.w("test", "Load Class WeeksId: " + classesParam.getWeekId());
            String uri = UriStatics.getClassUrl(classesParam.getClassId(), classesParam.getWeekId());
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

                    String data = stringBuilder.toString();

                    classes = new Gson().fromJson(data, Classes.class);
                }
            } catch (IOException e) {
                // TODO: Fehler melden beim laden der Daten!
                e.printStackTrace();
            }

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

}
