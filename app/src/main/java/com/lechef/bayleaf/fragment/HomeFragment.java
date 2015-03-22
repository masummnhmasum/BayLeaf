package com.lechef.bayleaf.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lechef.bayleaf.activity.R;

/**
 * Created by masum on 10/03/2015.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private Button btnMenuOrderOnline;
    public HomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView(rootView);
        loadData();
        setListViewListener();
        return rootView;
    }

    private void loadData() {

    }

    private void initView(View rootView) {
        btnMenuOrderOnline = (Button) rootView.findViewById(R.id.btnMenuOrderOnline);
    }

    private void setListViewListener() {
        btnMenuOrderOnline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMenuOrderOnline:
                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putInt("isVoiceOrder", 0);
                fragment = new CategoryFragment();
                fragment.setArguments(args);

                FragmentManager frgManager = getFragmentManager();
                android.app.FragmentTransaction ft = frgManager.beginTransaction();

                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment);
                ft.commit();

                break;
            default:
                break;
        }
    }
}
