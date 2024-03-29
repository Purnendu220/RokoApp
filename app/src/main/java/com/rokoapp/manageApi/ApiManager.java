package com.rokoapp.manageApi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rokoapp.listener.BookingListener;
import com.rokoapp.listener.ChecksumListener;
import com.rokoapp.listener.LoginListener;
import com.rokoapp.model.request.ChecksumRequest;
import com.rokoapp.model.request.EditProfileRequest;
import com.rokoapp.model.request.LoginRequest;
import com.rokoapp.model.request.PhoneDetailRequest;
import com.rokoapp.model.request.PostPayment;
import com.rokoapp.model.request.RouteFindRequest;
import com.rokoapp.model.request.SignUpRequest;
import com.rokoapp.model.request.UserBookingRequest;
import com.rokoapp.model.response.BookingModel;
import com.rokoapp.model.response.ChecksumMain;
import com.rokoapp.model.response.ChecksumResponse;
import com.rokoapp.model.response.LoginModel;
import com.rokoapp.model.response.PassModel;
import com.rokoapp.model.response.PassesListModel;
import com.rokoapp.model.response.ReferEarnModel;
import com.rokoapp.model.response.RouteFinderModel;
import com.rokoapp.model.response.RouteStopData;
import com.rokoapp.model.response.TripHistorySubModel;
import com.rokoapp.model.response.TripsHistoryModel;
import com.rokoapp.model.response.UserRouteFinder;
import com.rokoapp.utils.SaveCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.content.ContentValues.TAG;
import static com.rokoapp.utils.Constants.CALLBACK_URL;
import static com.rokoapp.utils.Constants.CHANNEL_ID;
import static com.rokoapp.utils.Constants.INDUSTRY_TYPE_ID;
import static com.rokoapp.utils.Constants.M_ID;
import static com.rokoapp.utils.Constants.M_KEY;
import static com.rokoapp.utils.Constants.WEBSITE;
import static com.rokoapp.utils.ParamUtils.COMPLIMENTARY_PASS;
import static com.rokoapp.utils.ParamUtils.COMPLIMENTARY_PASS_VALIDITY;
import static com.rokoapp.utils.ParamUtils.DOB;
import static com.rokoapp.utils.ParamUtils.EMAIL;
import static com.rokoapp.utils.ParamUtils.EMAIL_USER;
import static com.rokoapp.utils.ParamUtils.GENDER;
import static com.rokoapp.utils.ParamUtils.HOME_ADDRESS;
import static com.rokoapp.utils.ParamUtils.HOME_LAT_LONG;
import static com.rokoapp.utils.ParamUtils.ID_AUTH;
import static com.rokoapp.utils.ParamUtils.ID_USER;
import static com.rokoapp.utils.ParamUtils.MOBILE_NUM;
import static com.rokoapp.utils.ParamUtils.MOBILE_USER;
import static com.rokoapp.utils.ParamUtils.MORNING_HOME_LEAVE;
import static com.rokoapp.utils.ParamUtils.OFFICE_ADDRESS;
import static com.rokoapp.utils.ParamUtils.OFFICE_END_TIME;
import static com.rokoapp.utils.ParamUtils.OFFICE_LAT_LONG;
import static com.rokoapp.utils.ParamUtils.PASS_EARNED;
import static com.rokoapp.utils.ParamUtils.REFER_CODE;
import static com.rokoapp.utils.ParamUtils.TOTAL_PASS;
import static com.rokoapp.utils.ParamUtils.USER_NAME;
import static com.rokoapp.utils.ParamUtils.USER_TYPE;

public class ApiManager {

