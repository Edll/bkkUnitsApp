package de.edlly.bkkstundenplan.bkkstundenplan.model.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ListStatics.FACH;
import static de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ListStatics.HOUR;

public class Timetables {
    private List<Timetable> timetables = new ArrayList<>();

    @NonNull
    public static List<Map<String, Object>> getHoursPlanOfActualDay(List<Timetable> actualDay) {
        List<Map<String, Object>> dayTimeTable = new ArrayList<>();

        for (Timetable actualHour : actualDay) {

            if (actualHour.getDataTyp() == 1 || actualHour.getDataTyp() == 2) {
                Map<String, Object> timeTable = new HashMap<>();
                if (actualHour.getDataTyp() == 1) {
                    timeTable.put(HOUR, actualHour.getHour());
                } else {
                    timeTable.put(HOUR, "");
                }
                timeTable.put(FACH, actualHour.getData());
                dayTimeTable.add(timeTable);
            }

        }
        return dayTimeTable;
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    @Override
    public String toString() {
        return timetables.toString();
    }

    public Map<Integer, List<Timetable>> getDays() {

        Map<Integer, List<Timetables.Timetable>> days = new TreeMap<>();

        for (Timetables.Timetable timetable : timetables) {

            if (days.get(timetable.getDay()) == null) {
                List<Timetables.Timetable> timetableList = new ArrayList<>();
                timetableList.add(timetable);
                days.put(timetable.getDay(), timetableList);
            } else {
                List<Timetables.Timetable> timetableList = days.get(timetable.getDay());
                timetableList.add(timetable);
                days.put(timetable.getDay(), timetableList);
            }
        }
        return days;
    }


    public class Timetable extends Data {
        private String data;
        private Integer dataTyp;
        private Integer hour;
        private Integer day;


        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Integer getDataTyp() {
            return dataTyp;
        }

        public void setDataTyp(Integer dataTyp) {
            this.dataTyp = dataTyp;
        }

        public Integer getHour() {
            return hour;
        }

        public void setHour(Integer hour) {
            this.hour = hour;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        @Override
        public String toString() {
            return "Timetable{" +
                    "data='" + data + '\'' +
                    ", dataTyp=" + dataTyp +
                    ", hour=" + hour +
                    ", day=" + day +
                    '}';

        }
    }
}
