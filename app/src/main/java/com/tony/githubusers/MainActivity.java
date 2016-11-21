package com.tony.githubusers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;

public class MainActivity extends AppCompatActivity {
    EditText userName;
    TextView name;
    ImageView picture;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.etUserName);
        name = (TextView) findViewById(R.id.txtName);
        picture = (ImageView) findViewById(R.id.imgAvatar);
    }

    public void buscar (View v)
    {
        final String user = userName.getText().toString();
        if (user.length()>0)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);
            apiService.getUser(user).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code()==200)
                    {
                        if (response.body()!=null)
                        {
                            try {
                                JSONObject data = new JSONObject(response.body().string());
                                name.setText(data.getString("name"));
                                Glide.with(getApplicationContext())
                                        .load(data.getString("avatar_url"))
                                        .into(picture);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "No se encontró al usuario: "+user, Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error code: "+response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Toast.makeText(this, "Ingresa el nombre del usuario...", Toast.LENGTH_SHORT).show();
        }
    }
}
