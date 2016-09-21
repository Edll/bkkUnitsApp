package de.edlly.bkkstundenplan.bkkstundenplan.model.data;

import java.util.ArrayList;
import java.util.List;

public class Classes {
    private List<Classe> classes = new ArrayList<>();

    public List<Classe> getClasses() {
        return classes;
    }

    public void setClasses(List<Classe> classes) {
        this.classes = classes;
    }


    public String findClassId(String result, String weekId) throws DataLoadException {
        for (Classe classe : classes) {
            if (classe.getName().equals(result) && classe.getWeeksId().toString().equals(weekId)) {
                return classe.getId().toString();
            }
        }

        throw new DataLoadException("Es konnte keine Woche mit dem Datum: " + result + " gefunden werden.");
    }

    @Override
    public String toString() {
        return classes.toString();
    }

    @SuppressWarnings("unused")
    public class Classe extends Data {
        private String name;
        private Integer weeksId;
        private Integer number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getWeeksId() {
            return weeksId;
        }

        public void setWeeksId(Integer weeksId) {
            this.weeksId = weeksId;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "Classe{" +
                    "name='" + name + '\'' +
                    ", weeksId=" + weeksId +
                    ", number=" + number +
                    '}';
        }
    }

}
