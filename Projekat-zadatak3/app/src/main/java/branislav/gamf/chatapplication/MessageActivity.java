package branislav.gamf.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        final Button logoutButton = (Button) findViewById(R.id.logoutButton2);
        final Button sendButton = (Button) findViewById(R.id.sendButton);
        final EditText messageTextEdit = (EditText) findViewById(R.id.messsage);
        final TextView friendFullName = (TextView) findViewById(R.id.friendFullName);

        final SharedPreferences prefs = getSharedPreferences("PrefsFile",MODE_PRIVATE);
        final String receiverUserID = prefs.getString("reciever_userID",null);
        final String senderUserID = prefs.getString("loggedin_userID",null);
        final DatabaseHelper db = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String fullName = bundle.getString("fullNameFromContactList");
        friendFullName.setText(fullName);

        final CustomAdapter2 adapter = new CustomAdapter2(this);

        final ArrayList<Message> messages = new ArrayList<Message>();
        db.readMessages(messages,receiverUserID,senderUserID);

        adapter.update(messages,senderUserID);

        ListView list = (ListView) findViewById(R.id.customListMessages);
        list.setDivider(null);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MessageItem forDelete = (MessageItem) adapter.getItem(position);
                db.deleteMessage(forDelete.getMessageID());
                db.readMessages(messages,receiverUserID,senderUserID);
                adapter.update(messages,senderUserID);
                return true;
            }
        });

        messageTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String message = messageTextEdit.getText().toString();
                if(message.length()!=0)
                    sendButton.setEnabled(true);
                else
                    sendButton.setEnabled(false);
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),(CharSequence)"Message is sent!",(int) Toast.LENGTH_SHORT);
                String message = messageTextEdit.getText().toString();
                Message m = new Message(null,senderUserID,receiverUserID,message);

                db.insertMessage(m);
                db.readMessages(messages,receiverUserID,senderUserID);

                adapter.update(messages,senderUserID);

                toast.show();
                messageTextEdit.setText("");


            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this,MainActivity.class));
            }
        });
    }


}
