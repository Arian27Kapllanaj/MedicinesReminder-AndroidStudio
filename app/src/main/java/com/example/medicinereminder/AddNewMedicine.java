package com.example.medicinereminder;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medicinereminder.Model.MedicineModel;
import com.example.medicinereminder.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewMedicine extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewMedicine";

    //widgets
    private EditText mEditText;
    private Button mSaveButton;

    private DataBaseHelper myDB;

    public static AddNewMedicine newInstance() {
        return new AddNewMedicine();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_medicine, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = view.findViewById(R.id.edittext);
        mSaveButton = view.findViewById(R.id.button_save);

        myDB = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String medicine = bundle.getString("medicine");
            mEditText.setText(medicine);

            if (medicine.length() > 0) {
                mSaveButton.setEnabled(false);
            }
        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    mSaveButton.setEnabled(false);
                    mSaveButton.setBackgroundColor(Color.GRAY);
                }
                else {
                    mSaveButton.setEnabled(true);
                    mSaveButton.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();

                if (finalIsUpdate) {
                    myDB.updateMedicine(bundle.getInt("id"), text);
                }
                else {
                    MedicineModel item = new MedicineModel();
                    item.setMedicine(text);
                    item.setStatus(0);
                    myDB.insertMedicine(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner) {
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }

    }
}
