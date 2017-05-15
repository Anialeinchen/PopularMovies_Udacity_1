package com.annamorgiel.popularmovies_udacity_1.Rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anna Morgiel on 15.05.2017.
 */

public class ApiReviewResponse implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<ReviewObject> results = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalReviewObjects;
    public final static Parcelable.Creator<ApiReviewResponse> CREATOR = new Creator<ApiReviewResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ApiReviewResponse createFromParcel(Parcel in) {
            ApiReviewResponse instance = new ApiReviewResponse();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.results, (com.annamorgiel.popularmovies_udacity_1.Rest.model.ReviewObject.class.getClassLoader()));
            instance.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.totalReviewObjects = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public ApiReviewResponse[] newArray(int size) {
            return (new ApiReviewResponse[size]);
        }

    }
            ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<ReviewObject> getReviewObjects() {
        return results;
    }

    public void setReviewObjects(List<ReviewObject> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalReviewObjects() {
        return totalReviewObjects;
    }

    public void setTotalReviewObjects(Integer totalReviewObjects) {
        this.totalReviewObjects = totalReviewObjects;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(page);
        dest.writeList(results);
        dest.writeValue(totalPages);
        dest.writeValue(totalReviewObjects);
    }

    public int describeContents() {
        return 0;
    }

}