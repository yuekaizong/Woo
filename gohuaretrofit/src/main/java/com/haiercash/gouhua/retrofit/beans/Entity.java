package com.haiercash.gouhua.retrofit.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by Kaizo on 2017/11/22.
 */

public class Entity implements Serializable{

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
