package de.edlly.bkkstundenplan.bkkstundenplan.model.data;

import java.util.ArrayList;
import java.util.List;

public class Fields {
    private List<Field> fields = new ArrayList<>();

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return fields.toString();
    }


    public class Field extends Data {
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
            return "Field{" +
                    "data='" + data + '\'' +
                    ", dataTyp=" + dataTyp +
                    ", hour=" + hour +
                    ", day=" + day +
                    '}';

        }
    }
}
