package com.example.arunacholhousing;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.arunacholhousing.libUtils.OkHttpUtils;
import com.example.arunacholhousing.libUtils.Utils;

import org.json.JSONObject;

import java.util.Map;

public class AddRoomFragment extends Fragment {
    private EditText roomTitle,roomType,roomFacilities,totalTravellers,adultTravellers,childTravellers,roomPrice,discountPercent,totalRoom;
    private Button submitBtn;
    public AddRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roomTitle = view.findViewById(R.id.ListRoomTitle);
        roomType = view.findViewById(R.id.roomBookingType);
        roomFacilities = view.findViewById(R.id.roomFacilities);
        totalTravellers = view.findViewById(R.id.totalGuests);
        adultTravellers = view.findViewById(R.id.adultGuests);
        childTravellers = view.findViewById(R.id.chilidGuests);
        roomPrice = view.findViewById(R.id.ListRoomPrice);
        discountPercent = view.findViewById(R.id.discountPercent);
        totalRoom = view.findViewById(R.id.totalRoom);
        submitBtn = view.findViewById(R.id.addRoomBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoomDetails();
            }
        });
    }

    private void addRoomDetails() {
        Map<String, String> roomData = Utils.getAuth(getContext());
        roomData.put("room_title",roomTitle.getText().toString());
        roomData.put("room_type",roomType.getText().toString());
        roomData.put("room_facilities",roomFacilities.getText().toString());
        roomData.put("total_travellers",totalTravellers.getText().toString());
        roomData.put("adult_travellers",adultTravellers.getText().toString());
        roomData.put("child_travellers",childTravellers.getText().toString());
        roomData.put("room_price",roomPrice.getText().toString());
        roomData.put("discount_percent",discountPercent.getText().toString());
        roomData.put("total_room",totalRoom.getText().toString());

        //Log.d("TAG","roomData-" + roomData);
        OkHttpUtils okHttpUtils = new OkHttpUtils();
        String url = Utils.makeURL("api/add_room_details.php");
        okHttpUtils.postRequest(url, roomData, new OkHttpUtils.ResponseCallback() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String msg = response.getString("msg");
                    int err = response.getInt("err");
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.alert(getContext(),msg,"Ok","");
                        }
                    });


                }catch (Exception e){
                    Log.d("TAG","exp" + e.getMessage());
                }
            }

            @Override
            public void onError(String error) {
                Log.d("TAG","errAddRoom: " + error);
            }
        });


    }
}