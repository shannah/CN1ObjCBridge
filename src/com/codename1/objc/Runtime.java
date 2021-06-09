/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc;

import com.codename1.io.Log;
import com.codename1.objc.Objc.CallbackMethod;
import com.codename1.objc.Pointer.ByReference;
import com.codename1.objc.Pointer.ByteByReference;
import com.codename1.objc.Pointer.DoubleByReference;
import com.codename1.objc.Pointer.FloatByReference;
import com.codename1.objc.Pointer.IntByReference;
import com.codename1.objc.Pointer.LongByReference;
import com.codename1.objc.Pointer.PointerByReference;
import com.codename1.objc.Pointer.ShortByReference;
import com.codename1.system.NativeLookup;
import com.codename1.ui.ComCodename1ObjcAccessor;

import com.codename1.ui.PeerComponent;
import com.codename1.util.SuccessCallback;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author shannah
 */
public class Runtime {
    private RuntimeNative rt;
    private RuntimeNative rt() {
        if (rt == null) {
            try {
                rt = NativeLookup.create(RuntimeNative.class);
            } catch (Exception ex){
                Log.e(ex);
            }
        }
        return rt;
    }
    
    private static Runtime instance;
    public static Runtime getInstance() {
        if (instance == null) {
            instance = new Runtime();
            instance.rt();
        }
        return instance;
    }
    
    public boolean isSupported() {
        
        return rt() != null && rt.isSupported();
    }
    
    private static Pointer p(long address) {
        return new Pointer(address);
    }
    
    private static Pointer[] p(long[] addresses) {
        int len = addresses.length;
        Pointer[] out = new Pointer[len];
        for (int i=0; i<len; i++) {
            out[i] = p(addresses[i]);
        }
        return out;
       
                
    }
    
    public Pointer malloc(byte b) {
        return p(rt.malloc(b));
    }
    
    public Pointer malloc(int i) {
        return p(rt.malloc_int(i));
    }
    
    public Pointer malloc(long l) {
        return p(rt.malloc_long(l));
    }
    
    public Pointer malloc(short s) {
        return p(rt.malloc_short(s));
    }
    
    public Pointer malloc(float f) {
        return p(rt.malloc_float(f));
    }
    
    public Pointer malloc(double d) {
        return p(rt.malloc_double(d));
    }
    
    public void setPointerValue(Pointer ptr, int value) {
        rt.setPointerValue_int(ptr.address, value);
    }
    public void setPointerValue(Pointer ptr, byte value) {
        rt.setPointerValue_byte(ptr.address, value);
    }
    public void setPointerValue(Pointer ptr, Pointer  value) {
        rt.setPointerValue_long(ptr.address, value.address);
    }
    public void setPointerValue(Pointer ptr, short value) {
        rt.setPointerValue_short(ptr.address, value);
    }
    
    public void setPointerValue(Pointer ptr, float value) {
        rt.setPointerValue_float(ptr.address, value);
    }
    
    public void setPointerValue(Pointer ptr, double value) {
        rt.setPointerValue_double(ptr.address, value);
    }
    public int getPointerValueInt(Pointer ptr) {
        return rt.getPointerValueInt(ptr.address);
    }
    public byte getPointerValueByte(Pointer ptr) {
        return rt.getPointerValueByte(ptr.address);
    }
    public Pointer getPointerValuePointer(Pointer ptr) {
        return p(rt.getPointerValueLong(ptr.address));
    }
    public short getPointerValueShort(Pointer ptr) {
        return rt.getPointerValueShort(ptr.address);
    }
    public float getPointerValueFloat(Pointer ptr) {
        return rt.getPointerValueFloat(ptr.address);
    }
    public double getPointerValueDouble(Pointer ptr) {
        return rt.getPointerValueDouble(ptr.address);
    }
    
    public void free(Pointer p) {
        rt.free(p.address);
    }
    
    
    public Pointer objc_lookUpClass(String name) {
        return p(rt.objc_lookUpClass(name));
        
    }
    public String class_getName(Pointer id) {
        return rt.class_getName(id.address);
    }
    
    public Pointer class_getProperty(Pointer cls, String name) {
        return p(rt.class_getProperty(cls.address, name));
    }
    
    public Pointer class_getSuperclass(Pointer cls) {
        return p(rt.class_getSuperclass(cls.address));
    }
    
    public int class_getVersion(Pointer theClass) {
        return rt.class_getVersion(theClass.address);
    }
    public String class_getWeakIvarLayout(Pointer cls) {
        return rt.class_getWeakIvarLayout(cls.address);
    }
    public boolean class_isMetaClass(Pointer cls) {
        return rt.class_isMetaClass(cls.address);
    }
    public int class_getInstanceSize(Pointer cls) {
        return rt.class_getInstanceSize(cls.address);
    }
    public Pointer class_getInstanceVariable(Pointer cls, String name) {
        return p(rt.class_getInstanceVariable(cls.address, name));
    }
    public Pointer class_getInstanceMethod(Pointer cls, Pointer aSelector) {
        return p(rt.class_getInstanceMethod(cls.address, aSelector.address));
    }
    public Pointer class_getClassMethod(Pointer cls, Pointer aSelector) {
        return p(rt.class_getClassMethod(cls.address, aSelector.address));
    }
    
