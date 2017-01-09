package com.training.Medianet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.training.Medianet.Utils.CustomHttpClient;
import com.training.Medianet.Utils.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    String TAG = "LoginActivity";
    Button btnLogin;
    String username;
    String password;
    String status;
    String userid = "";
    String real_name = "";
    String urlContent;
    String url_login = "";
    String url="";
    //String url= "http://dwipk.esy.es/SPBU/login.php?";
    EditText name, pass;
    JSONArray mJSONarray;
    SessionManager sm;
    SweetAlertDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        name = (EditText) findViewById(R.id.edtname);
        pass = (EditText) findViewById(R.id.edtpass);

        initbutton();
    }

    private void initbutton(){

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username= name.getText().toString();
                password= pass.getText().toString();


                name.clearFocus();
                pass.clearFocus();
                View currentFocus = getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                Intent intent = new Intent( LoginActivity.this, MenuUtama.class);
                startActivity(intent);
                finish();

                //new GetAkses().execute();
                Log.d("dapet user detail", "onClick: " + real_name );

            }
        });
    }
    private class GetAkses extends AsyncTask<String,Void,String> {
        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        SweetAlertDialog loading = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        private String Content;
        private String Error = null;
        ArrayList<NameValuePair> userAuth = new ArrayList<NameValuePair>();
        String username;
        String password;


        @Override
        protected String doInBackground(String... params) {

            try {
                userAuth.add(new BasicNameValuePair("username", username));
                userAuth.add(new BasicNameValuePair("password", password));

                Content = CustomHttpClient.executeHttpPost(url_login, userAuth);


            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //final JsonParserClass sh = new JsonParserClass();



            return Content;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*this.dialog.setMessage("Loading Data...");
            this.dialog.show();*/
            loading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            loading.setTitleText("Logging In ...");
            loading.setCancelable(false);
            username= name.getText().toString();
            password= pass.getText().toString();


        }

        @Override
        protected void onPostExecute(String result) {
            this.dialog.dismiss();
            Log.d("jstring dapat", "onPostExecute: "+Content);
            String jsonStr=String.valueOf(Content);
            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONObject obj = new JSONObject(Content);
                    status = obj.getString("status");
                    userid = obj.getString("userid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            if (status!=null && status.contains("success")) {
                sm.createUserLoginSession(real_name,userid);

                name.clearFocus();
                pass.clearFocus();
                Intent intent = new Intent( LoginActivity.this, MenuUtama.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);

            } else {
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Invalid Username And Password!!...")
                        .show();
            }
            Log.d("Status", "onPostExecute: "+status);


        }
    }


    @Override
    public void onBackPressed() {
        SweetAlertDialog signout = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
        signout.setCanceledOnTouchOutside(true);
        signout
                .setTitleText("Sign Out")
                .setContentText("Anda Yakin?")
                .setCancelText("Tidak")
                .setConfirmText("Ya")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();


                    }
                })
                .show();

    }
}
