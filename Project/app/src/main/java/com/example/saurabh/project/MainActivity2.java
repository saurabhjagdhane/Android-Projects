package com.example.saurabh.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2  extends AppCompatActivity {
    SQLiteDatabase db;
    public static final String  DATABASE_NAME = "FitnessApp";
    TextView TableName;
    TextView suggesting;
    public static float BMR,desiredcalories = 0;
    Button PushupsButton, RunButton, NutriButton, CycleButton, DoneButton, shareTextButton, GetButton, btnNextScreen;
    public static String  TABLE_NAME = "AppData";
    public static String suggest;
    private static final int running = 16;
    private static final int cycling = 12;
    private static final int walking = 4;
    public static int set = 10, pushups_count = 0;
    public static float CaloriesBurntcycle,CaloriesBurntrun  =0;
    public static float distance = 0, speed =0, calories =0;
    float RunTime = 0, WalkTime = 0, CycleTime = 0, PAL = 0;
    private String spunits="m/s\n";
    private String disunits="m\n";

    int x = 0;
    float y, w, z = 0;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TableName = (TextView)findViewById(R.id.TableName);
        PushupsButton = (Button)findViewById(R.id.PushupsButton);
        RunButton = (Button)findViewById(R.id.RunButton);
        NutriButton = (Button)findViewById(R.id.NutriButton);
        CycleButton = (Button)findViewById(R.id.CycleButton);
        DoneButton = (Button)findViewById(R.id.DoneButton);
        shareTextButton = (Button)findViewById(R.id.shareTextButton);
        GetButton = (Button)findViewById(R.id.GetButton);
        suggesting = (TextView)findViewById(R.id.suggesting);
        btnNextScreen = (Button) findViewById(R.id.button);


    }

    protected void onStart() {
        try {
            if(set == 10){
                try {
                    String u = getIntent().getStringExtra("des");
                    System.out.println("Desired calorie burn 1 = " + u);// new omkar
                    desiredcalories = Float.parseFloat(u);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
                System.out.println("Desired calorie burn = " + desiredcalories);// new omkar

                suggest = getIntent().getStringExtra("suggestion");//omi
                System.out.println("suggestion = " + suggest);//omi
                suggesting.setText(suggest);//omi
            }
            try {
                //catch the table name sent by first activity and store it in string TABLE_NAME
                TABLE_NAME = getIntent().getStringExtra("table_name");
                TableName.setText(TABLE_NAME);
                String s = getIntent().getStringExtra("BMR");
                BMR = Float.parseFloat(s);
                System.out.println("bmr = "+BMR);
                String r = getIntent().getStringExtra("des");
                desiredcalories = Float.parseFloat(r);
                System.out.println("Desired calorie burn = " + desiredcalories);// new omkar
                //getIntent().getFloatExtra("BMR", BMR);//new omkar
                //getIntent().getFloatExtra("desiredcalories", desiredcalories);//new omkar
                //String suggest=getIntent().getStringExtra("suggestion");// new omkar
                //suggesting.setText(suggest);// new omkar
                //Toast.makeText(MainActivity2.this, TABLE_NAME, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (set == 1 || set == 2 || set == 3) {
                try {
                    if (set == 1) {
                        //catch the table name sent by first activity and store it in string TABLE_NAME
                        String count = getIntent().getStringExtra("Pushups_done");
                        pushups_count = Integer.parseInt(count);
                        //Toast.makeText(MainActivity2.this, TABLE_NAME, Toast.LENGTH_SHORT).show();
                        System.out.println("#pushups = " + pushups_count);
                    } else if(set == 2){
                        String travel = getIntent().getStringExtra("Distance_travelled");
                        String runt = getIntent().getStringExtra("runningtime");
                        String walkt = getIntent().getStringExtra("walkingtime");
                        String speedt = getIntent().getStringExtra("speed");
                        System.out.println("runing stats :\n "+travel +"\n"+runt+"\n"+walkt+"\n"+speedt);
                        distance += Float.parseFloat(travel);
                        RunTime = Float.parseFloat(runt);
                        RunTime /= 1000;
                        WalkTime = Float.parseFloat(walkt);
                        WalkTime /= 1000;
                        speed = Float.parseFloat(speedt);
                        System.out.println("distance covered = " + distance+ " "+ RunTime +" "+ WalkTime + " "+ speed);
                        PAL = ((running*RunTime)+(walking*WalkTime))/(RunTime+WalkTime);
                        CaloriesBurntrun = BMR * PAL / 5000;
                        System.out.println("run cal = "+ CaloriesBurntrun);
                    }else if(set == 3){
                        String travel = getIntent().getStringExtra("Distance_travelled");
                        String speedt = getIntent().getStringExtra("speed");
                        String cyclet = getIntent().getStringExtra("cyclingtime");
                        System.out.println("cycling stats :\n "+travel +"\n"+cyclet);
                        distance += Float.parseFloat(travel);
                        speed = Float.parseFloat(speedt);
                        CycleTime = Float.parseFloat(cyclet);
                        CycleTime /= 1000;
                        System.out.println("distance covered = " + distance+ " "+ CycleTime );
                        PAL = (cycling*CycleTime);
                        CaloriesBurntcycle = BMR * PAL / 5000;
                        System.out.println("cycle cal = "+ CaloriesBurntcycle);
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                set = 0;
            }



            //create the database in external storage of smart phone
            db = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME, null);
            db.beginTransaction();

            try {
                //db.beginTransaction();
                //create a table inside database with columns timestamp, x, y and z values
                db.execSQL("create table if not exists " + TABLE_NAME + " ("
                        + " timeStamp Text, "
                        + " distance float, "
                        + " speed float, "
                        + " calories float, "
                        + " targetcalories float, "
                        + " pushups Integer "
                        +
                        " ); ");

                db.setTransactionSuccessful(); //commit your changes
                System.out.println("created table " + TABLE_NAME);
                //db.endTransaction();
            } catch (SQLiteException e) {
                Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_LONG).show();//report problem
                System.out.println(e.getMessage().toString());
            } finally {
                db.endTransaction();
                System.out.println("111");
            }
        } catch (SQLException e) {

            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        PushupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set = 1;
                Intent intent = new Intent(getApplicationContext(), Pushups.class);
                //pass the table name from current activity to next activity
                System.out.println("pushup table = "+ TABLE_NAME);
                intent.putExtra("table_name", TABLE_NAME);
                String t = ""+desiredcalories;
                intent.putExtra("des",t);
                String t1 = ""+BMR;
                intent.putExtra("BMR",t1);
                //intent.putExtra("BMR", BMR);// new omkar
                //intent.putExtra("desiredcalories", desiredcalories);// new omkar
                //Start the new activity
                startActivity(intent);
                finish();
            }
        });

        RunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set = 2;
                Intent intent = new Intent(getApplicationContext(), MyRun.class);
                //pass the table name from current activity to next activity
                System.out.println("run table = "+ TABLE_NAME);
                intent.putExtra("table_name", TABLE_NAME);
                String t = ""+desiredcalories;
                intent.putExtra("des",t);
                String t1 = ""+BMR;
                intent.putExtra("BMR",t1);
                intent.putExtra("cycle","0");
                //intent.putExtra("BMR", BMR);// new omkar
                //intent.putExtra("desiredcalories", desiredcalories);// new omkar
                //Start the new activity
                startActivity(intent);
                finish();
            }
        });

        CycleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set = 3;
                Intent intent = new Intent(getApplicationContext(), MyRun.class);
                //pass the table name from current activity to next activity
                System.out.println("cycle table = "+ TABLE_NAME);
                intent.putExtra("table_name", TABLE_NAME);
                String t = ""+desiredcalories;
                intent.putExtra("des",t);
                String t1 = ""+BMR;
                intent.putExtra("BMR",t1);
                intent.putExtra("cycle","1");
                //intent.putExtra("BMR", BMR);// new omkar
                //intent.putExtra("desiredcalories", desiredcalories);// new omkar
                //Start the new activity
                startActivity(intent);
                finish();
            }
        });

        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoDB();
                distance = 0;
                pushups_count = 0;
                CaloriesBurntcycle=0;
                CaloriesBurntrun  =0;
                speed =0;
                RunTime = 0;
                WalkTime = 0;
                CycleTime = 0;
                PAL = 0;
            }
        });

        GetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDataFromDB();
                if(((GlobalState) MainActivity2.this.getApplication()).getMusicState()){
                    w=w/1000f;
                    disunits="km\n";
                    spunits="km/hr\n";
                    z=z*5/18;
                }
                else{
                    w=w/1600f;
                    disunits="miles\n";
                    spunits="miles/hr\n";
                    z=z*5/(18*1.6f);

                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity2.this);

                // set title
                alertDialogBuilder.setTitle("Scoreboard");

                // set dialog message

                alertDialogBuilder
                        .setMessage("Max distance covered : "+ String.valueOf(w) + disunits+"Max speed : "+ String.valueOf(z) + spunits+"Max calories : "+ String.valueOf(y) + "\n"+"Max pushups : "+ String.valueOf(x) + "\n")
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        NutriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, NutrientChart.class);
                intent.putExtra("table_name", TABLE_NAME);//new omkar
                String t = ""+desiredcalories;
                intent.putExtra("des",t);
                String t1 = ""+BMR;
                intent.putExtra("BMR",t1);
                //intent.putExtra("BMR", BMR);// new omkar
                //intent.putExtra("desiredcalories", desiredcalories);// new omkar
                startActivity(intent);
                finish();
            }
        });

        if(((GlobalState) MainActivity2.this.getApplication()).getSocialState()) {
            shareTextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(getApplicationContext(), ShareTextActivity.class);
                    startActivity(intent1);
                }
            });
        }

        //Listening to button event
        btnNextScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), BarGraph.class);
                nextScreen.putExtra("table_name", TABLE_NAME);
                String t = ""+desiredcalories;
                nextScreen.putExtra("des",t);
                String t1 = ""+BMR;
                nextScreen.putExtra("BMR",t1);
                //nextScreen.putExtra("BMR", BMR);// new omkar
                //nextScreen.putExtra("desiredcalories", desiredcalories);// new omkar
                startActivity(nextScreen);

            }

            //chart.setOnChartGestureListener(this);

        });

        super.onStart();

    }

    //method to obtain the current date and time information
    public static String getCurrentTimeStamp(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void insertIntoDB(){
        try{
            db = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME, null);
            db.beginTransaction();
            String time = getCurrentTimeStamp();
            //int numOfpushups = Integer.parseInt(pushups_count);
            System.out.println("# pushups converted to int : "+pushups_count);
            String query = "INSERT INTO "+ TABLE_NAME +" (timeStamp,distance,speed,calories,targetcalories,pushups) VALUES('"+time+"', '"+distance+"', '"+speed+"', '"+(CaloriesBurntcycle+CaloriesBurntrun)+"', '"+desiredcalories+"', '"+pushups_count+"');";
            db.execSQL(query);
            Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();
            db.setTransactionSuccessful(); //commit your changes
        }catch(Exception e){
            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_LONG).show();//report problem
        }finally {
            db.endTransaction();
        }
    }

    protected void getDataFromDB(){
        try{
            String result_0 = "";
            String result_1 = "";

            db = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME, null);
            db.beginTransaction();
            Cursor cursor = null, cursor1 = null, cursor2 = null, cursor3 = null;
            String selectpushupsQuery = "SELECT  * FROM " + TABLE_NAME+ " ORDER BY pushups DESC";;
            String selectdistanceQuery = "SELECT  * FROM " + TABLE_NAME+ " ORDER BY distance DESC";;
            String selectcaloriesQuery = "SELECT  * FROM " + TABLE_NAME+ " ORDER BY calories DESC";;
            String selectspeedQuery = "SELECT  * FROM " + TABLE_NAME+ " ORDER BY speed DESC";;
            try {
                cursor = db.rawQuery(selectpushupsQuery, null);
                cursor1 = db.rawQuery(selectdistanceQuery, null);
                cursor2 = db.rawQuery(selectcaloriesQuery, null);
                cursor3 = db.rawQuery(selectspeedQuery, null);
                db.setTransactionSuccessful(); //commit your changes
            } catch (Exception e) {
                System.out.print("Error is " + e);
                //report problem
            }
            if(cursor.moveToFirst()) {
                x = cursor.getInt(cursor.getColumnIndex("pushups"));
                System.out.println("max pushups : " + x);
            }
            if(cursor1.moveToFirst()) {
                w = cursor1.getFloat(cursor1.getColumnIndex("distance"));
                System.out.println("max distance : " + w);
            }
            if(cursor2.moveToFirst()) {
                y = cursor2.getFloat(cursor2.getColumnIndex("calories"));
                System.out.println("max calories : " + y);
            }
            if(cursor3.moveToFirst()) {
                z = cursor3.getFloat(cursor3.getColumnIndex("speed"));
                System.out.println("max speed : " + z);
            }
            //db.setTransactionSuccessful(); //commit your changes
        }catch(Exception e){
            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_LONG).show();//report problem
        }finally {
            db.endTransaction();
        }

        // Toast.makeText(MainActivity2.this, "ghsh", Toast.LENGTH_LONG).show();

    }

    private String Planner(float desiredCalories, float myBMR){
        float RunningTime = desiredCalories/(myBMR*running);
        float WalkingTime = desiredCalories/(myBMR*walking);
        float CyclingTime = desiredCalories/(myBMR*cycling);
        String suggestion = "Run for: "+ RunningTime+"\n"+ "Walk for: "+ WalkingTime + "\n" +"Cycle for: "+CyclingTime;
        return suggestion;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
