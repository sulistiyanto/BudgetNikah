package com.tubandev.budgetnikah.ui;

/**
 * Created by sulistiyanto on 07/01/18.
 */

public interface MainView {
    void error(String message);
    void success(AdapterData adapterData, String budgetRp, String keluarRp, String sisaRp);
    void hideProgressBar();
    void showProgressBar();
    void loadData();
}
