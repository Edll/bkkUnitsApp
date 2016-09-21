package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

@SuppressWarnings("unused")
public class LoadWeeksParam {
    public LoadWeeksParam(String week) {
        this.week = week;
    }

    private String week;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
