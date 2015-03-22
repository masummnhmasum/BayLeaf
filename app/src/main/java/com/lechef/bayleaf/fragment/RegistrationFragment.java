package com.lechef.bayleaf.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lechef.bayleaf.activity.R;
import com.lechef.bayleaf.adapter.FoodExpandAdapter;
import com.lechef.bayleaf.adapter.ViewPagerAdapter;
import com.lechef.bayleaf.datamodel.CategoryInfo;
import com.lechef.bayleaf.jsonparser.JsonParser;
import com.lechef.bayleaf.singleton.AppData;
import com.lechef.bayleaf.utils.ConstantValues;

import java.util.ArrayList;

/**
 * Created by masum on 10/03/2015.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {
    private Button btnMenuOrderOnline;
    private static ViewPager viewPager;
    String[] country;
    String[] categoryType;
    private Button btnSave, btnPrevious, btnNext;
    private TextView txtView;
    private ExpandableListView expandListViewFoodItem;
    private PagerAdapter adapter;
    public RegistrationFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        initView(rootView);
        loadData();
        setListViewListener();
        return rootView;
    }

    private void loadData() {

    }

    private void initView(View rootView) {
/*
        btnNext = (Button) rootView.findViewById(R.id.btnNext);
        btnPrevious = (Button) rootView.findViewById(R.id.btnPrevious);
        txtView = (TextView) rootView.findViewById(R.id.textView);
        expandListViewFoodItem = (ExpandableListView) rootView.findViewById(R.id.expandListViewFoodItem);
        expandListViewFoodItem.setGroupIndicator(null);
        expandListViewFoodItem.setChildIndicator(null);
       // expandListViewFoodItem.setChildDivider(getResources().getDrawable(R.color.));
       // expandListViewFoodItem.setDivider(getResources().getDrawable(R.color.abc_primary_text_disable_only_material_light));
        expandListViewFoodItem.setDividerHeight(2);

        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip) rootView.findViewById(R.id.titlestrip);
        pagerTitleStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        pagerTitleStrip.setTextColor(Color.WHITE);

        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(12);*/

    }

    private void setListViewListener() {

        /*btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);

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

        });*/

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
                if (AppData.getInstance().getCategoryInfo().size() > j) {
                    viewPager.setCurrentItem(j + 1);
                }
                break;
        }
    }

    public static CharSequence relativeSizeSpan(CharSequence source, int pos) {
        if (viewPager.getCurrentItem() != pos) {
            return source;
        }
        final SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.2f), 0, source.length(), 0);
        return ss;
    }


    private void createHttpRequest() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = ConstantValues.BASE_URL + "restaurant-cuisines?expand=dishCategories&restaurant_id=" + 17 + "&" + "sort=" + 0;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            ArrayList<CategoryInfo> categoryInfos = new ArrayList<>();
                            AppData.getInstance().setCategoryInfo(categoryInfos);
                            JsonParser.categoryParser(response);
                            adapter = new ViewPagerAdapter(getActivity(), country, AppData.getInstance().getCategoryInfo());
                            viewPager.setAdapter(adapter);

                            FoodExpandAdapter adapterFood = new FoodExpandAdapter(getActivity(), AppData.getInstance().getCategoryInfo(),  AppData.getInstance().getCategoryInfo());
                            expandListViewFoodItem.setAdapter(adapterFood);

                        } catch(Exception e) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.parse_error), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void getFoodItemRequest() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = ConstantValues.BASE_URL + "restaurant-dishes?restaurant_id=" + 17 + "&dish_category_id=" + 153 + "&parent_dish_id=" + 0 + "&sort=" +0;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //txtView.setText("Response is: " + response.substring(0, 500));
                        try {
                            ArrayList<CategoryInfo> categoryInfos = new ArrayList<>();
                            AppData.getInstance().setCategoryInfo(categoryInfos);
                            JsonParser.categoryParser(response);
                            adapter = new ViewPagerAdapter(getActivity(), country, AppData.getInstance().getCategoryInfo());
                            viewPager.setAdapter(adapter);

                        } catch(Exception e) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.parse_error), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
