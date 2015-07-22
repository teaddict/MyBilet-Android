package com.schwappkopf.mybilet;


import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Settings extends Activity{
    EditText etBakiye,etAktarma,etOgrenci,etTam;
    double _bakiye,_aktarma,_tam,_ogrenci;
    CheckBox cb;
    Button _kaydet;
    int sec;
    long id=0;
    private DataBaseHelper db;
    List<Bilet> biletler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db=new DataBaseHelper(this);
        Bilet blt=new Bilet();
        biletler=db.getAllBilet();
        blt=biletler.get(0);
        etBakiye=(EditText)findViewById(R.id.editText1);
        //_bakiye=db.getFiyat(id).getBakiye();
        _bakiye=blt.getBakiye();
        String strBakiye = String.valueOf(_bakiye);
        etBakiye.setText(strBakiye);

        etTam=(EditText)findViewById(R.id.editText2);
        //_tam=db.getFiyat(id).getTam_bilet();
        _tam=blt.getTam_bilet();
        String strTam = String.valueOf(_tam);
        etTam.setText(strTam);

        etOgrenci=(EditText)findViewById(R.id.editText3);
        //_ogrenci=db.getFiyat(id).getOgrenci_bilet();
        _ogrenci=blt.getOgrenci_bilet();
        String strOgrenci = String.valueOf(_ogrenci);
        etOgrenci.setText(strOgrenci);

        etAktarma=(EditText)findViewById(R.id.editText4);
        //_aktarma=db.getFiyat(id).getAktarma();
        _aktarma=blt.getAktarma();
        String strAktarma = String.valueOf(_aktarma);
        etAktarma.setText(strAktarma);

        cb=(CheckBox)findViewById(R.id.checkBox);
        sec=blt.getSecenek();
        //sec=db.getFiyat(id).getSecenek();
        if(sec==1)
            cb.setChecked(true);
        else
            cb.setChecked(false);

    }

    public void kaydet(View view){

        String aktrma=etAktarma.getText().toString();
        String bkye=etBakiye.getText().toString();
        String tm=etTam.getText().toString();
        String ogrnci=etOgrenci.getText().toString();
        int sc;
        Bilet bilet= new Bilet();
        bilet.setAktarma(Double.valueOf(aktrma));
        bilet.setBakiye(Double.valueOf(bkye));
        bilet.setOgrenci_bilet(Double.valueOf(ogrnci));
        bilet.setTam_bilet(Double.valueOf(tm));
        if(cb.isChecked())
            sc=1;//tam
        else
            sc=0;//ogrenci
        bilet.setSecenek(sc);
        db.updateBilet(bilet,1);

        Toast.makeText(getApplicationContext(), "Kaydedildi...",
                Toast.LENGTH_SHORT).show();

        //UPDATE WIDGET
        Intent intent = new Intent(this,Widget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), Widget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);

        Intent intentx = new Intent(Settings.this ,  MainActivity.class);
        startActivity(intentx);
    }
}
