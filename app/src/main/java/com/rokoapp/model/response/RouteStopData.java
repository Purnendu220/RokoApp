package com.rokoapp.model.response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class RouteStopData{
  @SerializedName("status_code")
  @Expose
  private String statusCode;
  @SerializedName("data")
  @Expose
  private List<Data> data;
  @SerializedName("message")
  @Expose
  private String message;
  public void setStatus(String statusCode){
   this.statusCode=statusCode;
  }
  public String getStatus(){
   return statusCode;
  }
  public void setData(List<Data> data){
   this.data=data;
  }
  public List<Data> getData(){
   return data;
  }
  public void setMessage(String message){
   this.message=message;
  }
  public String getMessage(){
   return message;
  }

 public class Data{
     public Data(Double latitude, Integer id, Integer bus_id, String bus_name, Double longitude) {
         this.latitude = latitude;
         this.id = id;
         this.bus_id = bus_id;
         this.bus_name = bus_name;
         this.longitude = longitude;
     }

     @SerializedName("latitude")
     @Expose
     private Double latitude;
     @SerializedName("id")
     @Expose
     private Integer id;
     @SerializedName("bus_id")
     @Expose
     private Integer bus_id;
     @SerializedName("bus_name")
     @Expose
     private String bus_name;
     @SerializedName("longitude")
     @Expose
     private Double longitude;
     public void setLatitude(Double latitude){
         this.latitude=latitude;
     }
     public Double getLatitude(){
         return latitude;
     }
     public void setId(Integer id){
         this.id=id;
     }
     public Integer getId(){
         return id;
     }
     public void setBus_id(Integer bus_id){
         this.bus_id=bus_id;
     }
     public Integer getBus_id(){
         return bus_id;
     }
     public void setBus_name(String bus_name){
         this.bus_name=bus_name;
     }
     public String getBus_name(){
         return bus_name;
     }
     public void setLongitude(Double longitude){
         this.longitude=longitude;
     }
     public Double getLongitude(){
         return longitude;
     }
 }
}