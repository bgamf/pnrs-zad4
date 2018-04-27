package branislav.gamf.chatapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        final Button logoutButton = (Button) findViewById(R.id.logoutButton1);
        final Button nextButton = (Button) findViewById(R.id.nextButton);
        final ListView listViewContacts = (ListView) findViewById(R.id.customListView);

        ArrayList<Contact> contacts = new ArrayList<Contact>();
        final DatabaseHelper db = new DatabaseHelper(this);
        db.readContacts(contacts);

        SharedPreferences prefs = getSharedPreferences("PrefsFile",MODE_PRIVATE);
        String userID = prefs.getString("loggedin_userID",null);

        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(ContactsActivity.this,MainActivity.class));
            }

        });




        final CustomAdapter adapter = new CustomAdapter(this);

        for(int i = 0; i < contacts.size(); i++){
            if(!(contacts.get(i).getcUserID().equals(userID))) {
                String fullName = contacts.get(i).getcFirstName() + " " + contacts.get(i).getcLastName();
                adapter.AddContact(new ContactItem(fullName, getResources().getDrawable(R.drawable.ic_send_black_24dp),contacts.get(i).getcUserID()));
            }
        }


        listViewContacts.setAdapter(adapter);



    }
}
