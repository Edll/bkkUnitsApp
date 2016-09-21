package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Weeks;

public class LoadWeeks extends AsyncTask<LoadWeeksParam, Long, Weeks> {
    private IloadWeeks listener;
    private DataLoader dataLoader = new DataLoader();

    public LoadWeeks(IloadWeeks listener) {
        this.listener = listener;
    }

    @Override
    protected Weeks doInBackground(LoadWeeksParam... loadWeeksParams) {
        Weeks weeks = null;
        for (LoadWeeksParam weeksParam : loadWeeksParams) {

            String uri = UriStatics.getWeeksUrl(weeksParam.getWeek());
            dataLoader.getJsonData(uri);

            String jsonData = dataLoader.getJsonData();
            if (jsonData != null) {
                weeks = new Gson().fromJson(jsonData, Weeks.class);
            } else {
                dataLoader.setError("Es konnte die Json Daten für " +
                        "die Wochen nicht geladen werden.");
            }
        }
        return weeks;

    }

    @Override
    protected void onPostExecute(final Weeks weeks) {
        super.onPostExecute(weeks);
        if (weeks == null) {
            listener.onException("Es konnten keine Wochen geladen werden.");
            return;
        }

        if (dataLoader.getError() != null) {
            listener.onException(dataLoader.getError());
            return;
        }
        listener.onLoaderWeeksCompleted(weeks);
    }

    public interface IloadWeeks extends Iload {
        void onLoaderWeeksCompleted(Weeks classes);
    }
}