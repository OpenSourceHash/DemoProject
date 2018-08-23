package sun.OpenSource.opendata.opendataexample;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.opensource.OpenListeners.OpenDataCallbacks;
import com.opensource.OpendataApp;
import com.opensource.OpenUtility.OpenDisplay;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OpenDataCallbacks {

    public static String advertiserId;
    String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code
                try {

                    AdvertisingIdClient.Info id = AdvertisingIdClient.getAdvertisingIdInfo(MainActivity.this.getApplicationContext());
                    if (id != null)
                        advertiserId = (id.getId());
                    else if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this) == ConnectionResult.SUCCESS) {
                        id = AdvertisingIdClient.getAdvertisingIdInfo(MainActivity.this.getApplicationContext());
                        if (id != null)
                            advertiserId = (id.getId());
                    }
                    if (advertiserId != null && !advertiserId.equalsIgnoreCase("")) {
                        OpenDisplay.DisplayLogD("RSA", " GOOGLE AD ID : " + advertiserId);
                        isEmailPermissionGranted();
                        if(isEmailPermissionGranted()) {
//                            OpendataApp.initialize(MainActivity.this, "8eksm3jhf0sdfkja", getEmail_AddressArray(MainActivity.this).get(0), advertiserId);
                            OpendataApp.initialize(MainActivity.this, "A4bHVKUR7HSMQl4GE88ANRRJWLtdKWW2", getEmail_AddressArray(MainActivity.this).get(0), advertiserId);
                        }

                    }
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
//                    Log.e(TAG,"GooglePlayServicesNotAvailableException"+((e != null) ? e.getMessage() + "" : ""));
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
//                    Log.e(TAG,"GooglePlayServicesRepairableException"+((e != null) ? e.getMessage() + "" : ""));
                } catch (IOException e) {
                    e.printStackTrace();
//                    Log.e(TAG,"IOException"+((e != null) ? e.getMessage() + "" : ""));
                } catch (Exception e) {
//                    Log.e(TAG,"Exception"+((e != null) ? e.getMessage() + "" : ""));
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * This method is to Get user emailId Array
     *
     * @param context
     * @return List of emails
     */
    public final ArrayList<String> getEmail_AddressArray(Context context) {
        String possibleEmail = "";
        OpenDisplay.DisplayLogII("RSA", "emailArray1 ");
        ArrayList<String> emailArray = new ArrayList();
        try {

            Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
            if (accounts != null && accounts.length > 0) {
                for (int i = 0; i < accounts.length; i++) {
                    Account account = accounts[i];
                    if (account != null) {
                        emailArray.add(account.name);
                        possibleEmail = account.name;
                    }
                }

            }
        } catch (ArrayIndexOutOfBoundsException ex1) {
            Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
            if (accounts != null && accounts.length > 0) {
                for (int i = 0; i < accounts.length; i++) {
                    Account account = accounts[i];
                    if (account != null) {
                        emailArray.add(account.name);
                        possibleEmail = account.name;
                    }
                }

            }
//            ex1.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        OpenDisplay.DisplayLogII("RSA", "emailArray " + emailArray.toString());
//        session.setEmailID(emailArray.toString());
        return emailArray;


    }

    public  boolean isEmailPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT <= 25) {
            if (checkSelfPermission(Manifest.permission.GET_ACCOUNTS)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, 1);
                return false;
            }
        } else if (Build.VERSION.SDK_INT >= 26) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission


//            if(isEmailPermissionGranted()) {
            if(getEmail_AddressArray(MainActivity.this).size() > 0) {
//                OpendataApp.initialize(MainActivity.this, "8eksm3jhf0sdfkja", getEmail_AddressArray(MainActivity.this).get(0), advertiserId);
                OpendataApp.initialize(MainActivity.this, "A4bHVKUR7HSMQl4GE88ANRRJWLtdKWW2", getEmail_AddressArray(MainActivity.this).get(0), advertiserId);
            }
            else{
                OpenDisplay.DisplayToast(MainActivity.this,"No emails in this device");
            }
//            }
        }else{
            Log.v(TAG,"Permission not given");
        }
    }

    @Override
    public void onDataSuccess(String message, String data) {
        Log.i("onDataSuccess",""+data);

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onDataFailure(String message) {
        Log.i("onDataFailure",""+message);
        Toast.makeText(this,message.toString(),2000).show();
    }
}
