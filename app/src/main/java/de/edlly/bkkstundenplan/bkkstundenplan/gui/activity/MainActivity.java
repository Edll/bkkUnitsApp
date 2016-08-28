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
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.edlly.bkkstundenplan.bkkstundenplan.R;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadClasses;
import de.edlly.bkkstundenplan.bkkstundenplan.model.asyncTasks.LoadWeeks;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Classes;
import de.edlly.bkkstundenplan.bkkstundenplan.model.data.Weeks;
import de.edlly.bkkstundenplan.bkkstundenplan.model.utils.ExtendedAdapter;

public class MainActivity extends Activity implements LoadClasses.IloadClasses<Classes>, LoadWeeks.IloadWeeks,  Spinner.OnItemSelectedListener{
    private Spinner weeksSelecter;
    private Spinner classSelecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        weeksSelecter = (Spinner) findViewById(R.id.weeksSelecter);
        weeksSelecter.setOnItemSelectedListener(this);
        classSelecter = (Spinner) findViewById(R.id.classSelecter);
        ListView stunden = (ListView) findViewById(R.id.timeTabel);


        LoadWeeks.LoadWeeksParam param;
        new LoadWeeks(this, this).execute();

        LoadClasses.LoadClassesParam param1;

        new LoadClasses(this, this).execute();

        TableRow row = new TableRow(this);

        TextView textView = new TextView(this);
        textView.setText("Bla");

        row.addView(textView);

        Map<String, Object> map = new HashMap<>();
        map.put("stunde", row);

        List<Map<String, Object>> list = new ArrayList<>();

        list.add(map);
        ExtendedAdapter adapter = new ExtendedAdapter(this, list, R.layout.listview_timetable, new String[]{"stunde"}, new int[]{R.id.stunden});

        stunden.setAdapter(adapter);

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
        Log.w("test", view.toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.w("test", adapterView.toString());
    }

    @Override
    public void onLoaderWeeksCompleted(Weeks weeks) {
        Log.w("test", "onLoaderWeeksCompleted: weeks" + weeks);

        List<String> list = new ArrayList<>();
        for (Weeks.Week week : weeks.getWeeks()) {
            list.add(week.getDate());
            Log.w("test", "onLoaderWeeksCompleted: " + week.getDate() );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        weeksSelecter.setAdapter(adapter);
    }
}
