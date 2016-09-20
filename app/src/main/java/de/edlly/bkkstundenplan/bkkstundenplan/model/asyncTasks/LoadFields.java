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

import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Fields;

public class LoadFields extends AsyncTask<LoadFieldParam, Long, Fields> {
    private IloadFields listener;
    private Context context;

    public LoadFields(IloadFields listener, Context context) {
        this.listener = listener;
        this.context = context;
    }


    @Override
    protected Fields doInBackground(LoadFieldParam... loadWeeksParams) {
        Fields fields = null;
        for(LoadFieldParam weeksParam : loadWeeksParams) {
            try {
                Log.w("test", "Load Field ClassId: " +  weeksParam.getClassId());
                Log.w("test", "Load Field FieldId: " +  weeksParam.getFieldId());
                URL url = new URL(UriStatics.getFieldUrl(weeksParam.getClassId(), weeksParam.getFieldId()));
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

                    fields = new Gson().fromJson(data, Fields.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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

}