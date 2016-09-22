package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Timetables;

public class LoadTimetable extends AsyncTask<LoadTimetableParam, Long, Timetables> {
    private IloadTimetable listener;
    private DataLoader dataLoader = new DataLoader();

    public LoadTimetable(IloadTimetable listener) {
        this.listener = listener;
    }

    @Override
    protected Timetables doInBackground(LoadTimetableParam... loadWeeksParams) {
        Timetables timetables = null;

        for (LoadTimetableParam weeksParam : loadWeeksParams) {

            String uri = UriStatics.getTimetableUrl(weeksParam.getClassId(), weeksParam.getWeekId());

            dataLoader.getJsonData(uri);

            String jsonData = dataLoader.getJsonData();
            if (jsonData != null) {
                timetables = new Gson().fromJson(jsonData, Timetables.class);
            } else {
                dataLoader.setError("Es konnte die Json Daten für den " +
                        "Stundenplan nicht geladen werden.");
            }
        }

        return timetables;
    }

    @Override
    protected void onPostExecute(final Timetables timetables) {
        if (timetables == null) {
            listener.onException("Es konnten kein Stundenplan geladen werden.");
            return;
        }

        if (dataLoader.getError() != null) {
            listener.onException(dataLoader.getError());
            return;
        }
        listener.onLoaderTimetableCompleted(timetables);
    }

    public interface IloadTimetable extends Iload {
        void onLoaderTimetableCompleted(Timetables timetables);
    }

}