/*******************************************************************************
 * Copyright (c) 2014, 2015  Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Dmitry Kornilov - Initial implementation
 ******************************************************************************/
package org.eclipse.persistence.internal.jpa.weaving;

import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.dynamic.EclipseLinkClassWriter;
import org.eclipse.persistence.internal.libraries.asm.ClassWriter;
import org.eclipse.persistence.internal.libraries.asm.MethodVisitor;
import org.eclipse.persistence.internal.libraries.asm.Opcodes;

/**
 * This class is used to generate XML type adapters for single entity references in JPARS 2.0.
 * The generated classes are subclasses of {@see RestReferenceV2Adapter}.
 *
 * @author Dmitry Kornilov
 * @since EclipseLink 2.6.0
 */
public class RestReferenceAdapterV2ClassWriter implements EclipseLinkClassWriter, Opcodes {
    public static final String CLASS_NAME_SUFFIX = "RestReferenceV2Adapter";
    public static final String REFERENCE_ADAPTER_SHORT_SIGNATURE = "org/eclipse/persistence/jpa/rs/util/xmladapters/ReferenceAdapterV2";

    private String parentClassName;

    /**
     * Creates a new RestReferenceAdapterV2ClassWriter.
     *
     * @param parentClassName superclass name.
     */
    public RestReferenceAdapterV2ClassWriter(String parentClassName){
        this.parentClassName = parentClassName;
    }

    /**
     * Returns a class name of reference adapter for given class name.
     *
     * @param className class name of the class to generate adapter.
     * @return the adapter name.
     */
    public static String getClassName(String className){
        final int index = className.lastIndexOf('.');
        final String packageName = index >= 0 ? className.substring(0, index) : "";
        final String shortClassName = index >= 0 ? className.substring(index + 1) : className;
        return packageName + "._" + shortClassName + CLASS_NAME_SUFFIX;
    }

    /**
     * Returns a class name of reference adapter.
     *
     * @return the adapter name.
     */
    public String getClassName(){
        return getClassName(parentClassName);
    }

    /**
     *  public class Adapter extends ReferenceAdapterV2<T> {
     *      public Adapter() {
     *          super();
     *      }
     *
     *      public Adapter(PersistentContext context) {
     *          super(context);
     *      }
     *  }
     *
     * @param loader
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public byte[] writeClass(DynamicClassLoader loader, String className)
            throws ClassNotFoundException {

        final ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_6, ACC_PUBLIC + ACC_SUPER, getASMClassName(), "L" + REFERENCE_ADAPTER_SHORT_SIGNATURE + "<L" + getASMParentClassName() + ";>;", REFERENCE_ADAPTER_SHORT_SIGNATURE, null);

        // Default constructor
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, REFERENCE_ADAPTER_SHORT_SIGNATURE, "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // Another constructor
        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Lorg/eclipse/persistence/jpa/rs/PersistenceContext;)V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESPECIAL, REFERENCE_ADAPTER_SHORT_SIGNATURE, "<init>", "(Lorg/eclipse/persistence/jpa/rs/PersistenceContext;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

        cw.visitEnd();

        return cw.toByteArray();
    }

    @Override
    public boolean isCompatible(EclipseLinkClassWriter writer) {
        return getParentClassName().equals(writer.getParentClassName());
    }

    @Override
    public Class<?> getParentClass() {
        return null;
    }

    @Override
    public String getParentClassName() {
        return parentClassName;
    }

    private String getASMParentClassName(){
        return parentClassName.replace('.', '/');
    }

    private String getASMClassName(){
        return getClassName().replace('.', '/');
    }

}

