package net.pwall.json.kotlin.codegen.gradle.annotation

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.ExtensiblePolymorphicDomainObjectContainer

interface FileAnnotationContainer : ExtensiblePolymorphicDomainObjectContainer<FileAnnotation> {

    fun add(): FileAnnotationAdd

    fun add(closure: Closure<*>): FileAnnotationAdd

    fun add(action: Action<in FileAnnotationAdd>): FileAnnotationAdd

    @Suppress("unused")
    fun add(annotationName: String) = add {
        this.fileAnnotation.set(annotationName)
    }

    @Suppress("unused")
    fun add(fileAnnotation: String, params: List<String>) = add {
        this.fileAnnotation.set(fileAnnotation)
        this.params.set(params)
    }
}
