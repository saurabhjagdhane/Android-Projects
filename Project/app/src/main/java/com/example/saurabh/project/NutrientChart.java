package com.example.saurabh.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NutrientChart extends AppCompatActivity {
    public static String  TABLE_NAME = "AppData";//new omkar
    public static float BMR = 0, desiredcalories = 0;//new omkar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportZoom(true);
        webView.loadUrl("https://ndb.nal.usda.gov/ndb/search");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        try {
            //catch the table name sent by first activity and store it in string TABLE_NAME
            TABLE_NAME = getIntent().getStringExtra("table_name");//new omkar
            String s = getIntent().getStringExtra("BMR");
            BMR = Float.parseFloat(s);
            System.out.println("pushups bmr = "+BMR);
            String r = getIntent().getStringExtra("des");
            desiredcalories = Float.parseFloat(r);
            System.out.println("nutri des cal : "+ desiredcalories);
            //getIntent().getFloatExtra("BMR", BMR);// new omkar
            //getIntent().getFloatExtra("desiredcalories", desiredcalories);// new omkar
            //TableName.setText(TABLE_NAME);
            //Toast.makeText(MainActivity2.this, TABLE_NAME, Toast.LENGTH_SHORT).show();
        }catch(Exception e) {
            Toast.makeText(NutrientChart.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {//new omkar
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);//new omkar
        intent.putExtra("table_name",TABLE_NAME);//new omkar
        String t = ""+desiredcalories;
        intent.putExtra("des",t);
        String t1 = ""+BMR;
        intent.putExtra("BMR",t1);
        //intent.putExtra("BMR",BMR);//new omkar
        //intent.putExtra("desiredcalories", desiredcalories);//new omkar
        //Start the new activity
        startActivity(intent);//new omkar
        finish();
    }

}
