package com.appblocky.smartparking;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

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

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    String uname,passwd,ip;

    URL url;
    EditText edtLoginUserName,edtLoginPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginUserName=findViewById(R.id.edtLoginUserName);
        edtLoginPassword=findViewById(R.id.edtLoginPassword);

//        ip="192.168.1.37";
//        ip="192.168.43.100";
//        ip="192.168.1.38";
        ip= AppDetails.ip;

//        ip="mangocity.appblocky.com";
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
                    Builder(LoginActivity.this);
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

    public void btnLogin(View view) {

        uname=edtLoginUserName.getText().toString();
        passwd=edtLoginPassword.getText().toString();


        try {
            url = new URL("http://"+ip+"/parking/login.php?email="+uname+"&password="+passwd);
            Log.v("URL : ","current location url");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Updatedata();
    }

    public void btnGoReg(View view) {
        Intent i=new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(i);
        finish();
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
                if (s != null) {
//	            	 Log.e("in method", s);
//                    JSONObject jsonObject = new JSONObject(s);
                    String res = s.toString();

                    Log.i("res :", "Val :" + res);
                    if (res.equals("1")) {
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("email", uname);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Login Fail : Invalid", Toast.LENGTH_SHORT).show();
                    }


                } else {

                }

            } catch (Exception e) {
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
