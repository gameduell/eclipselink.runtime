/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 *
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *  - mmacivor - 2.4 - Initial implementation
 ******************************************************************************/
package org.eclipse.persistence.testing.jaxb.externalizedmetadata.xmlaccessortype.inheritance;

import javax.xml.bind.annotation.XmlElement;

public class B extends A {

    String x;

    public B() {
        x = "Hello World";
    }

    @Override
    public String getX() {
        return x;
    }

    @XmlElement
    public TestClass getTestSub() {
        return new TestClass();
    }

    @XmlElement
    public TestSuperclass getTestSuper() {
        return new TestSuperclass();
    }

    public String getCalculatedValue() {
        return "Calculated Value";
    }

    public boolean equals(Object b) {
        return x.equals(((B) b).getX());
    }

}
