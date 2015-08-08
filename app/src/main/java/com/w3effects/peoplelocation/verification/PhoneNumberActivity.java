package com.w3effects.peoplelocation.verification;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.w3effects.peoplelocation.ApplicationConstants;
import com.w3effects.peoplelocation.R;

import java.io.IOException;

public class PhoneNumberActivity extends AppCompatActivity {

    private EditText mPhoneNumber;
    private EditText mEmailAddress;
    private Button mNextBtn;

    private static final String TAG = PhoneNumberActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        mPhoneNumber = (EditText)findViewById(R.id.phoneNum);
        mEmailAddress = (EditText)findViewById(R.id.emailId);

        mNextBtn = (Button)findViewById(R.id.nextBtn);

        mNextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String phoneNumber = mPhoneNumber.getText().toString().trim();
                String email = mEmailAddress.getText().toString().trim();

                TextInputLayout phnolayout = (TextInputLayout)findViewById(R.id.phonenumContainer);
                TextInputLayout emaillayout = (TextInputLayout)findViewById(R.id.emailIdContainer);

                if(phoneNumber.isEmpty()){
                    phnolayout.setError("Enter Phone Number");
                }else{
                    phnolayout.setError(null);
                }

                if(email.isEmpty()){
                    emaillayout.setError("Enter Email Adrress");
                }else{
                    emaillayout.setError(null);
                }

                if(!phoneNumber.isEmpty() && !email.isEmpty()){
                    OkHttpClient client = new OkHttpClient();
                    String url = ApplicationConstants.URL+"generateotp/"+phoneNumber+"/"+email;
                    Request request = new Request.Builder().url(url).build();
                    Call call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            Log.i(TAG, e.getMessage());

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            Log.i(TAG,response.body().string());
                        }
                    });

                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phone_number, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
