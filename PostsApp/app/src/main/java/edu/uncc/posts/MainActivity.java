package edu.uncc.posts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import edu.uncc.posts.models.AuthResponse;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        SignUpFragment.SignUpListener, PostsFragment.PostsListener, CreatePostFragment.CreatePostListener{
        String USER_INFO_KEY = "user_info_key";
        AuthResponse mAuthResponse=null;
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

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sharedPref.contains(USER_INFO_KEY)){
            Gson gson = new Gson();
            String json = sharedPref.getString(USER_INFO_KEY,"");
            mAuthResponse = gson.fromJson(json,AuthResponse.class);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new PostsFragment())
                    .commit();
        }else {
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
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_INFO_KEY, new Gson().toJson(mAuthResponse));
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new PostsFragment())
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
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(USER_INFO_KEY);
        editor.apply();


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();

    }

    @Override
    public void createPost() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreatePostFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public AuthResponse getAuthResponse() {
        return mAuthResponse;
    }

    @Override
    public void goBackToPosts() {
        getSupportFragmentManager().popBackStack();
    }
}