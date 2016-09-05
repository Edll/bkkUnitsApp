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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.edlly.bkkstundenplan.bkkstundenplan.R;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadClasses;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadClassesParam;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadFields;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadWeeks;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Classes;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.FieldStatics;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Fields;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Weeks;
import de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ExtendedAdapter;
import de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ListStatics;

import static de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ListStatics.*;

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



      //  LoadClasses.LoadClassesParam param2;

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

        switch (adapterView.getId()){
            case R.id.weeksSelecter:
                LoadClassesParam param1 = new LoadClassesParam("2", "this");
                 new LoadClasses(this, this).execute(param1);
                Log.w("test", view.toString());
                break;
        }


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

        // in Tagen Splitten
        Map<Integer, List<Fields.Field>> days = new TreeMap<>();
        for (Fields.Field field : fields.getFields()) {

            if (days.get(field.getDay()) == null) {
                List<Fields.Field> fieldList = new ArrayList<>();
                fieldList.add(field);
                days.put(field.getDay(), fieldList);
            } else {
                List<Fields.Field> fieldList = days.get(field.getDay());
                fieldList.add(field);
                days.put(field.getDay(), fieldList);
            }
        }


        List<Map<String, Object>> list = new ArrayList<>();

        for (Object o : days.entrySet()) {
            Map.Entry thisEntry = (Map.Entry) o;
            Integer key = (Integer) thisEntry.getKey();
            List<Fields.Field> object = (List<Fields.Field>) thisEntry.getValue();
            Map<String, Object> map = new HashMap<>();

            List<Map<String, Object>> listAdapta = new ArrayList<>();

            for(Fields.Field fieldData :  object){
                if(fieldData.getDataTyp() == 1) {
                    Map<String, Object> mapTest = new HashMap<>();
                    mapTest.put(HOUR, fieldData.getHour());
                    mapTest.put(FACH, fieldData.getData());
                    listAdapta.add(mapTest);
                }

            }

            SimpleAdapter test = new SimpleAdapter(this, listAdapta, R.layout.listview_hours, new String[]{HOUR, FACH}, new int[]{R.id.stunde, R.id.fach});


             map.put(STUNDEN, test);

            map.put(TAG, FieldStatics.getDayName(key));
            list.add(map);
        }

        ExtendedAdapter adapter = new ExtendedAdapter(this, list, R.layout.listview_timetable, new String[]{STUNDEN, TAG}, new int[]{R.id.stunden, R.id.tag});

        stunden.setAdapter(adapter);
    }
}
