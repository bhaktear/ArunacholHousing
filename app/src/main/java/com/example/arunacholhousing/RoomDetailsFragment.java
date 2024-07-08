package com.example.arunacholhousing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.arunacholhousing.libUtils.OkHttpUtils;
import com.example.arunacholhousing.libUtils.RoomDetailsItem;
import com.example.arunacholhousing.libUtils.RoomDetailsListAdapter;
import com.example.arunacholhousing.libUtils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomDetailsFragment extends Fragment {
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.roomDetailsData);
        roomDetails();
    }

    private void roomDetails() {
        Map<String, String> authData = Utils.getAuth(requireContext());
        String url = Utils.makeURL("api/room_details.php");
        OkHttpUtils okHttpUtils = new OkHttpUtils();
        okHttpUtils.postRequest(url, authData, new OkHttpUtils.ResponseCallback() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    int err = response.getInt("err");
                    String msg = response.getString("msg");
                    if(err == 1){
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.alert(requireContext(),msg,"OK","");
                            }
                        });
                    }
                    JSONArray dataArray = response.getJSONArray("data");
                    List<RoomDetailsItem> roomDetailsItemList = new ArrayList<>();

                    for(int i=0; i< dataArray.length(); i++){
                        JSONObject roomData = dataArray.getJSONObject(i);
                        String code = roomData.getString("code");
                        String roomTitle = roomData.getString("room_title");
                        String roomPrice = roomData.getString("room_price");
                        String totalRoom = roomData.getString("total_room");
                        String bookedRoom = roomData.getString("booked_room");
                        String availableRoom = roomData.getString("available_room");
                        RoomDetailsItem roomDetailsItem = new RoomDetailsItem(code,roomTitle,roomPrice,totalRoom,bookedRoom,availableRoom);
                        roomDetailsItemList.add(roomDetailsItem);
                    }
                    RoomDetailsListAdapter adapter = new RoomDetailsListAdapter(requireContext(),roomDetailsItemList);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(adapter);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }


}