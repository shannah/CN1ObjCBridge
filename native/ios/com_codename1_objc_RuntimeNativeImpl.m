#import <objc/runtime.h>
#import <objc/message.h>
#import <objc/objc.h>
#import <Foundation/Foundation.h>
#import "com_codename1_objc_RuntimeNativeImpl.h"
#import "com_codename1_objc_Runtime.h"
#import "CN1ObjcProxy.h"
#import "CodenameOne_GLViewController.h"
#import "ffi_mini.h"

@implementation com_codename1_objc_RuntimeNativeImpl

-(long long)objc_getClass:(NSString*)param{
    return objc_getClass([param UTF8String]);
}

-(long long)method_getImplementation:(long long)param{
    return method_getImplementation(param);
}

-(long long)method_setImplementation:(long long)param param1:(long long)param1{
    return method_setImplementation(param, param1);
}

-(long long)object_getIndexedIvars:(long long)param{
    return object_getIndexedIvars(param);
}

-(long long)objc_msgSendSuper_stret:(long long)param param1:(long long)param1 param2:(NSString*)param2{
    NSLog(@"objc_msgSendSuper_stret is not implemented");
    //objc_msgSendS
    //objc_msgSendSuper_stret((id)param, (void*)param1, [param2 UTF8String]);
    //objc_msgSendSuper_stret((void*)param, (void*)param1, [param2 UTF8String]);
    return 0; // This needs to be fixed... need to return something here.
}

-(double)objc_msgSend_fpret:(long long)param param1:(long long)param1 param2:(NSString*)param2{
    NSLog(@"objc_msgSendSuper_fpret is not implemented");
    return 0;
}

-(long long)objc_getProtocol:(NSString*)param{
    return objc_getProtocol([param UTF8String]);
}

-(long long)createAutoreleasePool{
    return [[NSAutoreleasePool alloc] init];
}

-(NSString*)method_getTypeEncoding:(long long)param{
    return [NSString stringWithUTF8String:method_getTypeEncoding(param)];
}

-(void)autorelease:(long long)param{
    [((NSObject*)param) autorelease];
}

-(BOOL)protocol_conformsToProtocol:(long long)param param1:(long long)param1{
    return protocol_conformsToProtocol(param, param1);
}

-(long long)malloc_double:(double)param{
    return malloc(sizeof(double));
}

-(long long)object_copy:(long long)param param1:(long long)param1{
    return object_copy(param, param1);
}

-(short)getPointerValueShort:(long long)param{
    return (short)*((short*)param);
}

-(NSString*)ivar_getName:(long long)param{
    return [NSString stringWithUTF8String:ivar_getName(param)];
}

-(void)class_setWeakIvarLayout:(long long)param param1:(NSString*)param1{
    class_setWeakIvarLayout(param, [param1 UTF8String]);
}

-(int)objc_getClassList:(long long)param param1:(int)param1{
    return objc_getClassList(param, param1);
}

-(NSString*)sel_getName:(long long)param{
    return [NSString stringWithUTF8String:sel_getName(param)];
}

-(long long)getPointerValueLong:(long long)param{
    return (long long)*((long long*)param);
}

-(long long)class_getProperty:(long long)param param1:(NSString*)param1{
    return class_getProperty(param, [param1 UTF8String]);
}

-(NSString*)class_getName:(long long)param{
    return [NSString stringWithUTF8String:class_getName(param)];
}

-(void)method_exchangeImplementations:(long long)param param1:(long long)param1{
    method_exchangeImplementations(param, param1);
}

-(BOOL)class_isMetaClass:(long long)param{
    return class_isMetaClass(param);
}

-(long long)objc_getRequiredClass:(NSString*)param{
    return objc_getRequiredClass([param UTF8String]);
}

-(long long)sel_getUid:(NSString*)param{
    return sel_getUid([param UTF8String]);
}

-(void)object_setIvar:(long long)param param1:(long long)param1 param2:(long long)param2{
    object_setIvar(param, param1, param2);
}

-(void)setPointerValue_int:(long long)param param1:(int)param1{
    *((int*)param) = param1;
}

-(long long)malloc_long:(long long)param{
    return malloc(sizeof(long long));
}

-(long long)protocol_copyPropertyList:(long long)param param1:(long long)param1{
    return protocol_copyPropertyList(param, param1);
}



