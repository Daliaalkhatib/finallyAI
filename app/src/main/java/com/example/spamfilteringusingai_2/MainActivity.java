   package com.example.spamfilteringusingai_2;

   import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;


   import java.io.BufferedReader;
   import java.io.IOException;
   import java.io.InputStream;
   import java.io.InputStreamReader;
   import java.nio.charset.Charset;
   import java.util.ArrayList;
   import java.util.Arrays;
   import java.util.List;

   public class MainActivity extends AppCompatActivity {
       private static final int RC_SIGN_IN = 9001;
       private  static final int REQUEST_CODE_TOKEN_AUTH=1337;
       public static GoogleSignInClient mGoogleSignInClient;
       GoogleSignInAccount account;
       GoogleAuthUtil mAuth;
       Context mContext = this;
       public static String accountemail;
       Gmail mService;
       HttpTransport transport;
       JsonFactory jsonFactory;
       public static  GoogleAccountCredential mCredential;
       private ConnectionResult mConnectionResult;
       static String[] SCOPES = {
               GmailScopes.MAIL_GOOGLE_COM
       };
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
           GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                   .requestScopes(
                   new Scope(GmailScopes.MAIL_GOOGLE_COM))
                   .requestIdToken("828589037396-h6ph26hdfr3mgckoe951v0fobsbuerre.apps.googleusercontent.com")
                   .requestEmail()
                   .build();
           mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    public void onLoginButtonPressed(View view) throws IOException {
           signIn();
    }
       private final List<WeatherSample> WeatherSamples = new ArrayList<>();

       private void readWeatherData() {
           InputStream is = getResources().openRawResource(R.raw .spam_or_not_spam);
           BufferedReader reader =new BufferedReader( new InputStreamReader(is, Charset.forName("UTF_8")));

           String line="";

           try {
               reader.readLine();
               while ((line = reader.readLine()) != null) {
                   Log.d("MyActivity","line:" + line);
                   String [] tokens =line.split(",");
                   WeatherSample sample =new WeatherSample();
                   sample.setEmail((tokens[0]));
                   if(tokens[1].length() > 0){
                       sample.setLabel(Integer.parseInt(tokens[1]));}
                   else {
                       sample.setLabel(0);
                   }

                   WeatherSamples.add(sample);

                   Log.d("MYActivity","just created"+sample);



               }

           } catch (IOException e) {
               Log.wtf("MyActivity" ,"Error reading data file on line "+ line ,e);
               e.printStackTrace();
           }

       }



       private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, Move To Main Screen and Save All Information about Google Account
            accountemail = account.getEmail();
            mCredential = GoogleAccountCredential.usingOAuth2(
                    getApplicationContext(), Arrays.asList(SCOPES))
                    .setBackOff(new ExponentialBackOff());
            mCredential.setSelectedAccount(account.getAccount());

            Intent moveToMainSecreen=new Intent
                    (MainActivity.this,MainActivity6.class);
            startActivity(moveToMainSecreen);

        } catch (ApiException e) {
            /* When The Sign in Failed Message will be show to user
            *  To inform him
            * */
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(mContext, "SignInFailed", Toast.LENGTH_SHORT).show();
            Log.w("info", "signInResult:failed code=" + e.getStatusCode());
        }
    }

}
