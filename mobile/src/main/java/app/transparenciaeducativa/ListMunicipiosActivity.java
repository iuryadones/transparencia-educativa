package app.transparenciaeducativa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListMunicipiosActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseReference base;

    // Spinner
    private ArrayAdapter<String> dataAdapter;
    private List<String> list_periodos = new ArrayList<>();

    // ListView
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_base = new ArrayList<>();

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
        setContentView(R.layout.activity_list_municipios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        raiz = getIntent().getExtras().getString("raiz", "");
        regiao = getIntent().getExtras().getString("regiao", "");
        estado = getIntent().getExtras().getString("estado", "");
        ano = getIntent().getExtras().getString("ano", "");
        transacao = getIntent().getExtras().getString("transacao", "");
        municipio = getIntent().getExtras().getString("municipio", "");

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                list_periodos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        base = root.child(raiz).child(regiao).child(estado);
        base.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                LinkedList<String> linkedList = new LinkedList<>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {

                    linkedList.add(((DataSnapshot) i.next()).getKey());

                }

                list_periodos.clear();
                list_periodos.addAll(linkedList);
                dataAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView = (ListView) findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list_base);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                municipio = ((TextView)view).getText().toString();

                Intent intent = new Intent(getApplicationContext(), GraficosActivity.class);
                intent.putExtra("raiz", raiz);
                intent.putExtra("regiao", regiao);
                intent.putExtra("estado",estado);
                intent.putExtra("ano", ano);
                intent.putExtra("transacao", transacao);
                intent.putExtra("municipio", municipio);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Object itemAtPosition = adapterView.getItemAtPosition(position);
        Context context = adapterView.getContext();

        ano = itemAtPosition.toString();

        Toast.makeText(context, "Selecionado: " + ano, Toast.LENGTH_LONG).show();

        try {
            setTitle(estado + " - " + ano);
        } catch (RuntimeException e) {
            setTitle(estado + " - " + getTitle().toString());
        }

        base = root.child(raiz).child(regiao).child(estado).child(ano).child(transacao);
        base.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                LinkedList<String> linkedList = new LinkedList<>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {

                    linkedList.add(((DataSnapshot) i.next()).getKey());

                }

                list_base.clear();
                list_base.addAll(linkedList);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
