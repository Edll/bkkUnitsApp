package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

import java.net.URL;

public abstract class UriStatics {
    //http://www.edlly.de/bkk/
    public final static String URL = "http://192.168.0.5/bkk/";


    public static String getClassUrl(String classId, String weekId) {
        return URL + "jsonoutput.php?week=" + weekId + "&class=" + classId;
    }

    public static String getWeeksUrlAll(){
        return URL + "jsonoutput.php?week=all";
    }

    public static String getFieldUrl(String classId, String fieldId){
        return URL + "jsonoutput.php?field="+ fieldId +"&class=" + classId;
    }
}
