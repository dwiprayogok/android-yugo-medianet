package com.training.Medianet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.training.Medianet.Adapter.Grid_adapter;
import com.training.Medianet.Utils.SessionManager;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by root on 05/01/17.
 */
public class MenuUtama extends AppCompatActivity {
    private Toolbar toolbar;
    SessionManager sm;
    private  static final String[] GRID_MENU = new String[]{
            "INPUT DATA",//1
            "SHOW DATA",//2
            "SHOW MAP",//3
            "EXIT"};//4
    public static int [] prgmImages=
            new int []{
            R.drawable.ic_launcher,//1
            R.drawable.ic_launcher,//2
            R.drawable.ic_launcher,//3
            R.drawable.ic_launcher};//4
    SweetAlertDialog progressLoadDbswAL;
    private GridView gv;
    ProgressDialog progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle("");
        }

         gv = (GridView) findViewById(R.id.grid_menu);
        gv.setAdapter(new Grid_adapter(this, GRID_MENU,prgmImages));

        initbutton();
    }

    private void initbutton(){
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               switch (position) {


                   case 0:
                       //inputdata
                       Intent keCatalog= new Intent(getApplicationContext(),InputData.class);
                       startActivity(keCatalog);

                   break;

                   case 1:
                       //swowdata
                       Intent product= new Intent(getApplicationContext(),ShowData.class);
                       startActivity(product);

                       break;

                   case 2:
                        //showmap
                       Intent pricelist= new Intent(getApplicationContext(),ShowMap.class);
                       startActivity(pricelist);

                       break;

                   case 3:
                       final SweetAlertDialog close = new SweetAlertDialog(MenuUtama.this, SweetAlertDialog.WARNING_TYPE);
                       close.setCanceledOnTouchOutside(true);
                       close.setTitleText("Perhatian!")
                               .setContentText("Anda Yakin Untuk Keluar ?")
                               .setConfirmText("Ya")
                               .setCancelText("Tidak")

                               .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                   @Override
                                   public void onClick(final SweetAlertDialog sDialog) {
                                       //sm.logoutUser();
                                       sDialog.dismiss();
                                       finish();

                                   }
                               })
                               .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                   @Override
                                   public void onClick(SweetAlertDialog sweetAlertDialog) {
                                       sweetAlertDialog.cancel();

                                   }
                               });
                       close.show();

                       break;



                   default:
                       break;

               }
           }
       });
    }
}
