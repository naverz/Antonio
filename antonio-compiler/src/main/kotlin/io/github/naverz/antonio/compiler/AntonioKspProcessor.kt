package io.github.naverz.antonio.compiler

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo
import io.github.naverz.antonio.annotations.MappedWithViewDependency

@OptIn(KotlinPoetKspPreview::class)
class AntonioKspProcessor(
    private val codeGenerator: CodeGenerator, private val logger: KSPLogger
) : SymbolProcessor {

    private val fragmentInitFileBuilder = FileSpec.builder(
        packageName = PACKAGE_NAME_ANTONIO,
        fileName = FILE_AntonioFragmentInitExtension
    ).addImport(
        PACKAGE_NAME_ANTONIO, CLASS_NAME_AntonioSettings
    )
    private val viewHolderInitFileBuilder = FileSpec.builder(
        packageName = PACKAGE_NAME_ANTONIO,
        fileName = FILE_AntonioViewHolderInitExtension
    ).addImport(
        PACKAGE_NAME_ANTONIO, CLASS_NAME_AntonioSettings
    )
    private val pagerViewInitFileBuilder = FileSpec.builder(
        packageName = PACKAGE_NAME_ANTONIO,
        fileName = FILE_AntonioPagerViewInitExtension
    ).addImport(
        PACKAGE_NAME_ANTONIO, CLASS_NAME_AntonioSettings
    )
    private val fragmentInitCodeBlocks = mutableListOf<CodeBlock>()
    private val pagerViewInitCodeBlocks = mutableListOf<CodeBlock>()
    private val viewHolderInitCodeBlocks = mutableListOf<CodeBlock>()
    private val fragmentOriginatingKSFiles = mutableListOf<KSFile>()
    private val pagerViewOriginatingKSFiles = mutableListOf<KSFile>()
    private val viewHolderOriginatingKSFiles = mutableListOf<KSFile>()

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val mappedWithViewDependencySymbols =
            resolver.getSymbolsWithAnnotation(MappedWithViewDependency::class.java.canonicalName)

        mappedWithViewDependencySymbols.forEach { classDeclaration ->
            if (classDeclaration !is KSClassDeclaration || classDeclaration.classKind != ClassKind.CLASS || !classDeclaration.validate()) {
                val message =
                    "${MappedWithViewDependency::class} annotation is only for the class type"
                logger.error(message, classDeclaration)
                return@forEach
            }
            val (viewDependencyType, addFormatText) = classDeclaration.makeAddingContainerModel()

            when (viewDependencyType) {
                ViewDependencyType.Fragment -> {
                    fragmentInitCodeBlocks.add(addFormatText)
                    fragmentOriginatingKSFiles.add(classDeclaration.containingFile!!)
                }
                ViewDependencyType.PagerView -> {
                    pagerViewInitCodeBlocks.add(addFormatText)
                    pagerViewOriginatingKSFiles.add(classDeclaration.containingFile!!)
                }
                ViewDependencyType.ViewHolder -> {
                    viewHolderInitCodeBlocks.add(addFormatText)
                    viewHolderOriginatingKSFiles.add(classDeclaration.containingFile!!)
                }
            }
        }
        return emptyList()
    }

    override fun finish() {
        super.finish()
        if (fragmentInitCodeBlocks.isEmpty() && pagerViewInitCodeBlocks.isEmpty() && viewHolderInitCodeBlocks.isEmpty()) return
        val fragmentInitFunc =
            FunSpec.builder(METHOD_initFragmentWithModel).addModifiers(KModifier.INTERNAL)
        val pagerInitFunc =
            FunSpec.builder(METHOD_initPagerViewWithModel).addModifiers(KModifier.INTERNAL)
        val viewHolderInitFunc =
            FunSpec.builder(METHOD_initViewHolderWithModel).addModifiers(KModifier.INTERNAL)
        fragmentInitCodeBlocks.forEach { fragmentInitFunc.addCode(it) }
        pagerViewInitCodeBlocks.forEach { pagerInitFunc.addCode(it) }
        viewHolderInitCodeBlocks.forEach { viewHolderInitFunc.addCode(it) }
        fragmentInitFileBuilder.addFunction(fragmentInitFunc.build())
        pagerViewInitFileBuilder.addFunction(pagerInitFunc.build())
        viewHolderInitFileBuilder.addFunction(viewHolderInitFunc.build())

        fragmentInitFileBuilder.build()
            .writeTo(
                codeGenerator = codeGenerator,
                dependencies = Dependencies(
                    aggregating = true,
                    sources = fragmentOriginatingKSFiles.toTypedArray()
                )
            )
        pagerViewInitFileBuilder.build()
            .writeTo(
                codeGenerator = codeGenerator,
                dependencies = Dependencies(
                    aggregating = true,
                    sources = pagerViewOriginatingKSFiles.toTypedArray()
                )
            )
        viewHolderInitFileBuilder.build()
            .writeTo(
                codeGenerator = codeGenerator,
                dependencies = Dependencies(
                    aggregating = true,
                    sources = viewHolderOriginatingKSFiles.toTypedArray()
                )
            )
        initFileBuilder()
    }

    private fun initFileBuilder() {
        val initFunction = FunSpec.builder("init").apply {
            addCode(CodeBlock.of("%L()\n", METHOD_initPagerViewWithModel))
            addCode(CodeBlock.of("%L()\n", METHOD_initFragmentWithModel))
            addCode(CodeBlock.of("%L()", METHOD_initViewHolderWithModel))
        }.build()
        FileSpec.builder(
            packageName = PACKAGE_NAME_ANTONIO,
            fileName = CLASS_NAME_AntonioAnnotation
        ).addType(
            TypeSpec.objectBuilder(CLASS_NAME_AntonioAnnotation)
                .addFunction(initFunction).build()
        ).build().writeTo(codeGenerator = codeGenerator, aggregating = false, listOf())
    }
}

class AntonioKspProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return AntonioKspProcessor(environment.codeGenerator, environment.logger)
    }
}
