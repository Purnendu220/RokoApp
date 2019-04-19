package com.rokoapp.model.response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Data{
  @SerializedName("attended")
  @Expose
  private Boolean attended;
  @SerializedName("attended_date_time")
  @Expose
  private Object attended_date_time;
  @SerializedName("route_id")
  @Expose
  private Integer route_id;
  @SerializedName("origin_station_lat")
  @Expose
  private Double origin_station_lat;
  @SerializedName("route_name")
  @Expose
  private String route_name;
  @SerializedName("destination_station_long")
  @Expose
  private Double destination_station_long;
  @SerializedName("destination_station_lat")
  @Expose
  private Double destination_station_lat;
  @SerializedName("virtual_route_id")
  @Expose
  private Integer virtual_route_id;
  @SerializedName("route_date_time")
  @Expose
  private String route_date_time;
  @SerializedName("origin_station_long")
  @Expose
  private Double origin_station_long;
  @SerializedName("booking_date_and_time")
  @Expose
  private String booking_date_and_time;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("destination_station_name")
  @Expose
  private String destination_station_name;
  @SerializedName("destination_station")
  @Expose
  private Integer destination_station;
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("origin_station_name")
  @Expose
  private String origin_station_name;
  @SerializedName("origin_station")
  @Expose
  private Integer origin_station;
  public void setAttended(Boolean attended){
   this.attended=attended;
  }
  public Boolean getAttended(){
   return attended;
  }
  public void setAttended_date_time(Object attended_date_time){
   this.attended_date_time=attended_date_time;
  }
  public Object getAttended_date_time(){
   return attended_date_time;
  }
  public void setRoute_id(Integer route_id){
   this.route_id=route_id;
  }
  public Integer getRoute_id(){
   return route_id;
  }
  public void setOrigin_station_lat(Double origin_station_lat){
   this.origin_station_lat=origin_station_lat;
  }
  public Double getOrigin_station_lat(){
   return origin_station_lat;
  }
  public void setRoute_name(String route_name){
   this.route_name=route_name;
  }
  public String getRoute_name(){
   return route_name;
  }
  public void setDestination_station_long(Double destination_station_long){
   this.destination_station_long=destination_station_long;
  }
  public Double getDestination_station_long(){
   return destination_station_long;
  }
  public void setDestination_station_lat(Double destination_station_lat){
   this.destination_station_lat=destination_station_lat;
  }
  public Double getDestination_station_lat(){
   return destination_station_lat;
  }
  public void setVirtual_route_id(Integer virtual_route_id){
   this.virtual_route_id=virtual_route_id;
  }
  public Integer getVirtual_route_id(){
   return virtual_route_id;
  }
  public void setRoute_date_time(String route_date_time){
   this.route_date_time=route_date_time;
  }
  public String getRoute_date_time(){
   return route_date_time;
  }
  public void setOrigin_station_long(Double origin_station_long){
   this.origin_station_long=origin_station_long;
  }
  public Double getOrigin_station_long(){
   return origin_station_long;
  }
  public void setBooking_date_and_time(String booking_date_and_time){
   this.booking_date_and_time=booking_date_and_time;
  }
  public String getBooking_date_and_time(){
   return booking_date_and_time;
  }
  public void setName(String name){
   this.name=name;
  }
  public String getName(){
   return name;
  }
  public void setDestination_station_name(String destination_station_name){
   this.destination_station_name=destination_station_name;
  }
  public String getDestination_station_name(){
   return destination_station_name;
  }
  public void setDestination_station(Integer destination_station){
   this.destination_station=destination_station;
  }
  public Integer getDestination_station(){
   return destination_station;
  }
  public void setId(Integer id){
   this.id=id;
  }
  public Integer getId(){
   return id;
  }
  public void setOrigin_station_name(String origin_station_name){
   this.origin_station_name=origin_station_name;
  }
  public String getOrigin_station_name(){
   return origin_station_name;
  }
  public void setOrigin_station(Integer origin_station){
   this.origin_station=origin_station;
  }
  public Integer getOrigin_station(){
   return origin_station;
  }
}