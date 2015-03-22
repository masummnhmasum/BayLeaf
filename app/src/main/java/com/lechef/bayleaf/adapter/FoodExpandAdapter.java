package com.lechef.bayleaf.adapter;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.lechef.bayleaf.activity.R;
import com.lechef.bayleaf.datamodel.CategoryInfo;
import com.lechef.bayleaf.singleton.AppData;


public class FoodExpandAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<CategoryInfo> parentItem;
	private ArrayList<String> dateItem = new ArrayList<String>();
	private ArrayList<CategoryInfo> childItem;
	private Activity activity;
	private boolean isSavedContact;
	private TextView txtViewSavedNoContact;
	private ListView expandableListView;
    ArrayList<CategoryInfo> categoryInfoSearchList = new ArrayList<CategoryInfo>();

	public FoodExpandAdapter(Context context, ArrayList<CategoryInfo> parentItem, ArrayList<CategoryInfo> childItem) {
		this.parentItem = parentItem;
        categoryInfoSearchList.addAll(parentItem);
		this.dateItem = dateItem;
		//this.childItem = childItem;
		mContext = context;
		this.activity = activity;
		this.isSavedContact = isSavedContact;
		inflater = LayoutInflater.from(mContext);
		this.txtViewSavedNoContact = txtViewSavedNoContact;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_food_child, parent, false);
		}

		final TextView txtViewEmail = (TextView) convertView.findViewById(R.id.txt_view_food_name);
        txtViewEmail.setText(parentItem.get(groupPosition).getDescription());

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		if(parentItem.size() > 0) {
			return parentItem.size();
		} else {
			return 0;
		}
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_food_group, parent, false);
		}

        final TextView txtViewName = (TextView) convertView.findViewById(R.id.txt_view_name);
        txtViewName.setText(parentItem.get(groupPosition).getCatName());

        final TextView txtViewDecription = (TextView) convertView.findViewById(R.id.txt_view_description);
        txtViewDecription.setText(parentItem.get(groupPosition).getDescription());

        final ImageButton imgButtonInfo = (ImageButton) convertView.findViewById(R.id.imgBtnInfo);
        imgButtonInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(isExpanded) ((ExpandableListView) parent).collapseGroup(groupPosition);
                else ((ExpandableListView) parent).expandGroup(groupPosition, true);

            }
        });

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

    public void filter(String charText) {

        try {
            charText = charText.toLowerCase(Locale.getDefault());

            // Separate the search result wrwer
            /*String[] splited = charText.split("\\s+");
            if (splited.length > 1) {
                charText = splited[0];
            }*/

            this.parentItem.clear();

            if (charText.length() == 0) {
                this.parentItem.addAll(categoryInfoSearchList);

            } else {
                for (int i = 0; i < categoryInfoSearchList.size(); i++) {
                    if (categoryInfoSearchList.get(i).getCatName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        this.parentItem.add(categoryInfoSearchList.get(i));
                    }
                }

            }

            this.notifyDataSetChanged();

        } catch (Exception e) {

        }

    }

}
