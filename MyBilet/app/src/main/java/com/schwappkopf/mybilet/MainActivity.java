package com.schwappkopf.mybilet;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends Activity {
    private DataBaseHelper db;
    TextView tvKalan;
    public long id=0;
    public double bakiye;
    private boolean status=true;
    List<Bilet> blt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db=new DataBaseHelper(this);
        blt=db.getAllBilet();
        int count=blt.size();
        if(count==0)
        {
            Bilet blet= new Bilet();
            blet.setAktarma(0.0);
            blet.setOgrenci_bilet(0.0);
            blet.setTam_bilet(0.0);
            blet.setSecenek(0);
            blet.setBakiye(0.0);
            db.createBilet(blet);
        }
        blt=db.getAllBilet();
        bakiye=blt.get(0).getBakiye();
        double round = bakiye;
        DecimalFormat df = new DecimalFormat("#.##");
        String bky= df.format(round);
        bky=bky.replaceAll(",",".");
        bakiye=Double.valueOf(bky);
        tvKalan=(TextView)findViewById(R.id.tvGuncelBakiye);
        tvKalan.setText(String.valueOf(bakiye));

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.hakkimda)
        {
            AlertDialog.Builder  builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(" teaddict; email:fth.mtn@gmail.com");
            builder.setCancelable(false);

            builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }

        if(item.getItemId()==R.id.action_settings)
        {
            Intent intent = new Intent(MainActivity.this ,  Settings.class);

            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }



    public void tek(View view){

        Bilet bilet= new Bilet();

        bilet.setAktarma(blt.get(0).getAktarma());
        bilet.setOgrenci_bilet(blt.get(0).getOgrenci_bilet());
        bilet.setTam_bilet(blt.get(0).getTam_bilet());
        bilet.setSecenek(blt.get(0).getSecenek());
        if(blt.get(0).getSecenek()==0) // tam == 1 , öğrenci == 1
        {
            if(bakiye-bilet.getOgrenci_bilet()>=0)
            {
                bakiye=bakiye-bilet.getOgrenci_bilet();
                //formatting to 2.23
                double round = bakiye;
                DecimalFormat df = new DecimalFormat("#.##");
                String bky= df.format(round);
                bky=bky.replaceAll(",",".");
                bakiye=Double.valueOf(bky);
                bilet.setBakiye(bakiye);
                db.updateBilet(bilet,1);
                String strBakiye = String.valueOf(bakiye);
                tvKalan.setText(strBakiye);
                tvKalan.refreshDrawableState();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Yetersiz Bakiye...",
                        Toast.LENGTH_SHORT).show();
            }

            //UPDATE WIDGET
            Intent intent = new Intent(this,Widget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
            // since it seems the onUpdate() is only fired on that:
            int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), Widget.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
            sendBroadcast(intent);
        }
        else
        {
            if(bakiye-bilet.getTam_bilet()>=0)
            {
                bakiye=bakiye-bilet.getTam_bilet();
                double round = bakiye;
                DecimalFormat df = new DecimalFormat("#.##");
                String bky= df.format(round);
                bky=bky.replaceAll(",",".");
                bakiye=Double.valueOf(bky);
                bilet.setBakiye(bakiye);
                db.updateBilet(bilet,1);
                String strBakiye = String.valueOf(bakiye);
                tvKalan.setText(strBakiye);
                tvKalan.refreshDrawableState();

                Intent intent = new Intent(this,Widget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
                // since it seems the onUpdate() is only fired on that:
                int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), Widget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                sendBroadcast(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Yetersiz Bakiye...",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }




    public void cift(View view){

        Bilet bilet= new Bilet();
        bilet.setAktarma(blt.get(0).getAktarma());
        bilet.setOgrenci_bilet(blt.get(0).getOgrenci_bilet());
        bilet.setTam_bilet(blt.get(0).getTam_bilet());
        bilet.setSecenek(blt.get(0).getSecenek());
        if(blt.get(0).getSecenek()==0)
        {
            if(bakiye-bilet.getOgrenci_bilet()-bilet.getTam_bilet()>=0)
            {
                bakiye=bakiye-bilet.getOgrenci_bilet()-bilet.getTam_bilet();
                double round = bakiye;
                DecimalFormat df = new DecimalFormat("#.##");
                String bky= df.format(round);
                bky=bky.replaceAll(",",".");
                bakiye=Double.valueOf(bky);
                bilet.setBakiye(bakiye);
                db.updateBilet(bilet,1);
                String strBakiye = String.valueOf(bakiye);
                tvKalan.setText(strBakiye);
                tvKalan.refreshDrawableState();

                //UPDATE WIDGET
                Intent intent = new Intent(this,Widget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
                // since it seems the onUpdate() is only fired on that:
                int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), Widget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                sendBroadcast(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Yetersiz Bakiye...",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            if(bakiye-bilet.getTam_bilet()-bilet.getTam_bilet()>=0)
            {
                bakiye=bakiye-bilet.getTam_bilet()-bilet.getTam_bilet();
                double round = bakiye;
                DecimalFormat df = new DecimalFormat("#.##");
                String bky= df.format(round);
                bky=bky.replaceAll(",",".");
                bakiye=Double.valueOf(bky);

                bilet.setBakiye(bakiye);
                db.updateBilet(bilet,1);
                String strBakiye = String.valueOf(bakiye);
                tvKalan.setText(strBakiye);
                tvKalan.refreshDrawableState();

                //UPDATE WIDGET
                Intent intent = new Intent(this,Widget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
                // since it seems the onUpdate() is only fired on that:
                int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), Widget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                sendBroadcast(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Yetersiz Bakiye...",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void aktarma(View view){


        Bilet bilet= new Bilet();
        bilet.setAktarma(blt.get(0).getAktarma());
        bilet.setOgrenci_bilet(blt.get(0).getOgrenci_bilet());
        bilet.setTam_bilet(blt.get(0).getTam_bilet());
        bilet.setSecenek(blt.get(0).getSecenek());
        if(bakiye-bilet.getAktarma()>=0)
        {
            bakiye=bakiye-bilet.getAktarma();
            double round = bakiye;
            DecimalFormat df = new DecimalFormat("#.##");
            String bky= df.format(round);
            bky=bky.replaceAll(",",".");
            bakiye=Double.valueOf(bky);

            bilet.setBakiye(bakiye);
            db.updateBilet(bilet,1);
            String strBakiye = String.valueOf(bakiye);
            tvKalan.setText(strBakiye);
            tvKalan.refreshDrawableState();

            //UPDATE WIDGET
            Intent intent = new Intent(this,Widget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
            // since it seems the onUpdate() is only fired on that:
            int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), Widget.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
            sendBroadcast(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Yetersiz Bakiye...",
                    Toast.LENGTH_SHORT).show();
        }

    }


    public void ayarla(View view){

        Intent intent = new Intent(MainActivity.this ,  Settings.class);

        startActivity(intent);
    }

}


