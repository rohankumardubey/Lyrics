/*
 * Copyright 2017 Flipkart Internet, pvt ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.helper.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.flipkart.lyrics.helper.Util.checkNotNull;
import static com.flipkart.lyrics.helper.Util.checkState;

public class ParameterSpec {
    public final String name;
    public final List<AnnotationSpec> annotations;
    public final Set<Modifier> modifiers;
    public final TypeName type;
    public final boolean nullable;
    public final CodeBlock initializer;
    public final String label;
    public final boolean required;

    public ParameterSpec(Builder builder) {
        this.name = checkNotNull(builder.name, "name == null");
        this.annotations = Util.immutableList(builder.annotations);
        this.modifiers = Util.immutableSet(builder.modifiers);
        this.type = checkNotNull(builder.type, "type == null");
        this.nullable = builder.nullable;
        this.initializer = (builder.initializer == null)
                ? CodeBlock.builder().build()
                : builder.initializer;
        this.label = builder.label;
        this.required = builder.required;
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return new Builder(typeName, name, modifiers);
    }

    public static Builder builder(Class<?> clazz, String name, Modifier... modifiers) {
        return new Builder(TypeName.get(clazz), name, modifiers);
    }

    public boolean hasModifier(Modifier modifier) {
        return modifiers.contains(modifier);
    }

    public Builder toBuilder() {
        return toBuilder(type, name);
    }

    Builder toBuilder(TypeName type, String name) {
        Builder builder = new Builder(type, name);
        builder.annotations.addAll(annotations);
        builder.modifiers.addAll(modifiers);
        builder.nullable = nullable;
        builder.initializer = initializer.formats.isEmpty() ? null : initializer;
        builder.label = label;
        builder.required = required;
        return builder;
    }

    public static class Builder {
        private final TypeName type;
        private final String name;
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private final List<Modifier> modifiers = new ArrayList<>();
        private boolean nullable = false;
        private CodeBlock initializer = null;
        private String label;
        private boolean required;

        private Builder(TypeName type, String name, Modifier... modifiers) {
            this.name = name;
            this.type = type;
            Collections.addAll(this.modifiers, modifiers);
        }

        public Builder required(boolean required) {
            this.required = required;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder nullable(boolean nullable) {
            this.nullable = nullable;
            return this;
        }

        public Builder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
            for (AnnotationSpec annotationSpec : annotationSpecs) {
                this.annotations.add(annotationSpec);
            }
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotations.add(annotationSpec);
            return this;
        }

        public Builder addAnnotation(ClassName annotation) {
            this.annotations.add(AnnotationSpec.builder(annotation).build());
            return this;
        }

        public Builder addAnnotation(Class<?> annotation) {
            return addAnnotation(ClassName.get(annotation));
        }

        public Builder addModifiers(Modifier... modifiers) {
            Collections.addAll(this.modifiers, modifiers);
            return this;
        }

        public Builder addModifiers(Iterable<Modifier> modifiers) {
            for (Modifier modifier : modifiers) {
                this.modifiers.add(modifier);
            }
            return this;
        }

        public Builder initializer(String format, Object... args) {
            return initializer(CodeBlock.of(format, args));
        }

        public Builder initializer(CodeBlock codeBlock) {
            checkState(this.initializer == null, "initializer was already set");
            this.initializer = checkNotNull(codeBlock, "codeBlock == null");
            return this;
        }

        public ParameterSpec build() {
            return new ParameterSpec(this);
        }
    }
}
