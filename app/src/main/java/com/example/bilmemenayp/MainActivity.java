package com.example.bilmemenayp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    Application app = new Application();
    EditText username, password;
    TextView logoText;
    Button login;
    ProgressBar progress;

    RelativeLayout logoLayout, inputLayout;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            logoLayout.setVisibility(View.VISIBLE);
            inputLayout.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoText = findViewById(R.id.logo_textView);

        username = findViewById(R.id.username_txt);
        password = findViewById(R.id.password_txt);
        login = findViewById(R.id.login_btn);

        progress = findViewById(R.id.progressBar);

        logoLayout = findViewById(R.id.logo_container);
        inputLayout = findViewById(R.id.input_container);

        handler.postDelayed(runnable, 2000);

        Typeface ttf = Typeface.createFromAsset(getAssets(), "fonts/Bella.ttf");
        logoText.setTypeface(ttf);

        getSupportActionBar().hide();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")) {
                    Toasty.info(MainActivity.this, "Boş geçmeyelim...", Toasty.LENGTH_SHORT).show();
                } else {
                    login.setEnabled(false);
                    username.setEnabled(false);
                    password.setEnabled(false);
                    progress.setVisibility(View.VISIBLE);

                    RequestQueue requestWay = Volley.newRequestQueue(getApplicationContext());
                    String url = app.getServiceUrl();

                    StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            checkUser(response);
                            progress.setVisibility(View.INVISIBLE);
                            login.setEnabled(true);
                            username.setEnabled(true);
                            password.setEnabled(true);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.warning(MainActivity.this, "Sunucuya bağlanılamadı. "+ app.getServiceUrl(), Toasty.LENGTH_LONG).show();
                            progress.setVisibility(View.INVISIBLE);
                            login.setEnabled(true);
                            username.setEnabled(true);
                            password.setEnabled(true);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("email", username.getText().toString());
                            params.put("password", password.getText().toString());

                            return params;
                        }
                    };
                    requestWay.add(req);
                }

            }
        });
    }

    public void checkUser(String response) {
        try {
            JSONArray json = new JSONArray(response);
            if (json.length() > 0) {
                JSONObject obj = json.getJSONObject(0);
                if (obj.getString("email").equals(String.valueOf(username.getText()))) {
                    Toasty.success(MainActivity.this, "Hoşgeldiniz, " + obj.getString("firstname"), Toast.LENGTH_SHORT).show();
                    Intent gotoBrands = new Intent(MainActivity.this, Brands.class);
                    gotoBrands.putExtra("user", obj.getString("firstname") + " " + obj.getString("lastname"));
                    startActivity(gotoBrands);

                    overridePendingTransition(R.animator.anim_in, R.animator.anim_out);
                } else {
                    Toasty.error(this, "Kullanıcı Adı veya Şifre yanlış.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toasty.error(this, "Kullanıcı Adı veya Şifre yanlış.", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
