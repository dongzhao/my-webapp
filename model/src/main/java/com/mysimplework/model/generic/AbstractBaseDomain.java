package com.mysimplework.model.generic;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by dzhao on 19/08/2015.
 */
@MappedSuperclass
public abstract class AbstractBaseDomain implements BaseDomain<String> {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
