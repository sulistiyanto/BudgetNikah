package com.tubandev.budgetnikah.ui;

import com.tubandev.budgetnikah.model.Data;

/**
 * Created by sulistiyanto on 07/01/18.
 */

public interface MainView {
    void error(String message);
    void success(AdapterData adapterData, String budgetRp, String keluarRp, String sisaRp);
    void hideProgressBar();
    void showProgressBar();
    void loadData();
    void showDialogDelete(Data data);
}
