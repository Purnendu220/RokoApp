package com.rokoapp.model.response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class TripDetailModel{
  @SerializedName("status_code")
  @Expose
  private String status_code;
  @SerializedName("data")
  @Expose
  private List<Data> data;
  @SerializedName("message")
  @Expose
  private String message;
  public void setStatus_code(String status_code){
   this.status_code=status_code;
  }
  public String getStatus_code(){
   return status_code;
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
}