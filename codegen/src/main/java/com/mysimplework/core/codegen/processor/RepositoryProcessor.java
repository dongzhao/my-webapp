package com.mysimplework.core.codegen.processor;

import com.mysimplework.core.annotation.MyReferenceSearch;
import com.mysimplework.core.codegen.FreeMarkerWriter;
import com.mysimplework.core.annotation.MyRepository;
import com.mysimplework.core.annotation.MySearch;
import com.mysimplework.core.codegen.template.MySearchTemplate;
import com.mysimplework.core.codegen.template.MyRepositoryTemplate;
import com.mysimplework.core.enums.OperatorEnum;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.*;

/**
 * Created by dzhao on 22/09/2015.
 * CodeGen process for generating a number of Spring Repository class against a model class
 */
public class RepositoryProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes(){
        return new HashSet<String>(Arrays.asList(MyRepository.class.getName()));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("#####################################################");
        System.out.println("start generating repository class ...");

        for(TypeElement typeElement : annotations){
            if(!typeElement.getQualifiedName().toString().equalsIgnoreCase(MyRepository.class.getName())){
                continue;
            }
            Collection<? extends Element> types = roundEnv.getElementsAnnotatedWith(typeElement);
            for (Element element : types){
                StringBuilder targetSourcePath = new StringBuilder();
                MyRepository repository = element.getAnnotation(MyRepository.class);

                TypeElement classElement = (TypeElement)element;
                PackageElement packageElement = (PackageElement)classElement.getEnclosingElement();

                String modelSimpleName = classElement.getSimpleName().toString();
                String modelPackageName = packageElement.getQualifiedName().toString();
                String repositorySimpleName = modelSimpleName + "Repository";
                String repositoryPackageName = repository.packageName();
                if(repositoryPackageName.isEmpty()){
                    StringBuilder sb = new StringBuilder();
                    sb.append(modelPackageName.substring(0, modelPackageName.lastIndexOf(".model"))+".repositories");
                    repositoryPackageName = sb.toString();
                }

                MyRepositoryTemplate template = new MyRepositoryTemplate();
                template.setModelPackageName(modelPackageName);
                template.setRepositoryPackageName(repositoryPackageName);
                template.setModelSimpleName(modelSimpleName);
                template.setRepositorySimpleName(repositorySimpleName);
                template.setMySearchTemplates(new ArrayList<MySearchTemplate>());
                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD) {
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

/*                            StringBuilder sb = new StringBuilder();
                            sb.append("List<" + modelPackageName + "." + modelSimpleName + "> ");
                            sb.append("findBy");
                            sb.append(fieldName.substring(0, 1).toUpperCase());
                            sb.append(fieldName.substring(1, fieldName.length()));

                            if(!operator.equals(OperatorEnum.EQUALS)){
                                sb.append(operator.getValue());
                            }
                            sb.append("(" + fieldType + " " + fieldName + ");");*/

                            searchTemplate.setType(fieldType);
                            searchTemplate.setName(fieldName);
                            searchTemplate.setOperator(operator.getValue());
                            searchTemplate.setPageable(pageable);
                            searchTemplate.setSortable(sortable);

                            template.getMySearchTemplates().add(searchTemplate);
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

/*                if(repository.joinedMethod()!=null) {
                    for (GenerateJoinedMethod joinedMethod : repository.joinedMethod()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("List<" + modelSimpleName + "> ");
                        sb.append("findBy");

                        StringBuilder methodNameBuilder = new StringBuilder();
                        StringBuilder parameterBuilder = new StringBuilder();
                        for (MySearch method : joinedMethod.method()) {
                            OperatorEnum operator = method.opertor();
                            String fieldName = method.name();
                            System.out.println(" ### field name: " + fieldName);
                            System.out.println(" ### field type name: " + method.type());

*//*                            StringBuilder sb = new StringBuilder();
                            sb.append("List<" + modelSimpleName + "> ");
                            sb.append("findBy");*//*
                            if(methodNameBuilder.length() > 0){
                                methodNameBuilder.append("And");
                            }
                            methodNameBuilder.append(fieldName.substring(0, 1).toUpperCase());
                            methodNameBuilder.append(fieldName.substring(1, fieldName.length()));
                            if(!operator.equals(OperatorEnum.EQUALS)){
                                methodNameBuilder.append(operator.getValue());
                            }
                            if(parameterBuilder.length() > 0){
                                parameterBuilder.append(", ");
                            }
                            parameterBuilder.append(method.type() + " " + fieldName);
                        }
                        sb.append(methodNameBuilder);
                        sb.append("(" + parameterBuilder + ");");
                        template.getRepositoryMethods().add(sb.toString());
                    }
                }*/

                try {
                    String sourcePath = processingEnv.getFiler().getResource(StandardLocation.SOURCE_OUTPUT, "", "test").toUri().getPath();
                    targetSourcePath.append(sourcePath.substring(0, sourcePath.lastIndexOf("/target/")) + "/");
                    System.out.println("targetSourcePath: " + targetSourcePath.toString());
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                targetSourcePath.append(repository.target());
                targetSourcePath.append(repositoryPackageName.replace(".", "/") + "/");
                targetSourcePath.append(repositorySimpleName + ".java");
                FreeMarkerWriter writer = new FreeMarkerWriter("src/main/resources/repository.ftl", targetSourcePath.toString(), template);
                writer.write();
            }
        }
        return false;
    }

}
