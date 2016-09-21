package de.edlly.bkkstundenplan.bkkstundenplan.gui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.edlly.bkkstundenplan.bkkstundenplan.R;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadClasses;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadClassesParam;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadTimetable;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadTimetableParam;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadWeeks;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Classes;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.DataLoadException;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.FieldStatics;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Timetables;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Weeks;
import de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ExtendedAdapter;

import static de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ListStatics.FACH;
import static de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ListStatics.HOUR;
import static de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ListStatics.STUNDEN;
import static de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ListStatics.TAG;

public class MainActivity extends AppActivity implements LoadClasses.IloadClasses, LoadWeeks.IloadWeeks, LoadTimetable.IloadTimetable, Spinner.OnItemSelectedListener {
    private Spinner weeksSelecter;
    private Spinner classSelecter;
    private ListView stunden;

    private Weeks weeks;
    private Classes classes;
    private String weeksId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupView();
        loadWeeks();
    }


    private void setupView() {
        setContentView(R.layout.activity_main);
        weeksSelecter = (Spinner) findViewById(R.id.weeksSelecter);
        weeksSelecter.setOnItemSelectedListener(this);
        classSelecter = (Spinner) findViewById(R.id.classSelecter);
        classSelecter.setOnItemSelectedListener(this);
        stunden = (ListView) findViewById(R.id.timeTabel);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.weeksSelecter:

                String weekDate = parent.getItemAtPosition(pos).toString();
                loadClasses(weekDate);
                break;
            case R.id.classSelecter:

                String className = parent.getItemAtPosition(pos).toString();
                loadTimetable(weeksId, className);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO: Standart daten lassen!
        //   Log.w("test", adapterView.toString());
    }

    private void loadWeeks() {
        new LoadWeeks(this, this).execute();
    }

    @Override
    public void onLoaderWeeksCompleted(Weeks weeks) {
        if (weeks != null) {
            this.weeks = weeks;
            List<String> list = new ArrayList<>();

            for (Weeks.Week week : weeks.getWeeks()) {
                list.add(week.getDate());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            weeksSelecter.setAdapter(adapter);
        }
    }

    private void loadClasses(String weekDate) {
        weeksId = null;
        try {
            weeksId = weeks.findWeeksId(weekDate);
        } catch (DataLoadException e) {
            e.printStackTrace();
        }

        LoadClassesParam classesParam = new LoadClassesParam(weeksId, "this");
        new LoadClasses(this, this).execute(classesParam);
    }

    @Override
    public void onLoaderClassesCompleted(Classes classes) {
        this.classes = classes;
        if (classes != null) {
            List<String> list = new ArrayList<>();
            for (Classes.Classe week : classes.getClasses()) {
                list.add(week.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            classSelecter.setAdapter(adapter);
        }
    }

    private void loadTimetable(String weeksId, String className) {
        String classId = null;
        try {
            classId = classes.findClassId(className, weeksId);
        } catch (DataLoadException e) {
            e.printStackTrace();
        }

        LoadTimetableParam fieldParam = new LoadTimetableParam(weeksId, classId);
        new LoadTimetable(this, this).execute(fieldParam);
    }


    @Override
    public void onLoaderTimetableCompleted(Timetables timetables) {
        if (timetables != null) {
            List<Map<String, Object>> weekTimeTable = getWeeksTimetable(timetables);
            setWeekTimetable(weekTimeTable);
        }
    }

    private List<Map<String, Object>> getWeeksTimetable(Timetables timetables) {
        Map<Integer, List<Timetables.Timetable>> days = timetables.getDays();

        List<Map<String, Object>> weekTimeTable = new ArrayList<>();
        for (Map.Entry<Integer, List<Timetables.Timetable>> actualDays : days.entrySet()) {

            SimpleAdapter timeTableAdapter = createDayTimeTable(actualDays);

            Map<String, Object> dayList = new HashMap<>();
            dayList.put(STUNDEN, timeTableAdapter);

            Integer dayNumber = actualDays.getKey();
            dayList.put(TAG, FieldStatics.getDayName(dayNumber));
            weekTimeTable.add(dayList);
        }
        return weekTimeTable;
    }

    private SimpleAdapter createDayTimeTable(Map.Entry<Integer, List<Timetables.Timetable>> actualDays) {
        List<Timetables.Timetable> actualDay = actualDays.getValue();
        List<Map<String, Object>> dayTimeTable = Timetables.getHoursPlanOfActualDay(actualDay);

        return new SimpleAdapter(
                this,
                dayTimeTable,
                R.layout.listview_hours,
                new String[]{
                        HOUR,
                        FACH},
                new int[]{
                        R.id.stunde,
                        R.id.fach});
    }

    private void setWeekTimetable(List<Map<String, Object>> weekTimeTable) {
        ExtendedAdapter adapter = new ExtendedAdapter(
                this,
                weekTimeTable,
                R.layout.listview_timetable,
                new String[]{
                        TAG,
                        STUNDEN},
                new int[]{
                        R.id.tag,
                        R.id.stunden});

        stunden.setAdapter(adapter);
    }

}
