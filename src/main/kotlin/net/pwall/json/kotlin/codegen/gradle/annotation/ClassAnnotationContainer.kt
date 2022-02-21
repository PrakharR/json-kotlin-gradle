package net.pwall.json.kotlin.codegen.gradle.annotation

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.ExtensiblePolymorphicDomainObjectContainer

interface ClassAnnotationContainer : ExtensiblePolymorphicDomainObjectContainer<ClassAnnotation> {

    fun add(): ClassAnnotationAdd

    fun add(closure: Closure<*>): ClassAnnotationAdd

    fun add(action: Action<in ClassAnnotationAdd>): ClassAnnotationAdd

    @Suppress("unused")
    fun add(annotationName: String) = add {
        this.classAnnotation.set(annotationName)
    }
}
