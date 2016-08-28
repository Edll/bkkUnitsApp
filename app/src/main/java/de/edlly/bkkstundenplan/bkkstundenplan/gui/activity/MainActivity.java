package de.edlly.bkkstundenplan.bkkstundenplan.gui.activity;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadFields;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadWeeks;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Classes;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Fields;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Weeks;
import de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ExtendedAdapter;

public class MainActivity extends Activity implements LoadClasses.IloadClasses, LoadWeeks.IloadWeeks, LoadFields.IloadFields, Spinner.OnItemSelectedListener {
    private Spinner weeksSelecter;
    private Spinner classSelecter;
    private ListView stunden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        weeksSelecter = (Spinner) findViewById(R.id.weeksSelecter);
        weeksSelecter.setOnItemSelectedListener(this);
        classSelecter = (Spinner) findViewById(R.id.classSelecter);
        stunden = (ListView) findViewById(R.id.timeTabel);


        LoadWeeks.LoadWeeksParam param;
        new LoadWeeks(this, this).execute();

        LoadClasses.LoadClassesParam param1;

        new LoadClasses(this, this).execute();

        LoadClasses.LoadClassesParam param2;

        new LoadFields(this, this).execute();


    }

    @Override
    public void onLoaderClassesCompleted(Classes classes) {
        List<String> list = new ArrayList<>();
        for (Classes.Classe week : classes.getClasses()) {
            list.add(week.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSelecter.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //   Log.w("test", view.toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //   Log.w("test", adapterView.toString());
    }

    @Override
    public void onLoaderWeeksCompleted(Weeks weeks) {

        List<String> list = new ArrayList<>();
        for (Weeks.Week week : weeks.getWeeks()) {
            list.add(week.getDate());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        weeksSelecter.setAdapter(adapter);
    }

    @Override
    public void onLoaderHoursCompleted(Fields fields) {
        Log.w("test", "runFueld");
        Log.w("test", "size: " + fields.getFields().size());
        for (Fields.Field field : fields.getFields()) {
            Log.w("test", field.toString());
        }


        List<Map<String, Object>> listAdapta = new ArrayList<>();

        Map<String, Object> mapTest = new HashMap<>();
        mapTest.put("hour", 1);
        mapTest.put("fach", "Käse Machen");
        listAdapta.add(mapTest);

        SimpleAdapter test = new SimpleAdapter(this, listAdapta, R.layout.listview_hours, new String[]{"hour", "fach"}, new int[]{R.id.stunde, R.id.fach});

        Map<String, Object> map = new HashMap<>();
        map.put("stunden", test);
        map.put("tag", "Montag");
        List<Map<String, Object>> list = new ArrayList<>();

        list.add(map);
        ExtendedAdapter adapter = new ExtendedAdapter(this, list, R.layout.listview_timetable, new String[]{"stunden", "tag"}, new int[]{R.id.stunden, R.id.tag});

        stunden.setAdapter(adapter);
    }
}
