/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc;

import com.codename1.system.NativeInterface;

/**
 *
 * @author shannah
 */
public interface RuntimeNative extends NativeInterface {
    
    
    /**
     * Registers a Java object with the Objective-C runtime so that it can begin
     * to receive messages from it.  This will create an Objective-C proxy 
     * that passes messages to the Recipient object.  This step is automatically
     * handled by the NSObject class inside its init() method.
     * @param client
     * @return 
     */
    public long createProxy(int client);
    
    /**
     * Returns the Java peer recipient for a native Objective-C object if it 
     * exists.  This will return null if nsObject is not an WLProxy object
     * that has been previously registered with a Recipient.
     * @param nsObject
     * @return 
     */
    public int getJavaPeer(long nsObject);
    
    public long malloc(byte b);
    
    public long malloc_int(int i);
    public long malloc_long(long l);
    public long malloc_short(short s);
    public long malloc_float(float f);
    public long malloc_double(double d);
    
    public void setPointerValue_int(long ptr, int value);
    public void setPointerValue_byte(long ptr, byte value);
    public void setPointerValue_long(long ptr, long  value);
    public void setPointerValue_short(long ptr, short value);
    public void setPointerValue_float(long ptr, float value);
    public void setPointerValue_double(long ptr, double value);
    
    public int getPointerValueInt(long ptr);
    public byte getPointerValueByte(long ptr);
    public long getPointerValueLong(long ptr);
    public short getPointerValueShort(long ptr);
    public float getPointerValueFloat(long ptr);
    public double getPointerValueDouble(long ptr);
    
    public void free(long l);
    
    public long objc_lookUpClass(String name);
    public String class_getName(long id);
    public long class_getProperty(long cls, String name);
    public long class_getSuperclass(long cls);
    public int class_getVersion(long theClass);
    public String class_getWeakIvarLayout(long cls);
    public boolean class_isMetaClass(long cls);
    public int class_getInstanceSize(long cls);
    public long class_getInstanceVariable(long cls, String name);
    public long class_getInstanceMethod(long cls, long aSelector);
    public long class_getClassMethod(long cls, long aSelector);
    
    public String class_getIvarLayout(long cls);
    public long class_getMethodImplementation(long cls, long name);
    public long class_getMethodImplementation_stret(long cls, long name);
    public long class_replaceMethod(long cls, long name, long imp, String types);
    public long class_respondsToSelector(long cls, long sel);
    public void class_setIvarLayout(long cls, String layout);
    public long class_setSuperclass(long cls, long newSuper);
    public void class_setVersion(long theClass, int version);
    public void class_setWeakIvarLayout(long cls, String layout);
    public String ivar_getName(long ivar);
    public long ivar_getOffset(long ivar);
    public String ivar_getTypeEncoding(long ivar);
    public String method_copyArgumentType(long method, int index);
    public String method_copyReturnType(long method);
    public void method_exchangeImplementations(long m1, long m2);
    public void method_getArgumentType(long method, int index, long dst, long dst_len);
    public long method_getImplementation(long method);
    public long method_getName(long method);
    public int method_getNumberOfArguments(long method);
    public void method_getReturnType(long method, long dst, long dst_len);
    public String method_getTypeEncoding(long method);
    public long method_setImplementation(long method, long imp);
    public long objc_allocateClassPair(long superclass, String name, long extraBytes);
    public long[] objc_copyProtocolList(long outCount);
    public long objc_getAssociatedObject(long object, String key);
    public long objc_getClass(String name);
    public int objc_getClassList(long buffer, int bufferlen);
    public long objc_getFutureClass(String name);
    public long objc_getMetaClass(String name);
    public long objc_getProtocol(String name);
    public long objc_getRequiredClass(String name);
    public long objc_msgSend(long theReceiver, long theSelector,String jsonarguments);
    
    public long objc_msgSendSuper(long superClassStruct, long op, String jsonarguments);
    public long objc_msgSendSuper_stret(long superClassStruct, long op, String jsonarguments);
    public double objc_msgSend_fpret(long self, long op, String  jsonarguments);
    public void objc_msgSend_stret(long stretAddr, long theReceiver, long theSelector, String jsonarguments);
    public void objc_registerClassPair(long cls);
    public void objc_removeAssociatedObjects(long object);
    public void objc_setAssociatedObject(long object, long key, long value, long policy);
    public void objc_setFutureClass(long cls, String name);
    public long object_copy(long obj, long size);
    public long object_dispose(long obj);
    public long object_getClass(long object);
    public String object_getClassName(long obj);
    public long object_getIndexedIvars(long obj);
    public long object_getInstanceVariable(long obj, String name, long outValue);
    public long object_getIvar(long object, long ivar);
    public long object_setClass(long object, long cls);
    public long object_setInstanceVariable(long obj, String name, long value);
    public void object_setIvar(long object, long ivar, long value);
    public String property_getAttributes(long property);
    public boolean protocol_conformsToProtocol(long proto, long other);
    //public Structure protocol_copyMethodDescriptionList(long protocol, boolean isRequiredMethod, boolean isInstanceMethod, long outCount);
    public long protocol_copyPropertyList(long proto, long outCount);
    public long protocol_copyProtocolList(long proto, long outCount);
    public long protocol_getMethodDescription(long proto, long aSel, boolean isRequiredMethod, boolean isInstanceMethod);
    /*
    - (NSMethodSignature *)methodSignatureForSelector:(SEL)inSelector
{
    NSMethodSignature *theMethodSignature = [super methodSignatureForSelector:inSelector];
    if (theMethodSignature == NULL)
    {
        struct objc_method_description theDescription = protocol_getMethodDescription(@protocol(GameCenterManagerDelegate),inSelector, NO, YES);
        theMethodSignature = [NSMethodSignature signatureWithObjCTypes:theDescription.types];
    }
    return(theMethodSignature);
}
    */
    public long protocol_getMethodSignature(long proto, long aSel, boolean isRequiredMethod, boolean isInstanceMethod);
    public String protocol_getName(long proto);
    public long protocol_getProperty(long proto, String name, boolean isRequiredProperty, boolean isInstanceProperty);
    public boolean protocol_isEqual(long protocol, long other);
    public String sel_getName(long aSelector);
    
    public long sel_getUid(String name);
    public boolean sel_isEqual(long lhs, long rhs);
    public long sel_registerName(String name);
    
    public void dispatch_sync(int id);
    
    public void dispatch_async(int id);
    public void dispatch_sync_block(long blockPtr);
    public long getNSValueAsBytes(long nsValue);
    
    public long createBlock(int id);
    public void releaseBlock(long blockPtr);
    
    public void retain(long obj);
    
    public void release(long obj);
    
    public void autorelease(long obj);
    
    public long createAutoreleasePool();

    public String utf8StringToString(long address);

    public long stringToUTF8String(String str);
    
    
}