    public String class_getIvarLayout(Pointer cls) {
        return rt.class_getIvarLayout(cls.address);
    }
    public Pointer class_getMethodImplementation(Pointer cls, Pointer name){
        return p(rt.class_getMethodImplementation(cls.address, name.address));
    }
    public Pointer class_getMethodImplementation_stret(Pointer cls, Pointer name) {
        return p(rt.class_getMethodImplementation(cls.address, name.address));
    }
    public Pointer class_replaceMethod(Pointer cls, Pointer name, Pointer imp, String types) {
        return p(rt.class_replaceMethod(cls.address, name.address, imp.address, types));
    }
    public Pointer class_respondsToSelector(Pointer cls, Pointer sel) {
        return p(rt.class_respondsToSelector(cls.address, sel.address));
    }
    public void class_setIvarLayout(Pointer cls, String layout) {
        rt.class_setIvarLayout(cls.address, layout);
    }
    public Pointer class_setSuperclass(Pointer cls, Pointer newSuper) {
        return p(rt.class_setSuperclass(cls.address, newSuper.address));
    }
    public void class_setVersion(Pointer theClass, int version) {
        rt.class_setVersion(theClass.address, version);
    }
    public void class_setWeakIvarLayout(Pointer cls, String layout) {
        rt.class_setWeakIvarLayout(cls.address, layout);
    }
    public String ivar_getName(Pointer ivar) {
        return rt.ivar_getName(ivar.address);
    }
    public Pointer ivar_getOffset(Pointer ivar) {
        return p(rt.ivar_getOffset(ivar.address));
    }
    public String ivar_getTypeEncoding(Pointer ivar) {
        return rt.ivar_getTypeEncoding(ivar.address);
    }
    public String method_copyArgumentType(Pointer method, int index) {
        return rt.method_copyArgumentType(method.address, index);
    }
    public String method_copyReturnType(Pointer method) {
        return rt.method_copyReturnType(method.address);
    }
    public void method_exchangeImplementations(Pointer m1, Pointer m2) {
        rt.method_exchangeImplementations(m1.address, m2.address);
    }
    public void method_getArgumentType(Pointer method, int index, Pointer dst, Pointer dst_len) {
        rt.method_getArgumentType(method.address, index, dst.address, dst_len.address);
    }
    public Pointer method_getImplementation(Pointer method) {
        return p(rt.method_getImplementation(method.address));
    }
    public Pointer method_getName(Pointer method) {
        return p(rt.method_getName(method.address));
    }
    public int method_getNumberOfArguments(Pointer method) {
        return rt.method_getNumberOfArguments(method.address);
    }
    public void method_getReturnType(Pointer method, Pointer dst, Pointer dst_len) {
        rt.method_getReturnType(method.address, dst.address, dst_len.address);
    }
    public String method_getTypeEncoding(Pointer method) {
        return rt.method_getTypeEncoding(method.address);
    }
    public Pointer method_setImplementation(Pointer method, Pointer imp) {
        return p(rt.method_setImplementation(method.address, imp.address));
    }
    public Pointer objc_allocateClassPair(Pointer superclass, String name, Pointer extraBytes) {
        return p(rt.objc_allocateClassPair(superclass.address, name, extraBytes.address));
    }
    public Pointer[] objc_copyProtocolList(Pointer outCount) {
        return p(rt.objc_copyProtocolList(outCount.address));
    }
    public Pointer objc_getAssociatedObject(Pointer object, String key) {
        return p(rt.objc_getAssociatedObject(object.address, key));
    }
    public Pointer objc_getClass(String name) {
        return p(rt.objc_getClass(name));
    }
    public int objc_getClassList(Pointer buffer, int bufferlen) {
        return rt.objc_getClassList(buffer.address, bufferlen);
    }
    public Pointer objc_getFutureClass(String name) {
        return p(rt.objc_getFutureClass(name));
    }
    public Pointer objc_getMetaClass(String name) {
        return p(rt.objc_getMetaClass(name));
    }
    public Pointer objc_getProtocol(String name) {
        return p(rt.objc_getProtocol(name));
    }
    public Pointer objc_getRequiredClass(String name) {
        return p(rt.objc_getRequiredClass(name));
    }
    
    /**
      * Produce a string in double quotes with backslash sequences in all the
      * right places. A backslash will be inserted within </, allowing JSON
      * text to be delivered in HTML. In JSON text, a string cannot contain a
      * control character or an unescaped quote or backslash.
      * @param string A String
      * @return  A String correctly formatted for insertion in a JSON text.
      */
     private static String quote(String string) {
         if (string == null || string.length() == 0) {
             return "\"\"";
         }

         char         b;
         char         c = 0;
         int          i;
         int          len = string.length();
         StringBuilder sb = new StringBuilder(len + 4);
         String       t;

         sb.append('"');
         for (i = 0; i < len; i += 1) {
             b = c;
             c = string.charAt(i);
             switch (c) {
             case '\\':
             case '"':
                 sb.append('\\');
                 sb.append(c);
                 break;
             case '/':
                 if (b == '<') {
                     sb.append('\\');
                 }
                 sb.append(c);
                 break;
             case '\b':
                 sb.append("\\b");
                 break;
             case '\t':
                 sb.append("\\t");
                 break;
             case '\n':
                 sb.append("\\n");
                 break;
             case '\f':
                 sb.append("\\f");
                 break;
             case '\r':
                 sb.append("\\r");
                 break;
             default:
                 if (c < ' ') {
                     t = "000" + Integer.toHexString(c);
                     sb.append("\\u" + t.substring(t.length() - 4));
                 } else {
                     sb.append(c);
                 }
             }
         }
         sb.append('"');
         return sb.toString();
     }
    
