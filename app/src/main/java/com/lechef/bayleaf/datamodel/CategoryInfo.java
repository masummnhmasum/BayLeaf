package com.lechef.bayleaf.datamodel;

/**
 * Created by masum on 10/03/2015.
 */
public class CategoryInfo {
    private int catId;
    private int resCuisineId;
    private String catName;
    private String description;
    private int status;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getResCuisineId() {
        return resCuisineId;
    }

    public void setResCuisineId(int resCuisineId) {
        this.resCuisineId = resCuisineId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
