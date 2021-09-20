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

object Antonio {
    const val NAME = "Antonio"
    const val DESCRIPTION =
        "Android library for the adapter view (RecyclerView, ViewPager, ViewPager2)"
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0-alpha"
    const val GROUP_ID = "io.github.naverz"
    const val REPOSITORY_HTTP_URL = "https://github.com/naverz/Antonio/"
    const val REPOSITORY_SCM_GIT_URL = "scm:git@github.com:naverz/Antonio.git"
    const val CORE = "io.github.naverz:antonio-core:$VERSION_NAME"
    const val PAGING2_CORE = "io.github.naverz:antonio-core-paging2:$VERSION_NAME"
    const val PAGING3_CORE = "io.github.naverz:antonio-core-paging3:$VERSION_NAME"
    const val DATA_BINDING = "io.github.naverz:antonio-databinding:$VERSION_NAME"
    const val DATA_BINDING_PAGING3 = "io.github.naverz:antonio-databinding-paging3:$VERSION_NAME"
    const val NORMAL = "io.github.naverz:antonio:$VERSION_NAME"
}

object AntonioAnnotation {
    const val VERSION_NAME = "0.0.1-alpha"
    const val COMPILER = "io.github.naverz:antonio-compiler:${VERSION_NAME}"
    const val ANNOTATION = "io.github.naverz:antonio-annotation:${VERSION_NAME}"
}

object Version {
    const val KOTLIN_VERSION = "1.5.21"
}

object Android {
    const val PAGING2 = "androidx.paging:paging-runtime:2.1.2"
    const val PAGING3 = "androidx.paging:paging-runtime:3.0.1"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:1.3.6"
    const val LIFECYCLE_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.2.1"
    const val VIEW_PAGER2 = "androidx.viewpager2:viewpager2:1.0.0"

    // For sample module
    const val LIFECYCLE_VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-alpha03"
    const val LIFECYCLE_LIVE_DATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0-alpha03"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.0"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:1.3.1"
    const val MATERIAL = "com.google.android.material:material:1.4.0"
    const val APP_COMPAT = "androidx.appcompat:appcompat:1.3.1"
    const val CORE_KTX = "androidx.core:core-ktx:1.6.0"
}

object Nav {
    const val FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:2.3.5"
    const val UI_KTX = "androidx.navigation:navigation-ui-ktx:2.3.5"
}


object Gradle {
    const val BUILD_GRADLE = "com.android.tools.build:gradle:7.0.2"
    const val GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30"
}

object DataBinding {
    const val COMPILER = "com.android.databinding:compiler:3.5.0"
}

object Test {
    const val JUNIT = "junit:junit:4.13.2"
    const val EXT_JUNIT = "androidx.test.ext:junit:1.1.3"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:3.4.0"
}

object Processor {
    const val AUTO_SERVICE = "com.google.auto.service:auto-service:1.0"
    const val JAVA_POET = "com.squareup:javapoet:1.13.0"
}

object Maven {
    const val REPOSITORY_URL = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
    const val SNAP_SHOP_REPOSITORY_URL =
        "https://s01.oss.sonatype.org/content/repositories/snapshots/"
}