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

import static io.github.naverz.antonio.compiler.ConstantsKt.CLASS_NAME_AntonioAnnotation;
import static io.github.naverz.antonio.compiler.ConstantsKt.CLASS_NAME_AntonioFragment;
import static io.github.naverz.antonio.compiler.ConstantsKt.CLASS_NAME_AntonioSettings;
import static io.github.naverz.antonio.compiler.ConstantsKt.CLASS_NAME_FragmentBuilder;
import static io.github.naverz.antonio.compiler.ConstantsKt.CLASS_NAME_NonNull;
import static io.github.naverz.antonio.compiler.ConstantsKt.CLASS_NAME_PagerViewDependency;
import static io.github.naverz.antonio.compiler.ConstantsKt.CLASS_NAME_PagerViewDependencyBuilder;
import static io.github.naverz.antonio.compiler.ConstantsKt.CLASS_NAME_TypedViewHolder;
import static io.github.naverz.antonio.compiler.ConstantsKt.CLASS_NAME_ViewGroup;
import static io.github.naverz.antonio.compiler.ConstantsKt.CLASS_NAME_ViewHolderBuilder;
import static io.github.naverz.antonio.compiler.ConstantsKt.METHOD_initFragmentWithModel;
import static io.github.naverz.antonio.compiler.ConstantsKt.METHOD_initPagerViewWithModel;
import static io.github.naverz.antonio.compiler.ConstantsKt.METHOD_initViewHolderWithModel;
import static io.github.naverz.antonio.compiler.ConstantsKt.PACKAGE_NAME_ANDROID_ANNOTATION;
import static io.github.naverz.antonio.compiler.ConstantsKt.PACKAGE_NAME_ANTONIO;
import static io.github.naverz.antonio.compiler.ConstantsKt.PACKAGE_NAME_ANTONIO_CORE;
import static io.github.naverz.antonio.compiler.ConstantsKt.PACKAGE_NAME_ANTONIO_CORE_FRAGMENT;
import static io.github.naverz.antonio.compiler.ConstantsKt.PACKAGE_NAME_ANTONIO_CORE_HOLDER;
import static io.github.naverz.antonio.compiler.ConstantsKt.PACKAGE_NAME_ANTONIO_CORE_VIEW;
import static io.github.naverz.antonio.compiler.ConstantsKt.PACKAGE_NAME_VIEW;
import static io.github.naverz.antonio.compiler.ConstantsKt.PROPERTY_fragmentContainer;
import static io.github.naverz.antonio.compiler.ConstantsKt.PROPERTY_pagerViewContainer;
import static io.github.naverz.antonio.compiler.ConstantsKt.PROPERTY_viewHolderContainer;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

import io.github.naverz.antonio.annotations.MappedWithViewDependency;

