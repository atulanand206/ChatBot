package com.creations.blogger.model.blogger;

import java.io.Serializable;

public class Blog implements Serializable {

    private String id;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

}
