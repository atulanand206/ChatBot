package com.creations.mvvm.models.props;

import com.creations.condition.Preconditions;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Option implements Serializable {

    public static final String OTHER = "OTHER";

    @SerializedName("key")
    private String key;

    @SerializedName("label")
    private String value;

    public Option(@NonNull final String key, @NonNull final String value) {
        this.key = Preconditions.requiresNonNull(key, "Key");
        this.value = Preconditions.requiresNonNull(value, "Value");
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean isOther() {
        return key.equals(OTHER);
    }
}
