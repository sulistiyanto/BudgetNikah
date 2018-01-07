package com.tubandev.budgetnikah.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tubandev.budgetnikah.R;
import com.tubandev.budgetnikah.model.Data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sulistiyanto on 30/12/17.
 */

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolder> {

    private List<Data> dataList;

    public AdapterData(List<Data> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textKet)
        TextView textKet;
        @BindView(R.id.textNominal)
        TextView textNominal;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Data data) {
            textKet.setText(data.getKeterangan());

            int nominal = Integer.parseInt(data.getNominal());
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
            formatRp.setCurrencySymbol("");
            formatRp.setMonetaryDecimalSeparator('-');
            formatRp.setGroupingSeparator('.');
            kursIndonesia.setDecimalFormatSymbols(formatRp);
            String price = kursIndonesia.format(nominal).replace("-00", "");
            textNominal.setText("Rp. " + price);
        }
    }
}
