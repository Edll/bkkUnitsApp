package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import android.util.Log;

import java.net.URL;

public abstract class UriStatics {
    //http://www.edlly.de/bkk/
    public final static String URL = "http://www.edlly.de/bkk/";


    public static String getClassUrl(String classId, String weekId) {
        return URL + "jsonoutput.php?week=" + weekId + "&class=" + classId;
    }

    public static String getWeeksUrlAll(){
        Log.w("test", "getWeeksUrlAll: " + URL + "jsonoutput.php?week=all");
        return URL + "jsonoutput.php?week=all";
    }

    public static String getFieldUrl(String classId, String fieldId){
        Log.w("test", "getFieldUrl: " +  URL + "jsonoutput.php?field="+ fieldId +"&class=" + classId);
        return URL + "jsonoutput.php?field="+ fieldId +"&class=" + classId;
    }
}
