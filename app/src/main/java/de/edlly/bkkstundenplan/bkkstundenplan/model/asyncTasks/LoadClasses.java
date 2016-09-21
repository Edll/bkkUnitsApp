package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Classes;

public class LoadClasses extends AsyncTask<LoadClassesParam, Long, Classes> {
    private IloadClasses listener;
    private DataLoader dataLoader = new DataLoader();


    public LoadClasses(IloadClasses listener) {
        this.listener = listener;
    }

    @Override
    protected Classes doInBackground(LoadClassesParam... classesParams) {
        Classes classes = null;

        for (LoadClassesParam classesParam : classesParams) {

            String uri = UriStatics.getClassUrl(classesParam.getClassId(), classesParam.getWeekId());
            dataLoader.getJsonData(uri);

            String jsonData = dataLoader.getJsonData();
            if (jsonData != null) {
                classes = new Gson().fromJson(jsonData, Classes.class);
            }
        }
        return classes;
    }

    @Override
    protected void onPostExecute(final Classes classes) {
        super.onPostExecute(classes);
        if (classes == null) {
            listener.onException("Es konnten keine Klassen geladen werden.");
            return;
        }

        if (dataLoader.getError() != null) {
            listener.onException(dataLoader.getError());
            return;
        }
        listener.onLoaderClassesCompleted(classes);
    }

    public interface IloadClasses extends Iload {
        void onLoaderClassesCompleted(Classes classes);
    }

}