-(long long)objc_msgSend:(long long)param param1:(long long)param1 param2:(NSString*)param2{
    //if ([param2 length] > 2) {
    //    param2 = [param2 stringByReplacingOccurrencesOfString:@"]" withString:@"}]"];
    //}
    NSData *data = [param2 dataUsingEncoding:NSUTF8StringEncoding];
    NSArray *nsargs = (NSArray*)[NSJSONSerialization JSONObjectWithData:data options:0 error:nil];
    
    ffim_cif cif;
    ffim_type *args[[nsargs count] + 2];
    void *values[[nsargs count] + 2];
    
    int* intValues[[nsargs count] + 2];
    double doubleValues[[nsargs count] + 2];
    float floatValues[[nsargs count] + 2];
    void* pointerValues[[nsargs count] + 2];
    
    
    char *s;
    
    /* Initialize the argument info vectors */
    args[0] = &ffim_type_pointer;
    pointerValues[0] = (void*)param;
    values[0] = &(pointerValues[0]);
    args[1] = &ffim_type_pointer;
    pointerValues[1] = (void*)param1;
    values[1] = &(pointerValues[1]);
    
    void* retVal;
    
    int index=2;
    for (NSDictionary* item in nsargs) {
        NSString* type = [item objectForKey:@"type"];
        NSObject* val = [item objectForKey:@"value"];
        if ([type isEqualToString:@"String"]) {
            args[index] = &ffim_type_pointer;
            pointerValues[index] = (void*)[((NSString*)val) UTF8String];
            values[index] = &(pointerValues[index]);
        } else if ([type isEqualToString:@"int"]) {
            args[index] = &ffim_type_sint32;
            intValues[index] = (JAVA_INT)[((NSNumber*)val) intValue];
            values[index] = &(intValues[index]);
        } else if ([type isEqualToString:@"float"]) {
            args[index] = &ffim_type_float;
            floatValues[index] = (float)[((NSNumber*)val) floatValue];
            values[index] = &(floatValues[index]);
        } else if ([type isEqualToString:@"double"]) {
            args[index] = &ffim_type_double;
            doubleValues[index] = (double)[((NSNumber*)val) doubleValue];
            values[index] = &(doubleValues[index]);
        } else if ([type isEqualToString:@"pointer"]) {
            void* ptr = (void*)[((NSString*)val) longLongValue];
            args[index] = &ffim_type_pointer;
            pointerValues[index] = ptr;
            values[index] = &(pointerValues[index]);
        }
        index++;
    }
    
    /* Initialize the cif */
    if (ffi_mini_prep_cif_var(&cif, FFIM_DEFAULT_ABI, 2, [nsargs count] + 2,
                     &ffim_type_pointer, args) == FFIM_OK)
    {
        ffi_mini_call(&cif, objc_msgSend, &retVal, values);
        /* rc now holds the result of the call to puts */
        return (long long )retVal;
        
    }
    
    return 0;
    
}

-(BOOL)protocol_isEqual:(long long)param param1:(long long)param1{
    return protocol_isEqual(param, param1);
}

-(void)setPointerValue_short:(long long)param param1:(short)param1{
    (*((short*)param)) = param1;
}

-(long long)objc_allocateClassPair:(long long)param param1:(NSString*)param1 param2:(long long)param2{
    return objc_allocateClassPair(param, [param1 UTF8String], param2);
}

-(BOOL)sel_isEqual:(long long)param param1:(long long)param1{
    return sel_isEqual(param, param1);
}

-(long long)malloc_short:(short)param{
    return malloc(sizeof(short));
}

-(NSString*)class_getWeakIvarLayout:(long long)param{
    return [NSString stringWithUTF8String:class_getWeakIvarLayout(param)];
}

-(NSString*)protocol_getName:(long long)param{
    return [NSString stringWithUTF8String:protocol_getName(param)];
}

-(NSString*)object_getClassName:(long long)param{
    return [NSString stringWithUTF8String:object_getClassName(param)];
}

-(void)method_getReturnType:(long long)param param1:(long long)param1 param2:(long long)param2{
    return method_getReturnType(param, param1, param2);
}

-(void)setPointerValue_byte:(long long)param param1:(char)param1{
    *((char*)param) = param1;
}

-(long long)class_getInstanceVariable:(long long)param param1:(NSString*)param1{
    return class_getInstanceVariable(param, [param1 UTF8String]);
}

-(void)objc_msgSend_stret:(long long)param param1:(long long)param1 param2:(long long)param2 param3:(NSString*)param3{
    objc_msgSend_stret((id)param1, (void*)param2, [param3 UTF8String]);
    
}

-(long long)objc_lookUpClass:(NSString*)param{
    return objc_lookUpClass([param UTF8String]);
}

-(long long)objc_copyProtocolList:(long long)param{
    return objc_copyProtocolList(param);
}

