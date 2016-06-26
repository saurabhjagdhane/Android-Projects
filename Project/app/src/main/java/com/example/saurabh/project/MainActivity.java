package com.example.saurabh.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText MyWeight, MyHeight, MyAge, MyName, ExpectedCalorie;
    TextView MyBmi;
    Button BMI, next;
    RadioGroup rg1;

    public static String  TABLE_NAME = "AppData";
    private String radioValue = "male";
    public static String suggest;
    private static final int running = 16;
    private static final int cycling = 12;
    private static final int walking = 4;
    float BMR, RunTime = 1, WalkTime = 1, CycleTime = 1, PAL, CaloriesBurnt, ExpectedCalorieBurn;
    float weight = 0, height = 0, desiredcalories = 0, bmiValue = 0;
    int age;
    Boolean sex = true;
    String a,w,h,cal,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MyWeight = (EditText)findViewById(R.id.MyWeight);
        MyHeight = (EditText)findViewById(R.id.MyHeight);
        MyAge = (EditText)findViewById(R.id.MyAge);
        MyBmi = (TextView)findViewById(R.id.MyBmi);
        MyName = (EditText)findViewById(R.id.MyName);
        BMI = (Button)findViewById(R.id.BmiButton);
        next = (Button)findViewById(R.id.NextButton);
        ExpectedCalorie = (EditText)findViewById(R.id.ExpectedCalorie);
        rg1 = (RadioGroup)findViewById(R.id.radioGrp);

        BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    username = MyName.getText().toString();
                    w = MyWeight.getText().toString();
                    h = MyHeight.getText().toString();
                    a = MyAge.getText().toString();
                    cal = ExpectedCalorie.getText().toString();
                    radioValue = ((RadioButton) findViewById(rg1.getCheckedRadioButtonId())).getText().toString();

                    weight = Float.parseFloat(w);
                    height = Float.parseFloat(h);
                    age = Integer.parseInt(a);
                    desiredcalories = Float.parseFloat(cal);

                }catch(Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();//report problem
                    age=0;
                }



                bmiValue = calculateBMI(weight, height);
                String result = interpretBMI(bmiValue);

                //MyBmi.setText(String.valueOf(bmiValue+" "+result));

                if(radioValue == "male"){
                    BMR = (float) (66 + (13.7*weight)+(5*height)-(6.8*age)); //Men: BMR = 66 + ( 13.7 x weight in kilos ) + ( 5 x height in cm ) - ( 6.8 x age in years )
                }else{
                    BMR = (float) (65.5 + (9.6*weight)+(1.8*height)-(4.7*age));//Women: BMR = 65.5 + ( 9.6 x weight in kilos ) + ( 1.8 x height in cm ) - ( 4.7 x age in years )
                }

                PAL = ((running*RunTime)+(walking*WalkTime)+(cycling*CycleTime))/(RunTime+WalkTime+CycleTime);
                CaloriesBurnt = BMR * PAL;
                MyBmi.setText(String.valueOf(bmiValue+" "+result));
                suggest = Planner(desiredcalories, BMR);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    username = MyName.getText().toString();
                    w = MyWeight.getText().toString();
                    h = MyHeight.getText().toString();
                    a = MyAge.getText().toString();
                    cal = ExpectedCalorie.getText().toString();
                    radioValue = ((RadioButton) findViewById(rg1.getCheckedRadioButtonId())).getText().toString();

                    weight = Float.parseFloat(w);
                    height = Float.parseFloat(h);
                    age = Integer.parseInt(a);
                    desiredcalories = Float.parseFloat(cal);

                    TABLE_NAME = username + "_" + age + "_" + radioValue;
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();//report problem
                    age=0;
                    TABLE_NAME = "username_0_male";
                }

                bmiValue = calculateBMI(weight, height);
                String result = interpretBMI(bmiValue);

                //MyBmi.setText(String.valueOf(bmiValue+" "+result));

                if(radioValue == "male"){
                    BMR = (float) (66 + (13.7*weight)+(5*height)-(6.8*age)); //Men: BMR = 66 + ( 13.7 x weight in kilos ) + ( 5 x height in cm ) - ( 6.8 x age in years )
                }else{
                    BMR = (float) (65.5 + (9.6*weight)+(1.8*height)-(4.7*age));//Women: BMR = 65.5 + ( 9.6 x weight in kilos ) + ( 1.8 x height in cm ) - ( 4.7 x age in years )
                }

                PAL = ((running*RunTime)+(walking*WalkTime)+(cycling*CycleTime))/(RunTime+WalkTime+CycleTime);
                CaloriesBurnt = BMR * PAL;
                MyBmi.setText(String.valueOf(bmiValue+" "+result));
                suggest = Planner(desiredcalories, BMR);

                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                //pass the table name from current activity to next activity
                String t = ""+desiredcalories;
                intent.putExtra("des",t);
                intent.putExtra("table_name",TABLE_NAME);
                String t1 = ""+BMR;
                intent.putExtra("BMR",t1);
                System.out.println("Desired calorie burn = " + desiredcalories);//omi
                //Start the new activity

                intent.putExtra("suggestion",suggest);
                startActivity(intent);;
            }
        });

        Button nextPage = (Button)findViewById(R.id.b1);

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, secondpage.class);
                startActivity(i);
                finish();
            }
        });
    }

    //Calculate BMI
    private float calculateBMI (float weight, float height) {
        return (float) (weight / (height * height));
    }

    // Interpret what BMI means
    private String interpretBMI(float bmiValue) {

        if (bmiValue < 16) {
            return "Severely underweight";
        } else if (bmiValue < 18.5) {

            return "Underweight";
        } else if (bmiValue < 25) {

            return "Normal";
        } else if (bmiValue < 30) {

            return "Overweight";
        } else {
            return "Obese";
        }
    }

    private String Planner(float desiredCalories, float myBMR){
        float RunningTime = desiredCalories/(myBMR*running);
        float WalkingTime = desiredCalories/(myBMR*walking);
        float CyclingTime = desiredCalories/(myBMR*cycling);
        String suggestion = "Today's suggestion : \nRun for: "+ (Math.round(RunningTime*60))+"min\n"+ "Walk for: "+ (Math.round(WalkingTime*60)) + "min\n" +"Cycle for: "+ (Math.round(CyclingTime*60))+"min";
        return suggestion;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            try {
                Intent settings_intent = new Intent(this, MySettings.class);
                startActivity(settings_intent);
                finish();
            }
            catch (Exception e){
                Toast.makeText(MainActivity.this, "Error is: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
