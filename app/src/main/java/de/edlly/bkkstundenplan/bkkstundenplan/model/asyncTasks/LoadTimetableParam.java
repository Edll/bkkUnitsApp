package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

public class LoadTimetableParam {
    private String weekId;
    private String classId;

    public LoadTimetableParam(String classId,String weekId) {
        this.weekId = weekId;
        this.classId = classId;
    }

    String getClassId() {
        return classId;
    }

    String getWeekId() {
        return weekId;
    }

}
