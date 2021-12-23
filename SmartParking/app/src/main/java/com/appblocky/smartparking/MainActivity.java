package com.appblocky.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

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

public class MainActivity extends AppCompatActivity implements PaymentResultListener,FormDialog.FormDialogListener{
String email,ip;

URL url,url1;


    JSONObject c;
    JSONArray slotdata;
    Button breturn,bloc;
    TextView txtArea,txtSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=getIntent().getStringExtra("email");
        breturn=findViewById(R.id.btnReturn);
        bloc=findViewById(R.id.btnViewLoc);

        txtArea=findViewById(R.id.txtArea);
        txtSlot=findViewById(R.id.txtSlot);


        ip= AppDetails.ip;
        try {
            url=new URL("http://mangocity.appblocky.com/parking/getBooking.php?user="+email);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Updatedata();

    }

    public void btnLoc(View view) {
        Intent i =new Intent(this,MapsActivity.class);
        i.putExtra("user",email);
        startActivity(i);
    }

    private void Updatedata(){

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            Toast.makeText(getApplicationContext(),"internet connected",Toast.LENGTH_SHORT).show();
            MainActivity.MyAsync myAsync = new MainActivity.MyAsync();
            myAsync.execute();

        }
        else {
            Toast.makeText(getApplicationContext(),"internet not connected",Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.
                    Builder(MainActivity.this);
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

    public void btnReturn(View view) {

        FormDialog formDialog=new FormDialog();
        formDialog.show(getSupportFragmentManager(), "form dialog");

    }

    @Override
    public void applyTexts(String nemail, String mobile) {
        startPayment(nemail,mobile);
    }


    public void startPayment(String nemail, String mobile) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Rk");
            options.put("description", "test Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "5000");

            JSONObject preFill = new JSONObject();
            preFill.put("email", nemail);
            preFill.put("contact", mobile);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        try {
            url1=new URL("http://mangocity.appblocky.com/parking/update.php?user="+email+"&pid=nul&slot=nul");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Updatedata1();

    }

    @Override
    public void onPaymentError(int i, String s) {

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

                    slotdata = jsonObject.getJSONArray("data");
                    c = slotdata.getJSONObject(0);
                    String pid=c.getString("pid");
                    String slot=c.getString("slot");

                    txtArea.setText(pid);
                    txtSlot.setText(slot);

                    if (pid.equals("nul")){
                        bloc.setVisibility(View.VISIBLE);
                        breturn.setVisibility(View.INVISIBLE);
                    }
                    else {
                        breturn.setVisibility(View.VISIBLE);
                        bloc.setVisibility(View.INVISIBLE);
                    }



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

    /////////////////////////////////////////////////////

    private void Updatedata1(){

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            Toast.makeText(getApplicationContext(),"internet connected",Toast.LENGTH_SHORT).show();
            MyAsync1 myAsync1 = new MyAsync1();
            myAsync1.execute();
        }
        else {
            Toast.makeText(getApplicationContext(),"internet not connected",Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.
                    Builder(MainActivity.this);
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
                        url=new URL("http://mangocity.appblocky.com/parking/getBooking.php?user="+email);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Log.e("On post","refresh..");
                    Updatedata();


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