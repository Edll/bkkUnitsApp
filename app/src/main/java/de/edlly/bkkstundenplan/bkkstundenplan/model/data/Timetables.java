package de.edlly.bkkstundenplan.bkkstundenplan.model.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ListStatics.FACH;
import static de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ListStatics.HOUR;

@SuppressWarnings("unused")
public class Timetables {
    private List<Timetable> timetables = new ArrayList<>();

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    public Map<Integer, List<Timetable>> getSortedDays() {
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

    public static List<Map<String, Object>> getHoursPlanOfActualDay(List<Timetable> actualDay) {
        List<Map<String, Object>> dayTimeTable = new ArrayList<>();

        Collections.sort(actualDay);

        Map<Integer, String> sortedDateMap = new TreeMap<>();

        for (Timetable actualHour : actualDay) {
            Integer hour = actualHour.getHour();
            if (sortedDateMap.containsKey(hour)) {
                String actual = sortedDateMap.get(hour);
                actual = actual + ", " + actualHour.getData();
                sortedDateMap.put(hour, actual);
            } else {
                sortedDateMap.put(hour, actualHour.getData());
            }
        }


        for (Map.Entry<Integer, String> actualHour : sortedDateMap.entrySet()) {

            Map<String, Object> timeTable = new HashMap<>();

            timeTable.put(HOUR, actualHour.getKey());

            timeTable.put(FACH, actualHour.getValue());
            dayTimeTable.add(timeTable);
        }

        return dayTimeTable;
    }

    @Override
    public String toString() {
        return "Timetables{" +
                "timetables=" + timetables +
                '}';
    }

    public class Timetable extends Data implements Comparable<Timetable> {
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

        Integer getDataTyp() {
            return dataTyp;
        }

        public void setDataTyp(Integer dataTyp) {
            this.dataTyp = dataTyp;
        }

        Integer getHour() {
            return hour;
        }

        public void setHour(Integer hour) {
            this.hour = hour;
        }

        Integer getDay() {
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

        @Override
        public int compareTo(@NonNull Timetable o) {
            if (this.getHour().equals(o.getHour())) {
                if (this.getDataTyp() < o.getDataTyp()) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (this.getHour() < o.getHour()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }
}
