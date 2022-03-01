package net.pwall.json.kotlin.codegen.gradle.annotation

import net.pwall.json.schema.codegen.CodeGenerator
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import javax.inject.Inject

class FileAnnotationAdd @Inject constructor(name: String, project: Project) : FileAnnotation(name, project) {

    @Input
    @Optional
    val params = project.objects.listProperty(String::class.java)

    override fun applyTo(codeGenerator: CodeGenerator) {
        codeGenerator.addFileAnnotation(classId, params.get())
    }
}
