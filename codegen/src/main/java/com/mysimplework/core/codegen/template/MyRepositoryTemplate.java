package com.mysimplework.core.codegen.template;

import java.util.List;

/**
 * Created by dzhao on 7/12/2015.
 */
public class MyRepositoryTemplate {

    private String modelSimpleName;
    private String modelPackageName;
    private String repositorySimpleName;
    private String repositoryPackageName;
    private List<MySearchTemplate> mySearchTemplates;

    public String getModelSimpleName() {
        return modelSimpleName;
    }

    public void setModelSimpleName(String modelSimpleName) {
        this.modelSimpleName = modelSimpleName;
    }

    public String getModelPackageName() {
        return modelPackageName;
    }

    public void setModelPackageName(String modelPackageName) {
        this.modelPackageName = modelPackageName;
    }

    public String getRepositorySimpleName() {
        return repositorySimpleName;
    }

    public void setRepositorySimpleName(String repositorySimpleName) {
        this.repositorySimpleName = repositorySimpleName;
    }

    public String getRepositoryPackageName() {
        return repositoryPackageName;
    }

    public void setRepositoryPackageName(String repositoryPackageName) {
        this.repositoryPackageName = repositoryPackageName;
    }

    public List<MySearchTemplate> getMySearchTemplates() {
        return mySearchTemplates;
    }

    public void setMySearchTemplates(List<MySearchTemplate> mySearchTemplates) {
        this.mySearchTemplates = mySearchTemplates;
    }
}
