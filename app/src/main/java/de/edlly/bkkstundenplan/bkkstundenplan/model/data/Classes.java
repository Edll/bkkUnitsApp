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

    @Override
    public String toString() {
        return classes.toString();
    }

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
            return "[Id: " + getId() + " Number: " + number + " Class: " + name + " ]";
        }
    }

}
