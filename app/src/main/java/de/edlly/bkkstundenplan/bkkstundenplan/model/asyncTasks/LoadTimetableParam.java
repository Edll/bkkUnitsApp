package de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks;

public class LoadTimetableParam {

    private String fieldId;
    private String classId;

    public LoadTimetableParam(String fieldId, String classId) {
        this.fieldId = fieldId;
        this.classId = classId;
    }

    public String getClassId() {
        return classId;
    }

    public String getFieldId() {
        return fieldId;
    }

}
