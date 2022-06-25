package io.github.naverz.antonio.compiler

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.CodeBlock
import io.github.naverz.antonio.annotations.MappedWithViewDependency

fun KSAnnotated.makeAddingContainerModel(): AddingContainerModel {
    val annotation = this.annotations.findMappedWithViewDependencyAnnotation()!!
    val viewDependencyClass =
        (((annotation.arguments.first().value as KSType).declaration) as KSClassDeclaration)
    val antonioModelClass = (annotation.parent as KSClassDeclaration)

    var viewDependencyType: ViewDependencyType? = null
    viewDependencyClass.getAllSuperTypes().forEach {
        viewDependencyType = when (it.declaration.qualifiedName?.asString()) {
            typedViewHolderQualifiedName -> ViewDependencyType.ViewHolder
            pagerViewDependencyQualifiedName -> ViewDependencyType.PagerView
            antonioFragmentQualifiedName -> ViewDependencyType.Fragment
            else -> return@forEach
        }
    }
    val parameters = viewDependencyClass.primaryConstructor!!.parameters
    when (viewDependencyType) {
        ViewDependencyType.ViewHolder -> {
            val typeSimpleName =
                viewDependencyClass.primaryConstructor!!.parameters.first().type.toString()
            if (parameters.size != 1 && typeSimpleName != CLASS_NAME_ViewGroup) {
                throw IllegalArgumentException(Error.ERROR_TEXT_WRONG_VIEW_HOLDER_PARAMETER)
            }
        }
        else -> {
            if (parameters.isNotEmpty()) {
                throw IllegalArgumentException(Error.ERROR_TEXT_WRONG_PARAMETER_FOR_VIEW_DEPENDENCIES)
            }
        }
    }

    return viewDependencyType?.let {
        AddingContainerModel(
            it, it.makeAddCodeBlock(antonioModelClass, viewDependencyClass)
        )
    } ?: throw IllegalStateException(
        Error.getErrorTextClassIsNotSupportedType(
            viewDependencyClass.simpleName.asString()
        )
    )
}

data class AddingContainerModel(
    val viewDependencyType: ViewDependencyType,
    val addFormatText: CodeBlock
)

private const val ADD_FORMAT = """
AntonioSettings.%L.add(%L) {
    return@add %L(it)
}    
"""
private const val ADD_FORMAT_WITHOUT_VIEW_GROUP_PARAMETER = """
AntonioSettings.%L.add(%L) {
    return@add %L()
}  
"""

private fun ViewDependencyType.makeAddCodeBlock(
    classFromAnnotationArgument: KSClassDeclaration,
    annotationAttachedClass: KSClassDeclaration
): CodeBlock {
    val addFormat = when (this) {
        ViewDependencyType.Fragment, ViewDependencyType.PagerView ->
            ADD_FORMAT_WITHOUT_VIEW_GROUP_PARAMETER
        ViewDependencyType.ViewHolder ->
            ADD_FORMAT
    }
    val container = when (this) {
        ViewDependencyType.Fragment -> PROPERTY_fragmentContainer
        ViewDependencyType.PagerView -> PROPERTY_pagerViewContainer
        ViewDependencyType.ViewHolder -> PROPERTY_viewHolderContainer
    }
    return CodeBlock.of(
        addFormat,
        container,
        "${classFromAnnotationArgument.qualifiedName!!.asString()}::class.java",
        annotationAttachedClass.qualifiedName!!.asString()
    )
}

private const val typedViewHolderQualifiedName =
    "$PACKAGE_NAME_ANTONIO_CORE_HOLDER.$CLASS_NAME_TypedViewHolder"
private const val pagerViewDependencyQualifiedName =
    "$PACKAGE_NAME_ANTONIO_CORE_VIEW.$CLASS_NAME_PagerViewDependency"
private const val antonioFragmentQualifiedName =
    "$PACKAGE_NAME_ANTONIO_CORE_FRAGMENT.$CLASS_NAME_AntonioFragment"


private fun Sequence<KSAnnotation>.findMappedWithViewDependencyAnnotation(): KSAnnotation? =
    this.find { it.shortName.asString() == MappedWithViewDependency::class.simpleName }