    private String encodeJson(Object... arguments) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (Object arg : arguments) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append("{\"type\":");
            if (arg instanceof String) {
                sb.append("\"String\", \"value\" : ").append(quote((String)arg));
            } else if (arg instanceof Integer || arg instanceof Short) {
                sb.append("\"int\", \"value\" :  ").append(String.valueOf(arg));
            } else if (arg instanceof Double) {
                sb.append("\"double\", \"value\" :  ").append(String.valueOf(arg));
            } else if (arg instanceof Float) {
                sb.append("\"float\", \"value\" :  ").append(String.valueOf(arg));
            } else if (arg instanceof Boolean) {
                sb.append("\"int\", \"value\" :  ").append(((Boolean)arg)?"1":"0");
            }else if (arg instanceof Pointer) {
                sb.append("\"pointer\", \"value\" : \"").append(String.valueOf(((Pointer)arg).address)).append("\"");
            
            } else if (arg == null) {
                sb.append("\"pointer\", \"value\" : 0");
            }
            sb.append("}");
            
            
        }
        sb.append("]");
        return sb.toString();
    }
    
    public Pointer objc_msgSend(Pointer theReceiver, Pointer theSelector,Object... arguments) {
        return p(rt.objc_msgSend(theReceiver.address, theSelector.address, encodeJson(arguments)));
    }
    
    // Commenting these out because there were compilation problems on Xcode 12 and it is hard to think
    // of a scenario that requires these.
    //public Pointer objc_msgSendSuper(Pointer superClassStruct, Pointer op, Object... arguments) {
    //    return p(rt.objc_msgSendSuper(superClassStruct.address, op.address, encodeJson(arguments)));
    //}
    //public Pointer objc_msgSendSuper_stret(Pointer superClassStruct, Pointer op, Object... arguments) {
    //    return p(rt.objc_msgSendSuper_stret(superClassStruct.address, op.address, encodeJson(arguments)));
    //}
    public double objc_msgSend_fpret(Pointer self, Pointer op, Object... arguments) {
        return rt.objc_msgSend_fpret(self.address, op.address, encodeJson(arguments));
    }
    public void objc_msgSend_stret(Pointer stretAddr, Pointer theReceiver, Pointer theSelector, Object... arguments) {
        rt.objc_msgSend_stret(stretAddr.address, theReceiver.address, theSelector.address, encodeJson(arguments));
    }
    public void objc_registerClassPair(Pointer cls) {
        rt.objc_registerClassPair(cls.address);
    }
    public void objc_removeAssociatedObjects(Pointer object) {
        rt.objc_removeAssociatedObjects(object.address);
    }
    public void objc_setAssociatedObject(Pointer object, Pointer key, Pointer value, Pointer policy) {
        rt.objc_setAssociatedObject(object.address, key.address, value.address, policy.address);
    }
    public void objc_setFutureClass(Pointer cls, String name) {
        rt.objc_setFutureClass(cls.address, name);
    }
    public Pointer object_copy(Pointer obj, Pointer size) {
        return p(rt.object_copy(obj.address, size.address));
    }
    public Pointer object_dispose(Pointer obj) {
        return p(rt.object_dispose(obj.address));
    }
    public Pointer object_getClass(Pointer object) {
        return p(rt.object_getClass(object.address));
    }
    public String object_getClassName(Pointer obj) {
        return rt.object_getClassName(obj.address);
    }
    public Pointer object_getIndexedIvars(Pointer obj) {
        return p(rt.object_getIndexedIvars(obj.address));
    }
    public Pointer object_getInstanceVariable(Pointer obj, String name, Pointer outValue) {
        return p(rt.object_getInstanceVariable(obj.address, name, outValue.address));
    }
    public Pointer object_getIvar(Pointer object, Pointer ivar) {
        return p(rt.object_getIvar(object.address, ivar.address));
    }
    public Pointer object_setClass(Pointer object, Pointer cls) {
        return p(rt.object_setClass(object.address, cls.address));
    }
    public Pointer object_setInstanceVariable(Pointer obj, String name, Pointer value) {
        return p(rt.object_setInstanceVariable(obj.address, name, value.address));
    }
    public void object_setIvar(Pointer object, Pointer ivar, Pointer value) {
        rt.object_setIvar(object.address, ivar.address, value.address);
    }
    public String property_getAttributes(Pointer property) {
        return rt.property_getAttributes(property.address);
    }
    public boolean protocol_conformsToProtocol(Pointer proto, Pointer other) {
        return rt.protocol_conformsToProtocol(proto.address, other.address);
    }
    //public Structure protocol_copyMethodDescriptionList(Pointer protocol, boolean isRequiredMethod, boolean isInstanceMethod, Pointer outCount);
    
    public Pointer protocol_copyPropertyList(Pointer proto, Pointer outCount) {
        return p(rt.protocol_copyPropertyList(proto.address, outCount.address));
    }
    public Pointer protocol_copyProtocolList(Pointer proto, Pointer outCount) {
        return p(rt.protocol_copyProtocolList(proto.address, outCount.address));
    }
    public Pointer protocol_getMethodDescription(Pointer proto, Pointer aSel, boolean isRequiredMethod, boolean isInstanceMethod) {
        return p(rt.protocol_getMethodDescription(proto.address, aSel.address, isRequiredMethod, isInstanceMethod));
    }
    public Pointer protocol_getMethodSignature(Pointer proto, Pointer aSel, boolean isRequiredMethod, boolean isInstanceMethod) {
        return p(rt.protocol_getMethodSignature(proto.address, aSel.address, isRequiredMethod, isInstanceMethod));
    }
    public String protocol_getName(Pointer proto) {
        return rt.protocol_getName(proto.address);
    }
    public Pointer protocol_getProperty(Pointer proto, String name, boolean isRequiredProperty, boolean isInstanceProperty) {
        return p(rt.protocol_getProperty(proto.address, name, isRequiredProperty, isInstanceProperty));
    }
    public boolean protocol_isEqual(Pointer protocol, Pointer other) {
        return rt.protocol_isEqual(protocol.address, other.address);
    }
    public String sel_getName(Pointer aSelector) {
        return rt.sel_getName(aSelector.address);
    }
    
    public Pointer sel_getUid(String name) {
        int pos = name.lastIndexOf(':');
        if (pos >= 0 && pos != name.length()-1) {
            // Common mistake... selector missing trailing colon
            Log.p("[WARNING] Selector '"+name+"' should be '"+name+":'.  Adding trailing colon automatically.  Please fix this in your source.");
            name = name + ":";
            
        }
        return p(rt.sel_getUid(name));
    }
    public boolean sel_isEqual(Pointer lhs, Pointer rhs) {
        return rt.sel_isEqual(lhs.address, rhs.address);
    }
    public Pointer sel_registerName(String name) {
        return p(rt.sel_registerName(name));
    }
    
    private static Map<Integer,Runnable> callbacks;
    private static Map<Integer,Runnable> callbacks() {
        if (callbacks == null) {
            callbacks = new HashMap<Integer,Runnable>();
        }
        return callbacks;
    }
    
    private int addCallback(Runnable r) {
        callbacks();
        for (int i=0; i<10000; i++) {
            if (!callbacks.containsKey(i)) {
                callbacks.put(i, r);
                return i;
            }
        }
        throw new RuntimeException("Failed to add callback because all ids are taken");
    }
    
    public static void runCallback(int id) {
        if (callbacks != null && callbacks.containsKey(id)) {
            Runnable r = callbacks.remove(id);
            r.run();
        }
    }
    
    public static void runCallbackNoRemove(int id) {
        if (callbacks != null && callbacks.containsKey(id)) {
            Runnable r = callbacks.get(id);
            r.run();
        }
    }
    
    public void dispatch_sync(Runnable r) {
        final Throwable[] ex = new Throwable[1];
        Runnable r2 = ()->{
            try (AutoreleasePool p = new AutoreleasePool()) {
                r.run();
            } catch (Throwable t) {
                Log.e(t);
                ex[0] = t;
            }
        };
        rt.dispatch_sync(addCallback(r2));
        if (ex[0] != null) {
            throw new RuntimeException("Exception thrown inside dispatch_sync", ex[0]);
        }
        
    }
    
    
    public void dispatch_sync(Block block) {
        rt.dispatch_sync_block(block.block.address);
    }
    
    public void dispatch_async(Runnable r) {
        Runnable r2 = ()->{
            try (AutoreleasePool p = new AutoreleasePool()) {
                r.run();
            } catch (Throwable t) {
                Log.p(t.getMessage());
                Log.e(t);
            }
        };
        rt.dispatch_async(addCallback(r2));
    }
    
    public static class Block implements Peerable {
        final Pointer block;
        final int callbackId;
        
        boolean callbackActive=true;
        boolean nativeActive=true;
        
        private Block(int callbackId, Pointer block) {
            this.callbackId = callbackId;
            this.block = block;
            
        }
        
        public void releaseNative() {
            if (nativeActive) {
                instance.rt.releaseBlock(block.address);
                nativeActive = false;
            }
            
        }
        
        public void removeCallback() {
            if (callbackActive) {
                if (callbackId >= 0) {
                    instance.callbacks.remove(callbackId);
                    callbackActive = false;
                }
            }
        }
        
        public boolean isCallbackActive() {
            return callbackActive;
        }
        
        public boolean isNativeActive() {
            return nativeActive;
        }

        @Override
        public Pointer getPeer() {
            return block;
        }

        @Override
        public void setPeer(Pointer peer) {
            throw new RuntimeException("setPeer() not allowed on blocks");
        }
    }
    
    public Block createBlock(Runnable r) {
        int id = addCallback(r);
        
        return new Block(id, new Pointer(rt.createBlock(id)));
    }
    
    public Block createBlock(Method m) {
        CallbackMethod cbm = new CallbackMethod(m);
        if (cbm.getObjectPointer() == null || cbm.getObjectPointer().address == 0) {
            throw new RuntimeException("Failed to create callback method.  Produced a null object pointer");
        }
        Block blk = new Block(-1, 
                new Pointer(rt.createBlockWithSignature(
                        m.getSignature().address, 
                        cbm.getObjectPointer().address,
                        cbm.getSelector().address
                ))
        );
        
        // For some reason this causes an error.
        // I guess it gets released on its own??
        //Objc.eval(cbm.getObjectPointer(), "release");
        return blk;
    }
    
    public void retain(Pointer obj) {
        rt.retain(obj.address);
    }
    
    public void release(Pointer obj) {
        rt.release(obj.address);
    }
    
    public void autorelease(Pointer obj) {
        rt.autorelease(obj.address);
    }
    
    public Pointer createAutoreleasePool() {
        return p(rt.createAutoreleasePool());
    }
    
    
    
    
    
    //------------------ Higher level utils --------------------------
    
    
    /**
     * Returns a pointer to the class for specific class name.
     * <pre>
     * {@code
     * Pointer nsObject = cls("NSObject");
     * }</pre>
     * @param name The name of the class to retrieve.
     * @return The pointer to the class structure.
     */
    public Pointer cls(String name){
        return objc_lookUpClass(name);
    }
    
    /**
     * Returns a pointer to a class given a Peerable object which wraps
     * the pointer.  Effectively this just calls peer.getPeer() and
     * returns its value.  A convenience method.
     * @param peer The peer object that is wrapping the Objective-C class
     * object.
     * @return A Pointer to the Objective-C class. 
     */
    public static Pointer cls(Peerable peer){
        return peer.getPeer();
    }
    
    /**
     * Returns the name of an objective C class specified by the given class pointer.
     * 
     * @param cls The pointer to the class whose name we wish to retrieve.
     * @return The name of the class.
     * @see Runtime.class_getName()
     */
    public String clsName(Pointer cls){
        return class_getName(cls);
    }
    
    /**
     * A wrapper for the clsName() method given a Peerable object that wraps
     * the class pointer.  This will return the class name.
     * @param peer
     * @return The class name of the objective-c class that is wrapped by the 
     * given Peerable object.
     * @see Runtime.class_getName()
     */
    public String clsName(Peerable peer){
        return clsName(peer.getPeer());
    }
    
    /**
     * Returns a pointer to the selector specified by the given selector name.
     * @param name
     * @return Pointer to an Objective-C message selector.
     * @see <a href="http://developer.apple.com/library/ios/#documentation/cocoa/conceptual/objectivec/Chapters/ocSelectors.html">Objective-C Selectors Reference</a>
     */
    public Pointer sel(String name){
        return sel_getUid(name);
    }
    
    
    /**
     * Returns a pointer to the selector that is wrapped by a Peerable object.
     * @param peer
     * @return Pointer to a specified selector.
     */
    public Pointer sel(Peerable peer){
        return peer.getPeer();
    }
    
    /**
     * Returns the name of a selector.
     * @param sel Pointer to a selector.
     * @return The name of the selector.
     */
    public String selName(Pointer sel){
        return sel_getName(sel);
    }
    
    /**
     * Returns the name of a selector.
     * @param sel Peerable that wraps a pointer to a selector.
     * @return The name of the selector.
     */
    public String selName(Peerable peer){
        return selName(peer.getPeer());
    }
    
    /**
     * Sends a message to a specified class using the given selector.
     * @param cls The name of the class.
     * @param msg The name of the selector.
     * @param args The arguments passed to the method.  These are passed raw
     * and will not be coerced.
     * @return The result of the message.  This may be a pointer, if the message
     * returns a pointer, or a value, if the method returns a BOOL, long, int, 
     * short, byte, or char.  Note that you cannot use this method for
     * messages that return float and double values.  For these, you <em>must</em>
     * use msgDouble().  This is because doubles and floats are handled using
     * a different underlying runtime function (objc_msgSend_fpret). Alternatively
     * you can use the variation of msg() that takes coerceInput and coerceOutput
     * boolean values, as this will automatically choose the correct underlying
     * message function depending on the return type reported by the message 
     * signature.
     * 
     * @see msgDouble()
     */
    public Pointer msg(String cls, String msg, Object... args){
        if (args.length > 0 && msg.lastIndexOf(':') != msg.length()-1) {
            Log.p("Selector '"+msg+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            msg = msg + ":";
        }
        try {
            return msg(cls(cls), msg, args);
        } catch (MethodSignatureNotFound ex) {
            if (ex.target == null) ex.target = cls;
            if (ex.selector == null) ex.selector = msg;
            throw ex;
        }
    }
    
    /**
     * Sends a message to a specified class using the given selector.
     * @param cls The name of the class.
     * @param msg The name of the selector.
     * @param args The arguments passed to the method.  These are passed raw
     * and will not be coerced.
     * @return The result of the message.  This may be a pointer, if the message
     * returns a pointer, or a value, if the method returns a BOOL, long, int, 
     * short, byte, or char.  Note that you cannot use this method for
     * messages that return float and double values.  For these, you <em>must</em>
     * use msgDouble().  This is because doubles and floats are handled using
     * a different underlying runtime function (objc_msgSend_fpret). Alternatively
     * you can use the variation of msg() that takes coerceInput and coerceOutput
     * boolean values, as this will automatically choose the correct underlying
     * message function depending on the return type reported by the message 
     * signature.
     * 
     * @see msgDouble()
     */
    public Pointer msg(String cls, Pointer msg, Object... args){
        try {
            return msg(cls(cls), msg, args);
        } catch (MethodSignatureNotFound ex) {
            if (ex.target == null) {
                ex.target = cls;
            }
            throw ex;
        }
    }
    
    
    /**
     * Sends a message to a specified class using the given selector.
     * @param cls The name of the class.
     * @param msg The name of the selector.
     * @param args The arguments passed to the method.  These are passed raw
     * and will not be coerced.
     * @return The result of the message.  This may be a pointer, if the message
     * returns a pointer, or a value, if the method returns a BOOL, long, int, 
     * short, byte, or char.  Note that you cannot use this method for
     * messages that return float and double values.  For these, you <em>must</em>
     * use msgDouble().  This is because doubles and floats are handled using
     * a different underlying runtime function (objc_msgSend_fpret). Alternatively
     * you can use the variation of msg() that takes coerceInput and coerceOutput
     * boolean values, as this will automatically choose the correct underlying
     * message function depending on the return type reported by the message 
     * signature.
     * 
     * @see msgDouble()
     */
    public Pointer msg(Pointer receiver, String msg, Object... args){
        if (args.length > 0 && msg.lastIndexOf(':') != msg.length()-1) {
            Log.p("Selector '"+msg+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            msg = msg + ":";
        }
        return objc_msgSend(receiver, sel(msg), args);
    }
    
    /**
     * Sends a message to a specified class using the given selector.
     * @param cls The name of the class.
     * @param msg The name of the selector.
     * @param args The arguments passed to the method.  These are passed raw
     * and will not be coerced.
     * @return The result of the message.  This may be a pointer, if the message
     * returns a pointer, or a value, if the method returns a BOOL, long, int, 
     * short, byte, or char.  Note that you cannot use this method for
     * messages that return float and double values.  For these, you <em>must</em>
     * use msgDouble().  This is because doubles and floats are handled using
     * a different underlying runtime function (objc_msgSend_fpret). Alternatively
     * you can use the variation of msg() that takes coerceInput and coerceOutput
     * boolean values, as this will automatically choose the correct underlying
     * message function depending on the return type reported by the message 
     * signature.
     * 
     * @see msgDouble()
     */
    public Pointer msg(Pointer receiver, Pointer selector, Object... args){
        return objc_msgSend(receiver, selector, args);
    }
    
    /**
     * Wrapper around msg() that returns a Pointer. This should only be used for
     * sending messages that return Pointers (or Objects).
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return A Pointer return value of the message.
     */
    public Pointer msgPointer(Pointer receiver, Pointer selector, Object... args){
        return msg(receiver, selector, args);
        
    }
    
    /**
     * Wrapper around msg() that returns a Pointer. This should only be used for
     * sending messages that return Pointers (or Objects).
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return A Pointer return value of the message.
     */
    public Pointer msgPointer(Pointer receiver, String selector, Object... args){
        if (args.length > 0 && selector.lastIndexOf(':') != selector.length()-1) {
            Log.p("Selector '"+selector+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            selector = selector + ":";
        }
        return msgPointer(receiver, sel(selector), args);
    }
    
    /**
     * Wrapper around msg() that returns a Pointer. This should only be used for
     * sending messages that return Pointers (or Objects).
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return A Pointer return value of the message.
     */
    public Pointer msgPointer(String receiver, Pointer selector, Object... args){
        return msgPointer(cls(receiver), selector, args);
    }
    
    /**
     * Wrapper around msg() that returns a Pointer. This should only be used for
     * sending messages that return Pointers (or Objects).
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return A Pointer return value of the message.
     */
    public Pointer msgPointer(String receiver, String selector, Object... args){
        if (args.length > 0 && selector.lastIndexOf(':') != selector.length()-1) {
            Log.p("Selector '"+selector+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            selector = selector + ":";
        }
        return msgPointer(cls(receiver), sel(selector), args);
    }
    
    /**
     * Wrapper around msg() that returns an int. This should only be used for
     * sending messages that return int-compatible numeric values.  E.g. 
     * byte, bool, long, int, short.  Do not use this if the message will return
     * something else (like a float, double, string, or pointer).
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return An int value returned by the message.
     */
    public int msgInt(Pointer receiver, Pointer selector, Object... args){
        Pointer res = msg(receiver, selector, args);
        return new Long(res.address).intValue();
    }
    
    /**
     * Wrapper around msg() that returns an int. This should only be used for
     * sending messages that return int-compatible numeric values.  E.g. 
     * byte, bool, long, int, short.  Do not use this if the message will return
     * something else (like a float, double, string, or pointer).
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return An int value returned by the message.
     */
    public int msgInt(String receiver, Pointer selector, Object... args){
        return msgInt(cls(receiver), selector, args);
    }
    
    /**
     * Wrapper around msg() that returns an int. This should only be used for
     * sending messages that return int-compatible numeric values.  E.g. 
     * byte, bool, long, int, short.  Do not use this if the message will return
     * something else (like a float, double, string, or pointer).
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return An int value returned by the message.
     */
    public int msgInt(String receiver, String selector, Object... args){
        if (args.length > 0 && selector.lastIndexOf(':') != selector.length()-1) {
            Log.p("Selector '"+selector+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            selector = selector + ":";
        }
        return msgInt(cls(receiver), sel(selector), args);
    }
    
    /**
     * Wrapper around msg() that returns an int. This should only be used for
     * sending messages that return int-compatible numeric values.  E.g. 
     * byte, bool, long, int, short.  Do not use this if the message will return
     * something else (like a float, double, string, or pointer).
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return An int value returned by the message.
     */
    public int msgInt(Pointer receiver, String selector, Object... args){
        if (args.length > 0 && selector.lastIndexOf(':') != selector.length()-1) {
            Log.p("Selector '"+selector+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            selector = selector + ":";
        }
        return msgInt(receiver, sel(selector), args);
    }
    
    /**
     * Wrapper around msg() that returns a boolean value.  This should only be used
     * for sending messages that return boolean-compatible numeric values.  Essentially
     * any non-zero value is interpreted (and returned) as true.  Zero values are 
     * interpreted as false.
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args Arguments passed to the message.
     * @return Boolean return value of the message.
     */
    public boolean msgBoolean(Pointer receiver, Pointer selector, Object... args){
        Pointer res = msg(receiver, selector, args);
        return res.address > 0L ? true : false;
    }
    
    /**
     * Wrapper around msg() that returns a boolean value.  This should only be used
     * for sending messages that return boolean-compatible numeric values.  Essentially
     * any non-zero value is interpreted (and returned) as true.  Zero values are 
     * interpreted as false.
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args Arguments passed to the message.
     * @return Boolean return value of the message.
     */
    public boolean msgBoolean(String receiver, Pointer selector, Object... args){
        return msgBoolean(cls(receiver), selector, args);
    }
    
    /**
     * Wrapper around msg() that returns a boolean value.  This should only be used
     * for sending messages that return boolean-compatible numeric values.  Essentially
     * any non-zero value is interpreted (and returned) as true.  Zero values are 
     * interpreted as false.
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args Arguments passed to the message.
     * @return Boolean return value of the message.
     */
    public boolean msgBoolean(String receiver, String selector, Object... args){
        if (args.length > 0 && selector.lastIndexOf(':') != selector.length()-1) {
            Log.p("Selector '"+selector+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            selector = selector + ":";
        }
        return msgBoolean(cls(receiver), sel(selector), args);
    }
    
    /**
     * Wrapper around msg() that returns a boolean value.  This should only be used
     * for sending messages that return boolean-compatible numeric values.  Essentially
     * any non-zero value is interpreted (and returned) as true.  Zero values are 
     * interpreted as false.
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args Arguments passed to the message.
     * @return Boolean return value of the message.
     */
    public boolean msgBoolean(Pointer receiver, String selector, Object... args){
        if (args.length > 0 && selector.lastIndexOf(':') != selector.length()-1) {
            Log.p("Selector '"+selector+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            selector = selector + ":";
        }
        return msgBoolean(receiver, sel(selector), args);
    }
    
    /**
     * Wrapper around msg() that returns a string value.  This should only be used
     * for messages that return a CString.  Do not use this for messages that 
     * return numeric values or Pointers to objects (e.g. NSString).  Use the 
     * msg() variant that takes coerceInputs and coerceOutputs parameters if you
     * want NSStrings to be automatically converted to java Strings.
     * 
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args Arguments passed to the message.
     * @return The string return value of the message.
     */
    public String msgString(Pointer receiver, Pointer selector, Object... args){
        Pointer res = msg(receiver, selector, args);
        return res.getString(0);
    }
    
    /**
     * Wrapper around msg() that returns a string value.  This should only be used
     * for messages that return a CString.  Do not use this for messages that 
     * return numeric values or Pointers to objects (e.g. NSString).  Use the 
     * msg() variant that takes coerceInputs and coerceOutputs parameters if you
     * want NSStrings to be automatically converted to java Strings.
     * 
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args Arguments passed to the message.
     * @return The string return value of the message.
     */
    public String msgString(String receiver, Pointer selector, Object... args){
        return msgString(cls(receiver), selector, args);
    }
    
    /**
     * Wrapper around msg() that returns a string value.  This should only be used
     * for messages that return a CString.  Do not use this for messages that 
     * return numeric values or Pointers to objects (e.g. NSString).  Use the 
     * msg() variant that takes coerceInputs and coerceOutputs parameters if you
     * want NSStrings to be automatically converted to java Strings.
     * 
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args Arguments passed to the message.
     * @return The string return value of the message.
     */
    public String msgString(String receiver, String selector, Object... args){
        if (args.length > 0 && selector.lastIndexOf(':') != selector.length()-1) {
            Log.p("Selector '"+selector+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            selector = selector + ":";
        }
        return msgString(cls(receiver), sel(selector), args);
    }
    
    /**
     * Wrapper around msg() that returns a string value.  This should only be used
     * for messages that return a CString.  Do not use this for messages that 
     * return numeric values or Pointers to objects (e.g. NSString).  Use the 
     * msg() variant that takes coerceInputs and coerceOutputs parameters if you
     * want NSStrings to be automatically converted to java Strings.
     * 
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args Arguments passed to the message.
     * @return The string return value of the message.
     */
    public String msgString(Pointer receiver, String selector, Object... args){
        if (args.length > 0 && selector.lastIndexOf(':') != selector.length()-1) {
            Log.p("Selector '"+selector+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            selector = selector + ":";
        }
        return msgString(receiver, sel(selector), args);
    }
    
    /**
     * Sends a message that returns a double value.  This variant must be used
     * if the method returns a float or a double.  It doesn't actually wrap the
     * primitive msg() method..  Rather it uses a different core Objective-C method,
     * objc_msgSend_fpret().
     * 
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return The double return value of the message
     */
    public double msgDouble(Pointer receiver, Pointer selector, Object... args){
        return objc_msgSend_fpret(receiver, selector, args);
    }
    
    /**
     * Sends a message that returns a double value.  This variant must be used
     * if the method returns a float or a double.  It doesn't actually wrap the
     * primitive msg() method..  Rather it uses a different core Objective-C method,
     * objc_msgSend_fpret().
     * 
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return The double return value of the message
     */
    public double msgDouble(String receiver, Pointer selector, Object... args){
        return msgDouble(cls(receiver), selector, args);
    }
    
    /**
     * Sends a message that returns a double value.  This variant must be used
     * if the method returns a float or a double.  It doesn't actually wrap the
     * primitive msg() method..  Rather it uses a different core Objective-C method,
     * objc_msgSend_fpret().
     * 
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return The double return value of the message
     */
    public double msgDouble(String receiver, String selector, Object... args){
        if (args.length > 0 && selector.lastIndexOf(':') != selector.length()-1) {
            Log.p("Selector '"+selector+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            selector = selector + ":";
        }
        return msgDouble(cls(receiver), sel(selector), args);
    }
    
    /**
     * Sends a message that returns a double value.  This variant must be used
     * if the method returns a float or a double.  It doesn't actually wrap the
     * primitive msg() method..  Rather it uses a different core Objective-C method,
     * objc_msgSend_fpret().
     * 
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments passed to the message.
     * @return The double return value of the message
     */
    public double msgDouble(Pointer receiver, String selector, Object... args){
        if (args.length > 0 && selector.lastIndexOf(':') != selector.length()-1) {
            Log.p("Selector '"+selector+"' should end with a colon if it accepts args.  Adding colon automatically.  Please correct this in your source.");
            selector = selector + ":";
        }
        return msgDouble(receiver, sel(selector), args);
    }
    
    public static class MethodSignatureNotFound extends RuntimeException {
        String target;
        String selector;
        public MethodSignatureNotFound() {
            
        }

        @Override
        public String getMessage() {
            return "Method signature not found for selector ["+selector+"] on target "+target;
        }

        @Override
        public String toString() {
            return getMessage();
        }
        
        
        
        
    }
    
    /**
     * Exception thrown when a class cannot be found.
     */
    public static class ObjCClassNotFound extends RuntimeException {
        String className;
        public ObjCClassNotFound(String className) {
            super("Objective-C class not found: "+className);
            this.className = className;
        }
    }
    
    static void throwRuntimeException(String msg) {
        throw new RuntimeException(msg);
    }
    
    /**
     * Sends a message with the option of coercing the inputs and outputs. This variant
     * uses a higher level of abstraction than the standard msg() and msgXXX() methods.
     * If coerceReturn and coerceArgs are set to true, then this method will 
     * convert the inputs from Java inputs to the corresponding Objective-C inputs.  
     * Further it will return values that are more appropriate for use in Java.
     * 
     * <p>Furthermore, this variant is smart about which variable of msg() to call
     * behind the scenes.  E.g. if the message signature specifies that this message
     * returns a double, it will automatically use the double variant of msg().</p>
     * 
     * @param coerceReturn If true, then the return value will be mapped to an appropriate
     *  Java value using the TypeMapper class.
     * @param coerceArgs If true, then the inputs will be mapped from Java to appropriate
     * C values using the TypeMapper class.
     * @param receiver The target of the message.
     * @param selector The selector for the message.
     * @param args The arguments to be passed in the message.
     * @return The return value of the message.  This may be a Pointer or a value depending
     *  on the return type of the message.
     * 
     */
    public Object msg(boolean coerceReturn, boolean coerceArgs, Pointer receiver, Pointer selector, Object... args){

        
        Object[] originalArgs = args;
        
        Pointer methodSignature = msgPointer(receiver, "methodSignatureForSelector:", selector);
        if (methodSignature == null || methodSignature.address == 0) {
            Log.p("No method signature found for provided selector");
            throw new MethodSignatureNotFound();
        }
        int numArgs = (int)msg(methodSignature, "numberOfArguments").getInt();
        if ( numArgs ==2   &&  numArgs != args.length+2 ){
            throw new RuntimeException("Wrong argument count.  The selector "+selName(selector)+" requires "+(numArgs-2)+" arguments, but received "+args.length);
        }
        
        
        Pointer returnTypePtr = msg(methodSignature, "methodReturnType");
        if (returnTypePtr == null || returnTypePtr.address == 0) {
            Log.p("No method signature found for provided selector");
            throw new IllegalArgumentException("No return type found for selector");
        }
        String returnTypeSignature = returnTypePtr.getString(0);
        if ( numArgs == 0 && returnTypeSignature == null ){
            return msg(receiver, selector, args);
        }
        
        Block[] createdBlocks = new Block[args.length];
        
        if ( coerceArgs && args.length > 0 ){
            originalArgs = new Object[args.length];
            System.arraycopy(args, 0, originalArgs, 0, args.length);
            
            
            for ( int i=0; i<args.length; i++ ){
                if (args[i] instanceof Runnable) {
                    args[i] = createBlock((Runnable)args[i]);
                    createdBlocks[i] = (Block)args[i];
                    args[i] = createdBlocks[i].block;
                }
                if (args[i] instanceof Method) {
                    args[i] = createBlock((Method)args[i]);
                    createdBlocks[i] = (Block)args[i];
                    args[i] = createdBlocks[i].block;
                }
                if (args[i] instanceof Block) {
                    if (!((Block)args[i]).isNativeActive()) {
                        throw new IllegalArgumentException("Attempt to pass an inactive block to a method as an argument, This block has already had releaseNative() called on it.");
                    }
                    args[i] = ((Block)args[i]).block;
                    
                }
                
                ByteByReference out = new ByteByReference();
                Pointer out2 = msg(methodSignature, "getArgumentTypeAtIndex:", i+2);
                String argumentTypeSignature = out2.getString(0);
                args[i] = TypeMapper.getInstance().jToC(args[i], argumentTypeSignature, TypeMapper.getInstance());
                
            }
        }
        
        
        String prefixes = "rnNoORV";
        int offset = 0;
        while ( prefixes.indexOf(returnTypeSignature.charAt(offset)) != -1 ){
            offset++;
            if ( offset > returnTypeSignature.length()-1 ){
                break;
            }
        }
        if ( offset > 0 ){
            returnTypeSignature = returnTypeSignature.substring(offset);
        }
        
        String returnTypeFirstChar = returnTypeSignature.substring(0,1);
        if ( "[{(".indexOf(returnTypeFirstChar) ==-1 ){
            // We are not returning a structure so we'll just
            // do the message.
            
            // We need to handle doubles and floats separately
            if ( "df".indexOf(returnTypeFirstChar) != -1 ){
                Object res = msgDouble(receiver, selector, args);
                for ( int i=0; i<args.length; i++){
                    Proxy.release(args[i]);
                }
                for (Block b : createdBlocks) {
                    if (b != null) {
                        b.releaseNative();
                    }
                }
                return res;
            } else {
            
                Pointer result = msg(receiver, selector, args);
                if ( coerceReturn ){
                    Object res2 =  TypeMapper.getInstance().cToJ(result, returnTypeSignature, TypeMapper.getInstance());
                    for ( int i=0; i<args.length; i++){
                        Proxy.release(args[i]);
                    }
                    for (Block b : createdBlocks) {
                        if (b != null) {
                            b.releaseNative();
                        }
                    }
                    return res2;
                } else {
                    for ( int i=0; i<args.length; i++){
                        Proxy.release(args[i]);
                    }
                    for (Block b : createdBlocks) {
                        if (b != null) {
                            b.releaseNative();
                        }
                    }
                    return result;
                }
            }
        }
        
        
        Object output =  msg(receiver, selector, args);
        for ( int i=0; i<args.length; i++){
            Proxy.release(args[i]);
        }
        return output;
        
        
        
        
    }
    
    
    /**
     * Returns the size of an array that is specified in a signature.
     * @param signature The signature for a parameter using <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Conceptual/ObjCRuntimeGuide/Articles/ocrtTypeEncodings.html">Objective-C type encodings</a>.
     * @return The size of the array.
     * @see <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Conceptual/ObjCRuntimeGuide/Articles/ocrtTypeEncodings.html">Objective-C Type Encodings</a>
     */
    public int arraySize(String signature){
        int typeIndex = 2;
        String digits = "0123456789";
            
        while ( digits.indexOf(signature.charAt(typeIndex++)) != -1 ){}
        
        return Integer.parseInt(signature.substring(1, typeIndex));
        
    }
    
     
     
     /**
      * Returns the pointer to the Native peer for a Peerable object.
      * 
      * @param peer
      * @return The Pointer to a native peer.
      */
     public Pointer addr(Peerable peer){
         return peer.getPeer();
     }
     
     
    
     /**
      * Converts a Java string to an NSString object.
      * @param str The Java string to convert.
      * @return Pointer to the NSString that corresponds to this string.
      */
    public Pointer str(String str){
        return msgPointer("NSString", "stringWithUTF8String:", str);
    }
    
    /**
     * Converts A native NSString object to a Java string.
     * @param str
     * @return A Java string.
     */
    public String str(Pointer str){
        Pointer ptr = msg(str, "UTF8String");
        return ptr.getString(0);
    }
    
    
    
    
    /**
     * Wraps the given value in the appropriate ByReference subclass according
     * to the provided signature.  This is a useful method in cases where
     * you need to pass a parameter to a C-function by reference, but you don't
     * necessarily know what type of data it is.
     * @param val The value to be wrapped in a byReference.
     * @param signature The signature (using <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Conceptual/ObjCRuntimeGuide/Articles/ocrtTypeEncodings.html">Objective-C type encodings</a>) of the value.
     * @return A pointer to the reference that can be passed in a C function.
     * @see <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Conceptual/ObjCRuntimeGuide/Articles/ocrtTypeEncodings.html">Objective-C Type Encodings</a>
     */
    public Pointer getAsReference(Object val, String signature){
        return getAsReferenceWrapper(val, signature).getPointer();
    }
    
    /**
     * Wraps the given value in the appropriate ByReference subclass according
     * to the provided signature.  This is a useful method in cases where
     * you need to pass a parameter to a C-function by reference, but you don't
     * necessarily know what type of data it is.
     * @param val The value to be wrapped in a byReference.
     * @param signature The signature (using <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Conceptual/ObjCRuntimeGuide/Articles/ocrtTypeEncodings.html">Objective-C type encodings</a>) of the value.
     * @return The ByReference object that contains the pointer that can be passed
     * to a c function.
     * @see <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Conceptual/ObjCRuntimeGuide/Articles/ocrtTypeEncodings.html">Objective-C Type Encodings</a>
     */
    public ByReference getAsReferenceWrapper(Object val, String signature){
        String prefixes = "rnNoORV";
        int offset = 0;
        while ( prefixes.indexOf(signature.charAt(offset)) != -1 ){
            offset++;
            if ( offset > signature.length()-1 ){
                break;
            }
        }
        if ( offset > 0 ){
            signature = signature.substring(offset);
        }
        
        String firstChar = signature.substring(0,1);
        String numeric = "iIsSlLqQfd";
        
       
        
        
        //String firstChar = signature.substring(0,1);
        switch ( signature.charAt(0)){
            case 'i':
            case 'I':
                if ( !(val instanceof Integer) ){
                    if ( val instanceof Number ){
                        val = ((Number)val).intValue();
                    } else if ( val instanceof String){
                        val = Integer.parseInt((String)val);
                    } else {
                        throw new RuntimeException("Attempt to pass ineligible value to int: "+val);
                    }
                }
                return new IntByReference((Integer)val);
            case 's':
            case 'S':
                if ( !(val instanceof Short) ){
                    if ( val instanceof Number ){
                        val = ((Number)val).shortValue();
                    } else if ( val instanceof String){
                        val = new Integer(Integer.parseInt((String)val)).shortValue();
                    } else {
                        throw new RuntimeException("Attempt to pass ineligible value to short: "+val);
                    }
                }
                return new ShortByReference((Short)val);
                
            case 'l':
            case 'L':
            case 'q':
            case 'Q':
                if ( !(val instanceof Long) ){
                    if ( val instanceof Number ){
                        val = ((Number)val).longValue();
                    } else if ( val instanceof String){
                        val = new Long(Long.parseLong((String)val)).longValue();
                    } else {
                        throw new RuntimeException("Attempt to pass ineligible value to long: "+val);
                    }
                }
                return new LongByReference((Long)val);
                
            case 'f':
                if ( !(val instanceof Float) ){
                    if ( val instanceof Number){
                        val = ((Number)val).floatValue();
                    } else if ( String.class.isInstance(val)){
                        val = new Float(Float.parseFloat((String)val)).floatValue();
                    } else {
                        throw new RuntimeException("Attempt to pass ineligible value to long: "+val);
                    }
                }
                return new FloatByReference((Float)val);
                
            case 'd':
                if ( !(val instanceof Double) ){
                    if (val instanceof Number ){
                        val = ((Number)val).doubleValue();
                    } else if ( val instanceof String){
                        val = new Double(Double.parseDouble((String)val)).doubleValue();
                    } else {
                        throw new RuntimeException("Attempt to pass ineligible value to long: "+val);
                    }
                }
                return new DoubleByReference((Double)val);
            case 'B':
            case 'b':
            case 'c':
            case 'C':
                if ( val instanceof Number ){
                    val = (byte)((Number)val).intValue();
                } else if ( String.class.isInstance(val)){
                    val = (byte)new Byte(Byte.parseByte((String)val)).intValue();
                } else {
                    throw new RuntimeException("Attempt to pass ineligible value to byte: "+val);
                }
                return new ByteByReference((Byte)val);
            case 'v':
                return null;
            case '^':
            default:
                ////System.out.println("Outputting pointer by reference for value "+val+" and signature "+signature);
                if ( val == null ){
                    try {
                        throw new RuntimeException("Checking stack trace for val "+val+" and signature "+signature);
                        
                    } catch (Exception ex){
                        Log.e(ex);
                    }
                }
                if ( Pointer.class.isInstance(val) ){
                    return new PointerByReference((Pointer)val);
                } else if ( val instanceof Long){
                    return new PointerByReference(new Pointer((Long)val));
                } else {
                    throw new RuntimeException("Don't know what to do for conversion of value "+val+" and signature "+signature);
                }
                
            
                
        }
        
    }
    
    /**
     * Class for working with pointers to Structs
     */
    public static class Struct {
        // Pointer to a struct
        Pointer ptr;
        
        // Sizes of struct entries in bytes
        int[] sizes;
        
        /**
         * Creates a new struct
         * @param nsValue The NSValue pointer that contains the struct
         * @param sizes The sizes of the entries in bytes.
         */
        public Struct(Pointer nsValue, int... sizes) {
            this.ptr = new Pointer(instance.rt.getNSValueAsBytes(nsValue.address));
            this.sizes = sizes;
        }
        
        public int getInt(int index) {
            long address = ptr.address + indexToOffset(index);
            return new Pointer(address).getValueAsInt();
        }
        
        public float getFloat(int index) {
            long address = ptr.address + indexToOffset(index);
            return new Pointer(address).getValueAsFloat();
        }
        
        public double getDouble(int index) {
            long address = ptr.address + indexToOffset(index);
            return new Pointer(address).getValueAsDouble();
        }
        
        private int indexToOffset(int index) {
            int out = 0;
            for (int i=0; i<index; i++) {
                out += sizes[i];
            }
            return out;
        }

        
        protected void finalize() throws Throwable {
            instance.rt.release(ptr.address);
        }
        
        
        
    }
    
    private Map<Integer,Recipient> proxies;
    private Map<Integer,Recipient> proxies() {
        if (proxies == null) {
            proxies = new HashMap<Integer,Recipient>();
        }
        return proxies;
    }
    
    static boolean respondsToSelector(int proxyId, long selector) {
        Runtime instance = Runtime.getInstance();
        if (instance.proxies != null) {
            Recipient r;
            synchronized(instance.proxies) {
                r = instance.proxies.get(proxyId);
            }
            if (r != null) {
                return r.respondsToSelector(new Pointer(selector));
            }
        }
        return false;
    }
    
    static void forwardInvocation(int proxyId, long invocation) {
        Runtime instance = Runtime.getInstance();
        if (instance.proxies != null) {
            Recipient r;
            synchronized(instance.proxies) {
                r = instance.proxies.get(proxyId);
            }
            if (r != null) {
                r.forwardInvocation(new Pointer(invocation));
            }
        }
    }
    
    static long methodSignatureForSelector(int proxyId, long selector) {
        Runtime instance = Runtime.getInstance();
        if (instance.proxies != null) {
            Recipient r;
            synchronized(instance.proxies) {
                r = instance.proxies.get(proxyId);
            }
            if (r != null) {
                Pointer res = r.methodSignatureForSelector(new Pointer(selector));
                if (res != null) {
                    return res.address;
                }
            }
        }
        return 0;
    }
    
    
    
    /**
     * Registers a Java object with the Objective-C runtime so that it can begin
     * to receive messages from it.  This will create an Objective-C proxy 
     * that passes messages to the Recipient object.  This step is automatically
     * handled by the NSObject class inside its init() method.
     * @param client
     * @return 
     */
    public Pointer createProxy(Recipient client) {
        proxies();
        synchronized(proxies) {
            for (int i=0; i<10000; i++) {
                if (!proxies.containsKey(i)) {
                    proxies.put(i, client);
                    return p(rt.createProxy(i));
                }
            }
        }
        throw new RuntimeException("Failed to create proxy.  Could not find free proxy ID.");
    }
    
    /**
     * Callback from native code when a proxy is deallocated.
     * @param id 
     */
    public static void deleteProxy(int id) {
        Runtime instance = getInstance();
        
        if (instance.proxies != null) {
            synchronized(instance.proxies) {
                if (instance.proxies.containsKey(id)) {
                    instance.proxies.remove(id);
                }
            }
        }
    }
    
    /**
     * Returns the Java peer recipient for a native Objective-C object if it 
     * exists.  This will return null if nsObject is not an WLProxy object
     * that has been previously registered with a Recipient.
     * @param nsObject
     * @return 
     */
    public Recipient getJavaPeer(Pointer nsObject) {
        int id = rt.getJavaPeer(nsObject.address);
        if (id >= 0 && proxies != null) {
            synchronized(proxies) {
                if (proxies.containsKey(id)) {
                    return proxies.get(id);
                }
            }
        }
        
        return null;
        
    }
    
    
    public PeerComponent createPeerComponent(Pointer uiView) {
        return ComCodename1ObjcAccessor.createPeerComponent(uiView);
    }
    
    public String utf8StringToString(Pointer ptr) {
        return rt.utf8StringToString(ptr.address);
    }
    
    public Pointer stringToUTF8String(String str) {
        return p(rt.stringToUTF8String(str));
    }
}
