package com.annamorgiel.popularmovies_udacity_1.Rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anna Morgiel on 10.05.2017.
 */

public class VideoObject implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("type")
    @Expose
    private String type;
    public final static Parcelable.Creator<VideoObject> CREATOR = new Creator<VideoObject>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VideoObject createFromParcel(Parcel in) {
            VideoObject instance = new VideoObject();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.iso6391 = ((String) in.readValue((String.class.getClassLoader())));
            instance.iso31661 = ((String) in.readValue((String.class.getClassLoader())));
            instance.key = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.site = ((String) in.readValue((String.class.getClassLoader())));
            instance.size = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public VideoObject[] newArray(int size) {
            return (new VideoObject[size]);
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(iso6391);
        dest.writeValue(iso31661);
        dest.writeValue(key);
        dest.writeValue(name);
        dest.writeValue(site);
        dest.writeValue(size);
        dest.writeValue(type);
    }

    public int describeContents() {
        return 0;
    }

}