-(long long)protocol_getMethodDescription:(long long)param param1:(long long)param1 param2:(BOOL)param2 param3:(BOOL)param3{
    
    NSLog(@"protocol_getMethodDescription not implemented");
    return 0;
    //eturn protocol_getMethodDescription(param, param1, param2, param3);
}

-(void)objc_registerClassPair:(long long)param{
    objc_registerClassPair(param);
}

-(long long)object_getInstanceVariable:(long long)param param1:(NSString*)param1 param2:(long long)param2{
    return object_getInstanceVariable(param, [param1 UTF8String], param2);
}

-(long long)class_getClassMethod:(long long)param param1:(long long)param1{
    return class_getClassMethod(param, param1);
}

-(long long)protocol_copyProtocolList:(long long)param param1:(long long)param1{
    return protocol_copyProtocolList(param, param1);
}

-(long long)ivar_getOffset:(long long)param{
    return ivar_getOffset(param);
}

-(int)method_getNumberOfArguments:(long long)param{
    return method_getNumberOfArguments(param);
}

-(long long)class_replaceMethod:(long long)param param1:(long long)param1 param2:(long long)param2 param3:(NSString*)param3{
    return class_replaceMethod(param, param1, param2, [param3 UTF8String]);
}

-(long long)class_getMethodImplementation_stret:(long long)param param1:(long long)param1{
    return class_getMethodImplementation_stret(param, param1);
}

-(long long)class_respondsToSelector:(long long)param param1:(long long)param1{
    return class_respondsToSelector(param, param1);
}

-(long long)protocol_getProperty:(long long)param param1:(NSString*)param1 param2:(BOOL)param2 param3:(BOOL)param3{
    return protocol_getProperty(param, [param1 UTF8String], param2, param3);
}

-(long long)class_getSuperclass:(long long)param{
    return class_getSuperclass((void*)param);
}

-(void)objc_removeAssociatedObjects:(long long)param{
    objc_removeAssociatedObjects(param);
}

-(double)getPointerValueDouble:(long long)param{
    return *((double*)param);
}

-(long long)object_getIvar:(long long)param param1:(long long)param1{
    return object_getIvar(param, param1);
}

-(int)class_getInstanceSize:(long long)param{
    return class_getInstanceSize(param);
}

-(long long)object_setInstanceVariable:(long long)param param1:(NSString*)param1 param2:(long long)param2{
    return object_setInstanceVariable(param, [param1 UTF8String], param2);
}

-(long long)protocol_getMethodSignature:(long long)param param1:(long long)param1 param2:(BOOL)param2 param3:(BOOL)param3{
    struct objc_method_description theDescription = protocol_getMethodDescription(param,param1, param2, param3);
    return [NSMethodSignature signatureWithObjCTypes:theDescription.types];
}

-(void)class_setVersion:(long long)param param1:(int)param1{
    class_setVersion(param, param1);
}

-(long long)object_setClass:(long long)param param1:(long long)param1{
    return object_setClass(param, param1);
}

-(long long)class_getInstanceMethod:(long long)param param1:(long long)param1{
    return class_getInstanceMethod(param, param1);
}

-(void)objc_setAssociatedObject:(long long)param param1:(long long)param1 param2:(long long)param2 param3:(long long)param3{
    objc_setAssociatedObject(param, param1, param2, param3);
}

-(float)getPointerValueFloat:(long long)param{
    return *((float*)param);
}

-(long long)malloc_int:(int)param{
    return malloc(sizeof(int));
}

-(int)getPointerValueInt:(long long)param{
    return *((int*)param);
}

-(long long)object_dispose:(long long)param{
    return object_dispose(param);
}

-(long long)method_getName:(long long)param{
    return method_getName(param);
}

-(int)getJavaPeer:(long long)param{
    NSObject* proxy = (NSObject*)param;
    if ( [proxy respondsToSelector:@selector(proxyId)] ){
        return [((CN1ObjcProxy*)param) proxyId];
    } else {
        return -1;
    }
}

-(NSString*)class_getIvarLayout:(long long)param{
    return [NSString stringWithUTF8String:class_getIvarLayout(param)];
}

-(long long)object_getClass:(long long)param{
    return object_getClass(param);
}

-(char)getPointerValueByte:(long long)param{
    return *((char*)param);
}

-(long long)class_getMethodImplementation:(long long)param param1:(long long)param1{
    return class_getMethodImplementation(param, param1);
}

-(NSString*)method_copyReturnType:(long long)param{
    return [NSString stringWithUTF8String:method_copyReturnType(param)];
}

