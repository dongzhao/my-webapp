package com.mysimplework.core.codegen.processor;

import com.mysimplework.core.annotation.MyReferenceSearch;
import com.mysimplework.core.annotation.MySearch;
import com.mysimplework.core.codegen.FreeMarkerWriter;
import com.mysimplework.core.annotation.MyRestController;
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
                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD) {
                        MySearch mySearch = enclosedElement.getAnnotation(MySearch.class);
                        if(mySearch !=null) {
                            Column column = enclosedElement.getAnnotation(Column.class);
                            if(column != null){
                                System.out.println(" ### JPA column annotation ...");
                            }
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

}
