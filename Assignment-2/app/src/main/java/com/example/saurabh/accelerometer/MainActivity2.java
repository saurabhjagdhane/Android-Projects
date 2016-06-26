package com.example.saurabh.accelerometer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    SQLiteDatabase db;
    MyReceiver myReceiver;
    public static final String  DATABASE_NAME = "Saurabh_Omkar_Vishal";
    public static String  TABLE_NAME = "AppData";
    float[] x  = new float[10]; //array to store x-axis data of accelerometer
    float[] y  = new float[10]; //array to store y-axis data of accelerometer
    float[] z  = new float[10]; //array to store z-axis data of accelerometer
    private static int var=2;

    private String[] vertical_labels = new String[]{"+10", "+8", "+6", "+4", "+2", "0", "-2", "-4", "-6", "-8", "-10"};
    private String[] horizontal_labels = new String[]{"0", "2", "4", "6", "8", "10"};
    private GraphView graphView;
    private LinearLayout graph;


    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    // File url to download
    //private static String file_url = "http://api.androidhive.info/progressdialog/hive.jpg";
    //private static String file_url = "https://impact.asu.edu/CSE535Spring16Folder/downloadedfile.jpg";
    private static String file_url = "https://impact.asu.edu/Appenstance/Saurabh_Omkar_Vishal";


    final String uploadFilePath = "/storage/emulated/0/";
    final String uploadFileName = "Saurabh_Omkar_Vishal";
    //final String upLoadServerUri = "https://impact.asu.edu/CSE535Spring16Folder/UploadToServer.php";
    //http://androidexample.com/media/
    final String upLoadServerUri = "https://impact.asu.edu/Appenstance/UploadToServerGPS.php";

    //Intent intent = new Intent(this,MyService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        graph = (LinearLayout) findViewById(R.id.graph);
        graphView.flag=false;
        graphView = new GraphView(MainActivity2.this, x, "Accelerometer Data", horizontal_labels, vertical_labels, GraphView.LINE);
        graphView.setValues(x,y,z);
        graph.addView(graphView);

        Button plotData = (Button)findViewById(R.id.start_button);
        Button removeData = (Button)findViewById(R.id.stop_button);
        Button uploadData = (Button) findViewById(R.id.upload_button);
        Button downloadData = (Button)findViewById(R.id.download_button);

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);
        startService(new Intent(MainActivity2.this, MyService.class));
        var=0;

        try {
            //catch the table name sent by first activity and store it in string TABLE_NAME
            TABLE_NAME = getIntent().getStringExtra("table_name");
            //Toast.makeText(MainActivity2.this, TABLE_NAME, Toast.LENGTH_SHORT).show();
        }catch(Exception e) {
            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        plotData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                graph.removeView(graphView);
                getPositionDetail();
                for (int j=0;j<10;j++) {
                    System.out.print("  "+ x[j]);
                    System.out.print("  "+ y[j]);
                    System.out.println("  "+ z[j]);
                }
                Toast.makeText(MainActivity2.this, "Graph Updated", Toast.LENGTH_SHORT).show();
                graphView.flag = true;
                graphView.setValues(x,y,z);
                graph.addView(graphView);
            }
        });

        removeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "Graph Cleared", Toast.LENGTH_SHORT).show();
                graph.removeView(graphView);
                graphView.flag = false;
                graph.addView(graphView);
            }
        });

        uploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Implement Upload database here
                new UploadFiletoURL(MainActivity2.this).execute(uploadFilePath, uploadFileName, upLoadServerUri);
                //Toast.makeText(getApplicationContext(), "File Upload Complete.",Toast.LENGTH_SHORT).show();
            }
        });

        downloadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Implement Download database here

                new DownloadFileFromURL(MainActivity2.this).execute(file_url);
                if(var==0) {
                    unregisterReceiver(myReceiver);
                    //stopService(new Intent(MainActivity.this, MyService.class));
                    var = 1;
                }

                //Toast.makeText(getApplicationContext(), "File Download Complete.",Toast.LENGTH_SHORT).show();

            }
        });

    }

    protected void onStart() {
        try{
            //create the database in external storage of smart phone
            db = SQLiteDatabase.openOrCreateDatabase( Environment.getExternalStorageDirectory()+File.separator+ DATABASE_NAME, null);
            db.beginTransaction();

            try {
                //create a table inside database with columns timestamp, x, y and z values
                db.execSQL("create table "+ TABLE_NAME +" ("
                        + " timeStamp Text, "
                        + " x_pos float, "
                        + " y_pos float, "
                        + " z_pos float "
                        +
                        " ); ");

                db.setTransactionSuccessful(); //commit your changes
                //db.endTransaction();
            }
            catch (SQLiteException e) {
                Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();//report problem
            }
            finally {
                db.endTransaction();
            }
        }catch (SQLException e){

            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        super.onStart();
    }


    @Override
    protected void onStop() {
        if(var==0) {
            unregisterReceiver(myReceiver);
            //stopService(new Intent(MainActivity.this, MyService.class));
            var = 1;
        }
        super.onStop();
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



    //method to obtain the recent 10 readings of accelerometer from table inside the database
    public int getPositionDetail() {

        Cursor cursor = null;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME+ " ORDER BY timeStamp DESC";;
        try {
            cursor = db.rawQuery(selectQuery, null);
            db.setTransactionSuccessful(); //commit your changes
        } catch (Exception e) {
            System.out.print("Error is " + e);
            //report problem
        }

        int i = 0;

        try {
            //put the data from database into x, y and z arrays
            if (cursor.moveToFirst()) {
                do {
                    // get the data into array, or class variable
                    x[i] = cursor.getFloat(cursor.getColumnIndex("x_pos"));
                    y[i] = cursor.getFloat(cursor.getColumnIndex("y_pos"));
                    z[i] = cursor.getFloat(cursor.getColumnIndex("z_pos"));
                    i++;
                    cursor.moveToNext();
                    //count++;
                } while (i < 10);
            }
            cursor.close();
        }catch(Exception e){
            x  = new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            y  = new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            z  = new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            Toast.makeText(MainActivity2.this, "Please wait 10 secs for database to Update!!" , Toast.LENGTH_LONG).show();

        }

        return 0;
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {

            float datapassed1 = arg1.getFloatExtra("DATAPASSED1", 0);
            float datapassed2 = arg1.getFloatExtra("DATAPASSED2", 0);
            float datapassed3 = arg1.getFloatExtra("DATAPASSED3", 0);
            Log.d("msg", "hi");
            String times = getCurrentTimeStamp();

            try {
                //insert the data into database
                db.execSQL("insert into "+TABLE_NAME+"(timeStamp, x_pos, y_pos, z_pos) values ('" + times + "', '" + datapassed1 + "', '" + datapassed2 + "', '" + datapassed3 + "' );");
            } catch (SQLiteException e) {
                //report problem
            } finally {
            }
        }
    }

   @Override
   public void onBackPressed() {
        if (var == 0) {
           unregisterReceiver(myReceiver);
           //stopService(new Intent(MainActivity.this, MyService.class));
           var = 1;
            super.onBackPressed();
        }
       super.onBackPressed();
   }


}

