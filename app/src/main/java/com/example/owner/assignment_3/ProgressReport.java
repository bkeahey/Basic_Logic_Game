package com.example.owner.assignment_3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static com.example.owner.assignment_3.Utils.Utils.SHARED_PREF_FILENAME;
import static com.example.owner.assignment_3.Utils.Utils.W1L1FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.W1L2FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.W1L3FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.W2L1FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.W2L2FAILURES;
import static com.example.owner.assignment_3.Utils.Utils.W2L3FAILURES;

public class ProgressReport extends AppCompatActivity {

    private Button back;
    private TextView log;
    SharedPreferences sharedPreferences;

    /*
        Starts sharedPreferences for this page and allows it to be accessed
        It then displays the information in the user log
        This page also displays a graph with the information from shared preferences
        based on the total failures in each level
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report);

        back=(Button)findViewById(R.id.back_btn_progress);
        back.setOnClickListener(new progLstnr());
        log=(TextView)findViewById(R.id.tv_prog);
        sharedPreferences=getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_PRIVATE);
        String name=sharedPreferences.getString("Name","");
        log.append(name+'\n');

        String w1l1=sharedPreferences.getString("One","");
        String w1l2=sharedPreferences.getString("Two","");
        String w1l3=sharedPreferences.getString("Three","");
        String w2l1=sharedPreferences.getString("world2One","");
        String w2l2=sharedPreferences.getString("world2Two","");
        String w2l3=sharedPreferences.getString("world2Three","");

        log.append(w1l1+'\n');
        log.append(w1l2+'\n');
        log.append(w1l3+'\n');
        log.append(w2l1+'\n');
        log.append(w2l2+'\n');
        log.append(w2l3+'\n');

        BarChart barChart = (BarChart) findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(W1L1FAILURES, 0));
        entries.add(new BarEntry(W1L2FAILURES, 1));
        entries.add(new BarEntry(W1L3FAILURES, 2));
        entries.add(new BarEntry(W2L1FAILURES, 3));
        entries.add(new BarEntry(W2L2FAILURES, 4));
        entries.add(new BarEntry(W2L3FAILURES, 5));
        //sets data graph will use

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("1-1");
        labels.add("1-2");
        labels.add("1-3");
        labels.add("2-1");
        labels.add("2-2");
        labels.add("2-3");
        //adds names to the bars on the graph

        BarData data = new BarData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        barChart.setData(data);
        barChart.animateY(5000);
        //animates graph

    }

    private class progLstnr implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ProgressReport.this,WorldSelect.class);
            startActivity(intent);
            //sends user back to world select page when back button is clicked
        }
    }
}
