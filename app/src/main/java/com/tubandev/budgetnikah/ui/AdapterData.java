package com.tubandev.budgetnikah.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private OnItemClickListerner listerner;

    public AdapterData(List<Data> dataList, OnItemClickListerner listerner) {
        this.dataList = dataList;
        this.listerner = listerner;
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

    public interface OnItemClickListerner {
        void onClick(Data data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textKet)
        TextView textKet;
        @BindView(R.id.textNominal)
        TextView textNominal;
        @BindView(R.id.imageDelete)
        ImageView imageDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Data data) {
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

            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listerner.onClick(data);
                }
            });
        }
    }
}
