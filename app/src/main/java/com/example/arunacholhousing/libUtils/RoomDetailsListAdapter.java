package com.example.arunacholhousing.libUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.arunacholhousing.R;

import java.util.List;

public class RoomDetailsListAdapter extends ArrayAdapter<RoomDetailsItem> {
    RoomDetailsItem roomDetailsItem;
    TextView roomID,roomTitle,roomPrice,totalRoom,bookedRoom,availableRoom;
    public RoomDetailsListAdapter(Context context, List<RoomDetailsItem> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_details_item,parent,false);
        }
        roomDetailsItem = getItem(position);
        roomID = convertView.findViewById(R.id.ListRoomID);
        roomTitle = convertView.findViewById(R.id.ListRoomTitle);
        roomPrice = convertView.findViewById(R.id.ListRoomPrice);
        totalRoom = convertView.findViewById(R.id.ListTotalRoom);
        availableRoom = convertView.findViewById(R.id.ListAvailableRoom);
        bookedRoom = convertView.findViewById(R.id.ListBookedRoom);

        roomID.setText("Id: " + roomDetailsItem.getCode());
        roomTitle.setText("Room Title: " + roomDetailsItem.getRoomTitle());
        roomPrice.setText("Price: " + roomDetailsItem.getRoomPrice());
        totalRoom.setText("Total Room: " + roomDetailsItem.getTotalRoom());
        bookedRoom.setText("Booked Room: " + roomDetailsItem.getBookedRoom());
        availableRoom.setText("Available Room: " + roomDetailsItem.getAvailableRoom());
        return convertView;
    }
}
