package com.example.kasayonetimi;

import java.io.Serializable;

public class SelectItems implements Serializable {

    private String buroNo;
    private String esasNo;
    private String mahkeme;
    private String borcluİsim;
    private boolean isSelected = false;
    private int listID;

    public SelectItems() {
    }

    public SelectItems(String esasNo, String borcluİsim) {
        this.esasNo = esasNo;
        this.borcluİsim = borcluİsim;
    }

    public SelectItems(String buroNo, String esasNo, String mahkeme, String borcluİsim) {
        this.buroNo = buroNo;
        this.esasNo = esasNo;
        this.mahkeme = mahkeme;
        this.borcluİsim = borcluİsim;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public String getBuroNo() {
        return buroNo;
    }

    public void setBuroNo(String buroNo) {
        this.buroNo = buroNo;
    }

    public String getEsasNo() {
        return esasNo;
    }

    public void setEsasNo(String esasNo) {
        this.esasNo = esasNo;
    }

    public String getMahkeme() {
        return mahkeme;
    }

    public void setMahkeme(String mahkeme) {
        this.mahkeme = mahkeme;
    }

    public String getBorcluİsim() {
        return borcluİsim;
    }

    public void setBorcluİsim(String borcluİsim) {
        this.borcluİsim = borcluİsim;
    }

    public void changeText(String text){
        buroNo = text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }
}
