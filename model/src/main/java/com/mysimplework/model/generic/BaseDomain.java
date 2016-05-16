package com.mysimplework.model.generic;

import java.io.Serializable;

/**
 * Created by dzhao on 19/08/2015.
 */
public interface BaseDomain<ID extends Serializable> extends Serializable{

    ID getId();

}