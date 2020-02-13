package com.udacity.bakingapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {
    @Expose
    @SerializedName("id")
    public final int id;

    @Expose
    @SerializedName("shortDescription")
    public final String shortDescription;

    @Expose
    @SerializedName("description")
    public final String description;

    @Expose
    @SerializedName("videoURL")
    public final String videoURL;

    @Expose
    @SerializedName("thumbnailURL")
    public final String thumbnailURL;

    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }
}
