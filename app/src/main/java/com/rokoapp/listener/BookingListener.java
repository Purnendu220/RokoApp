package com.rokoapp.listener;

import com.rokoapp.model.response.BookingData;
import com.rokoapp.model.response.TripHistorySubModel;

import java.util.List;

public interface BookingListener {
    void getBookingData(TripHistorySubModel data);
}
