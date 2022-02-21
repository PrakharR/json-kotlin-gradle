package net.pwall.json.kotlin.codegen.gradle.annotation

import net.pwall.json.schema.codegen.CodeGenerator
import org.gradle.api.Project
import javax.inject.Inject

class ClassAnnotationAdd @Inject constructor(name: String, project: Project) : ClassAnnotation(name, project) {

    override fun applyTo(codeGenerator: CodeGenerator) {
        codeGenerator.addClassAnnotation(classAnnotation.get())
    }
}
