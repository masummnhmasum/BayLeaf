package com.lechef.bayleaf.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lechef.bayleaf.fragment.CategoryFragment;
import com.lechef.bayleaf.fragment.HomeFragment;
import com.lechef.bayleaf.fragment.RegistrationFragment;

import java.util.Locale;

/**
 * Created by masum on 10/03/2015.
 */
public class HomeActivity extends ActionBarActivity implements View.OnClickListener{

    private Button btnHome, btnOrder, btnVoiceOrder, btnOffers;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        loadDefaultFragment();
        setListener();
    }

    public void initView() {
        btnHome = (Button) findViewById(R.id.btnHome);
        btnVoiceOrder = (Button) findViewById(R.id.btnVoiceOrder);
        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnOffers = (Button) findViewById(R.id.btnOffers);

    }

    public void setListener() {
        btnHome.setOnClickListener(this);
        btnVoiceOrder.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        btnOffers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHome:
               loadDefaultFragment();
                break;
            case R.id.btnVoiceOrder:
                loadCategoryFragment();
                //promptSpeechInput();
                break;
            case R.id.btnOrder:
                break;
            case R.id.btnOffers:
                loadRegistrationFragment();
                break;
            default:
                break;
        }
    }

    private void loadCategoryFragment() {
        Fragment fragment = null;
        fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt("isVoiceOrder", 1);

        fragment.setArguments(args);

        FragmentManager frgManager = getFragmentManager();
        android.app.FragmentTransaction ft = frgManager.beginTransaction();

        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private void loadDefaultFragment() {
        Fragment fragment = null;
        fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("isVoiceOrder", 1);

        fragment.setArguments(args);

        FragmentManager frgManager = getFragmentManager();
        android.app.FragmentTransaction ft = frgManager.beginTransaction();

        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private void loadRegistrationFragment() {
        Fragment fragment = null;
        Bundle args = new Bundle();
        fragment = new RegistrationFragment();
        fragment.setArguments(args);

        FragmentManager frgManager = getFragmentManager();
        android.app.FragmentTransaction ft = frgManager.beginTransaction();

        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }



}
