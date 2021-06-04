package com.example.medicinereminder.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinereminder.AddNewMedicine;
import com.example.medicinereminder.Model.MedicineModel;
import com.example.medicinereminder.R;
import com.example.medicinereminder.ReminderActivity;
import com.example.medicinereminder.Utils.DataBaseHelper;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.MyViewHolder> {

    private List<MedicineModel> mList;
    private ReminderActivity activity;
    private DataBaseHelper myDB;

    public MedicinesAdapter(DataBaseHelper myDB, ReminderActivity activity) {
        this.activity = activity;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicines_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MedicineModel item = mList.get(position);
        holder.mCheckBox.setText(item.getMedicine());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myDB.updateStatus(item.getId(), 1);
                }
                else {
                    myDB.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public boolean toBoolean(int num) {
        return num != 0;
    }

    public Context getContext() {
        return  activity;
    }

    public void setMedicines(List<MedicineModel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deleteMedicine(int position) {
        MedicineModel item = mList.get(position);
        myDB.deleteMedicine(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void editMedicine(int position) {
        MedicineModel item = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("medicine", item.getMedicine());

        AddNewMedicine medicine = new AddNewMedicine();
        medicine.setArguments(bundle);
        medicine.show(activity.getSupportFragmentManager(), medicine.getTag());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
        }
    }
}
