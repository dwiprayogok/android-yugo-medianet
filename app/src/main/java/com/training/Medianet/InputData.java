package com.training.Medianet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.training.Medianet.Utils.CustomHttpClient;
import com.training.Medianet.Utils.JsonParserClass;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by root on 05/01/17.
 */
public class InputData extends AppCompatActivity {

    EditText name,alamat ;
    Button save;
    String
            nama="",
            address=""
          ;

    String url_insert = "http://103.253.113.42/thamrin_sales_pack/api/update.php";
    String TAG = "InputData";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_data);

        name = (EditText) findViewById(R.id.input_name);
        alamat = (EditText) findViewById(R.id.address);
        save = (Button) findViewById(R.id.saveinsert);

        initbutton();
    }

    private void initbutton(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
                new GetAkses().execute();
            }
        });
    }

    private void updateData(){
        nama = name.getText().toString().trim();
        address = alamat.getText().toString().trim();

        Log.d(TAG, "updateData: "+ nama + " dan " + address);
    }

    private class GetAkses extends AsyncTask<String, Void, String> {

        SweetAlertDialog sDialog = new SweetAlertDialog(InputData.this,SweetAlertDialog.PROGRESS_TYPE);
        private String Content;
        String post;
        private String Error = null;
        ArrayList<NameValuePair> userAuth = new ArrayList<NameValuePair>();
        Bitmap bmp2;


        @Override
        protected String doInBackground(String... params) {

            final JsonParserClass sh = new JsonParserClass();
            JSONObject sendJsobj=new JSONObject();

            try {
                userAuth.add(new BasicNameValuePair("nama",nama));
                userAuth.add(new BasicNameValuePair("address",address));


                Content = CustomHttpClient.executeHttpPost(url_insert,userAuth);
                Log.d(TAG, "doInBackground: "+Content);
                Log.d(TAG, "doInBackground: cek id aja"+nama+"/n"+address);
                Log.d(TAG, "doInBackground: kiriman"+userAuth);
                Log.d(TAG, "doInBackground: isi content"+" "+Content);


            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (Exception e) {
                e.printStackTrace();
            }



            return Content;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sDialog
                    .setTitleText("Submit data..")
                    .setContentText("loading..")
                    .showCancelButton(false);
            sDialog.show();

        }

        @Override
        protected void onPostExecute(String result) {
            //this.dialog.dismiss();
            Log.d("jstring dapat", "onPostExecute: "+result );
            //String jsonStr=String.valueOf(Content);
            /*if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONObject obj = new JSONObject(Content);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
*/
            if(result.contains("OK")){
                sDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sDialog.dismiss();
                        Intent returnintent = new Intent();
                        setResult(RESULT_OK,returnintent);
                        finish();
                    }
                });
                sDialog.setTitleText("Berhasil..");
                sDialog.setContentText(Html.fromHtml(Content).toString());



            } else if (result.contains("ERROR")){

                sDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sDialog.dismiss();

                    }
                });

                sDialog.setTitle("Mohon Maaf..");
                sDialog.setContentText(Html.fromHtml(Content).toString());

            }


        }
    }

}
