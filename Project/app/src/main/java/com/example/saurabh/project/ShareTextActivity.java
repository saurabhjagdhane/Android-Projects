package com.example.saurabh.project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class ShareTextActivity extends AppCompatActivity {
    private EditText textEntry;
    private Button shareButton;
    private File imageFile;
    private Button take_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textEntry = (EditText)findViewById(R.id.share_text_entry);
        shareButton = (Button)findViewById(R.id.share_text_button);
        take_picture = (Button)findViewById(R.id.take_picture_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEntry = textEntry.getText().toString();

                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.putExtra(Intent.EXTRA_TEXT, userEntry);
                textShareIntent.setType("text/plain");

                //startActivity(textShareIntent);
                // ^^ this auto-picks the defined default program for a content type, but since we want users to
                //    have options, we instead use the OS to create a chooser for users to pick from

                startActivity(Intent.createChooser(textShareIntent, "Share text with..."));
            }
        });

        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        "test.jpg");
                Uri tempuri = Uri.fromFile(imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, 0);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0){
            switch (resultCode){
                case Activity.RESULT_OK:
                    if (imageFile.exists()){
                        Toast.makeText(this, "The file was saved at " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);

                        // If you want to share a png image only, you can do:
                        // setType("image/png"); OR for jpeg: setType("image/jpeg");
                        shareIntent.setType("image/jpg");

                        // Make sure you put example png image named myImage.png in your
                        // directory
                        String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                + "/test.jpg";

                        File imageFileToShare = new File(imagePath);

                        Uri uri = Uri.fromFile(imageFileToShare);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

                        startActivity(Intent.createChooser(shareIntent, "Share Image!"));
                    }
                    else
                    {
                        Toast.makeText(this, "There was an error saving the file ", Toast.LENGTH_LONG).show();
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    break;
                default:
                    break;
            }
        }
    }


}
