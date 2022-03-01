package net.pwall.json.kotlin.codegen.gradle.annotation

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.ExtensiblePolymorphicDomainObjectContainer
import org.gradle.api.Project

class FileAnnotationContainerImpl(project: Project, delegate: ExtensiblePolymorphicDomainObjectContainer<FileAnnotation>) :
    FileAnnotationContainer, ExtensiblePolymorphicDomainObjectContainer<FileAnnotation> by delegate {

    init {
        registerFactory(FileAnnotationAdd::class.java) { name -> FileAnnotationAdd(name, project) }
    }

    override fun add(): FileAnnotationAdd = add {}

    override fun add(closure: Closure<*>): FileAnnotationAdd {
        return create(generatedName, FileAnnotationAdd::class.java) {
            configure(closure)
        }
    }

    override fun add(action: Action<in FileAnnotationAdd>): FileAnnotationAdd {
        return create(generatedName, FileAnnotationAdd::class.java) {
            action.execute(this)
        }
    }

    companion object {
        private var generationNumber = 0

        val generatedName: String
            get() = "mapping${generationNumber++}"
    }
}
