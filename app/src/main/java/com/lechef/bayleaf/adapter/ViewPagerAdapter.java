package com.lechef.bayleaf.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lechef.bayleaf.activity.MainActivity;
import com.lechef.bayleaf.activity.R;
import com.lechef.bayleaf.datamodel.CategoryInfo;
import com.lechef.bayleaf.fragment.CategoryFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    String[] categoryTitle;
    String[] country;
    ArrayList<CategoryInfo> categoryInfos = new ArrayList<CategoryInfo>();
    String[] population;
    int[] flag;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, String[] country, ArrayList<CategoryInfo> categoryInfos) {
        this.context = context;
        this.country = country;
        this.categoryInfos = categoryInfos;
        this.categoryTitle = categoryTitle;
    }

    @Override
    public int getCount() {
        return categoryInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Declare Variables
        TextView txtCountry;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

        txtCountry = (TextView) itemView.findViewById(R.id.country);

        // Capture position and set to the TextViews
        Log.i("Position", "**" + position);
        txtCountry.setText(categoryInfos.get(position).getCatName());
        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return title[position];
//    }

    @Override
    public CharSequence getPageTitle(int pos) {
        /*switch (pos) {
            case 0:
                return MainActivity.relativeSizeSpan(title[0], pos);
            case 1:
                return MainActivity.relativeSizeSpan(title[1], pos);
            case 2:
                return MainActivity.relativeSizeSpan(title[2], pos);
            default:
                break;
        }*/
        return CategoryFragment.relativeSizeSpan(categoryInfos.get(pos).getCatName(), pos);
    }

}
