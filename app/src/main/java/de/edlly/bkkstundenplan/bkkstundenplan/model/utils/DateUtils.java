package de.edlly.bkkstundenplan.bkkstundenplan.model.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getActualWeek() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        Date time = c.getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.M.yyyy");
        return simpleDateFormat.format(time);
    }
}
