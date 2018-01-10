
#import <Foundation/Foundation.h>


@interface CN1ObjcProxy : NSProxy {
    JAVA_INT _proxyId;

}

-(CN1ObjcProxy*)init:(JAVA_INT)proxyId;
-(NSMethodSignature*)methodSignatureForSelector:(SEL)sel;
-(void)forwardInvocation:(NSInvocation *)invocation;
-(BOOL)respondsToSelector:(SEL)aSelector;
-(JAVA_INT)proxyId;



@end
