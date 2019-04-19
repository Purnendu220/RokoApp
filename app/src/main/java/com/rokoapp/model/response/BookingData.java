package com.rokoapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingData {

    @SerializedName("id")
    private String id;
    @SerializedName("route_name")
    private String routeName;
    @SerializedName("name")
    private String name;
    @SerializedName("destination_station_name")
    private String destinationStationName;
    @SerializedName("origin_station_name")
    private String originStationName;
    @SerializedName("booking_date_and_time")
    private String bookingDateTime;
    @SerializedName("attended_date_time")
    private String attendedDateTime;
    @SerializedName("route_date_time")
    private String routeDateTime;
    @SerializedName("route_id")
    private String routeId;
    @SerializedName("virtual_route_id")
    private String virtualRouteId;
    @SerializedName("attended")
    @Expose
    private String attended;
    @SerializedName("origin_station_lat")
    @Expose
    private double origin_station_lat;
    @SerializedName("destination_station_long")
    @Expose
    private double destination_station_long;
    @SerializedName("destination_station_lat")
    @Expose
    private double destination_station_lat;
    @SerializedName("origin_station_long")
    @Expose
    private double origin_station_long;
    @SerializedName("destination_station")
    @Expose
    private int destination_station;
    @SerializedName("origin_station")
    @Expose
    private int origin_station;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestinationStationName() {
        return destinationStationName;
    }

    public void setDestinationStationName(String destinationStationName) {
        this.destinationStationName = destinationStationName;
    }

    public String getOriginStationName() {
        return originStationName;
    }

    public void setOriginStationName(String originStationName) {
        this.originStationName = originStationName;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getAttendedDateTime() {
        return attendedDateTime;
    }

    public void setAttendedDateTime(String attendedDateTime) {
        this.attendedDateTime = attendedDateTime;
    }

    public String getRouteDateTime() {
        return routeDateTime;
    }

    public void setRouteDateTime(String routeDateTime) {
        this.routeDateTime = routeDateTime;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getVirtualRouteId() {
        return virtualRouteId;
    }

    public void setVirtualRouteId(String virtualRouteId) {
        this.virtualRouteId = virtualRouteId;
    }

    public String getAttended() {
        return attended;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public double getOrigin_station_lat() {
        return origin_station_lat;
    }

    public void setOrigin_station_lat(double origin_station_lat) {
        this.origin_station_lat = origin_station_lat;
    }

    public double getDestination_station_long() {
        return destination_station_long;
    }

    public void setDestination_station_long(double destination_station_long) {
        this.destination_station_long = destination_station_long;
    }

    public double getDestination_station_lat() {
        return destination_station_lat;
    }

    public void setDestination_station_lat(double destination_station_lat) {
        this.destination_station_lat = destination_station_lat;
    }

    public double getOrigin_station_long() {
        return origin_station_long;
    }

    public void setOrigin_station_long(double origin_station_long) {
        this.origin_station_long = origin_station_long;
    }

    public int getDestination_station() {
        return destination_station;
    }

    public void setDestination_station(int destination_station) {
        this.destination_station = destination_station;
    }

    public int getOrigin_station() {
        return origin_station;
    }

    public void setOrigin_station(int origin_station) {
        this.origin_station = origin_station;
    }
}

