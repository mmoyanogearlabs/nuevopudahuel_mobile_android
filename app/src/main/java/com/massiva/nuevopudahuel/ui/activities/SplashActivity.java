package com.massiva.nuevopudahuel.ui.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.util.Log;

import com.bzutils.BZUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.constants.PudahuelPrefs;
import com.massiva.nuevopudahuel.controllers.UserController;
import com.massiva.nuevopudahuel.push.GcmRegistrationIntentService;
import com.massiva.nuevopudahuel.ui.activities.AlertActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST_POST_NOTIFICATIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logIntentDetails("onCreate", getIntent());

        requestFCMToken();

        if (Build.VERSION.SDK_INT >= 33) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_POST_NOTIFICATIONS);
            } else {
                initApp(true);
            }
        } else {
            initApp(true);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void configView() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("FCM_DEBUG", "POST_NOTIFICATIONS permission granted");
            } else {
                Log.w("FCM_DEBUG", "POST_NOTIFICATIONS permission denied");
            }
            initApp(true);
        } else if (requestCode == 11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initApp(true);
            } else {
                BZUtils.showSimpleMessage(this, getString(R.string.permissionDeniedLocation));
                initApp(false);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        logIntentDetails("onNewIntent", intent);
        initApp(true);
    }

    private boolean isValidFlightIdentifier(String val) {
        if (val == null) return false;
        String str = val.trim();
        if (str.isEmpty() || str.equalsIgnoreCase("null") || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
            return false;
        }
        if (str.equals("0") || str.equals("1") || str.startsWith("google.") || str.startsWith("gcm.")) {
            return false;
        }
        return true;
    }

    private String extractFlightId(Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return null;
        }
        Bundle extras = intent.getExtras();
        for (String key : extras.keySet()) {
            Log.d("FCM_DEBUG", "Intent extra -> " + key + " = " + extras.get(key));
        }

        // Priority 1: Specific flight ID & flight code keys
        String[] priorityKeys = new String[]{
                AlertActivity.EXTRA_FLIGHT,
                "flight_id", "flightId", "npg_id", "npgId",
                "flight_code", "flightCode", "flight_number", "flightNumber"
        };
        for (String key : priorityKeys) {
            if (extras.containsKey(key)) {
                Object val = extras.get(key);
                if (val != null) {
                    String strVal = String.valueOf(val).trim();
                    if (isValidFlightIdentifier(strVal)) {
                        Log.d("FCM_DEBUG", "Extracted flightId from priority key '" + key + "': " + strVal);
                        return strVal;
                    }
                }
            }
        }

        // Priority 2: JSON string payloads
        for (String key : extras.keySet()) {
            Object val = extras.get(key);
            if (val instanceof String) {
                String strVal = ((String) val).trim();
                if (strVal.startsWith("{") && strVal.endsWith("}")) {
                    try {
                        JSONObject json = new JSONObject(strVal);
                        for (String pKey : priorityKeys) {
                            if (json.has(pKey)) {
                                String jsonVal = json.optString(pKey, "").trim();
                                if (isValidFlightIdentifier(jsonVal)) {
                                    Log.d("FCM_DEBUG", "Extracted flightId from JSON extra '" + key + "." + pKey + "': " + jsonVal);
                                    return jsonVal;
                                }
                            }
                        }
                    } catch (JSONException ignored) {
                    }
                }
            }
        }

        // Priority 3: Notify text payloads (title/body/message) when the server did not send flight_id explicitly
        String[] textKeys = new String[]{"title", "body", "message", "text"};
        for (String key : textKeys) {
            if (extras.containsKey(key)) {
                Object val = extras.get(key);
                if (val != null) {
                    String strVal = String.valueOf(val).trim();
                    String flightCode = parseFlightCodeFromText(strVal);
                    if (flightCode != null) {
                        Log.d("FCM_DEBUG", "Extracted flightId from text key '" + key + "': " + flightCode);
                        return flightCode;
                    }
                }
            }
        }

        // Priority 4: Secondary generic keys
        String[] secondaryKeys = new String[]{"vuelo", "npg", "flight", "id", "code"};
        for (String key : secondaryKeys) {
            if (extras.containsKey(key)) {
                Object val = extras.get(key);
                if (val != null) {
                    String strVal = String.valueOf(val).trim();
                    if (isValidFlightIdentifier(strVal)) {
                        Log.d("FCM_DEBUG", "Extracted flightId from secondary key '" + key + "': " + strVal);
                        return strVal;
                    }
                }
            }
        }

        return null;
    }

    private String parseFlightCodeFromText(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        try {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\b([A-Za-z]{1,2}|[A-Za-z][0-9]|[0-9][A-Za-z])\\s?(\\d{2,4})\\b");
            java.util.regex.Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String code = matcher.group(0);
                Log.d("FCM_DEBUG", "Regex extracted flight code from text: " + code);
                return code;
            }
        } catch (Exception e) {
            Log.e("FCM_DEBUG", "Error parsing flight code regex from notification text", e);
        }
        return null;
    }

    private void logIntentDetails(String source, Intent intent) {
        Log.d("FCM_DEBUG", "=== Intent details [" + source + "] ===");
        if (intent == null) {
            Log.d("FCM_DEBUG", "intent is null");
            return;
        }

        Log.d("FCM_DEBUG", "action=" + intent.getAction());
        Log.d("FCM_DEBUG", "component=" + (intent.getComponent() != null ? intent.getComponent().flattenToString() : "null"));
        Log.d("FCM_DEBUG", "flags=" + intent.getFlags());
        Log.d("FCM_DEBUG", "data=" + (intent.getDataString() != null ? intent.getDataString() : "null"));

        Bundle extras = intent.getExtras();
        if (extras == null || extras.isEmpty()) {
            Log.d("FCM_DEBUG", "extras=none");
            Log.d("FCM_DEBUG", "=== End intent details [" + source + "] ===");
            return;
        }

        for (String key : extras.keySet()) {
            Object value = extras.get(key);
            Log.d("FCM_DEBUG", "extra[" + key + "]=" + String.valueOf(value));
        }
        Log.d("FCM_DEBUG", "=== End intent details [" + source + "] ===");
    }

    private void initApp(boolean locationPermission) {
        logIntentDetails("initApp", getIntent());

        UserController.getInstance().startSyncProcess(getPudahuelApplication());

        boolean fromPush = getIntent().getBooleanExtra("fromPush", false);
        String flightId = extractFlightId(getIntent());

        Log.d("FCM_DEBUG", "initApp: fromPush=" + fromPush + ", flight_id=" + flightId);

        if (flightId != null && !flightId.isEmpty()) {
            // Deep-link: open AlertActivity for the specific flight
            Log.d("FCM_DEBUG", "Deep-linking to AlertActivity with flight_id: " + flightId);
            Intent alertIntent = new Intent(this, AlertActivity.class);
            alertIntent.putExtra(AlertActivity.EXTRA_FLIGHT, flightId);
            alertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(alertIntent);
            finish();
            return;
        }

        if (fromPush) {
            // Push received without flightId -> bring HomeActivity forward and show My Flights
            Intent homeIntent = new Intent(this, HomeActivity.class);
            homeIntent.putExtra("show_my_flights", true);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(homeIntent);
            finish();
            return;
        }

        if (getPudahuelApplication().isAlive()) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
            return;
        }

        getPudahuelApplication().setAlive(true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, VideoSplashActivity.class));
                finish();
            }
        }, 1500);
    }

    private void requestFCMToken() {
        Log.d("FCM_TOKEN", "Requesting FCM registration token...");
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e("FCM_TOKEN", "====================================================");
                            Log.e("FCM_TOKEN", "FAILED to fetch FCM registration token!", task.getException());
                            Log.e("FCM_TOKEN", "====================================================");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and store
                        ((PudahuelApplication) getApplicationContext()).storeString(PudahuelPrefs.PUSH_TOKEN, token);
                        UserController.getInstance().registerPushToken((PudahuelApplication) getApplicationContext());

                        Log.i("FCM_TOKEN", "====================================================");
                        Log.i("FCM_TOKEN", ">> FCM REGISTRATION TOKEN <<");
                        Log.i("FCM_TOKEN", token);
                        Log.i("FCM_TOKEN", "====================================================");
                    }
                });
    }

}
