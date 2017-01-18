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

package com.flipkart.lyrics.android.config;

import com.flipkart.lyrics.android.annotators.validations.AndroidValidationStyle;
import com.flipkart.lyrics.android.sets.AndroidCreatorSet;
import com.flipkart.lyrics.android.sets.AndroidFieldTypeHandlerSet;
import com.flipkart.lyrics.android.sets.AndroidHandlerSet;
import com.flipkart.lyrics.annotators.validations.ValidationAnnotatorStyle;
import com.flipkart.lyrics.config.DefaultTune;
import com.flipkart.lyrics.sets.CreatorSet;
import com.flipkart.lyrics.sets.FieldTypeHandlerSet;
import com.flipkart.lyrics.sets.HandlerSet;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shrey.garg on 15/01/17.
 */
public abstract class AndroidTune extends DefaultTune {

    private final FieldTypeHandlerSet fieldTypeHandlerSet = new AndroidFieldTypeHandlerSet();

    public abstract List<String> createStringDefsFor();

    @Override
    public CreatorSet getCreatorSet() {
        return new AndroidCreatorSet();
    }

    @Override
    public HandlerSet getHandlerSet() {
        return new AndroidHandlerSet();
    }

    @Override
    public FieldTypeHandlerSet getFieldTypeHandlerSet() {
        return fieldTypeHandlerSet;
    }

    @Override
    public List<ValidationAnnotatorStyle> getValidationAnnotatorStyles() {
        return Arrays.asList(
                new AndroidValidationStyle()
        );
    }
}
