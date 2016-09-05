package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

public abstract class UriStatics {
    //http://www.edlly.de/bkk/
    public final static String URL = "http://www.edlly.de/bkk/";


    public static String getClassUrl(String classId, String weekId) {
        return URL + "jsonoutput.php?week=" + weekId + "&class=" + weekId;
    }
}
