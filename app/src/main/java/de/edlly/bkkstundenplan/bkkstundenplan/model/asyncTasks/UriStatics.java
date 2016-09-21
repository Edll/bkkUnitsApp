package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import android.util.Log;

import java.net.URL;

abstract class UriStatics {
    //http://www.edlly.de/bkk/
    final static String URL = "http://www.edlly.de/bkk/";

    static String getWeeksUrl(String weeksId){
        Log.w("test", "getWeeksUrlAll: " + URL + "jsonoutput.php?week=all");
        return URL + "jsonoutput.php?week=" + weeksId;
    }

    static String getClassUrl(String classId, String weekId) {
        return URL + "jsonoutput.php?week=" + weekId + "&class=" + classId;
    }

    static String getFieldUrl(String classId, String fieldId){
        Log.w("test", "getFieldUrl: " +  URL + "jsonoutput.php?field="+ fieldId +"&class=" + classId);
        return URL + "jsonoutput.php?field="+ fieldId +"&class=" + classId;
    }
}
