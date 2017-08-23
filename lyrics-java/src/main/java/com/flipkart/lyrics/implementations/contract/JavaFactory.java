package com.flipkart.lyrics.implementations.contract;

import com.flipkart.lyrics.implementations.*;
import com.flipkart.lyrics.interfaces.*;
import com.flipkart.lyrics.interfaces.contract.Factory;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Kind;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

/**
 * @author kushal.sharma on 09/08/17.
 */
public class JavaFactory implements Factory {
    @Override
    public MethodSpec.Builder createConstructorBuilder() {
        return new JavaMethodSpec.Builder();
    }

    @Override
    public MethodSpec.Builder createMethodBuilder(String name) {
        return new JavaMethodSpec.Builder(name);
    }

    @Override
    public AnnotationSpec.Builder createAnnotationBuilder(Class clazz) {
        return new JavaAnnotationSpec.Builder(clazz);
    }

    @Override
    public AnnotationSpec.Builder createAnnotationBuilder(ClassName className) {
        return new JavaAnnotationSpec.Builder(className);
    }

    @Override
    public TypeSpec.Builder createClassBuilder(String name) {
        return new JavaTypeSpec.Builder(Kind.CLASS, name);
    }

    @Override
    public TypeSpec.Builder createAnnotationBuilder(String name) {
        return new JavaTypeSpec.Builder(Kind.ANNOTATION, name);
    }

    @Override
    public TypeSpec.Builder createInterfaceBuilder(String name) {
        return new JavaTypeSpec.Builder(Kind.INTERFACE, name);
    }

    @Override
    public TypeSpec.Builder createEnumBuilder(String name) {
        return new JavaTypeSpec.Builder(Kind.ENUM, name);
    }

    @Override
    public TypeSpec.Builder createAnonymousClassBuilder(String typeArgumentsFormat, Object... args) {
        return new JavaTypeSpec.Builder(Kind.ANONYMOUS, typeArgumentsFormat, args);
    }

    @Override
    public FieldSpec.Builder createFieldBuilder(TypeName typeName, String name, Modifier[] modifiers) {
        return new JavaFieldSpec.Builder(typeName, name, modifiers);
    }

    @Override
    public FieldSpec.Builder createFieldBuilder(Class<?> clazz, String name, Modifier[] modifiers) {
        return new JavaFieldSpec.Builder(clazz, name, modifiers);
    }

    @Override
    public ParameterSpec.Builder createParameterBuilder(TypeName typeName, String name, Modifier[] modifiers) {
        return new JavaParameterSpec.Builder(typeName, name, modifiers);
    }

    @Override
    public ParameterSpec.Builder createParameterBuilder(Class<?> clazz, String name, Modifier[] modifiers) {
        return new JavaParameterSpec.Builder(clazz, name, modifiers);
    }
}
