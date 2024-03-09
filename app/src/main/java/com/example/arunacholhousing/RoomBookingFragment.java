package com.example.arunacholhousing;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.arunacholhousing.libUtils.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RoomBookingFragment extends Fragment implements DatePickerFragment.OnDateSetListener {
    private final Calendar selectedDate = Calendar.getInstance();
    private EditText checkInDate, checkoutDate;
    private boolean datePicker1ShownOnce = false;
    private boolean datePicker2ShownOnce = false;
    private EditText selectedEditText;
    public RoomBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkInDate = view.findViewById(R.id.checkInDate);
        checkoutDate = view.findViewById(R.id.checkOutDate);
        checkInDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !datePicker1ShownOnce) {
                selectedEditText = checkInDate;
                showDatePickerDialog(checkInDate);
                hideKeyboard();
                datePicker1ShownOnce = true;
            }
        });
        checkInDate.setOnClickListener(v -> {
            selectedEditText = checkInDate;
            showDatePickerDialog(checkInDate);
            hideKeyboard();
        });

        checkoutDate = view.findViewById(R.id.checkOutDate);
        checkoutDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !datePicker2ShownOnce) {
                selectedEditText = checkoutDate;
                showDatePickerDialog(checkoutDate);
                hideKeyboard();
                datePicker2ShownOnce = true;
            }
        });

        checkoutDate.setOnClickListener(v -> {
            selectedEditText = checkoutDate;
            showDatePickerDialog(checkoutDate);
            hideKeyboard();
        });


    }
    private void showDatePickerDialog(EditText editText) {
        // Create an instance of the DatePickerFragment class
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        // Set the RoomBookingFragment instance as the listener for date selection
        datePickerFragment.setOnDateSetListener(this);
        datePickerFragment.setEditText(editText);

        // Show the date picker dialog
        assert getFragmentManager() != null;
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Update selectedDate with the selected date
        selectedDate.set(year, month, dayOfMonth);

        // Format the selected date as "dd/MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String selectedDateString = sdf.format(selectedDate.getTime());

        if (selectedEditText != null) {
            selectedEditText.setText(selectedDateString);

            // Check if checkout date is before check-in date
            if (selectedEditText == checkoutDate) {
                Calendar checkOutDateCalendar = Calendar.getInstance();
                checkOutDateCalendar.set(year, month, dayOfMonth);
                if (checkOutDateCalendar.before(selectedDate)) {
                    // Set checkout date to check-in date + 1 day
                    selectedDate.add(Calendar.DAY_OF_MONTH, 1);
                    checkoutDate.setText(sdf.format(selectedDate.getTime()));
                }
            }
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(checkInDate.getWindowToken(), 0);
    }


}