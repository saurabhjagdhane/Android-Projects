package com.example.saurabh.extracredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText a11, a12, a13, a14, a21, a22, a23, a24, a31, a32, a33, a34, a41, a42, a43, a44,
    b11, b12, b13, b14, b21, b22, b23, b24, b31, b32, b33, b34, b41, b42, b43, b44 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button multiply = (Button)findViewById(R.id.multiply_button);

        a11 = (EditText)findViewById(R.id.a11);
        a12 = (EditText)findViewById(R.id.a12);
        a13 = (EditText)findViewById(R.id.a13);
        a14 = (EditText)findViewById(R.id.a14);
        a21 = (EditText)findViewById(R.id.a21);
        a22 = (EditText)findViewById(R.id.a22);
        a23 = (EditText)findViewById(R.id.a23);
        a24 = (EditText)findViewById(R.id.a24);
        a31 = (EditText)findViewById(R.id.a31);
        a32 = (EditText)findViewById(R.id.a32);
        a33 = (EditText)findViewById(R.id.a33);
        a34 = (EditText)findViewById(R.id.a34);
        a41 = (EditText)findViewById(R.id.a41);
        a42 = (EditText)findViewById(R.id.a42);
        a43 = (EditText)findViewById(R.id.a43);
        a44 = (EditText)findViewById(R.id.a44);


        b11 = (EditText)findViewById(R.id.b11);
        b12 = (EditText)findViewById(R.id.b12);
        b13 = (EditText)findViewById(R.id.b13);
        b14 = (EditText)findViewById(R.id.b14);
        b21 = (EditText)findViewById(R.id.b21);
        b22 = (EditText)findViewById(R.id.b22);
        b23 = (EditText)findViewById(R.id.b23);
        b24 = (EditText)findViewById(R.id.b24);
        b31 = (EditText)findViewById(R.id.b31);
        b32 = (EditText)findViewById(R.id.b32);
        b33 = (EditText)findViewById(R.id.b33);
        b34 = (EditText)findViewById(R.id.b34);
        b41 = (EditText)findViewById(R.id.b41);
        b42 = (EditText)findViewById(R.id.b42);
        b43 = (EditText)findViewById(R.id.b43);
        b44 = (EditText)findViewById(R.id.b44);


        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String A11 = a11.getText().toString();
                    float fa11 = Float.parseFloat(A11);

                    String A12 = a12.getText().toString();
                    float fa12 = Float.parseFloat(A12);

                    String A13 = a13.getText().toString();
                    float fa13 = Float.parseFloat(A13);

                    String A14 = a14.getText().toString();
                    float fa14 = Float.parseFloat(A14);

                    String A21 = a21.getText().toString();
                    float fa21 = Float.parseFloat(A21);

                    String A22 = a22.getText().toString();
                    float fa22 = Float.parseFloat(A22);

                    String A23 = a23.getText().toString();
                    float fa23 = Float.parseFloat(A23);

                    String A24 = a24.getText().toString();
                    float fa24 = Float.parseFloat(A24);

                    String A31 = a31.getText().toString();
                    float fa31 = Float.parseFloat(A31);

                    String A32 = a32.getText().toString();
                    float fa32 = Float.parseFloat(A32);

                    String A33 = a33.getText().toString();
                    float fa33 = Float.parseFloat(A33);

                    String A34 = a34.getText().toString();
                    float fa34 = Float.parseFloat(A34);

                    String A41 = a41.getText().toString();
                    float fa41 = Float.parseFloat(A41);

                    String A42 = a42.getText().toString();
                    float fa42 = Float.parseFloat(A42);

                    String A43 = a43.getText().toString();
                    float fa43 = Float.parseFloat(A43);

                    String A44 = a44.getText().toString();
                    float fa44 = Float.parseFloat(A44);



                    String B11 = b11.getText().toString();
                    float fb11 = Float.parseFloat(B11);

                    String B12 = b12.getText().toString();
                    float fb12 = Float.parseFloat(B12);

                    String B13 = b13.getText().toString();
                    float fb13 = Float.parseFloat(B13);

                    String B14 = b14.getText().toString();
                    float fb14 = Float.parseFloat(B14);

                    String B21 = b21.getText().toString();
                    float fb21 = Float.parseFloat(B21);

                    String B22 = b22.getText().toString();
                    float fb22 = Float.parseFloat(B22);

                    String B23 = b23.getText().toString();
                    float fb23 = Float.parseFloat(B23);

                    String B24 = b24.getText().toString();
                    float fb24 = Float.parseFloat(B24);

                    String B31 = b31.getText().toString();
                    float fb31 = Float.parseFloat(B31);

                    String B32 = b32.getText().toString();
                    float fb32 = Float.parseFloat(B32);

                    String B33 = b33.getText().toString();
                    float fb33 = Float.parseFloat(B33);

                    String B34 = b34.getText().toString();
                    float fb34 = Float.parseFloat(B34);

                    String B41 = b41.getText().toString();
                    float fb41 = Float.parseFloat(B41);

                    String B42 = b42.getText().toString();
                    float fb42 = Float.parseFloat(B42);

                    String B43 = b43.getText().toString();
                    float fb43 = Float.parseFloat(B43);

                    String B44 = b44.getText().toString();
                    float fb44 = Float.parseFloat(B44);

                    final float[] mProjectionMatrix = new float[]{fa11, fa12, fa13, fa14, fa21, fa22, fa23, fa24, fa31, fa32, fa33, fa34, fa41, fa42, fa43, fa44};
                    final float[] mViewMatrix = new float[]{fb11, fb12, fb13, fb14, fb21, fb22, fb23, fb24, fb31, fb32, fb33, fb34, fb41, fb42, fb43, fb44};
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    i.putExtra("M1", mProjectionMatrix);
                    i.putExtra("M2", mViewMatrix);
                    startActivity(i);
                }
                catch (Exception e){
                    final float[] mProjectionMatrix = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                    final float[] mViewMatrix = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};;
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    i.putExtra("M1", mProjectionMatrix);
                    i.putExtra("M2", mViewMatrix);
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Make sure all the values are entered.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
