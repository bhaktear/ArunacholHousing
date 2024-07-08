package com.example.arunacholhousing.libUtils;

public class RoomDetailsItem {
    private String code;
    private String roomTitle,roomPrice,totalRoom,bookedRoom,availableRoom;

    public RoomDetailsItem(String code, String roomTitle, String roomPrice, String totalRoom, String bookedRoom, String availableRoom){
        this.code = code;
        this.roomTitle = roomTitle;
        this.roomPrice = roomPrice;
        this.totalRoom = totalRoom;
        this.bookedRoom = bookedRoom;
        this.availableRoom = availableRoom;
    }

    public String getCode() {
        return code;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public String getTotalRoom() {
        return totalRoom;
    }

    public String getBookedRoom() {
        return bookedRoom;
    }

    public String getAvailableRoom() {
        return availableRoom;
    }
}
