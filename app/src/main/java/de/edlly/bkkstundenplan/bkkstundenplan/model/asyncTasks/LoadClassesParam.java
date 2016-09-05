package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;


public class LoadClassesParam {
    public LoadClassesParam(String weekId, String classId) {
        this.weekId = weekId;
        this.classId = classId;
    }

    private String classId;
    private String weekId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getWeekId() {
        return weekId;
    }

    public void setWeekId(String weekId) {
        this.weekId = weekId;
    }
}