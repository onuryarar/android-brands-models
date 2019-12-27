package com.example.bilmemenayp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;

public class Brands extends AppCompatActivity {

    Application app = new Application();
    ArrayList<BrandClass> brandList = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        listView = findViewById(R.id.listView);

        getSupportActionBar().setTitle("Markalar");

        getBrands();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BrandClass brand = brandList.get(position);
                Intent gotoModel = new Intent(Brands.this, Models.class);
                gotoModel.putExtra("brandId", brand.getId());
                gotoModel.putExtra("brandName", brand.getBrandName());
                startActivity(gotoModel);
                overridePendingTransition(R.animator.anim_in, R.animator.anim_out);
            }
        });
    }

    private void getBrands() {
        RequestQueue reqWay = Volley.newRequestQueue(getApplicationContext());
        String url = app.getServiceUrl() + "?brands";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    for (int i=0; i < json.length(); i++) {
                        JSONObject obj = json.getJSONObject(i);
                        brandList.add(new BrandClass(
                                Integer.parseInt(obj.get("Id").toString()),
                                obj.get("logo").toString(),
                                obj.get("brand").toString()));
                    }

                    CustomAdapter adapter = new CustomAdapter(getApplicationContext(), R.layout.brands_layout, brandList);
                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Brands.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        reqWay.add(req);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent gotoMain = new Intent(Brands.this, MainActivity.class);
        startActivity(gotoMain);
        return super.onOptionsItemSelected(item);
    }
}