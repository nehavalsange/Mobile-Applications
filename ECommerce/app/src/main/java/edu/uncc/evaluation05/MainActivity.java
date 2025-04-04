package edu.uncc.evaluation05;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import edu.uncc.evaluation05.models.AuthResponse;
import edu.uncc.evaluation05.models.PurchaseList;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, SignUpFragment.SignUpListener,
        PurchaseListsFragment.PurchaseListsListener, PurchaseListDetailsFragment.PurchaseListDetailsListener, CreatePurchaseListFragment.CreatePurchaseListListener {
    String USERINFO_KEY = "USERINFO";
    AuthResponse mAuthResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sharedPref.contains(USERINFO_KEY)){
            Gson gson = new Gson();
            String json = sharedPref.getString(USERINFO_KEY, "");
            mAuthResponse = gson.fromJson(json, AuthResponse.class);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new PurchaseListsFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new LoginFragment())
                    .commit();
        }
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SignUpFragment())
                .commit();
    }

    @Override
    public void authCompleted(AuthResponse authResponse) {
        mAuthResponse = authResponse;
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString(USERINFO_KEY, gson.toJson(authResponse));
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new PurchaseListsFragment())
                .commit();
    }

    @Override
    public void login() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void logout() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(USERINFO_KEY);
        editor.apply();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void doneCreatingPurchaseList() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public AuthResponse getAuthResponse() {
        return mAuthResponse;
    }

    @Override
    public void gotoCreatePurchaseList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreatePurchaseListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoPurchaseListDetails(PurchaseList purchaseList) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, PurchaseListDetailsFragment.newInstance(purchaseList))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doneViewingPurchaseList() {
        getSupportFragmentManager().popBackStack();
    }
}