-(NSString*)method_copyArgumentType:(long long)param param1:(int)param1{
    return [NSString stringWithUTF8String:method_copyArgumentType(param, param1)];
}

-(void)retain:(long long)param{
    [((NSObject*)param) retain];
}

-(void)setPointerValue_double:(long long)param param1:(double)param1{
    *((double*)param) = param1;
}

-(void)release:(long long)param{
    [((NSObject*)param) release];
}

-(void)free:(long long)param{
    free((void*)param);
}

-(long long)createProxy:(int)param{
    return (long long)[[CN1ObjcProxy alloc] init:param];
}

-(long long)class_setSuperclass:(long long)param param1:(long long)param1{
    return class_setSuperclass(param, param1);
}

-(NSString*)property_getAttributes:(long long)param{
    return [NSString stringWithUTF8String:property_getAttributes(param)];
}

-(void)dispatch_sync:(int)param{
    dispatch_sync(dispatch_get_main_queue(), ^{
        com_codename1_objc_Runtime_runCallback___int(CN1_THREAD_GET_STATE_PASS_ARG param);
       //com_codename1_objc_Runtime_runCallback___int(CN1_THREAD_GET_STATE_PASS_ARG param);
    });
}
-(void)dispatch_sync_block:(long long)param {
    dispatch_sync(dispatch_get_main_queue(), (void (^)(void))param);
}

-(long long)createBlock:(int)param{
    void (^simpleBlock)(void);
    simpleBlock = ^{
        com_codename1_objc_Runtime_runCallbackNoRemove___int(CN1_THREAD_GET_STATE_PASS_ARG param);
    };
    
    return (long long)Block_copy(simpleBlock);
}

-(void)releaseBlock:(long long)param {
    Block_release((void*)param);
}


-(long long)malloc:(char)param{
    return malloc(sizeof(char));
}

-(long long)malloc_float:(float)param{
    return malloc(sizeof(float));
}

-(void)method_getArgumentType:(long long)param param1:(int)param1 param2:(long long)param2 param3:(long long)param3{
    method_getArgumentType(param, param1, param2, param3);
}

-(long long)objc_getAssociatedObject:(long long)param param1:(NSString*)param1{
    return objc_getAssociatedObject(param, [param1 UTF8String]);
}

-(void)dispatch_async:(int)param{
    dispatch_async(dispatch_get_main_queue(), ^{
       com_codename1_objc_Runtime_runCallback___int(CN1_THREAD_GET_STATE_PASS_ARG param); 
    });
}

-(long long)objc_getMetaClass:(NSString*)param{
    
    return objc_getMetaClass([param UTF8String]);
}

-(void)setPointerValue_float:(long long)param param1:(float)param1{
    *((float*)param) = param1;
}

-(NSString*)ivar_getTypeEncoding:(long long)param{
    return [NSString stringWithUTF8String:ivar_getTypeEncoding(param)];
}

-(void)class_setIvarLayout:(long long)param param1:(NSString*)param1{
    class_setIvarLayout(param, [param1 UTF8String]);
}

-(long long)objc_getFutureClass:(NSString*)param{
    return objc_getFutureClass([param UTF8String]);
}

-(long long)sel_registerName:(NSString*)param{
    return sel_registerName([param UTF8String]);
}

-(int)class_getVersion:(long long)param{
    return class_getVersion(param);
}

-(void)setPointerValue_long:(long long)param param1:(long long)param1{
    *((long long*)param) = param1;
}

-(void)objc_setFutureClass:(long long)param param1:(NSString*)param1{
    NSLog(@"objc_setFutureClass not implemented");
    //objc_setFutureClass((void*)param, [param1 UTF8String]);
}

-(long long)objc_msgSendSuper:(long long)param param1:(long long)param1 param2:(NSString*)param2{
    return objc_msgSendSuper(param, param1, [param2 UTF8String]);
}

-(NSString*)utf8StringToString:(long long)param {
    return [NSString stringWithUTF8String:param];
}

-(long long)stringToUTF8String:(NSString*)param {
    return (long long)[param UTF8String];
}

-(long long)getNSValueAsBytes:(long long)param {
    NSValue* value = (NSValue*)param;
    CGRect actualRect = value.CGRectValue;
    NSUInteger size;
    const char* encoding = [value objCType];
    NSGetSizeAndAlignment(encoding, &size, NULL);

    void* ptr = malloc(size);
    [value getValue:ptr];
    //NSData* data = [NSData dataWithBytes:ptr length:size];
    //free(ptr);

    return (long long)ptr;
}

-(BOOL)isSupported{
    return YES;
}

@end
