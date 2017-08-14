package app.transparenciaeducativa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RoomChatRealTimeActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private EditText input_msg;
    private TextView chat_conversation;

    private String chat;
    private String room_name;
    private String user_name;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseReference base;
    private DatabaseReference message;

    private String temp_key;

    private String chat_msg;
    private String chat_user_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_chat_real_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        input_msg = (EditText) findViewById(R.id.msg_input);
        chat_conversation = (TextView) findViewById(R.id.text_view_chat);

        chat = getIntent().getExtras().getString("chat", "");
        room_name = getIntent().getExtras().getString("room_name", "");
        user_name = getIntent().getExtras().getString("user_name", "");

        setTitle("Sala - "+ room_name);

        base = root.child(chat).child(room_name);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = base.push().getKey();
                base.updateChildren(map);

                message = base.child(temp_key);

                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("nome", user_name);
                map2.put("msg", input_msg.getText().toString());
                message.updateChildren(map2);

                input_msg.setText("");

            }
        });

        base.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        if (dataSnapshot.hasChildren()) {

            Iterator i = dataSnapshot.getChildren().iterator();

            while (i.hasNext()){

                chat_msg = (String) ((DataSnapshot)i.next()).getValue();
                chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

                chat_conversation.append(chat_user_name + ":\n" + chat_msg + "\n\n");

            }
        }
    }
}
