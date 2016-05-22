package com.mysimplework.core.codegen.template;

import java.util.List;

/**
 * Created by dzhao on 22/09/2015.
 */
public class MyRestControllerTemplate {
    private String modelPackageName;
    private String repositoryPackageName;
    private String modelClassName;
    private String modelInstanceName;
    private String repositoryClassName;
    private String repositoryInstanceName;
    private String urlPathName;
    private String controllerClassName;
    private String controllerPackageName;

    private List<MySearchTemplate> mySearchTemplates;

    private List<MyFieldTemplate> myFieldTemplates;

    public String getModelPackageName() {
        return modelPackageName;
    }

    public void setModelPackageName(String modelPackageName) {
        this.modelPackageName = modelPackageName;
    }

    public String getRepositoryPackageName() {
        return repositoryPackageName;
    }

    public void setRepositoryPackageName(String repositoryPackageName) {
        this.repositoryPackageName = repositoryPackageName;
    }

    public String getControllerClassName() {
        return controllerClassName;
    }

    public void setControllerClassName(String controllerClassName) {
        this.controllerClassName = controllerClassName;
    }

    public String getModelClassName() {
        return modelClassName;
    }

    public void setModelClassName(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    public String getModelInstanceName() {
        return modelInstanceName;
    }

    public void setModelInstanceName(String modelInstanceName) {
        this.modelInstanceName = modelInstanceName;
    }

    public String getRepositoryClassName() {
        return repositoryClassName;
    }

    public void setRepositoryClassName(String repositoryClassName) {
        this.repositoryClassName = repositoryClassName;
    }

    public String getRepositoryInstanceName() {
        return repositoryInstanceName;
    }

    public void setRepositoryInstanceName(String repositoryInstanceName) {
        this.repositoryInstanceName = repositoryInstanceName;
    }

    public String getUrlPathName() {
        return urlPathName;
    }

    public void setUrlPathName(String urlPathName) {
        this.urlPathName = urlPathName;
    }

    public String getControllerPackageName() {
        return controllerPackageName;
    }

    public void setControllerPackageName(String controllerPackageName) {
        this.controllerPackageName = controllerPackageName;
    }

    public List<MySearchTemplate> getMySearchTemplates() {
        return mySearchTemplates;
    }

    public void setMySearchTemplates(List<MySearchTemplate> mySearchTemplates) {
        this.mySearchTemplates = mySearchTemplates;
    }

    public List<MyFieldTemplate> getMyFieldTemplates() {
        return myFieldTemplates;
    }

    public void setMyFieldTemplates(List<MyFieldTemplate> myFieldTemplates) {
        this.myFieldTemplates = myFieldTemplates;
    }
}
