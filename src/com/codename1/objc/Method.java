/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc;

import com.codename1.util.SuccessCallback;

/**
 *
 * @author shannah
 */
public abstract class Method {
    
    public static enum ArgType {
        Char("c"),
        Int("i"),
        Short("s"),
        Long("l"),
        LongLong("q"),
        UnsignedChar("C"),
        UnsignedInt("I"),
        UnsignedShort("S"),
        UnsignedLong("L"),
        UnsignedLongLong("Q"),
        Float("f"),
        Double("d"),
        Bool("B"),
        Void("v"),
        CString("*"),
        Object("@"),
        Class("#"),
        Pointer("^v");
        
        String code;
        
        ArgType(String code) {
            this.code = code;
        }
        
        public String getCode() {
            return code;
        }
    }
    
    
    public static interface MethodBody {
        public Object invoke(Object... args);
    }
    
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
    
    public Method(ArgType returnType, ArgType[] argTypes) {
        this(createSignature(returnType) + "@:" + createSignature(argTypes));
    }
    
    public static Method create(ArgType argType, MethodBody body) {
        return new Method(null, new ArgType[]{argType}) {

            @Override
            public Object invoke(Object... args) {
                return body.invoke(args);
            }
            
        };
    }
    
    public static Method create(ArgType returnType, ArgType[] argTypes, MethodBody body) {
        return new Method(returnType, argTypes) {

            @Override
            public Object invoke(Object... args) {
                return body.invoke(args);
            }
            
        };
    }
    
    
    
    private void checkSignature() {
        if (signature == null || signature.address == 0) {
            throw new RuntimeException("Attempt to get number of arguments on method with null signature");
        }
    }
    
    public int getNumberOfArguments() {
        checkSignature();
        return Objc.eval(signature, "numberOfArguments").asInt()-2;
    }
    
    public ArgType getMethodReturnType() {
        checkSignature();
        String str = Objc.eval(signature, "methodReturnType").asString();
        if (str == null || str.length() == 0) {
            throw new RuntimeException("Unexpected value for method return type");
        }
        return getArgTypeForCode(str);
    }
    
    public ArgType getArgumentTypeAtIndex(int index) {
        checkSignature();
        String str = Objc.eval(signature, "getArgumentTypeAtIndex:", index+2).asString();
        if (str == null || str.length() == 0) {
            throw new RuntimeException("Unexpected value for method return type");
        }
        return getArgTypeForCode(str);
    }
    
    
    public static ArgType getArgTypeForCode(String code) {
        char c = code.charAt(0);
        switch (c) {
            case 'c': return ArgType.Char;
            case 'i': return ArgType.Int;
            case 's': return ArgType.Short;
            case 'l': return ArgType.Long;
            case 'q': return ArgType.LongLong;
            case 'C': return ArgType.UnsignedChar;
            case 'I': return ArgType.UnsignedInt;
            case 'S': return ArgType.UnsignedShort;
            case 'Q': return ArgType.UnsignedLongLong;
            case 'f': return ArgType.Float;
            case 'd': return ArgType.Double;
            case 'B': return ArgType.Bool;
            case 'v': return ArgType.Void;
            case '*': return ArgType.CString;
            case '@': return ArgType.Object;
            case '#': return ArgType.Class;
            case '^': return ArgType.Pointer;
            default: throw new RuntimeException("Unsupported return type for signature "+code);
                
                
        }
        
    }
    
    public static String createSignature(ArgType... argTypes) {
        StringBuilder sb = new StringBuilder();
        for (ArgType cls : argTypes) {
            if (cls == null) {
                sb.append("v");
            } else {
                sb.append(cls.getCode());
            }
        }
        return sb.toString();
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
    
    public static Pointer getArgAsPointer(Object arg) {
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
    
    public static int getArgAsInt(Object arg) {
        if (arg instanceof Number) {
            return ((Number)arg).intValue();
        } else if (arg instanceof Pointer) {
            return ((Pointer)arg).getInt();
        } else {
            return 0;
        }
    }
    
    public static boolean getArgAsBoolean(Object arg) {
        return getArgAsInt(arg) != 0;
    }
    
    public static double getArgAsDouble(Object arg) {
        if (arg instanceof Number) {
            return ((Number)arg).doubleValue();
        } else {
            return 0;
        }
    }
    
    public static String getArgAsString(Object arg) {
        if (arg instanceof String) {
            return (String)arg;
        } else if (arg instanceof Pointer) {
            return ((Pointer)arg).getString(0);
        } else {
            return String.valueOf(arg);
        }
    }
}
