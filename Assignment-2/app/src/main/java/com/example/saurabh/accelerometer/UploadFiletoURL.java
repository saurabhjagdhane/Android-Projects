package com.example.saurabh.accelerometer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Calendar;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Saurabh on 09-03-2016.
 */

/**
 * Background Async Task to Upload file
 * */

class UploadFiletoURL extends AsyncTask<String, String, String> {

    private String upLoadServerUri = "";

    private Context context;
    Calendar c = Calendar.getInstance();

    public UploadFiletoURL(Context context) {
        this.context = context;
    }




    /*
        private OnTaskCompletedUpload listener;

        public UploadFileFroURL (OnTaskCompletedUpload listener){
            this.listener=listener;
        }

        public UploadFileFroURL() {
            // TODO Auto-generated constructor stub

        }
    */

    @Override
    protected String doInBackground(String... content) {

        String address=content[0]+content[1];
        upLoadServerUri=content[2];

        long size = uploadFile(address);

        return " ";
    }

    public long uploadFile(String sourceFileUri)
    {   long actuaStartlTime = 0 ;
        double debutPaquet ;
        double[] tabTotalDebit= new double [10];
        String fileName = sourceFileUri;
        long TotalSize=0;
        HttpURLConnection comm = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int serverResponseCode = 0;
        int bytesRead,bytesReadBis,bytesAvailable,bufferSize;
        byte []buffer;
        int maxBufferSize =   10240 ;
        File sourceFile = new File(sourceFileUri);

        if(!sourceFile.isFile())
        {
            //dialog.dismiss();
            Log.e("UploadFile", "Source File not Exist: " + sourceFileUri);

            return 0;
        }
        else
        {
            try
            {
                FileInputStream fis = new FileInputStream(sourceFile);
                TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                } };
                // Install the all-trusting trust manager
                final SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                // Create all-trusting host name verifier
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };

                // Install the all-trusting host verifier
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);



                URL url = new URL(upLoadServerUri);

                comm = (HttpsURLConnection)url.openConnection();
                comm.setDoInput(true);
                comm.setDoOutput(true);
                comm.setUseCaches(false);
                comm.setRequestMethod("POST");
                comm.setRequestProperty("Connection", "Keep-Alive");
                comm.setRequestProperty("ENCTYPE", "multipart/form-data");
                comm.setRequestProperty("Content-Type", "multipart/form-data;boundary=" +boundary);
                comm.setRequestProperty("uploaded_file", fileName);
                dos = new DataOutputStream(comm.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                bytesAvailable = fis.available();
                TotalSize = bytesAvailable ;
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fis.read(buffer,0,bufferSize);
                long global = System.currentTimeMillis();
                debutPaquet = System.currentTimeMillis() ;
                double totalTime =0;

                while(bytesRead > 0)

                { dos.write(buffer,0,bufferSize);
                    bytesAvailable = fis.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fis.read(buffer,0,bufferSize);
                    publishProgress (bytesRead+"");

                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                serverResponseCode = comm.getResponseCode();
                String serverResponseMessage = comm.getResponseMessage();
                if(serverResponseCode == 200)
                { String msg = "File Upload Completed. \n\n See uploaded file here : https://impact.asu.edu/Appenstance/";
                }

                fis.close();
                dos.flush();
                dos.close();
            }
            catch(MalformedURLException e)
            {

                e.printStackTrace();

                Log.e("Upload file to server", "error: " + e.getMessage(), e);
            }
            catch(Exception e)
            {

                e.printStackTrace();

                Log.e("Upload file to server", "error: " + e.getMessage(), e);
            }

            return TotalSize;
        }
    }
    protected void onProgressUpdate(String... progress) {
    }

    protected void onPostExecute(String result) {
        Log.i("msg", "Upload complete. \n\n See Upload file here : : https://impact.asu.edu/Appenstance/ ");
        Toast.makeText(context, "Upload Done! @\n" + c.getTime() , Toast.LENGTH_LONG).show();


    }
}

