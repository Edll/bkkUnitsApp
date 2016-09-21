package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;


@SuppressWarnings("unused")
public class LoadClassesParam {
    public LoadClassesParam(String weekId, String classId) {
        this.weekId = weekId;
        this.classId = classId;
    }

    private String classId;
    private String weekId;

    String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    String getWeekId() {
        return weekId;
    }

    public void setWeekId(String weekId) {
        this.weekId = weekId;
    }
}