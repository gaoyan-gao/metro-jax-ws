/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package fromwsdl.wrapper_xmltype_601.client;

import junit.framework.TestCase;

import javax.xml.ws.Holder;
import java.math.BigInteger;

/**
 * Test for issue 601
 * xml type is used for deciding whether a wrapper parameter is INOUT
 *
 * @author Jitendra Kotamraju
 */
public class WrapperTest extends TestCase {

    private Hello proxy;

    public WrapperTest(String name) throws Exception{
        super(name);
    }

    protected void setUp() throws Exception {
        proxy = new Hello_Service().getHelloPort();
    }

    public void testMethod() {
        BigInteger inParam1 = new BigInteger("10000");
        Holder<BigInteger> outParam1 = new Holder<BigInteger>();
        int ret = proxy.testApl(inParam1, outParam1);
        assertEquals(10, ret);
        assertEquals(inParam1, outParam1.value);
    }

}
