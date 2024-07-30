/*
 * Antonio
 * Copyright (c) 2021-present NAVER Z Corp.
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

package io.github.naverz.antonio.buildsrc

const val TARGET_SDK = 34
const val COMPILE_SDK = 34

const val KSP_VERSION = "1.9.25-1.0.20"

object Antonio {
    private const val VERSION_NAME = "1.0.9-alpha"
    const val DATA_BINDING = "io.github.naverz:antonio-databinding:$VERSION_NAME"
    const val DATA_BINDING_PAGING3 = "io.github.naverz:antonio-databinding-paging3:$VERSION_NAME"
    const val NORMAL = "io.github.naverz:antonio:$VERSION_NAME"
}

object AntonioAnnotation {
    const val VERSION_NAME = "0.0.8-alpha"
    const val COMPILER = "io.github.naverz:antonio-compiler:${VERSION_NAME}"
    const val ANNOTATION = "io.github.naverz:antonio-annotation:${VERSION_NAME}"
}

object Android {
    const val PAGING2 = "androidx.paging:paging-runtime:2.1.2"
    const val PAGING3 = "androidx.paging:paging-runtime:3.2.1"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:1.6.2"
    const val LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:2.6.2"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.2.1"
    const val FLEX_BOX = "com.google.android.flexbox:flexbox:3.0.0"
    const val VIEW_PAGER2 = "androidx.viewpager2:viewpager2:1.0.0"

    // For sample module
    const val LIFECYCLE_VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:1.8.0"
    const val MATERIAL = "com.google.android.material:material:1.10.0"
    const val APP_COMPAT = "androidx.appcompat:appcompat:1.6.1"
    const val CORE_KTX = "androidx.core:core-ktx:1.12.0"
}

object Nav {
    const val FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:2.7.5"
    const val UI_KTX = "androidx.navigation:navigation-ui-ktx:2.7.5"
}

object TestDep {
    const val JUNIT = "junit:junit:4.13.2"
    const val EXT_JUNIT = "androidx.test.ext:junit:1.1.3"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:3.4.0"
}

object Processor {
    const val JAVA_POET = "com.squareup:javapoet:1.13.0"
    const val KOTLIN_POET = "com.squareup:kotlinpoet:1.11.0"
    const val KOTLIN_POET_KSP = "com.squareup:kotlinpoet-ksp:1.11.0"
    const val KSP = "com.google.devtools.ksp:symbol-processing-api:${KSP_VERSION}"
}

object Incap {
    const val INCAP = "net.ltgt.gradle.incap:incap:0.3"
    const val PROCESSOR = "net.ltgt.gradle.incap:incap-processor:0.3"
}