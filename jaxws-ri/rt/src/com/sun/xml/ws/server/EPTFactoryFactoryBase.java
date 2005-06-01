/**
 * $Id: EPTFactoryFactoryBase.java,v 1.2 2005-06-01 00:52:42 jitu Exp $
 *
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.xml.ws.server;

import com.sun.pept.ept.EPTFactory;
import com.sun.pept.ept.MessageInfo;
import com.sun.pept.presentation.TargetFinder;
import com.sun.xml.ws.encoding.internal.InternalEncoder;
import com.sun.xml.ws.encoding.jaxb.LogicalEncoder;
import com.sun.xml.ws.encoding.soap.SOAPDecoder;
import com.sun.xml.ws.encoding.soap.SOAPEncoder;
import com.sun.xml.ws.encoding.soap.ServerEncoderDecoder;
import com.sun.xml.ws.server.provider.ProviderMessageDispatcher;
import com.sun.xml.ws.spi.runtime.MessageContext;
import javax.xml.ws.Provider;
import com.sun.xml.ws.util.MessageInfoUtil;
import com.sun.xml.ws.server.provider.ProviderPeptTie;
import com.sun.xml.ws.server.provider.ProviderSED;

public abstract class EPTFactoryFactoryBase {

    public static final ProviderMessageDispatcher providerMessageDispatcher =
        new ProviderMessageDispatcher();
    public static final LogicalEncoder logicalEncoder = new LogicalEncoderImpl();
    public static final SOAPEncoder soap11Encoder = new SOAPXMLEncoder();
    public static final SOAPDecoder soap11Decoder = new SOAPXMLDecoder();
    public static final SOAPMessageDispatcher soap11MessageDispatcher =
        new SOAPMessageDispatcher();
    public static final InternalEncoder internalSED = new ServerEncoderDecoder();
    public static final InternalEncoder providerSED = new ProviderSED();
    public static final TargetFinder providerTargetFinder =
            new TargetFinderImpl(new ProviderPeptTie());
    public static final TargetFinder targetFinder =
            new TargetFinderImpl(new PeptTie());
    public static final EPTFactory providerSoap11 =
            new EPTFactoryBase(soap11Encoder, soap11Decoder,
                logicalEncoder, providerSED, providerTargetFinder,
                providerMessageDispatcher);
    public static final EPTFactory soap11 =
            new EPTFactoryBase(
                soap11Encoder, soap11Decoder,
                logicalEncoder, internalSED, targetFinder,
                soap11MessageDispatcher);

    public static boolean isEncodeFast(MessageContext context) {
        boolean fast = false;
/*
        SOAPMessage message = ((SOAPMessageContext)context).getMessage();
        MimeHeaders headers = message.getMimeHeaders();
        String[] values = headers.getHeader("Accept");
        if (values != null) {
            for(int i=0; i < values.length; i++) {
//System.out.println("****** Accept="+values[i]);
                if (values[i].indexOf("application/fastsoap") != -1) {
                    fast = true;
                    break;
                }
            }
        }
*/
        return fast;
    }

    public static boolean isDecodeFast(MessageContext context) {
        boolean fast = false;
/*
        SOAPMessage message = ((SOAPMessageContext)context).getMessage();
        MimeHeaders headers = message.getMimeHeaders();
        String[] values = headers.getHeader("Content-Type");
        if (values != null) {
            for(int i=0; i < values.length; i++) {
//System.out.println("****** Content-Type="+values[i]);
                if (values[i].indexOf("application/fastsoap") != -1) {
                    fast = true;
                    break;
                }
            }
        }
*/
        return fast;
    }
    

    
    /**
     * Choose correct EPTFactory. MessageInfo contains all the needed
     * information like Binding, JaxrpcConnection to make that decision.
     */
    public static EPTFactory getEPTFactory(MessageInfo mi) {
        RuntimeContext rtCtxt = MessageInfoUtil.getRuntimeContext(mi);
        RuntimeEndpointInfo endpointInfo = rtCtxt.getRuntimeEndpointInfo();
        if (endpointInfo.getImplementor() instanceof Provider) {
            return providerSoap11;
        } else {
            return soap11;
        }
    }

}
