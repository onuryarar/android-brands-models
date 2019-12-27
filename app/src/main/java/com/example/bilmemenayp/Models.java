package com.example.bilmemenayp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Models extends AppCompatActivity {

    Application app = new Application();
    ListView listView;
    ArrayList<ModelClass> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);

        listView = findViewById(R.id.listView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int brandId = Integer.parseInt(getIntent().getExtras().get("brandId").toString());
        String brandName = getIntent().getExtras().get("brandName").toString();
        getSupportActionBar().setTitle(brandName);

        RequestQueue reqWay = Volley.newRequestQueue(getApplicationContext());
        String url = app.getServiceUrl() + "?getModels=" + brandId;
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    for (int i=0; i < json.length(); i++) {
                        JSONObject obj = json.getJSONObject(i);
                        modelList.add(new ModelClass(
                                Integer.parseInt(obj.get("Id").toString()),
                                Integer.parseInt(obj.get("brand_id").toString()),
                                obj.get("model").toString(),
                                obj.get("image").toString(),
                                obj.get("details").toString()));
                    }

                    CustomModelAdapter adapter = new CustomModelAdapter(getApplicationContext(), R.layout.model_layout, modelList);
                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        reqWay.add(req);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, Brands.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
