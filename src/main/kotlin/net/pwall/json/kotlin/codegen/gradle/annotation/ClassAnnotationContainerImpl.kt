package net.pwall.json.kotlin.codegen.gradle.annotation

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.ExtensiblePolymorphicDomainObjectContainer
import org.gradle.api.Project

class ClassAnnotationContainerImpl(project: Project, delegate: ExtensiblePolymorphicDomainObjectContainer<ClassAnnotation>) :
        ClassAnnotationContainer, ExtensiblePolymorphicDomainObjectContainer<ClassAnnotation> by delegate {

    init {
        registerFactory(ClassAnnotationAdd::class.java) { name -> ClassAnnotationAdd(name, project) }
    }

    override fun add(): ClassAnnotationAdd = add {}

    override fun add(closure: Closure<*>): ClassAnnotationAdd {
        return create(generatedName, ClassAnnotationAdd::class.java) {
            configure(closure)
        }
    }

    override fun add(action: Action<in ClassAnnotationAdd>): ClassAnnotationAdd {
        return create(generatedName, ClassAnnotationAdd::class.java) {
            action.execute(this)
        }
    }

    companion object {
        private var generationNumber = 0

        val generatedName: String
            get() = "mapping${generationNumber++}"
    }
}
