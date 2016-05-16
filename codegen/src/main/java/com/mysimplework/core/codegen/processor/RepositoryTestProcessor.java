package com.mysimplework.core.codegen.processor;

import com.mysimplework.core.annotation.MyRepositoryTest;
import com.mysimplework.core.annotation.Testable;
import com.mysimplework.core.codegen.FreeMarkerWriter;
import com.mysimplework.core.codegen.PojoConvertor;
import com.mysimplework.core.codegen.template.MyFieldTestTemplate;
import com.mysimplework.core.codegen.template.MyRepositoryTestTemplate;

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
 *
 */
public class RepositoryTestProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes(){
        return new HashSet<String>(Arrays.asList(MyRepositoryTest.class.getName()));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("#####################################################");
        System.out.println("start generating unit test class ...");
        for(TypeElement typeElement : annotations){
            if(!typeElement.getQualifiedName().toString().equalsIgnoreCase(MyRepositoryTest.class.getName())){
                continue;
            }
            Collection<? extends Element> types = roundEnv.getElementsAnnotatedWith(typeElement);
            for (Element element : types){

                StringBuilder targetSourcePath = new StringBuilder();
                MyRepositoryTest test = element.getAnnotation(MyRepositoryTest.class);

                TypeElement classElement = (TypeElement)element;
                PackageElement packageElement = (PackageElement)classElement.getEnclosingElement();

                String modelSimpleName = classElement.getSimpleName().toString();
                String modelPackageName = packageElement.getQualifiedName().toString();

                // repository package name
                String repositorySimpleName = modelSimpleName + "Repository";
                String repositoryPackageName = test.repositoryPackageName();
                if(repositoryPackageName.isEmpty()){
                    StringBuilder sb = new StringBuilder();
                    sb.append(modelPackageName.substring(0, modelPackageName.lastIndexOf(".model"))+".repositories");
                    repositoryPackageName = sb.toString();
                }
                // unit test name
                String simpleName = test.simpleName();
                if(simpleName.isEmpty()){
                    simpleName = modelSimpleName + "PersistentTest";
                }
                // unit package name
                String pkgName = test.packageName();
                if(pkgName.isEmpty()){
                    pkgName = repositoryPackageName + "." + "test";
                }

                MyRepositoryTestTemplate template = new MyRepositoryTestTemplate();
                template.setModelPackageName(modelPackageName);
                template.setRepositoryPackageName(repositoryPackageName);
                template.setModelSimpleName(modelSimpleName);
                template.setRepositorySimpleName(repositorySimpleName);
                template.setUnitTestSimpleName(simpleName);
                template.setUnitTestPackageName(pkgName);
                template.setMyFieldTestTemplates(new ArrayList<MyFieldTestTemplate>());
                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.FIELD) {
                        Testable testable = enclosedElement.getAnnotation(Testable.class);
                        if(testable!=null){
                            String fieldType = enclosedElement.asType().toString();
                            MyFieldTestTemplate myFieldTestTemplate = new MyFieldTestTemplate();
                            myFieldTestTemplate.setName(enclosedElement.getSimpleName().toString());
                            myFieldTestTemplate.setType(fieldType);
                            myFieldTestTemplate.setInputValue(PojoConvertor.convertTo(testable.input(), fieldType));
                            String output = testable.output().isEmpty() ? testable.input() : testable.output();
                            myFieldTestTemplate.setOutputValue(PojoConvertor.convertTo(output, fieldType));
                            template.getMyFieldTestTemplates().add(myFieldTestTemplate);
                        }
                    }
                }

                try {
                    String sourcePath = processingEnv.getFiler().getResource(StandardLocation.SOURCE_OUTPUT, "", "test").toUri().getPath();
                    targetSourcePath.append(sourcePath.substring(0, sourcePath.lastIndexOf("/target/")) + "/");
                    System.out.println("targetSourcePath: " + targetSourcePath.toString());
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                targetSourcePath.append(test.target());
                targetSourcePath.append(pkgName.replace(".", "/") + "/");
                targetSourcePath.append(simpleName + ".java");
                FreeMarkerWriter writer = new FreeMarkerWriter("src/main/resources/repositorytest.ftl", targetSourcePath.toString(), template);
                writer.write();
            }
        }
        return false;
    }
}
