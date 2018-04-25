package branislav.gamf.chatapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final DatePicker calendar = (DatePicker) findViewById(R.id.regCalendar);
        final Button registerButton = (Button) findViewById(R.id.regButton);
        final EditText usernameTextEdit = (EditText) findViewById(R.id.regUsername);
        final EditText passwordTextEdit = (EditText) findViewById(R.id.regPassword);
        final EditText firstNameTextEdit = (EditText) findViewById(R.id.regFirstName);
        final EditText lastNameTextEdit = (EditText) findViewById(R.id.regLastName);
        final EditText emailTextEdit = (EditText) findViewById(R.id.regEmail);
        final boolean[] usernameValid = {false};
        final boolean[] passwordValid = {false};
        final boolean[] emailValid = {false};
        final DatabaseHelper db = new DatabaseHelper(this);

        calendar.setMaxDate(Calendar.getInstance().getTime().getTime());



        usernameTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = usernameTextEdit.getText().toString();
                if(username.length() != 0){
                    usernameValid[0] = true;
                    if(passwordValid[0] && emailValid[0])
                        registerButton.setEnabled(true);
                }
                else{
                    usernameValid[0] = false;
                    registerButton.setEnabled(false);
                }
            }
        });

        passwordTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = passwordTextEdit.getText().toString();
                if(password.length() >= 6){
                    passwordValid[0] = true;
                    if(usernameValid[0] && emailValid[0])
                        registerButton.setEnabled(true);
                }
                else{
                    passwordValid[0] = false;
                    registerButton.setEnabled(false);
                }
            }
        });

        emailTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = emailTextEdit.getText().toString();
                if(email.length() != 0 && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailValid[0] = true;
                    if(usernameValid[0] && passwordValid[0])
                        registerButton.setEnabled(true);
                }
                else{
                    emailValid[0] = false;
                    registerButton.setEnabled(false);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Contact contact = new Contact(null,usernameTextEdit.getText().toString(),
                        firstNameTextEdit.getText().toString(), lastNameTextEdit.getText().toString());
                db.insertContact(contact);
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
    }
}
