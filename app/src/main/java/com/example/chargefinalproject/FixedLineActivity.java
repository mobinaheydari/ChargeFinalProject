package com.example.chargefinalproject;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FixedLineActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSONType = MediaType.parse("application/json; charset=utf-8");
    public String url = "https://charge.sep.ir/Inquiry/FixedLineBillInquiry";

    EditText phoneNumber;
    Button submit;
    TextView billID;
    TextView finalTermPaymentID;
    TextView midTermPaymentID;
    TextView finalTermAmount;
    TextView midTermAmount;
    CardView cv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_line);
        phoneNumber = (EditText) findViewById(R.id.editTextPhone);
        submit = (Button) findViewById(R.id.button_submit);
        cv = (CardView) findViewById(R.id.CardViewFixedLine);
        billID = (TextView) findViewById(R.id.textView_bill_id);
        finalTermPaymentID = (TextView) findViewById(R.id.textView_final_term_id);
        midTermPaymentID = (TextView) findViewById(R.id.textView_mid_term_id);
        finalTermAmount = (TextView) findViewById(R.id.textView_final_term_amount);
        midTermAmount = (TextView) findViewById(R.id.textView_mid_term_amount);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String phoneHeader = "{\n" + "   \"FixedLineNumber\": \" " + phoneNumber.getText().toString() + "\"\n" + "}";
                RequestBody requestBody = RequestBody.create(JSONType, phoneHeader);
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        call.cancel();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    assert response.body() != null;
                                    String jsonData = response.body().string();
                                    JSONObject object = new JSONObject(jsonData);
                                    if (object.getString("code").equals("-16")) {
                                        Toast.makeText(FixedLineActivity.this, object.getString("errorMessage"), Toast.LENGTH_LONG).show();
                                    } else {
                                        cv.setVisibility(View.VISIBLE);
                                        billID.setText(object.getJSONObject("data").getJSONObject("FinalTerm").getString("BillID"));
                                        finalTermPaymentID.setText(object.getJSONObject("data").getJSONObject("FinalTerm").getString("PaymentID"));
                                        midTermPaymentID.setText(object.getJSONObject("data").getJSONObject("MidTerm").getString("PaymentID"));
                                        finalTermAmount.setText(object.getJSONObject("data").getJSONObject("FinalTerm").getString("Amount"));
                                        midTermAmount.setText(object.getJSONObject("data").getJSONObject("MidTerm").getString("Amount"));

                                    }
                                } catch (JSONException | IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        });
                    }
                });

            }
        });


    }


}


