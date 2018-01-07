package com.tubandev.budgetnikah.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sulistiyanto on 07/01/18.
 */

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("nominal")
    @Expose
    private String nominal;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
