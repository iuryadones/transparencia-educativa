package app.transparenciaeducativa;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraficosActivity extends AppCompatActivity {

    // Constates do Intent
    private String raiz;
    private String regiao;
    private String estado;
    private String ano;
    private String transacao;
    private String municipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        raiz = getIntent().getExtras().getString("raiz", "");
        regiao = getIntent().getExtras().getString("regiao", "");
        estado = getIntent().getExtras().getString("estado", "");
        ano = getIntent().getExtras().getString("ano", "");
        transacao = getIntent().getExtras().getString("transacao", "");
        municipio = getIntent().getExtras().getString("municipio", "");

        setTitle(municipio + " - " + ano);

        BarChart chart = (BarChart) findViewById(R.id.chart);
        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Gráfico de Barras");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }

}
