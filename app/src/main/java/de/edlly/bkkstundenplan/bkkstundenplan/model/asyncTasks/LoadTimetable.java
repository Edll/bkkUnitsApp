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

import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Timetables;

public class LoadTimetable extends AsyncTask<LoadTimetableParam, Long, Timetables> {
    private IloadTimetable listener;
    private Context context;

    public LoadTimetable(IloadTimetable listener, Context context) {
        this.listener = listener;
        this.context = context;
    }


    @Override
    protected Timetables doInBackground(LoadTimetableParam... loadWeeksParams) {
        Timetables timetables = null;
        for(LoadTimetableParam weeksParam : loadWeeksParams) {
            try {
                Log.w("test", "Load Timetable ClassId: " +  weeksParam.getClassId());
                Log.w("test", "Load Timetable FieldId: " +  weeksParam.getFieldId());
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

                    Log.w("test", "doInBackground: " + data);
                    timetables = new Gson().fromJson(data, Timetables.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return timetables;

    }

    @Override
    protected void onPostExecute(final Timetables weeks) {

        listener.onLoaderTimetableCompleted(weeks);

    }

    public interface IloadTimetable {
        void onLoaderTimetableCompleted(Timetables timetables);
    }

}