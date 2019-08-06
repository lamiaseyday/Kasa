package com.example.kasayonetimi;

public class DenemeModel {

    private String den1;
    private String den2;
    private int reso;


    public DenemeModel(String den1, String den2, int reso) {
        this.den1 = den1;
        this.den2 = den2;
        this.reso = reso;
    }

    public String getDen1() {
        return den1;
    }

    public void setDen1(String den1) {
        this.den1 = den1;
    }

    public String getDen2() {
        return den2;
    }

    public void setDen2(String den2) {
        this.den2 = den2;
    }

    public int getReso() {
        return reso;
    }

    public void setReso(int reso) {
        this.reso = reso;
    }
}
