package com.rokoapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.rokoapp.R;
import com.rokoapp.manageApi.ApiManager;
import com.rokoapp.manageApi.FailureCodes;
import com.rokoapp.manageApi.ResponseProgressListener;

import org.json.JSONObject;

import static com.rokoapp.utils.ParamUtils.EMAIL_USER;

public class PaymentActivity extends Activity implements PaymentResultListener, ResponseProgressListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();

    private int cost;
    private String id, passName, description, imageUrl, noOfRides, complimentaryRide, validityOfComplimentaryRide, status;
    private ImageView passImage;
    private TextView namePass, passDesc, passesCount, costOfPasses, validity;
    private Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setUpActionBar();
        setContentView(R.layout.activity_payment);

        initViews();

        Intent i = getIntent();
        id = i.getStringExtra("id");
        passName = i.getStringExtra("name");
        description = i.getStringExtra("description");
        imageUrl = i.getStringExtra("image_url");
        noOfRides = i.getStringExtra("no_of_rides");
        complimentaryRide = i.getStringExtra("complimentry_ride");
        validityOfComplimentaryRide = i.getStringExtra("validity_of_complimentry_ride");
        cost = i.getIntExtra("cost",0);
        status = i.getStringExtra("status");

        showData();

        Checkout.preload(getApplicationContext());

        buyButton.setOnClickListener(v ->
            startPayment());

    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", passName);
            options.put("description", description);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", R.drawable.roko);
            options.put("currency", "INR");
            options.put("amount", cost*100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", EMAIL_USER);
            preFill.put("contact", "8377052474");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
//            Toast.makeText(this, "Payment Successful:============================================ " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Payment Successful:============================================ " + razorpayPaymentID );
            ApiManager.getPostPayStatus(PaymentActivity.this, id, "success", razorpayPaymentID, cost, PaymentActivity.this);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @Override
    public void onPaymentError(int code, String response) {
        try {
//            Toast.makeText(this, "Payment failed:" + code + " " + response, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Payment failed: ===============================================" + code + " " + response );
            ApiManager.getPostPayStatus(PaymentActivity.this, id, response, "", cost, PaymentActivity.this);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    private void showData() {
        namePass.setText(passName);
        passDesc.setText(description);

//        String cost = String.valueOf(passModel.getCost()).split("\\.")[0];
        costOfPasses.setText("In just Rs. "+cost+" only");
        passesCount.setText(noOfRides+" Rides "+"+ "+complimentaryRide+" Rides Extra");

        /*if (imageUrl !=null){
            Glide.with(PaymentActivity.this).load(imageUrl).into(passImage);
        }*/

        if (validityOfComplimentaryRide != null){
            validity.setText("*Extra rides valid upto "+validityOfComplimentaryRide+" days, till the buy date.");
        }
    }

    private void setUpActionBar() {
//        android.support.v7.app.ActionBar actionBar = getActionBar();
        getActionBar().setTitle("Buy Pass");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        passImage = findViewById(R.id.passImage);
        namePass = findViewById(R.id.namePass);
        passDesc = findViewById(R.id.passDesc);
        passesCount = findViewById(R.id.passesCount);
        costOfPasses = findViewById(R.id.costOfPasses);
        validity = findViewById(R.id.validity);
        buyButton = findViewById(R.id.buyButton);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(MyPass.this, Home.class));
        startActivity(new Intent(PaymentActivity.this, PassesList.class));
        PaymentActivity.this.finish();
    }

    @Override
    public void onResponseInProgress() {

    }

    @Override
    public void onResponseCompleted(int i) {

    }

    @Override
    public void onResponseCompletedWithData(int i, Object data) {

    }

    @Override
    public void onResponseFailed(FailureCodes code) {

    }
}
