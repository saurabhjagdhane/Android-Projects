package com.example.saurabh.accelerometer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String  TABLE_NAME = "AppData";

    //if no user input then default values for table name in database
    String patientNameD = "name";
    String patientIDD = "ID";
    String patientAgeD = "22";
    String radioValue = "male";

    EditText patientName;
    EditText patientID;
    EditText patientAge;
    RadioGroup rg1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextScreen = (Button) findViewById(R.id.next_button);

        patientName = (EditText) findViewById(R.id.Name);
        patientID = (EditText) findViewById(R.id.ID);
        patientAge = (EditText) findViewById(R.id.Age);
        rg1 = (RadioGroup)findViewById(R.id.radioGrp);

        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //data to create the table name
                    patientNameD = patientName.getText().toString();
                    patientIDD = patientID.getText().toString();
                    patientAgeD = patientAge.getText().toString();
                    radioValue = ((RadioButton)findViewById(rg1.getCheckedRadioButtonId())).getText().toString();
                }catch(Exception e){
                    //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();//report problem
                }


                TABLE_NAME = patientNameD + "_" + patientIDD + "_" + patientAgeD + "_" + radioValue;
                if (TABLE_NAME.equals("___male") ){
                    TABLE_NAME = "DefaultDatabase";
                }
                    //Toast.makeText(MainActivity.this, TABLE_NAME, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    //pass the table name from current activity to next activity
                    intent.putExtra("table_name", TABLE_NAME);
                    //Start the new activity

                    startActivity(intent);

            }
        });
    }
}

