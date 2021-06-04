package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medicinereminder.Adapter.MedicinesAdapter;
import com.example.medicinereminder.Model.MedicineModel;
import com.example.medicinereminder.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReminderActivity extends AppCompatActivity implements OnDialogCloseListner{

    private RecyclerView mRecyclerview;
    private Button fab;
    private DataBaseHelper myDB;
    private List<MedicineModel> mList;
    private MedicinesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        mRecyclerview = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        myDB = new DataBaseHelper(ReminderActivity.this);
        mList = new ArrayList<>();
        adapter = new MedicinesAdapter(myDB, ReminderActivity.this);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adapter);

        mList = myDB.getAllMedicines();
        Collections.reverse(mList);
        adapter.setMedicines(mList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewMedicine.newInstance().show(getSupportFragmentManager(), AddNewMedicine.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        //ItemTouchHelper.attachToRecyclerView(mRecyclerview);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = myDB.getAllMedicines();
        Collections.reverse(mList);
        adapter.setMedicines(mList);
        adapter.notifyDataSetChanged();
    }
}