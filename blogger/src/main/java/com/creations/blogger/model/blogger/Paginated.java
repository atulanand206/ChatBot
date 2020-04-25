package com.creations.blogger.model.blogger;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Paginated<T> implements Serializable {

    @SerializedName("kind")
    private String kind;

    @SerializedName("nextPageToken")
    private String nextPageToken;

    @SerializedName("items")
    private List<T> items;

    @SerializedName("etag")
    private String etag;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
