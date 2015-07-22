package com.schwappkopf.mybilet;

/**
 * Created by schwappkopf on 1/31/15.
 */

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class Widget extends AppWidgetProvider {

    public static String ACTION_WIDGET_TEK = "ActionReceiverTek";
    public static String ACTION_WIDGET_AKTARMA = "ActionReceiverAktarma";
    public static String ACTION_WIDGET_CIFT = "ActionReceiverCift";

    List<Bilet> blt;
    public long id=0;
    public double bakiye;

    public void onDeleted(Context context, int[] appWidgetIds) {
        // TODO Auto-generated method stub
        super.onDeleted(context, appWidgetIds);
        Toast.makeText(context, "widget deleted", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        watchWidget = new ComponentName(context, Widget.class);

        DataBaseHelper db =new DataBaseHelper(context);
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


        remoteViews.setTextViewText(R.id.Bakiye,"Bakiye: " + String.valueOf(bakiye));

        remoteViews.setOnClickPendingIntent(R.id.btnTek, getPendingSelfIntent(context, ACTION_WIDGET_TEK));
        remoteViews.setOnClickPendingIntent(R.id.Aktarma, getPendingSelfIntent(context, ACTION_WIDGET_AKTARMA));
        remoteViews.setOnClickPendingIntent(R.id.btnCift, getPendingSelfIntent(context, ACTION_WIDGET_CIFT));
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (ACTION_WIDGET_TEK.equals(intent.getAction())) {


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            watchWidget = new ComponentName(context, Widget.class);

            DataBaseHelper db =new DataBaseHelper(context);
            Bilet bilet= new Bilet();
            blt=db.getAllBilet();
            bakiye=blt.get(0).getBakiye();
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

                    Toast.makeText(context, "işlem başarılı", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context, "Yetersiz Bakiye...",Toast.LENGTH_SHORT).show();
                }

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
                    db.updateBilet(bilet, 1);
                    Toast.makeText(context, "işlem başarılı", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context, "Yetersiz Bakiye...",Toast.LENGTH_SHORT).show();
                }

            }


            remoteViews.setTextViewText(R.id.Bakiye,"Bakiye: " + String.valueOf(bakiye));
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }

        else if (ACTION_WIDGET_AKTARMA.equals(intent.getAction())) {


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            watchWidget = new ComponentName(context, Widget.class);

            DataBaseHelper db =new DataBaseHelper(context);
            Bilet bilet= new Bilet();
            blt=db.getAllBilet();
            bakiye=blt.get(0).getBakiye();
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

                Toast.makeText(context, "işlem başarılı", Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(context, "Yetersiz Bakiye...",Toast.LENGTH_SHORT).show();
            }



            remoteViews.setTextViewText(R.id.Bakiye,"Bakiye: " + String.valueOf(bakiye));
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }

        else if (ACTION_WIDGET_CIFT.equals(intent.getAction())) {


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            watchWidget = new ComponentName(context, Widget.class);

            DataBaseHelper db =new DataBaseHelper(context);
            Bilet bilet= new Bilet();
            blt=db.getAllBilet();
            bakiye=blt.get(0).getBakiye();
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

                    Toast.makeText(context, "işlem başarılı", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context, "Yetersiz Bakiye...",Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(context, "işlem başarılı", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context, "Yetersiz Bakiye...",Toast.LENGTH_SHORT).show();
                }
            }



            remoteViews.setTextViewText(R.id.Bakiye,"Bakiye: " + String.valueOf(bakiye));
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }

    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

}