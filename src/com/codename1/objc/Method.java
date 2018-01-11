/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc;

/**
 *
 * @author shannah
 */
public abstract class Method {
    private final Pointer signature;
    private String selectorName;
    public Method(String protocolName, String selectorName) {
        Pointer protocol = Runtime.getInstance().objc_getProtocol(protocolName);
        if (protocol == null || protocol.address == 0) {
            throw new RuntimeException("Protocol "+protocolName+" not found");
        }
        Pointer selector = Runtime.getInstance().sel(selectorName);
        if (selector == null || selector.address == 0) {
            throw new RuntimeException("Selector "+selectorName+" of protocol "+protocolName+" not found");
        }
        Pointer signatureRequired = Runtime.getInstance().protocol_getMethodSignature(protocol, selector, true, true);
        Pointer signatureOptional = Runtime.getInstance().protocol_getMethodSignature(protocol, selector, false, true);
        signature = signatureRequired.address != 0 ?signatureRequired : signatureOptional;
        if (signature == null || signature.address == 0) {
            throw new RuntimeException("Method Signature for selector "+selectorName+" on protocol "+protocolName+" not found");
        }
        this.selectorName = selectorName;
    }
    
    public Method(String signatureStr) {
        Pointer strPointer = Runtime.getInstance().stringToUTF8String(signatureStr);
        if (strPointer == null || strPointer.address == 0) {
            throw new RuntimeException("Signature string "+signatureStr+" produced a null value when converted to a UTF8 String");
        }
        signature = Runtime.getInstance().msgPointer(Runtime.getInstance().cls("NSMethodSignature"), "signatureWithObjCTypes:", strPointer);
        
    }
    
    public String getSelectorName() {
        return selectorName;
    }
    
    public void setSelectorName(String name) {
        this.selectorName = name;
    }
    
    public Pointer getSelector() {
        if (selectorName == null) {
            return null;
        }
        return Runtime.getInstance().sel(selectorName);
    }
    
    public Pointer getSignature() {
        return signature;
    }
    
    public abstract Object invoke(Object... args);
    public int invokeInt(Object... args) {
        return (int)invoke(args);
    }
    public Pointer invokePointer(Object... args) {
        return (Pointer)invoke(args);
    }
    
    public double invokeDouble(Object... args) {
        return (double)invoke(args);
    }
    
    public float invokeFloat(Object... args) {
        return (float)invoke(args);
    }
    
    public String invokeString(Object... args) {
        return String.valueOf(invoke(args));
    }
    
    protected Pointer getArgAsPointer(Object arg) {
        if (arg instanceof Pointer) {
            return (Pointer)arg;
            
        } else if (arg instanceof Proxy) {
            return ((Proxy)arg).getPeer();
        } else if (arg instanceof Long) {
            return new Pointer((Long)arg);
        } else {
            return null;
        }
    }
    
    protected int getArgAsInt(Object arg) {
        if (arg instanceof Number) {
            return ((Number)arg).intValue();
        } else if (arg instanceof Pointer) {
            return ((Pointer)arg).getInt();
        } else {
            return 0;
        }
    }
    
    protected boolean getArgAsBoolean(Object arg) {
        return getArgAsInt(arg) != 0;
    }
    
    protected double getArgAsDouble(Object arg) {
        if (arg instanceof Number) {
            return ((Number)arg).doubleValue();
        } else {
            return 0;
        }
    }
    
    protected String getArgAsString(Object arg) {
        if (arg instanceof String) {
            return (String)arg;
        } else if (arg instanceof Pointer) {
            return ((Pointer)arg).getString(0);
        } else {
            return String.valueOf(arg);
        }
    }
}
