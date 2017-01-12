/*
 * Copyright 2016 Flipkart Internet, pvt ltd.
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

package com.flipkart.lyrics.processor.methods;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.lang.model.element.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shrey.garg on 26/11/16.
 */
public class EqualsAndHashCodeHandler extends Handler {

    public EqualsAndHashCodeHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        if (!tune.areHashCodeAndEqualsNeeded()) {
            return;
        }

        String className = metaInfo.getClassName();
        Map<String, FieldModel> fieldModels = typeModel.getFields();
        List<String> nonStaticFields = fieldModels.entrySet().stream()
                .filter(entry ->
                        (!entry.getValue().isExcludeFromEqualsAndHashCode() && !Arrays.stream(entry.getValue().getModifiers())
                                .anyMatch(modifier -> modifier.equals(Modifier.STATIC))))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        MethodSpec.Builder equalsBuilder = MethodSpec.methodBuilder("equals")
                .addModifiers(Modifier.PUBLIC)
                .returns(boolean.class)
                .addAnnotation(Override.class)
                .addParameter(Object.class, "o");

        MethodSpec.Builder hashCodeBuilder = MethodSpec.methodBuilder("hashCode")
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addAnnotation(Override.class);

        equalsBuilder.addStatement("if (this == o) return true");
        equalsBuilder.addStatement("if (!(o instanceof $L)) return false", className);
        equalsBuilder.addCode("\n");
        equalsBuilder.addStatement("$L that = ($L) o", className, className);
        equalsBuilder.addCode("\n");

        ClassName equalsBuilderClass = ClassName.get(EqualsBuilder.class);
        equalsBuilder.addCode("return new $T()\n", equalsBuilderClass);
        equalsBuilder.addCode("\t\t.appendSuper(super.equals(that))\n", equalsBuilderClass);

        ClassName hashCodeBuilderClass = ClassName.get(HashCodeBuilder.class);
        hashCodeBuilder.addCode("return new $T()\n", hashCodeBuilderClass);
        hashCodeBuilder.addCode("\t\t.appendSuper(super.hashCode())\n", hashCodeBuilderClass);
        for (String field : nonStaticFields) {
            equalsBuilder.addCode("\t\t.append($L, that.$L)\n", field, field);
            hashCodeBuilder.addCode("\t\t.append($L)\n", field);
        }
        equalsBuilder.addCode("\t\t.isEquals();\n");
        hashCodeBuilder.addCode("\t\t.toHashCode();\n");

        typeBuilder.addMethod(equalsBuilder.build());
        typeBuilder.addMethod(hashCodeBuilder.build());
    }
}