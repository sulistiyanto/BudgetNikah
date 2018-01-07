package com.tubandev.budgetnikah.ui;

import com.tubandev.budgetnikah.model.Data;
import com.tubandev.budgetnikah.model.ResultData;
import com.tubandev.budgetnikah.network.API;
import com.tubandev.budgetnikah.network.APIClient;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sulistiyanto on 07/01/18.
 */

public class IMainPresenter implements MainPresenter {

    private MainView view;

    public IMainPresenter(MainView view) {
        this.view = view;
    }

    @Override
    public void loadData(boolean connecting) {
        if (connecting) {
            view.showProgressBar();
            API api = APIClient.getService();
            Call<ResultData> call = api.loadData();
            call.enqueue(new Callback<ResultData>() {
                @Override
                public void onResponse(Call<ResultData> call, Response<ResultData> response) {
                    view.hideProgressBar();
                    boolean error = response.body().getError();
                    if (error) {
                        view.error("Service tidak bisa diakses");
                    } else {
                        List<Data> dataList = response.body().getDataList();
                        int budget = Integer.parseInt(response.body().getBudget());
                        if (dataList.size() > 0) {
                            int keluar = 0;
                            for (Data data : dataList) {
                                keluar = keluar + Integer.parseInt(data.getNominal());
                            }
                            int sisa = budget - keluar;

                            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
                            formatRp.setCurrencySymbol("");
                            formatRp.setMonetaryDecimalSeparator('-');
                            formatRp.setGroupingSeparator('.');
                            kursIndonesia.setDecimalFormatSymbols(formatRp);
                            String budgetRp = "Rp. " + kursIndonesia.format(budget).replace("-00", "");
                            String keluarRp = "Rp. " + kursIndonesia.format(keluar).replace("-00", "");
                            String sisaRp = "Rp. " + kursIndonesia.format(sisa).replace("-00", "");

                            AdapterData adapterData = new AdapterData(dataList, new AdapterData.OnItemClickListerner() {
                                @Override
                                public void onClick(Data data) {
                                    view.showDialogDelete(data);
                                }
                            });
                            view.success(adapterData, budgetRp, keluarRp, sisaRp);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResultData> call, Throwable t) {
                    view.hideProgressBar();
                    view.error("Service tidak bisa diakses");
                }
            });

        } else {
            view.loadData();
            view.error("Koneksi internet lemah");
        }
    }

    @Override
    public void savedata(boolean connecting, String ket, String nominal) {
        if (connecting) {
            view.showProgressBar();
            API api = APIClient.getService();
            Call<ResultData> call = api.save(ket, nominal);
            call.enqueue(new Callback<ResultData>() {
                @Override
                public void onResponse(Call<ResultData> call, Response<ResultData> response) {
                    boolean error = response.body().getError();
                    if (error) {
                        view.error("Gagal, silahkan coba lagi");
                    }
                    view.loadData();
                }

                @Override
                public void onFailure(Call<ResultData> call, Throwable t) {
                    view.hideProgressBar();
                    view.error("Service tidak bisa diakses");
                }
            });

        } else {
            view.error("Koneksi internet lemah");
        }
    }

    @Override
    public void delete(boolean connecting, String id) {
        if (connecting) {
            view.showProgressBar();
            API api = APIClient.getService();
            Call<ResultData> call = api.delete(id);
            call.enqueue(new Callback<ResultData>() {
                @Override
                public void onResponse(Call<ResultData> call, Response<ResultData> response) {
                    boolean error = response.body().getError();
                    if (error) {
                        view.error("Gagal, silahkan coba lagi");
                    }
                    view.loadData();
                }

                @Override
                public void onFailure(Call<ResultData> call, Throwable t) {
                    view.hideProgressBar();
                    view.error("Service tidak bisa diakses");
                }
            });

        } else {
            view.error("Koneksi internet lemah");
        }
    }
}
