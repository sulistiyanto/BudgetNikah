package com.tubandev.budgetnikah.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sulistiyanto on 07/01/18.
 */

public class ResultData {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("error_msg")
    @Expose
    private String error_msg;
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("list")
    @Expose
    private List<Data> dataList = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }
}
