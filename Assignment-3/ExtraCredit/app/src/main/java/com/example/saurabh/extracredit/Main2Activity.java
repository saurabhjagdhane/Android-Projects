package com.example.saurabh.extracredit;

import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private final float[] mMVPMatrix = new float[16];
    float[] mProjectionMatrix = new float[16];
    float[] mViewMatrix = new float[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProjectionMatrix=getIntent().getFloatArrayExtra("M1");
        mViewMatrix = getIntent().getFloatArrayExtra("M2");


        TextView ans33=(TextView)findViewById(R.id.editText33);
        TextView ans34=(TextView)findViewById(R.id.editText34);
        TextView ans35=(TextView)findViewById(R.id.editText35);
        TextView ans36=(TextView)findViewById(R.id.editText36);
        TextView ans37=(TextView)findViewById(R.id.editText37);
        TextView ans38=(TextView)findViewById(R.id.editText38);
        TextView ans39=(TextView)findViewById(R.id.editText39);
        TextView ans40=(TextView)findViewById(R.id.editText40);
        TextView ans41=(TextView)findViewById(R.id.editText41);
        TextView ans42=(TextView)findViewById(R.id.editText42);
        TextView ans43=(TextView)findViewById(R.id.editText43);
        TextView ans44=(TextView)findViewById(R.id.editText44);
        TextView ans45=(TextView)findViewById(R.id.editText45);
        TextView ans46=(TextView)findViewById(R.id.editText46);
        TextView ans47=(TextView)findViewById(R.id.editText47);
        TextView ans48=(TextView)findViewById(R.id.editText48);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        ans33.setText(""+mMVPMatrix[0]);
        ans34.setText(""+mMVPMatrix[1]);
        ans35.setText(""+mMVPMatrix[2]);
        ans36.setText(""+mMVPMatrix[3]);
        ans37.setText(""+mMVPMatrix[4]);
        ans38.setText(""+mMVPMatrix[5]);
        ans39.setText(""+mMVPMatrix[6]);
        ans40.setText(""+mMVPMatrix[7]);
        ans41.setText(""+mMVPMatrix[8]);
        ans42.setText(""+mMVPMatrix[9]);
        ans43.setText(""+mMVPMatrix[10]);
        ans44.setText(""+mMVPMatrix[11]);
        ans45.setText(""+mMVPMatrix[12]);
        ans46.setText(""+mMVPMatrix[13]);
        ans47.setText(""+mMVPMatrix[14]);
        ans48.setText(""+mMVPMatrix[15]);


       // System.out.println("Result= "+mMVPMatrix);

    }

}
