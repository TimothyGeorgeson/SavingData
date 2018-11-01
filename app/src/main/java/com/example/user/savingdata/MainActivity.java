package com.example.user.savingdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.savingdata.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    private EditText etSharedPref;
    private TextView tvSharedPref;
    private EditText etPersonName;
    private EditText etPersonAge;
    private EditText etPersonGender;
    private PersonDatabase personDatabase;
    private ListView lvPerson;
    private ArrayAdapter<String> personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        personDatabase = new PersonDatabase(getApplicationContext());

        personAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        lvPerson.setAdapter(personAdapter);
    }

    private void bindViews() {
        //shared preferences
        etSharedPref = findViewById(R.id.etSharedPref);
        tvSharedPref = findViewById(R.id.tvSharedPref);
        //database
        etPersonName = findViewById(R.id.etPersonName);
        etPersonAge = findViewById(R.id.etPersonAge);
        etPersonGender = findViewById(R.id.etPersonGender);
        lvPerson = findViewById(R.id.lvPerson);
    }

    public void onSharedPreferences(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (view.getId())
        {
            case R.id.btnSaveData:
                editor.putString("edittext", etSharedPref.getText().toString());
                //commit will make the change right away, apply will make changes in a different thread
                editor.apply();
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnGetData:
                String etValue = sharedPreferences.getString("edittext", "Default String");
                tvSharedPref.setText(etValue);
                Toast.makeText(this, "Data retrieved", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onSQLiteDatabase(View view) {

        String personName = etPersonName.getText().toString();
        String personAge = etPersonAge.getText().toString();
        String personGender = etPersonGender.getText().toString();

        Person person = new Person(personName, personAge, personGender);

        switch (view.getId()) {
            case R.id.btnSavePerson:
                long rowId = personDatabase.savePerson(person);
                Toast.makeText(this, String.valueOf(rowId), Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnGetAllPerson:

                for (Person person1 : personDatabase.getPeople()) {
                    personAdapter.add(person1.toString());
                    Log.d(TAG, "onSQLiteDatabase: "+ person1.toString());
                }
                break;
        }
    }
}
