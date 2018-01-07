package com.tubandev.budgetnikah.ui;

/**
 * Created by sulistiyanto on 07/01/18.
 */

public interface MainPresenter {
    void loadData(boolean connecting);
    void savedata(boolean connecting, String ket, String nominal);
}
