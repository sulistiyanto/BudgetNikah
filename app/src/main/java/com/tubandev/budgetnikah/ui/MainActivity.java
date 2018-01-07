package com.tubandev.budgetnikah.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tubandev.budgetnikah.R;
import com.tubandev.budgetnikah.model.Data;
import com.tubandev.budgetnikah.utils.ConnectionNetwork;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.textKeluar)
    TextView textKeluar;
    @BindView(R.id.textSisa)
    TextView textSisa;

    private MainPresenter presenter;
    private ConnectionNetwork connectionNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("Budget : Rp. 0");
        connectionNetwork = new ConnectionNetwork();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        presenter = new IMainPresenter(this);
        presenter.loadData(connectionNetwork.isConnecting(this));
    }

    @OnClick(R.id.fab)
    void fab() {
        addDialog();
    }

    private void addDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptsView = layoutInflater.inflate(R.layout.add, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle("Kebutuhan");

        final EditText etKet = promptsView.findViewById(R.id.etKet);
        final EditText etNominal = promptsView.findViewById(R.id.etNominal);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Tambah",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String ket = etKet.getText().toString();
                                String nominal = etNominal.getText().toString();
                                dialog.cancel();
                                save(ket, nominal);
                            }
                        })
                .setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void save(String ket, String nominal) {
        presenter.savedata(connectionNetwork.isConnecting(this), ket, nominal);
    }

    @Override
    public void error(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void success(AdapterData adapterData, String budgetRp, String keluarRp, String sisaRp) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapterData);
        getSupportActionBar().setSubtitle("Budget : " + budgetRp);
        textKeluar.setText("Keluar : " + keluarRp);
        textSisa.setText("Sisa : " + sisaRp);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void loadData() {
        presenter.loadData(connectionNetwork.isConnecting(this));
    }

    @Override
    public void showDialogDelete(final Data data) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin ingin hapus data " + data.getKeterangan());
        alertDialogBuilder.setPositiveButton("Hapus",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        delete(data.getId());
                    }
                });

        alertDialogBuilder.setNegativeButton("Batal",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void delete(String id) {
        presenter.delete(connectionNetwork.isConnecting(this), id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.loadData(connectionNetwork.isConnecting(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
