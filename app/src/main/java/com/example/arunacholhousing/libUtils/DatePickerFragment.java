package com.example.arunacholhousing.libUtils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private EditText editText; // EditText to update with the selected date
    private OnDateSetListener onDateSetListener;

    public interface OnDateSetListener {
        void onDateSet(DatePicker view, int year, int month, int dayOfMonth);
    }

    public void setOnDateSetListener(OnDateSetListener listener) {
        this.onDateSetListener = listener;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Forward the date set event to the listener
        if (onDateSetListener != null) {
            onDateSetListener.onDateSet(view, year, month, dayOfMonth);
        }
    }
}
