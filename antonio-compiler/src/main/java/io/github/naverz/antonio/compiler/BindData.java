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

package io.github.naverz.antonio.compiler;

class BindData {

    final ViewDependencyType type;
    final String modelClass;
    final String viewClass;

    public BindData(ViewDependencyType type, String modelClass, String viewClass) {
        this.type = type;
        this.modelClass = modelClass;
        this.viewClass = viewClass;
    }
}
