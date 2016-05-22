package com.mysimplework.core.codegen.processor;

import com.mysimplework.core.annotation.MyReferenceSearch;
import com.mysimplework.core.annotation.MySearch;
import com.mysimplework.core.codegen.FreeMarkerWriter;
import com.mysimplework.core.annotation.MyRestController;
import com.mysimplework.core.codegen.template.MyFieldTemplate;
import com.mysimplework.core.codegen.template.MyRestControllerTemplate;
import com.mysimplework.core.codegen.template.MySearchTemplate;
import com.mysimplework.core.enums.OperatorEnum;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.persistence.Column;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.*;

/**
 *
 */
public class RestControllerProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes(){
        return new HashSet<String>(Arrays.asList(MyRestController.class.getName()));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("#####################################################");
        System.out.println("start generating restcontroller ...");
        for(TypeElement typeElement : annotations){
            if(!typeElement.getQualifiedName().toString().equalsIgnoreCase(MyRestController.class.getName())){
                continue;
            }
            Collection<? extends Element> types = roundEnv.getElementsAnnotatedWith(typeElement);
            for (Element element : types){

                StringBuilder targetSourcePath = new StringBuilder();
                MyRestController controller = element.getAnnotation(MyRestController.class);

                TypeElement classElement = (TypeElement)element;
                PackageElement packageElement = (PackageElement)classElement.getEnclosingElement();

                String modelClassName = element.getSimpleName().toString();
                String modelPackageName = packageElement.getQualifiedName().toString();

                //String modelInstanceName = modelClassName.replaceFirst(modelClassName.substring(0, 1), modelClassName.substring(0, 1).toLowerCase());
                String repositoryPackageName = controller.repositoryPackageName();

                if(repositoryPackageName.isEmpty()){
                    StringBuilder sb = new StringBuilder();
                    sb.append(modelPackageName.substring(0, modelPackageName.lastIndexOf(".model"))+".repositories");
                    repositoryPackageName = sb.toString();
                }

                String repositoryClassName = modelClassName + "Repository";
                //String repositoryInstanceName = repositoryClassName.replaceFirst(repositoryClassName.substring(0, 1), repositoryClassName.substring(0, 1).toLowerCase());
                String controllerClassName = controller.simpleName();
                if(controllerClassName.isEmpty()){
                    controllerClassName = modelClassName + "RestController";
                }

                String controllerPackageName = controller.simplePackageName();
                if(controllerPackageName.isEmpty()){
                    StringBuilder sb = new StringBuilder();
                    sb.append(modelPackageName.substring(0, modelPackageName.lastIndexOf(".model"))+".controllers");
                    controllerPackageName = sb.toString();
                }

                StringBuilder urlBuilder = new StringBuilder(controller.rootPath());
                if(!controller.rootPath().endsWith("/")){
                    urlBuilder.append("/");
                }

                MyRestControllerTemplate template = new MyRestControllerTemplate();
                template.setModelPackageName(modelPackageName);
                template.setRepositoryPackageName(repositoryPackageName);
                template.setModelClassName(modelClassName);
                template.setRepositoryClassName(repositoryClassName);
                template.setControllerPackageName(controllerPackageName);
                template.setControllerClassName(controllerClassName);
                template.setUrlPathName(urlBuilder.toString());

                template.setMySearchTemplates(new ArrayList<MySearchTemplate>());
                template.setMyFieldTemplates(new ArrayList<MyFieldTemplate>());

                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD) {

                        // Store all fields name and type for update function
                        Column column = enclosedElement.getAnnotation(Column.class);
                        if(column != null){
                            try {
                                Class clazz = Class.forName(enclosedElement.asType().toString());
                                System.out.println(" ### jpa field type class: " + clazz.getName());
                                System.out.println(" ### jpa field type class: " + int.class.equals(clazz));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                            MyFieldTemplate myFieldTemplate = new MyFieldTemplate();
                            String fieldType = enclosedElement.asType().toString();
                            String fieldName = enclosedElement.getSimpleName().toString();
                            myFieldTemplate.setName(fieldName);
                            myFieldTemplate.setType(fieldType);
                            template.getMyFieldTemplates().add(myFieldTemplate);
                        }


                        MySearch mySearch = enclosedElement.getAnnotation(MySearch.class);
                        if(mySearch !=null) {
                            MySearchTemplate searchTemplate = new MySearchTemplate();
                            OperatorEnum operator = mySearch.opertor();
                            String fieldType = enclosedElement.asType().toString();
                            String fieldName = enclosedElement.getSimpleName().toString();
                            boolean pageable = mySearch.pageable();
                            boolean sortable = mySearch.sortable();
                            System.out.println(" ### field name: " + fieldName);
                            System.out.println(" ### field type: " + fieldType);

                            searchTemplate.setType(fieldType);
                            searchTemplate.setName(fieldName);
                            searchTemplate.setOperator(operator.getValue());
                            searchTemplate.setPageable(pageable);
                            searchTemplate.setSortable(sortable);
                        }

                        MyReferenceSearch referenceSearch = enclosedElement.getAnnotation(MyReferenceSearch.class);
                        if(referenceSearch !=null) {

                            System.out.println("####### reference type: " + enclosedElement.asType().toString());
                            for (Element elem: enclosedElement.getEnclosedElements()){
                                System.out.println("####### reference field name: " + elem.getSimpleName().toString());

                            }
                            String referenceName = enclosedElement.getSimpleName().toString();
                            for (MySearch myReferenceSearch : referenceSearch.fieldSearch()) {
                                MySearchTemplate searchTemplate = new MySearchTemplate();
                                OperatorEnum operator = referenceSearch.opertor();
                                String fieldType = referenceSearch.type();
                                String fieldName = myReferenceSearch.name();
                                searchTemplate.setType(fieldType); // reference field type
                                searchTemplate.setName(fieldName); // reference field name
                                searchTemplate.setReferenceName(referenceName); // reference class name
                                searchTemplate.setOperator(operator.getValue());
                                template.getMySearchTemplates().add(searchTemplate);
                            }
                        }
                    }
                }

                try {
                    // to add the target folder rootPath
                    String sourcePath = processingEnv.getFiler().getResource(StandardLocation.SOURCE_OUTPUT, "", "test").toUri().getPath();
                    targetSourcePath.append(sourcePath.substring(0, sourcePath.lastIndexOf("/target/")) + "/");
                    System.out.println("targetSourcePath: " + targetSourcePath.toString());
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                // to aggregate the target rootPath from annotation target
                targetSourcePath.append(controller.target());
                System.out.println("targetSourcePath: " + targetSourcePath.toString());
                // to aggregate the target rootPath from annotation package name
                targetSourcePath.append(controllerPackageName.replace(".", "/") + "/");
                // to aggregate the target rootPath from annotation simple dao class name
                targetSourcePath.append(controllerClassName + ".java");
                FreeMarkerWriter writer = new FreeMarkerWriter("src/main/resources/restcontroller.ftl", targetSourcePath.toString(), template);
                writer.write();
            }
        }
        return false;
    }

 /*   private static String getOperator(Element element){
        if (int.class.isAssignableFrom(element.getClass())) {
            return ;
        } else if (boolean.class.isAssignableFrom(field.getType())) {
            return configuration.getBoolean(key);
        } else if (long.class.isAssignableFrom(field.getType())) {
            return configuration.getLong(key);
        } else if (short.class.isAssignableFrom(field.getType())) {
            return configuration.getShort(key);
        } else if (byte.class.isAssignableFrom(field.getType())) {
            return configuration.getByte(key);
        } else if (float.class.isAssignableFrom(field.getType())) {
            return configuration.getFloat(key);
        } else if (double.class.isAssignableFrom(field.getType())) {
            return configuration.getDouble(key);
        } else if (char.class.isAssignableFrom(field.getType())) {
            return configuration.getString(key).toCharArray()[0];
        } else if (String[].class.isAssignableFrom(field.getType())) {
            return configuration.getStringArray(key);
        } else if (String.class.isAssignableFrom(field.getType())) {
            return configuration.getString(key, null);
        } else if (Integer.class.isAssignableFrom(field.getType())) {
            return configuration.getInteger(key, Integer.valueOf(DEFAULT_INT));
        } else if (Long.class.isAssignableFrom(field.getType())) {
            return configuration.getLong(key, Long.valueOf(DEFAULT_LONG));
        } else if (Double.class.isAssignableFrom(field.getType())) {
            return configuration.getDouble(key, Double.valueOf(DEFAULT_DOUBLE));
        } else if (Float.class.isAssignableFrom(field.getType())) {
            return configuration.getFloat(key, Float.valueOf(DEFAULT_FLOAT));
        } else if (Boolean.class.isAssignableFrom(field.getType())) {
            return configuration.getBoolean(key, Boolean.valueOf(null));
        } else if (Byte.class.isAssignableFrom(field.getType())) {
            return configuration.getByte(key, Byte.valueOf(DEFAULT_BYTE));
        } else if (Short.class.isAssignableFrom(field.getType())) {
            return configuration.getShort(key, Short.valueOf(DEFAULT_SHORT));
        } else if (List.class.isAssignableFrom(field.getType())) {
            return configuration.getList(key, null);
        } else {
            throw new UnsupportedOperationException("not supported the value type");
        }
    }*/

}
