package com.appblocky.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    EditText edtName,edtMail,edtPass,edtCnfPass;
    Handler hos;
    URL url;

    String uname,mail,passwd,cnfPass,ip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtName=findViewById(R.id.edtName);
        edtMail=findViewById(R.id.edtMail);
        edtPass=findViewById(R.id.edtPass);
        edtCnfPass=findViewById(R.id.edtCnfPass);

//        ip="192.168.1.38";
        ip= AppDetails.ip;
//        ip="mangocity.appblocky.com";
//        ip="192.168.43.100";

        hos=new Handler();

//        new Thread(new Runnable()
//        {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while(true)
//                {
//                    try
//                    {
//                        Thread.sleep(10000);
//                        hos.post(new Runnable()
//                        {
//
//                            @Override
//                            public void run() {
//                                // TODO Auto-generated method stub
//
//                                ////////////
//                                try {
//                                    Thread.sleep(1000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//
////                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//
//                            }
//
//                        });
//
//                    }
//                    catch(Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        }).start();


    }

    public void btnLogin(View view) {
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void btnReg(View view) {
        uname=edtName.getText().toString();
        mail=edtMail.getText().toString();
        passwd=edtPass.getText().toString();
        cnfPass=edtCnfPass.getText().toString();


        try {
            url = new URL("http://"+ip+"/parking/register.php?username="+uname+"&password="+passwd+"&email="+mail);
            Log.v("URL : ","current location url");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Updatedata();


    }


    private void Updatedata(){

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            Toast.makeText(getApplicationContext(),"internet connected",Toast.LENGTH_SHORT).show();
            MyAsync myAsync = new MyAsync();
            myAsync.execute();

        }
        else {
            Toast.makeText(getApplicationContext(),"internet not connected",Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.
                    Builder(RegisterActivity.this);
            builder.setTitle("Alert!");
            builder.setMessage("Please check your network connection");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.create().show();
        }
    }


    class MyAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
//                url = new URL("http://mangocity.appblocky.com/webdb/getvalue.php?tag=AcLoc");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = inputStreamToString(in);
//	                BufferedReader bfr=new BufferedReader(new InputStreamReader(in));
//	                String line="";
//	                while(line!=null){
//	                   line=bfr.readLine();
//	                   data=data+line+"\n";
//	                   Log.e("Field", result);
//	                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if(s!=null) {
//	            	 Log.e("in method", s);
                    JSONObject jsonObject = new JSONObject(s);



                }else{

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }
}

