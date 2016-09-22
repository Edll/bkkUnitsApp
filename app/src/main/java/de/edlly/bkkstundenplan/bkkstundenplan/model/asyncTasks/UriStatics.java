package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import android.util.Log;

import java.net.URL;

abstract class UriStatics {
    //http://www.edlly.de/bkk/
    final static String URL = "http://www.edlly.de/bkk/";

    static String getWeeksUrl(String weeksId){
        String url =  URL + "jsonoutput.php?func=week&week_id=" + weeksId;
        Log.w("test", "getWeeksUrl: " +  url);
        return url;
    }

    static String getClassUrl(String weekId) {
        String url = URL + "jsonoutput.php?func=class&week_id=" + weekId ;
        Log.w("test", "getClassUrl: " +  url);
        return url;
    }

    static String getTimetableUrl(String classId, String weekId){
        String url = URL + "jsonoutput.php?func=timetable&week_id="+ weekId +"&class_id=" + classId;
        Log.w("test", "getTimetableUrl: " +  url);
        return url;
    }
}
