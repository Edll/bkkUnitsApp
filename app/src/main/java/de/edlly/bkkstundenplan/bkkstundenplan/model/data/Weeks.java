package de.edlly.bkkstundenplan.bkkstundenplan.model.data;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Weeks {
    private List<Week> weeks = new ArrayList<>();

    public List<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }

    @Override
    public String toString() {
        return weeks.toString();
    }

    public String findWeeksId(String result) throws DataLoadException {
        for (Week week : weeks) {
            if (week.getDate().equals(result)) {
                return week.getId().toString();
            }
        }
        throw new DataLoadException("Es konnte keine Woche mit dem Datum: " + result
                + " gefunden werden.");
    }


    public class Week extends Data {
        private String date;
        private Integer number;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "Week{" +
                    "date='" + date + '\'' +
                    ", number=" + number +
                    '}';
        }
    }
}
