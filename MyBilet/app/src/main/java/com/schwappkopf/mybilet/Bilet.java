package com.schwappkopf.mybilet;

/**
 * Created by schwappkopf on 9/29/14.
 */

public class Bilet {

    double ogrenci_bilet;
    double tam_bilet;
    double aktarma;
    double bakiye;
    int id;
    int secenek;

    public Bilet() {
        // TODO Auto-generated constructor stub
    }



    public Bilet(double ogrenci_bilet, double tam_bilet, double aktarma,
                 double bakiye, int id, int secenek) {
        super();
        this.ogrenci_bilet = ogrenci_bilet;
        this.tam_bilet = tam_bilet;
        this.aktarma = aktarma;
        this.bakiye = bakiye;
        this.id = id;
        this.secenek = secenek;
    }



    public double getOgrenci_bilet() {
        return ogrenci_bilet;
    }
    public void setOgrenci_bilet(double ogrenci_bilet) {
        this.ogrenci_bilet = ogrenci_bilet;
    }
    public double getTam_bilet() {
        return tam_bilet;
    }
    public void setTam_bilet(double tam_bilet) {
        this.tam_bilet = tam_bilet;
    }
    public double getAktarma() {
        return aktarma;
    }
    public void setAktarma(double aktarma) {
        this.aktarma = aktarma;
    }
    public double getBakiye() {
        return bakiye;
    }
    public void setBakiye(double bakiye) {
        this.bakiye = bakiye;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSecenek() {
        return secenek;
    }
    public void setSecenek(int secenek) {
        this.secenek = secenek;
    }
}