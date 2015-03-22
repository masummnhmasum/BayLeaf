package com.lechef.bayleaf.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lechef.bayleaf.activity.R;
import com.lechef.bayleaf.activity.VoiceRecoActivity;
import com.lechef.bayleaf.adapter.FoodExpandAdapter;
import com.lechef.bayleaf.adapter.ViewPagerAdapter;
import com.lechef.bayleaf.datamodel.CategoryInfo;
import com.lechef.bayleaf.jsonparser.JsonParser;
import com.lechef.bayleaf.singleton.AppData;
import com.lechef.bayleaf.utils.ConstantValues;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by masum on 10/03/2015.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener {

    String[] country;
    String[] categoryType;

    /* Control Declaration */
    private Button btnMenuOrderOnline;
    private Button btnSave, btnPrevious, btnNext, btnVoiceText;
    private EditText editTextCategorySearch;
    private TextView txtView;
    private ExpandableListView expandListViewFoodItem;


    /* Speech helper dialog */
    private Dialog dialog = null;
    private ProgressBar progressBar = null;

    /*Category Navigation controll */
    private static ViewPager viewPager;
    private PagerAdapter adapter;
    private FoodExpandAdapter adapterFood;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String LOG_TAG = "CategoryFragmentVoiceRecognize";
    ArrayList<CategoryInfo> categoryInfoSearchList = new ArrayList<CategoryInfo>();

    /* Voice Recognizer Object*/
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;

    public CategoryFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        initView(rootView);
        loadData();
        setListViewListener(rootView);
        return rootView;
    }

    private void loadData() {
        if (getArguments().getInt("isVoiceOrder") == 0) {
            btnVoiceText.setVisibility(View.GONE);
        } else {
            btnVoiceText.setVisibility(View.VISIBLE);
        }
        createHttpRequest();
        country = new String[] {"Apple", "Banana"};
        categoryType = new String[] {"China", "Bangladesh"};

    }

    private void initView(View rootView) {
        editTextCategorySearch = (EditText) rootView.findViewById(R.id.editTextCategorySearch);
        btnNext = (Button) rootView.findViewById(R.id.btnNext);
        btnPrevious = (Button) rootView.findViewById(R.id.btnPrevious);
        btnVoiceText = (Button) rootView.findViewById(R.id.btnVoiceText);
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
        viewPager.setPageMargin(12);

    }



    private void setListViewListener(View rootView) {
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnVoiceText.setOnClickListener(this);

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

        editTextCategorySearch = (EditText) rootView.findViewById(R.id.editTextCategorySearch);

        // Capture Text in EditText
        editTextCategorySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editTextCategorySearch.getText().toString().toLowerCase(Locale.getDefault());
                Log.i("Text", "** " + text);
                adapterFood.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

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
            case R.id.btnVoiceText:
               // promptSpeechInput();
                /*initVoiceRecognition();
                progressBar.setIndeterminate(true);*/

                Intent intent = new Intent(getActivity(), VoiceRecoActivity.class);
                //startActivityForResult(intent, 2);
                startActivityForResult(intent, 100);

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

                            categoryInfoSearchList = AppData.getInstance().getCategoryInfo();

                            ArrayList<CategoryInfo> cat = new ArrayList<CategoryInfo>();
                            cat.addAll(AppData.getInstance().getCategoryInfo());

                           adapter = new ViewPagerAdapter(getActivity(), country, cat);
                           viewPager.setAdapter(adapter);

                            adapterFood = new FoodExpandAdapter(getActivity(), categoryInfoSearchList,  categoryInfoSearchList);
                            expandListViewFoodItem.setAdapter(adapterFood);

                            if (getArguments().getInt("isVoiceOrder") == 1) {
                                //promptSpeechInput();
                            }

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

    @Override
    public void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "on Resume");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == getActivity().RESULT_OK && null != data) {
                    Log.i("Final Text", "****" + data.getExtras().getStringArrayList("data"));
                    ArrayList<String> result = new ArrayList<String>();
                    result = data.getExtras().getStringArrayList("data");
                    editTextCategorySearch.setText(result.get(0));
                    int pos = editTextCategorySearch.getText().length();
                    editTextCategorySearch.setSelection(pos);
                }
                break;
            }

        }
    }


}
