package com.lechef.bayleaf.singleton;

import com.lechef.bayleaf.datamodel.CategoryInfo;

import java.util.ArrayList;

/**
 * Created by masum on 11/03/2015.
 */
public class AppData {
    private ArrayList<CategoryInfo> categoryInfo = new ArrayList<CategoryInfo>();
    private static AppData ourInstance = new AppData();

    private AppData() {
    }

    public static AppData getInstance() {
        return ourInstance;
    }

    public static void destroyInstance() {
        ourInstance = null;
    }

    public ArrayList<CategoryInfo> getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(ArrayList<CategoryInfo> categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

}
