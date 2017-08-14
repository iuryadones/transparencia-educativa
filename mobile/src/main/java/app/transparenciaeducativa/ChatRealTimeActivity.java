package app.transparenciaeducativa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatRealTimeActivity extends AppCompatActivity {

    private EditText nome_sala;
    private ListView listViewSala;
    private FloatingActionButton fab;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_de_salas = new ArrayList<>();

    private String chat;
    private String room_name;
    private String user_name;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseReference base;

    private EditText input_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_real_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Salas de Bate-papo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chat = getIntent().getExtras().getString("chat", "");

        base = root.child(chat);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        nome_sala = (EditText) findViewById(R.id.msg_input);
        listViewSala = (ListView) findViewById(R.id.list_view_sala);

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list_de_salas);
        listViewSala.setAdapter(arrayAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> map = new HashMap<String, Object>();
                map.put(nome_sala.getText().toString(), "");
                base.updateChildren(map);

                nome_sala.setText("");

                Snackbar.make(view, "Criada sala de Bate-papo público.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).setDuration(1500).show();
            }
        });

        request_user_name();

        base.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }
                list_de_salas.clear();
                list_de_salas.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewSala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                room_name = ((TextView) view).getText().toString();

                Intent intent = new Intent(getApplicationContext(), RoomChatRealTimeActivity.class);
                intent.putExtra("chat", chat);
                intent.putExtra("room_name", room_name);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        });
    }

    private void request_user_name(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nome: ");

        input_field = new EditText(this);

        builder.setView(input_field);
        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user_name = input_field.getText().toString();
            }

        });

        builder.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        builder.show();
    }
}
