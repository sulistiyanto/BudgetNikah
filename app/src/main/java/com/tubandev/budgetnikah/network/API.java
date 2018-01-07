package com.tubandev.budgetnikah.network;

import com.tubandev.budgetnikah.model.ResultData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by sulistiyanto on 30/12/17.
 */

public interface API {

    @GET("budget_nikah.php")
    Call<ResultData> loadData();

    @FormUrlEncoded
    @POST("add.php")
    Call<ResultData> save(@Field("keterangan") String keterangan,
                          @Field("nominal") String nominal);

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResultData> delete(@Field("id") String id);
}