public class AntonioProcessor extends AbstractProcessor {
    private static final String ADD_FORMAT =
            "                .add(%s, new $T() {\n" +
                    "                    @$T\n" +
                    "                    @Override\n" +
                    "                    public $T<?> build(@NonNull $T parent) {\n" +
                    "                        return new %s(parent);\n" +
                    "                    }\n" +
                    "                })";
    private static final String ADD_FORMAT_WITHOUT_VIEW_GROUP_PARAMETER =
            "                .add(%s, new $T() {\n" +
                    "                    @$T\n" +
                    "                    @Override\n" +
                    "                    public $T<?> build() {\n" +
                    "                        return new %s();\n" +
                    "                    }\n" +
                    "                })";
    private final ClassName viewGroupClass = ClassName.get(PACKAGE_NAME_VIEW, CLASS_NAME_ViewGroup);
    private final ClassName nonNullClass = ClassName.get(PACKAGE_NAME_ANDROID_ANNOTATION, CLASS_NAME_NonNull);
    private final ClassName antonioSettingClass = ClassName.get(PACKAGE_NAME_ANTONIO, CLASS_NAME_AntonioSettings);
    private final ClassName viewHolderBuilderClass = ClassName.get(PACKAGE_NAME_ANTONIO_CORE, CLASS_NAME_ViewHolderBuilder);
    private final ClassName pagerViewBuilderClass = ClassName.get(PACKAGE_NAME_ANTONIO_CORE, CLASS_NAME_PagerViewDependencyBuilder);
    private final ClassName fragmentBuilderClass = ClassName.get(PACKAGE_NAME_ANTONIO_CORE, CLASS_NAME_FragmentBuilder);
    private final ClassName typedViewHolderClass = ClassName.get(PACKAGE_NAME_ANTONIO_CORE_HOLDER, CLASS_NAME_TypedViewHolder);
    private final ClassName pagerViewClass = ClassName.get(PACKAGE_NAME_ANTONIO_CORE_VIEW, CLASS_NAME_PagerViewDependency);
    private final ClassName antonioFragmentClass = ClassName.get(PACKAGE_NAME_ANTONIO_CORE_FRAGMENT, CLASS_NAME_AntonioFragment);

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new LinkedHashSet<String>() {
            {
                add(MappedWithViewDependency.class.getCanonicalName());
            }
        };
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    @SuppressWarnings("UnhandledException")
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
            final List<MethodSpec> mapMethodSpecs = makeMethodSpec(roundEnvironment);
            if (!mapMethodSpecs.isEmpty()) {
                writeFile(mapMethodSpecs);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    protected ArrayList<MethodSpec> makeMethodSpec(RoundEnvironment roundEnvironment) throws IllegalStateException {
        final ArrayList<MethodSpec> mapMethodSpecs = new ArrayList<>();
        final BindDataSet bindDataSet = bindMappedWithModel(roundEnvironment);
        if (!bindDataSet.fragmentBinds.isEmpty()) {
            mapMethodSpecs.add(buildBuilderWithModelType(ViewDependencyType.Fragment, bindDataSet.fragmentBinds));
        }
        if (!bindDataSet.pagerViewBinds.isEmpty()) {
            mapMethodSpecs.add(buildBuilderWithModelType(ViewDependencyType.PagerView, bindDataSet.pagerViewBinds));
        }
        if (!bindDataSet.viewHolderBinds.isEmpty()) {
            mapMethodSpecs.add(buildBuilderWithModelType(ViewDependencyType.ViewHolder, bindDataSet.viewHolderBinds));
        }
        return mapMethodSpecs;
    }

    protected String removeGenericTypeIfExist(String className) {
        if (className.endsWith(">")) {
            int indexOfStart = className.indexOf("<");
            className = className.substring(0, indexOfStart);
        }
        return className;
    }

    protected void writeFile(List<MethodSpec> specs) throws IOException {
        final TypeSpec.Builder classBuilder = TypeSpec.classBuilder(CLASS_NAME_AntonioAnnotation)
                .addModifiers(Modifier.PUBLIC);
        final MethodSpec.Builder initMethodBuilder = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        for (MethodSpec spec : specs) {
            classBuilder.addMethod(spec);
            initMethodBuilder.addCode(spec.name + "();\n");
        }
        classBuilder.addMethod(initMethodBuilder.build());
        JavaFile.builder(PACKAGE_NAME_ANTONIO, classBuilder.build())
                .build().writeTo(processingEnv.getFiler());
    }


    private BindDataSet bindMappedWithModel(RoundEnvironment roundEnvironment) throws IllegalStateException {
        final ArrayList<BindData> viewHolderBinds = new ArrayList<>();
        final ArrayList<BindData> pagerViewBinds = new ArrayList<>();
        final ArrayList<BindData> fragmentBinds = new ArrayList<>();
        final BindDataSet dataSet = new BindDataSet(viewHolderBinds, pagerViewBinds, fragmentBinds);
        for (Element element : roundEnvironment.getElementsAnnotatedWith(MappedWithViewDependency.class)) {
            String modelClassType = removeGenericTypeIfExist(element.asType().toString());
            TypeMirror typeMirror = getMirrorType(element.getAnnotation(MappedWithViewDependency.class));
            ViewDependencyType type = getType(typeMirror);
            switch (type) {
                case PagerView:
                    pagerViewBinds.add(new BindData(type, modelClassType, typeMirror.toString()));
                    break;
                case Fragment:
                    fragmentBinds.add(new BindData(type, modelClassType, typeMirror.toString()));
                    break;
                case ViewHolder:
                    viewHolderBinds.add(new BindData(type, modelClassType, typeMirror.toString()));
                    break;
            }
        }
        return dataSet;
    }


    private MethodSpec buildBuilderWithModelType(ViewDependencyType type, List<BindData> bindDataList) {
        final String addFormat = type == ViewDependencyType.ViewHolder ? ADD_FORMAT : ADD_FORMAT_WITHOUT_VIEW_GROUP_PARAMETER;
        final String methodName;
        final String containerName;
        if (type == ViewDependencyType.PagerView) {
            methodName = METHOD_initPagerViewWithModel;
            containerName = PROPERTY_pagerViewContainer;
        } else if (type == ViewDependencyType.Fragment) {
            methodName = METHOD_initFragmentWithModel;
            containerName = PROPERTY_fragmentContainer;
        } else {
            methodName = METHOD_initViewHolderWithModel;
            containerName = PROPERTY_viewHolderContainer;
        }

        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .addCode(String.format("$T.%s\n", containerName), antonioSettingClass);
        int count = 0;
        for (BindData data : bindDataList) {
            String formatStr =
                    String.format(addFormat, data.modelClass + ".class", data.viewClass);
            if (type == ViewDependencyType.ViewHolder) {
                builder.addCode(formatStr, viewHolderBuilderClass, nonNullClass, typedViewHolderClass, viewGroupClass);
            } else if (type == ViewDependencyType.Fragment) {
                builder.addCode(formatStr, fragmentBuilderClass, nonNullClass, antonioFragmentClass);
            } else {
                builder.addCode(formatStr, pagerViewBuilderClass, nonNullClass, pagerViewClass);

            }
            if (count < bindDataList.size() - 1) {
                builder.addCode("\n");
                ++count;
            } else {
                builder.addCode(";");
            }
        }
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    private ViewDependencyType getType(TypeMirror mirror) throws IllegalStateException {
        TypeMirror processingMirror = mirror;
        while (true) {
            List<TypeMirror> types = (List<TypeMirror>) processingEnv.getTypeUtils().directSupertypes(processingMirror);
            if (types.isEmpty()) break;
            for (TypeMirror superTypesMirror : types) {
                String type = removeGenericTypeIfExist(superTypesMirror.toString());
                if (type.equals(antonioFragmentClass.canonicalName())) {
                    return ViewDependencyType.Fragment;

                } else if (type.equals(typedViewHolderClass.canonicalName())) {
                    return ViewDependencyType.ViewHolder;

                } else if (type.equals(pagerViewClass.canonicalName())) {
                    return ViewDependencyType.PagerView;
                }
            }
            processingMirror = types.get(0);
        }
        throw new IllegalStateException(Error.getErrorTextClassIsNotSupportedType(mirror.toString()));
    }

    private TypeMirror getMirrorType(MappedWithViewDependency model) {
        try {
            String name = model.viewClass().getName();
            throw new IllegalStateException(Error.ERROR_TEXT_UNKNOWN);
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
    }
}
