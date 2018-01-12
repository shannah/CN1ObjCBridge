#import "CN1ObjcTester.h"

@implementation CN1ObjcTester : NSObject
        
+(void)runIntBlock:(void (^)(int))block withArg:(int) arg {
    block(arg);
}
     
+(void)runFloatBlock:(void (^)(float))block withArg:(float) arg {
    block(arg);
}

+(void)runDoubleBlock:(void (^)(double))block withArg:(double) arg {
    block(arg);
}

+(void)runObjectBlock:(void (^)(id))block withArg:(id) arg {
    block(arg);
}
@end
        
