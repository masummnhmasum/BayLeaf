package com.lechef.bayleaf.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lechef.bayleaf.adapter.ViewPagerAdapter;
import com.lechef.bayleaf.datamodel.CategoryInfo;
import com.lechef.bayleaf.singleton.AppData;

import java.util.ArrayList;
//import com.android.vo

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private static ViewPager viewPager;
    String[] country;
    String[] categoryTitle;
    private Button btnSave, btnPrevious, btnNext;
    private TextView txtView;
    private PagerAdapter adapter;

    public static CharSequence relativeSizeSpan(CharSequence source, int pos) {
        if (viewPager.getCurrentItem() != pos) {
            return source;
        }
        final SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.2f), 0, source.length(), 0);
        return ss;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnSave = (Button) findViewById(R.id.button);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        txtView = (TextView) findViewById(R.id.textView);
        btnSave.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);

        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.titlestrip);
        pagerTitleStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        pagerTitleStrip.setTextColor(Color.WHITE);

        country = new String[]{"Pakistanipeople International", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan"};

        categoryTitle = new String[]{
                "Prawn Masala",
                "Original Italian Oven Fresh Piza",
                "Arabian Shawarma",
                "Pizza Roll",
                "Burger",
                "Italian Baked Pasta", "Salad Sandwich", "Tandoor Gost",
                "Shorba", "Biryani", "Rice", "Salad", "Shahi Meetha"};


        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(12);

        adapter = new ViewPagerAdapter(MainActivity.this, country, AppData.getInstance().getCategoryInfo());
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                //Toast.makeText(MyActivity.this, i+"  Is Selected  " + data.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int i) {
                //Toast.makeText(getApplicationContext(),"Selected postion " + currentPosition, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                // adapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                createHttpRequest();
                break;
            case R.id.btnPrevious:
                int i = viewPager.getCurrentItem();
                if (i > 0) {
                    viewPager.setCurrentItem(i - 1);
                }

                break;
            case R.id.btnNext:
                int j = viewPager.getCurrentItem();
                if (categoryTitle.length > j) {
                    viewPager.setCurrentItem(j + 1);
                }
                break;
        }
    }

    private void createHttpRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        txtView.setText("Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
