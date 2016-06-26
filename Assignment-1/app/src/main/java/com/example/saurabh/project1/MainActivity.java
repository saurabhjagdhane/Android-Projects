package com.example.saurabh.project1;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  {

    private float[] values = new float[]{150, 100, 200, 50, 170, 60, 180};
    private String[] vertical_labels = new String[]{"200", "180", "160", "140", "120", "100", "80", "60", "40", "20", "0"};
    private String[] horizontal_labels = new String[]{"0", "10", "20", "30", "40", "50", "60"};
    private GraphView graphView;
    private LinearLayout graph;
    private boolean runnable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graph = (LinearLayout) findViewById(R.id.graph);
        graphView.flag=false;
        graphView = new GraphView(MainActivity.this, values, "RealTimeTEST", horizontal_labels, vertical_labels, GraphView.LINE);
        graph.addView(graphView);

        Button b1 = (Button)findViewById(R.id.run);
        Button b2 = (Button)findViewById(R.id.stop);


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "RUN", Toast.LENGTH_SHORT).show();
                        graph.removeView(graphView);
                        graphView.flag = true;
                        graph.addView(graphView);
                        if (runnable==false) {
                            runnable = true;
                            startDraw.start();
                        }
                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "STOP", Toast.LENGTH_SHORT).show();
                        graph.removeView(graphView);
                        graphView.flag = false;
                        graph.addView(graphView);

                }
            });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        runnable = false;
    }

    public void setGraph(int data){
        for(int i=0; i<values.length-1; i++){
            values[i] = values[i+1];
        }
        values[values.length-1] = (float)data;
        graph.removeView(graphView);
        graph.addView(graphView);
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg){
            switch(msg.what){

                case 1:
                    int testValue = (int)(Math.random() * 400)+1;
                    setGraph(testValue);
                    break;
            }
        }
    };

    public Thread startDraw = new Thread(){
        @Override
        public void run(){
            while(runnable){
                handler.sendEmptyMessage(1);
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