    /*User Login*/
    public static void getParentLogin(final Context context, String email, String password, final ResponseProgressListener listener, final LoginListener loginListener) {
        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            LoginRequest request = new LoginRequest(email, password,"customer");
            Call<LoginModel> call = api.getUserLogin(request);
            listener.onResponseInProgress();
            final ProgressDialog pd = ProgressDialog.show(context, "", "Signing In ...", true);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                    LoginModel parent = response.body();
                    Headers header = response.headers();
                    String auth = header.get("Roku-Auth-Token");
                    Log.d(TAG, "onResponse: ============================================== Roku-Auth-Token: " + auth);
                    SaveCache.save(context, "Authorization", auth);
                    if (parent != null && parent.getStatus().equals("2XX")) {
                        pd.dismiss();
                        if (parent.getData() != null && parent.getData().getId() != null) {
                            SaveCache.save(context, USER_NAME, parent.getData().getName());
                            loginListener.getUserId(parent.getData().getId());
                            SaveCache.save(context, "UserId", parent.getData().getId());
                            loginListener.getUserName(parent.getData().getName());
                            SaveCache.save(context, MOBILE_NUM, parent.getData().getPhone_no());
                            loginListener.getMobile(parent.getData().getPhone_no());
                            SaveCache.save(context, EMAIL, parent.getData().getEmail());
                            loginListener.getEmail(parent.getData().getEmail());
                            SaveCache.save(context, PASS_EARNED, parent.getData().isEarned());
                            SaveCache.save(context, DOB, parent.getData().getDob());
                            SaveCache.save(context, GENDER, parent.getData().getGender());;
                            SaveCache.save(context, USER_TYPE, parent.getData().getUserType());
                            SaveCache.save(context, HOME_LAT_LONG, parent.getData().getHomeLatLong());
                            SaveCache.save(context, OFFICE_LAT_LONG, parent.getData().getOfficeLatLong());
                            SaveCache.save(context, MORNING_HOME_LEAVE, parent.getData().getHomeLeavingTime());
                            SaveCache.save(context, OFFICE_END_TIME, parent.getData().getOfficeLeavingTime());
                            SaveCache.save(context, HOME_ADDRESS, parent.getData().getHomeAddress());
                            SaveCache.save(context, OFFICE_ADDRESS, parent.getData().getOfficeAddress());
                            SaveCache.save(context, REFER_CODE, parent.getData().getReferAndEarn().getReferAndCode());
                            if (parent.getData().getUserPassDetail() != null) {
                                SaveCache.save(context, COMPLIMENTARY_PASS, parent.getData().getUserPassDetail().getComplimentaryPass());
                                SaveCache.save(context, COMPLIMENTARY_PASS_VALIDITY, parent.getData().getUserPassDetail().getComplimentaryPassValidity());
                                SaveCache.save(context, TOTAL_PASS, parent.getData().getUserPassDetail().getTotalPass());
                            }
                        }
                        listener.onResponseCompleted(1);
                    } else {
                        pd.dismiss();
                        Toast.makeText(context, parent != null ? parent.getData().toString() : "Unable to Login !!!", Toast.LENGTH_LONG).show();
//                        listener.onResponseFailed(FailureCodes.RESPONSE_NULL);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                    pd.dismiss();
                    listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                }
            });
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }

    /*User Registration*/
    public static void getParentSignUp(final Context context, String fullName, String mobile, String email, String password, String gender, String dob, String homeAddName, String officeAddName, String timeLeaveHome, String timeLeaveOffice, String homeLatLong, String officeLatLong, final ResponseProgressListener listener) {
        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            try {
//            Call<LoginModel> call = api.getUserSignUp(fullName, email, password, "customer",gender,"","","","","","", dob);
                SignUpRequest request = new SignUpRequest(fullName, email, password, "customer", gender, dob, mobile, homeAddName, officeAddName, timeLeaveHome, timeLeaveOffice, homeLatLong, officeLatLong);
                Call<LoginModel> call = api.getUserSignUp(request);
                listener.onResponseInProgress();
                final ProgressDialog pd = ProgressDialog.show(context, "", "Signing Up ...", true);
                call.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                        LoginModel parent = response.body();
//                        Toast.makeText(context, response.body().toString(),Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse:====================== " + response.body());
                        if (parent != null && parent.getStatus().equals("2XX")) {
                            pd.dismiss();
                            listener.onResponseCompleted(1);
                        } else {
                            pd.dismiss();
//                        listener.onResponseFailed(FailureCodes.RESPONSE_NULL);
                            Toast.makeText(context, parent != null ? parent.getData().toString() : "Unable to Register !!!", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                        pd.dismiss();
                        listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                    }
                });
//            } catch (JSONException e) {
            } catch (Exception e) {
                e.printStackTrace();
                listener.onResponseFailed(FailureCodes.NETWORK_ERROR);
            }
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }

    /* User Edit Profile*/
    public static void getUserEditProfile(final Context context, String name, String email, String birthday, String gender, String homeName, String homeTime, String homeLatLong, String officeName, String officeTime, String officeLatLong, final ResponseProgressListener listener) {
        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            try {
                EditProfileRequest request = new EditProfileRequest("customer", email, name, homeLatLong, homeTime, officeLatLong, officeTime, homeName, officeName, birthday, gender);
                Call<LoginModel> call = api.getUserEditProfile(ID_AUTH, ID_USER, request);
                listener.onResponseInProgress();
                final ProgressDialog pd = ProgressDialog.show(context, "", "Please Wait ...", true);
                call.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                        LoginModel parent = response.body();
//                        Toast.makeText(context, response.body().toString(),Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse:====================== " + response.body());
                        if (parent != null && parent.getStatus().equals("2XX")) {
                            pd.dismiss();
                            SaveCache.save(context, USER_NAME, parent.getData().getName());
                            SaveCache.save(context, MOBILE_NUM, parent.getData().getPhone_no());
                            SaveCache.save(context, EMAIL, parent.getData().getEmail());
                            SaveCache.save(context, OFFICE_END_TIME, parent.getData().getOfficeLeavingTime());
                            SaveCache.save(context, PASS_EARNED, parent.getData().isEarned());
                            SaveCache.save(context, DOB, parent.getData().getDob());
                            SaveCache.save(context, GENDER, parent.getData().getGender());
                            SaveCache.save(context, OFFICE_ADDRESS, parent.getData().getOfficeAddress());
                            SaveCache.save(context, USER_TYPE, parent.getData().getUserType());
                            SaveCache.save(context, HOME_LAT_LONG, parent.getData().getHomeLatLong());
                            SaveCache.save(context, MORNING_HOME_LEAVE, parent.getData().getHomeLeavingTime());
                            SaveCache.save(context, OFFICE_LAT_LONG, parent.getData().getOfficeLatLong());
                            SaveCache.save(context, HOME_ADDRESS, parent.getData().getHomeAddress());
                            SaveCache.save(context, REFER_CODE, parent.getData().getReferAndEarn().getReferAndCode());
                            listener.onResponseCompleted(1);
                        } else {
                            pd.dismiss();
//                        listener.onResponseFailed(FailureCodes.RESPONSE_NULL);
                            Toast.makeText(context, parent != null ? parent.getData().toString() : "Please Retry !!!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                        pd.dismiss();
                        listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                    }
                });
//            } catch (JSONException e) {
            } catch (Exception e) {
                e.printStackTrace();
                listener.onResponseFailed(FailureCodes.NETWORK_ERROR);
            }
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }

    /*Show Route to User*/
    public static void getRouteForUser(final Context context, double home_lat, double home_long, double office_lat, double office_long, final ResponseProgressListener listener, final List<RouteFinderModel> routeFinderList) {
//        List<RouteFinderModel> routeFinderList = new ArrayList<>();
        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            try {
                RouteFindRequest routeFindRequest = new RouteFindRequest(home_lat + "", home_long + "", office_lat + "", office_long + "");
                Call<UserRouteFinder> call = api.getRouteForUser(ID_AUTH, routeFindRequest);
                listener.onResponseInProgress();
                final ProgressDialog pd = ProgressDialog.show(context, "", "Please Wait ...", true);
                call.enqueue(new Callback<UserRouteFinder>() {
                    @Override
                    public void onResponse(@NonNull Call<UserRouteFinder> call, @NonNull Response<UserRouteFinder> response) {
                        UserRouteFinder parent = response.body();
                        Log.d(TAG, "onResponse:====================== " + response.body());
                        if (parent != null && parent.getStatus_code().equals("2XX")) {
                            pd.dismiss();
                            if (parent.getData() != null && !parent.getData().isEmpty()) {
                                routeFinderList.addAll(parent.getData());
                            }
                            listener.onResponseCompleted(1);
                        } else {
                            pd.dismiss();
//                        listener.onResponseFailed(FailureCodes.RESPONSE_NULL);
                            Toast.makeText(context, parent != null ? parent.getData().toString() : "Route not found!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onResponse: ====================" + parent.getData());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserRouteFinder> call, @NonNull Throwable t) {
                        pd.dismiss();
                        listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                    }
                });
//            } catch (JSONException e) {
            } catch (Exception e) {
                e.printStackTrace();
                listener.onResponseFailed(FailureCodes.NETWORK_ERROR);
            }
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }

    /*Booking Confirmation for User*/
    public static void getBookingOfUser(final Context context, String route, String homeStation, String destinationStation, String bookingTime, TextView errorMessage, final ResponseProgressListener listener, final BookingListener bookingListener) {
        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            try {
                Log.d(TAG, "getBookingOfUser: user id =============================================== " + ID_USER);
                UserBookingRequest bookingRequest = new UserBookingRequest(ID_USER, route, homeStation, destinationStation, bookingTime);
                Call<BookingModel> call = api.getBookingOfUser(ID_AUTH, bookingRequest);
                listener.onResponseInProgress();
                final ProgressDialog pd = ProgressDialog.show(context, "", "Please Wait..", true);
                call.enqueue(new Callback<BookingModel>() {
                    @Override
                    public void onResponse(@NonNull Call<BookingModel> call, @NonNull Response<BookingModel> response) {
                        BookingModel parent = response.body();
                        Log.d(TAG, "onResponse:====================== " + response.body());
                        if (parent != null && parent.getStatus().equals("2XX") /*&& parent.getMessage().equals("success")*/) {
                            pd.dismiss();
                            if (parent.getData() != null) {
                                bookingListener.getBookingData(parent.getData().getBookingDetail());
                                if (parent.getData().getUserData() != null) {
                                    SaveCache.save(context, COMPLIMENTARY_PASS, parent.getData().getUserData().getData().getUserPassDetail().getComplimentaryPass());
                                    SaveCache.save(context, COMPLIMENTARY_PASS_VALIDITY, parent.getData().getUserData().getData().getUserPassDetail().getComplimentaryPassValidity());
                                    SaveCache.save(context, TOTAL_PASS, parent.getData().getUserData().getData().getUserPassDetail().getTotalPass());
                                }
                                Toast.makeText(context, "Booking Confirm from station: " + parent.getData().getBookingDetail().getOriginStationName() + " to Drop Point: " + parent.getData().getBookingDetail().getDestinationStationName(), Toast.LENGTH_LONG).show();
                            }
                            listener.onResponseCompleted(1);
                        } else if (parent != null && parent.getStatus().equals("4XX")) {
                            pd.dismiss();
//                        listener.onResponseFailed(FailureCodes.RESPONSE_NULL);
                            Toast.makeText(context, parent.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        if (response.code()==400){
                            pd.dismiss();
                            listener.onResponseFailed(FailureCodes.RESPONSE_NULL);
                            Gson gson = new GsonBuilder().create();
                            BookingModel mError;
                            try {
                                mError= gson.fromJson(response.errorBody().string(),BookingModel .class);
//                                Toast.makeText(context, mError.getMessage(), Toast.LENGTH_LONG).show();
                                errorMessage.setText(mError.getMessage());
                            } catch (IOException e) {
                                // handle failure to read error
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<BookingModel> call, @NonNull Throwable t) {
                        pd.dismiss();
                        listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                    }
                });
//            } catch (JSONException e) {
            } catch (Exception e) {
                e.printStackTrace();
                listener.onResponseFailed(FailureCodes.NETWORK_ERROR);
            }
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }

    /*PASSES List for User*/
    public static void getPassesData(final Context context, List<PassModel> passList, final ResponseProgressListener listener) {

        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            try {
                Log.d(TAG, "getBookingOfUser: user id =============================================== " + ID_USER);
//                UserBookingRequest bookingRequest = new UserBookingRequest(ID_USER, route, homeStation, destinationStation, bookingTime);
                Call<PassesListModel> call = api.getPassesData();
                listener.onResponseInProgress();
                final ProgressDialog pd = ProgressDialog.show(context, "", "Please Wait...", true);
                call.enqueue(new Callback<PassesListModel>() {
                    @Override
                    public void onResponse(@NonNull Call<PassesListModel> call, @NonNull Response<PassesListModel> response) {
                        PassesListModel parent = response.body();
                        Log.d(TAG, "onResponse:====================== " + response.body());
                        if (parent != null && parent.getStatus_code().equals("2XX") && parent.getMessage().equals("success")) {
                            pd.dismiss();
                            if (parent.getData() != null && !parent.getData().isEmpty()) {
                                passList.addAll(parent.getData());
                            }

                            listener.onResponseCompleted(1);
                        } else {
                            pd.dismiss();
//                        listener.onResponseFailed(FailureCodes.RESPONSE_NULL);
                            Toast.makeText(context, parent != null ? parent.getMessage() : "Booking is not confirmed, Please try again !!!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PassesListModel> call, @NonNull Throwable t) {
                        pd.dismiss();
                        listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                    }
                });
//            } catch (JSONException e) {
            } catch (Exception e) {
                e.printStackTrace();
                listener.onResponseFailed(FailureCodes.NETWORK_ERROR);
            }
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }

    /*Pay Request for Checksum*/
    public static void getChecksumKey(final Context context, String amount, final ResponseProgressListener listener, final ChecksumListener checksumListener) {
        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            try {
                Log.d(TAG, "getChecksumKey: user id =============================================== " + ID_USER);
                ChecksumRequest request = new ChecksumRequest(M_ID, M_KEY, ID_USER + M_ID + System.currentTimeMillis(), amount, ID_USER, INDUSTRY_TYPE_ID, WEBSITE, CHANNEL_ID, CALLBACK_URL, MOBILE_USER, EMAIL_USER);
                Call<ChecksumMain> call = api.getChecksumKey(request);
                listener.onResponseInProgress();
                final ProgressDialog pd = ProgressDialog.show(context, "", "Please Wait..", true);
                call.enqueue(new Callback<ChecksumMain>() {
                    @Override
                    public void onResponse(@NonNull Call<ChecksumMain> call, @NonNull Response<ChecksumMain> response) {
                        ChecksumMain parent = response.body();
                        Log.d(TAG, "onResponse:====================== " + response.body());
                        if (parent != null && parent.getStatus().equals("2XX") && parent.getMessage().equals("success")) {
                            pd.dismiss();
                            ChecksumResponse checksumResponse = parent.getData();
                            checksumListener.getCheckSumObject(checksumResponse);
                            checksumListener.getChecksumHash(parent.getData().getChecksumHash());
                            checksumListener.getTxnAmount(parent.getData().getTxnAmount());
                            listener.onResponseCompleted(1);
                        } else {
                            pd.dismiss();
//                        listener.onResponseFailed(FailureCodes.RESPONSE_NULL);
                            Toast.makeText(context, parent != null ? parent.getMessage() : "Booking is not confirmed, Please try again !!!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ChecksumMain> call, @NonNull Throwable t) {
                        pd.dismiss();
                        listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                    }
                });
//            } catch (JSONException e) {
            } catch (Exception e) {
                e.printStackTrace();
                listener.onResponseFailed(FailureCodes.NETWORK_ERROR);
            }
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }

    /*GET DEVICE SPECIFICATIONS DETAILS*/
    public static void getDeviceDetail(final Context context, String androidToken, String fireBaseId, final ResponseProgressListener listener) {
        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            try {
                Log.d(TAG, "getChecksumKey: user id =============================================== " + ID_USER);
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                String versionName = packageInfo.versionName;
                /*String android_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);*/
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(context, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{READ_PHONE_STATE}, 100);
                }else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{READ_PHONE_STATE}, 100);
                }
                String android_id = telephonyManager.getDeviceId();
//                Toast.makeText(context, android_id, Toast.LENGTH_LONG).show();
                PhoneDetailRequest request = new PhoneDetailRequest(ID_USER, androidToken,"android",  androidToken, System.getProperty("os.version"), versionName, Build.MODEL, ID_AUTH, android_id);
                Call<ReferEarnModel> call = api.getDeviceDetail(ID_AUTH, request);
                listener.onResponseInProgress();
                final ProgressDialog pd = ProgressDialog.show(context, "", "Please Wait..", true);
                call.enqueue(new Callback<ReferEarnModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ReferEarnModel> call, @NonNull Response<ReferEarnModel> response) {
                        ReferEarnModel parent = response.body();
                        Log.d(TAG, "onResponse:====================== "+response.body());
                        if (parent != null && parent.getStatus().equals("2XX") && parent.getMessage().equals("success")) {
                            pd.dismiss();
                            listener.onResponseCompleted(2);
                        } else {
                            pd.dismiss();
//                            Toast.makeText(context, parent != null ? parent.getMessage() : "Please try again !!!", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ReferEarnModel> call, @NonNull Throwable t) {
                        pd.dismiss();
                        listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                listener.onResponseFailed(FailureCodes.NETWORK_ERROR);
            }
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }

    /*GET USER BOOKING HISTORY*/
    public static void getUserBookingHistory(final Context context, List<TripHistorySubModel> tripLists, final String historyType, final ResponseProgressListener listener) {
        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            Call<TripsHistoryModel> call = api.getUserBookingHistory(ID_AUTH, ID_USER, historyType);
            listener.onResponseInProgress();
            final ProgressDialog pd = ProgressDialog.show(context, "", "Please Wait..", true);
            call.enqueue(new Callback<TripsHistoryModel>() {
                @Override
                public void onResponse(@NonNull Call<TripsHistoryModel> call, @NonNull Response<TripsHistoryModel> response) {
                    TripsHistoryModel parent = response.body();
                    Log.d(TAG, "onResponse:====================== "+response.body());
                    if (parent != null && parent.getStatus().equals("2XX") && parent.getMessage().equals("success")) {
                        pd.dismiss();
                        if (parent.getData() != null && !parent.getData().isEmpty()){
                            tripLists.addAll(parent.getData());
                        }
                        listener.onResponseCompleted(1);
                    } else {
                        pd.dismiss();
                        Toast.makeText(context, parent != null ? parent.getMessage() : "Please try again !!!", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<TripsHistoryModel> call, @NonNull Throwable t) {
                    pd.dismiss();
                    listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                }
            });
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }

    /*GET USER BOOKING HISTORY*/
    public static void getRouteStopList(final Context context,final String virtualRouteID,final ResponseProgressListener listener) {
        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            Call<RouteStopData> call = api.getRouteStopLIst(ID_AUTH, virtualRouteID);
            listener.onResponseInProgress();
            final ProgressDialog pd = ProgressDialog.show(context, "", "Please Wait..", true);
            call.enqueue(new Callback<RouteStopData>() {
                @Override
                public void onResponse(@NonNull Call<RouteStopData> call, @NonNull Response<RouteStopData> response) {
                    RouteStopData parent = response.body();
                    Log.d(TAG, "onResponse:====================== "+response.body());
                    if (parent != null && parent.getStatus().equals("2XX") && parent.getMessage().equals("success")) {
                        pd.dismiss();
                        if (parent.getData() != null && !parent.getData().isEmpty()){
                            listener.onResponseCompletedWithData(1,parent);

                        }
                        else{
                            Toast.makeText(context, "No Data Found", Toast.LENGTH_LONG).show();
                            listener.onResponseCompleted(1);

                        }
                    } else {
                        pd.dismiss();
                        Toast.makeText(context, parent != null ? parent.getMessage() : "Please try again !!!", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<RouteStopData> call, @NonNull Throwable t) {
                    pd.dismiss();
                    listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                }
            });
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }


    /*POST PAYMENT API*/
    public static void getPostPayStatus(final Context context, final String passId, final String paymentStatus, final String orderId, final int amount, final ResponseProgressListener listener) {
        if (InternetConnectionCheck.haveNetworkConnection(context)) {
            ApiClient api = RestManager.getInstance();
            PostPayment postPayment = new PostPayment(ID_USER, passId, paymentStatus, ID_USER + System.currentTimeMillis(), amount);
            Call<ReferEarnModel> call = api.getPostPayStatus(ID_AUTH, postPayment);
            listener.onResponseInProgress();
            final ProgressDialog pd = ProgressDialog.show(context, "", "Please Wait..", true);
            call.enqueue(new Callback<ReferEarnModel>() {
                @Override
                public void onResponse(@NonNull Call<ReferEarnModel> call, @NonNull Response<ReferEarnModel> response) {
                    ReferEarnModel parent = response.body();
                    Log.d(TAG, "onResponse:====================== "+response.body());
                    if (parent != null && parent.getStatus().equals("2XX") && parent.getMessage().equals("success")) {
                        pd.dismiss();
                        Toast.makeText(context, parent.getData(), Toast.LENGTH_LONG).show();
                        listener.onResponseCompleted(1);
                    } else {
                        pd.dismiss();
                        Toast.makeText(context, parent != null ? parent.getData() : "Payment Failed !!!", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ReferEarnModel> call, @NonNull Throwable t) {
                    pd.dismiss();
                    listener.onResponseFailed(FailureCodes.SERVER_ERROR);
                }
            });
        } else {
            listener.onResponseFailed(FailureCodes.NO_INTERNET);
        }
    }
}