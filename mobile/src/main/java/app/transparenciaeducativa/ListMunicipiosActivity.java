package app.transparenciaeducativa;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class ListMunicipiosActivity extends AppCompatActivity {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseReference base;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_base = new ArrayList<>();

    // Constates do Intent
    private String raiz;
    private String regiao;
    private String estado;
    private String municipio;
    private String ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_municipios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list_base);
        listView.setAdapter(arrayAdapter);

        raiz = getIntent().getExtras().getString("raiz", "");
        regiao = getIntent().getExtras().getString("regiao", "");
        estado = getIntent().getExtras().getString("estado", "");
        municipio = getIntent().getExtras().getString("municipio", "");
        ano = getIntent().getExtras().getString("ano", "");

        base = root.child(raiz).child(regiao).child(estado).child(municipio).child(ano);

        base.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                LinkedList<String> linkedList = new LinkedList<String>();
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

}
