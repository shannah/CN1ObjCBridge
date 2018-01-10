
#import "CN1ObjcProxy.h"
#import "com_codename1_objc_Runtime.h"
#import "CodenameOne_GLViewController.h"

@implementation CN1ObjcProxy

-(CN1ObjcProxy*)init:(JAVA_INT)proxyId
{
    
    _proxyId = proxyId;
    
    return self;
}

-(void)dealloc
{
    com_codename1_objc_Runtime_deleteProxy___int(CN1_THREAD_GET_STATE_PASS_ARG _proxyId);
    [super dealloc];
}

-(NSMethodSignature*)methodSignatureForSelector:(SEL)sel
{
    
    return (NSMethodSignature*)com_codename1_objc_Runtime_methodSignatureForSelector___int_long_R_long(CN1_THREAD_GET_STATE_PASS_ARG _proxyId, (JAVA_LONG)sel);
}

-(void)forwardInvocation:(NSInvocation *)invocation
{
    com_codename1_objc_Runtime_forwardInvocation___int_long(CN1_THREAD_GET_STATE_PASS_ARG _proxyId, (JAVA_LONG)invocation);
}

-(BOOL)respondsToSelector:(SEL)aSelector
{
    JAVA_BOOLEAN response = com_codename1_objc_Runtime_respondsToSelector___int_long_R_boolean(CN1_THREAD_GET_STATE_PASS_ARG _proxyId, (JAVA_LONG)aSelector);
    return response==1?TRUE:FALSE;
}

-(JAVA_INT)proxyId
{
    return _proxyId;
}
@end
