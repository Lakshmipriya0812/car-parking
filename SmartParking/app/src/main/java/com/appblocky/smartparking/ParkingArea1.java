package com.appblocky.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

public class ParkingArea1 extends AppCompatActivity {
    String loc,ip;
    URL url,url1;

    JSONObject c;
    JSONArray slotdata;
    ImageButton b1,b2,b3,b4;
    String d1,d2,d3,d4;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_area1);

        d1="0";
        d2="0";
        d3="0";
        d4="0";

        b1=findViewById(R.id.imageButton1);
        b2=findViewById(R.id.imageButton2);
        b3=findViewById(R.id.imageButton3);
        b4=findViewById(R.id.imageButton4);


        loc=getIntent().getStringExtra("loc");
        user=getIntent().getStringExtra("user");
        Log.e("loc",loc);
        ip= AppDetails.ip;
        try {
            url=new URL("http://mangocity.appblocky.com/parking/getSlot.php?pid="+loc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Updatedata();

    }

    private void Updatedata(){

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            Toast.makeText(getApplicationContext(),"internet connected",Toast.LENGTH_SHORT).show();
            ParkingArea1.MyAsync myAsync = new ParkingArea1.MyAsync();
            myAsync.execute();

        }
        else {
            Toast.makeText(getApplicationContext(),"internet not connected",Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.
                    Builder(ParkingArea1.this);
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

    void bookSlot(String cloc,String cslot){

        try {
            url1=new URL("http://mangocity.appblocky.com/parking/update.php?user="+user+"&pid="+cloc+"&slot="+cslot);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Updatedata1();
    }



    public void btnS1(View view) {
        if (d1.equals("1")) {
            Toast.makeText(getApplicationContext(), "Slot Already booked", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.
                    Builder(ParkingArea1.this);
            builder.setTitle("Alert!");
            builder.setMessage("Do you want book this slot?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    bookSlot(loc,"s1");
                    Toast.makeText(getApplicationContext(), "Slot booked", Toast.LENGTH_SHORT).show();
//                finish();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                finish();
                }

            });
            builder.create().show();
        }
    }
    public void btnS2(View view) {
        if (d2.equals("1")) {
            Toast.makeText(getApplicationContext(), "Slot Already booked", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.
                    Builder(ParkingArea1.this);
            builder.setTitle("Alert!");
            builder.setMessage("Do you want book this slot?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    bookSlot(loc,"s2");

                    Toast.makeText(getApplicationContext(), "Slot booked", Toast.LENGTH_SHORT).show();
//                finish();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                finish();
                }

            });
            builder.create().show();
        }
    }
    public void btnS3(View view) {
        if (d3.equals("1")) {
            Toast.makeText(getApplicationContext(), "Slot Already booked", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.
                    Builder(ParkingArea1.this);
            builder.setTitle("Alert!");
            builder.setMessage("Do you want book this slot?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    bookSlot(loc,"s3");

                    Toast.makeText(getApplicationContext(), "Slot booked", Toast.LENGTH_SHORT).show();
//                finish();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                finish();
                }

            });
            builder.create().show();
        }
    }
    public void btnS4(View view) {
        if (d4.equals("1")) {
            Toast.makeText(getApplicationContext(), "Slot Already booked", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.
                    Builder(ParkingArea1.this);
            builder.setTitle("Alert!");
            builder.setMessage("Do you want book this slot?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    bookSlot(loc,"s4");

                    Toast.makeText(getApplicationContext(), "Slot booked", Toast.LENGTH_SHORT).show();
//                finish();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                finish();
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
                if (s != null) {
	            	 Log.e("in method", s);
                    JSONObject jsonObject = new JSONObject(s);
                    slotdata = jsonObject.getJSONArray("data");
                    c = slotdata.getJSONObject(0);
                    String s1=c.getString("s1");
                    String s2=c.getString("s2");
                    String s3=c.getString("s3");
                    String s4=c.getString("s4");
                    d1=s1;
                    d2=s2;
                    d3=s3;
                    d4=s4;

                    Log.e("data ",""+s1+","+s2+","+s3+","+s4);


                    if (s1.equals("0")){
                        b1.setBackgroundColor(Color.rgb(0,255,0));
                    }
                    else {
                        b1.setBackgroundColor(Color.rgb(255, 0, 0));

                    }
                        if (s2.equals("0")){
                            b2.setBackgroundColor(Color.rgb(0,255,0));
                        }
                        else{
                            b2.setBackgroundColor(Color.rgb(255,0,0));
                        }

                        if (s3.equals("0")){
                            b3.setBackgroundColor(Color.rgb(0,255,0));
                        }
                        else{
                            b3.setBackgroundColor(Color.rgb(255,0,0));
                        }

                        if (s4.equals("0")){
                            b4.setBackgroundColor(Color.rgb(0,255,0));
                        }
                        else{
                            b4.setBackgroundColor(Color.rgb(255,0,0));
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


    /////////////////////////////////////////////////////

    private void Updatedata1(){

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            Toast.makeText(getApplicationContext(),"internet connected",Toast.LENGTH_SHORT).show();
            ParkingArea1.MyAsync1 myAsync1 = new ParkingArea1.MyAsync1();
            myAsync1.execute();
        }
        else {
            Toast.makeText(getApplicationContext(),"internet not connected",Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.
                    Builder(ParkingArea1.this);
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


    class MyAsync1 extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = inputStreamToString1(in);
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
                    Log.e("in method", s);
                    JSONObject jsonObject = new JSONObject(s);
                    try {
                        url=new URL("http://mangocity.appblocky.com/parking/getSlot.php?pid="+loc);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Updatedata();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("email", user);
                    startActivity(i);
                    finish();

                }else{

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String inputStreamToString1(InputStream is) {
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