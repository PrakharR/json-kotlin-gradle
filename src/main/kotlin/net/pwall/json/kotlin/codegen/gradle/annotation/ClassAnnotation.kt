package net.pwall.json.kotlin.codegen.gradle.annotation

import net.pwall.json.schema.codegen.ClassId
import net.pwall.json.schema.codegen.ClassName
import net.pwall.json.schema.codegen.CodeGenerator
import org.gradle.api.Named
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

abstract class ClassAnnotation @Inject constructor(@Input val name0: String, project: Project) : Named {

    override fun getName(): String = name0

    @Input
    val classAnnotation = project.objects.property<String>()

    abstract fun applyTo(codeGenerator: CodeGenerator)

    val classId: ClassId
        get() = classAnnotation.get().let {
            val i = it.lastIndexOf('.')
            if (i < 0)
                ClassName(it)
            else
                ClassName(it.substring(i + 1), it.substring(0, i))
        }
}
