package com.creations.blogger.model.blogger;

import java.io.Serializable;

public class Image implements Serializable {
    private String url;

    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
}
