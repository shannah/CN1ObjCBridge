/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc;


import com.codename1.io.Log;
import com.codename1.objc.Pointer.ByReference;
import com.codename1.objc.Pointer.DoubleByReference;
import com.codename1.objc.Pointer.LongByReference;
import com.codename1.objc.Pointer.PointerByReference;

import java.util.HashMap;
import java.util.Map;

/**
 * The base class for objects that can interact with the Objective-C runtime.
 * NSObjects are connected to both an Objective-C peer object, and an Objective-C
 * parent object.  The peer is a reflection of the object in Objective-C.  It is
 * a WLProxy object that will simply forward messages from Objective-C to Java.
 * 
 * <p>The parent object is used as a sort of superclass so that messages that aren't
 * explicitly handled by the Java class can be handled by the superclass.</p>
 * 
 * <h3>Simple Example</h3>
 * 
 * <p>The following example shows a subclass of NSObject that is used as a delegate
 * for an NSOpenPanel.  Notice, that, by using the {@literal @}Msg annotation, the
 * start() method is effectively called via Objective-C.  Similarly, the panelSelectionDidChange()
 * method is called by the NSOpenPanel class to respond to events when the user clicks on 
 * a different item in the open dialog.</p>
 * <script src="https://gist.github.com/3966989.js?file=NSOpenPanelSample.java"></script>
 * 
 * <p>If you run this application, it will open an NSOpenPanel modal dialog and allow you to 
 * select a file.  If you run this program and select a single file, the output will look
 * something like:</p>
 * 
 * <script src="https://gist.github.com/3966989.js?file=output.txt"></script>
 * 
 * 
 * @see NSOpenPanelSample
 * 
 * @author shannah
 */
public class NSObject extends Proxy implements PeerableRecipient {
    
    
    
    /**
     * Pointer to the parent objective-c object of this object.
     */
    public Pointer parent;
    
    /**
     * Pointer to the objective-c class of the parent object.
     */
    private Pointer cls;
    
    /**
     * Maps string selectors to java methods for this class.
     */
    private Map<String,Method> methodMap = new HashMap<String,Method>();
    
    
     
    public NSObject(String className){
        this();
        init(className);
    } 
    
    public void addMethod(String selector, Method method) {
        methodMap.put(selector, method);
        method.setSelectorName(selector);
    }
    
    /**
     * Creates null proxy (i.e. a proxy around a null pointer).  In order
     * to make this class functional and register it with the objective-c
     * runtime, you still need to call one of the init() method variants.
     */
    public NSObject(){
        super();

    }
    
    /**
     * Creates an NSObject to wrap (i.e. send messages to) the specified
     * Objective-C object.  This doesn't actually register an object yet
     * with the Objective-C runtime.  You must still call init() to do this.
     * 
     * @param peer 
     */
    public NSObject(Pointer peer){
        super(peer);
    }
    
    /**
     * Creates a null proxy using the specified client as the default client
     * with which to send messages to the objective-c runtime.
     * @param c The client that should be used to send messages in this
     * object.
     */
    public NSObject(Client c){
        super(c);
    }
    
    /**
     * Creates a proxy for the specified objective-c object.
     * @param c The client that should be used for sending messages via this proxy.
     * 
     * @param peer The peer object.
     */
    public NSObject(Client c, Pointer peer){
        super(c, peer);
    }
    
    
    /**
     * Initializes this object and registers it with the Objective-C runtime.
     * @param parent A pointer to a parent object that is used as a sort of 
     *  super class.  I.e. messages that this object doesn't handle will be
     * passed to this parent object transparently in the background.  It
     * acts 100% as a superclass would.
     * @return Self for chaining.
     */
    public NSObject init(Pointer parent){
        this.cls = Runtime.getInstance().object_getClass(parent);
        this.parent = parent;
        
        if ( this.peer.address == 0 ){
            this.peer = Runtime.getInstance().createProxy(this);
        }
        
        return this;
    }
    
    
    /**
     * Initializes this object and registers it with the Objective-C runtime.
     * @param parent A pointer to a parent object that is used as a sort of 
     *  super class.  I.e. messages that this object doesn't handle will be
     * passed to this parent object transparently in the background.  It
     * acts 100% as a superclass would.
     * @param cls The name of the class to use as the super class for this object.
     * @return Self for chaining.
     */
    public NSObject init(String cls){
        Pointer res = Client.getRawClient().sendPointer(cls, "alloc");
        Client.getRawClient().sendPointer(res, "init");
        return init(res);
        
    }
    

    /**
     * Returns the java method that responds to a specific selector for the
     * current object.
     * @param selector The 
     * @return The method object that handles the specified selector (or null
     * if none is specified).
     * 
     * @see RuntimeUtils.sel()
     */
    public Method methodForSelector(String selector){
        return methodMap.get(selector);
        
    }

    
    private static Pointer sel(String selector) {
        return Runtime.getInstance().sel(selector);
    }
    
    
    
    /**
     * Returns the NSMethodSignature (Objective-C) object pointer for the 
     * specified selector.  If there is a Java method registered with this
     * selector, then it will return its signature.  Otherwise it will
     * return the method signature of the parent object.
     * @param selector
     * @return Pointer to an NSMethodSignature object.
     * 
     * @see <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Reference/Foundation/Classes/NSMethodSignature_Class/Reference/Reference.html">NSMethodSignature Class Reference</a>
     */
    @Override
    public Pointer methodSignatureForSelector(Pointer selector) {
        Method method = methodForSelector(Runtime.getInstance().selName(selector));
        if ( method != null){
            return method.getSignature();
            
        }
        return new Pointer(0);

    }
    
    
    
    /**
     * Forwards an NSInvocation to the parent object to be handled.  The parent will
     * handle the invocation (if it contains an appropriate selector), but the peer
     * will still be treated as the "Self" of the message.  I.e. this acts exactly
     * like calling super() in an OO language.
     * @param invocation Pointer to the objective-c NSInvocation object.
     * @see <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Reference/Foundation/Classes/NSInvocation_Class/Reference/Reference.html">NSInvocation Class Reference</a>
     */
    public void forwardInvocationToParent(Pointer invocation){
        Runtime.getInstance().msg(invocation, "invokeWithTarget:", parent);
        
    }
   
    
    /**
     * Handles a method invocation.  This will first check to see if there is a matching
     * Java method in this class (method requires the @Msg annotation), and call that 
     * method if it is available.  Otherwise it will obtain the method implementation from
     * the parent class and execute it.  The return value is added to the NSInvocation object.
     * 
     * This method is used by the Native WLProxy to pipe all messages to this object's peer
     * through Java so that it has a chance to process it.
     * @param invocation NSInvocation Objective-C object that is to be invoked.
     * 
     * @see <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Reference/Foundation/Classes/NSInvocation_Class/Reference/Reference.html">NSInvocation Class Reference</a>
     * @see <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Reference/Foundation/Classes/NSProxy_Class/Reference/Reference.html">NSProxy forwardInvocation Documentation</a>
     */
    @Override
    public void forwardInvocation(Pointer invocation) {
        Client rawClient = Client.getRawClient();
        
        
        Pointer sig = Runtime.getInstance().msgPointer(invocation, "methodSignature");
        Proxy pSig = new Proxy(rawClient, sig);
        Pointer selector = Runtime.getInstance().msgPointer(invocation, "selector");
        long numArgs = pSig.sendInt("numberOfArguments");
        
        Method method = methodForSelector(selName(selector));
        if ( method != null){
            Object[] args = new Object[new Long(numArgs).intValue()-2];
            ByReference[] refArgs = new ByReference[args.length];
            for ( int i=2; i<numArgs; i++){

                Pointer argumentSigAddr = pSig.sendPointer("getArgumentTypeAtIndex:", i);
                String argumentSignature = argumentSigAddr.getString(0);

                if ( "fd".indexOf(argumentSignature.substring(0,1)) != -1 ){
                    DoubleByReference ptrRef = new DoubleByReference();

                    Runtime.getInstance().msg(invocation, "getArgument:atIndex:", ptrRef.getPointer(), i);
                    refArgs[i-2] = ptrRef;
                    args[i-2] = TypeMapper
                                .getInstance()
                                .cToJ(
                                    ptrRef.getValue(),
                                    //argPtr.toNative(),
                                    argumentSignature, 
                                    TypeMapper.getInstance()
                            );
                } else {
                    LongByReference ptrRef = new LongByReference();

                    Runtime.getInstance().msg(invocation, "getArgument:atIndex:", ptrRef.getPointer(), i);

                    refArgs[i-2] = ptrRef;
                    args[i-2] = TypeMapper
                                .getInstance()
                                .cToJ(
                                    ptrRef.getValue(),
                                    //argPtr.toNative(),
                                    argumentSignature, 
                                    TypeMapper.getInstance()
                            );
                }



            }

            try {
                //method.setAccessible(true);
                Object res = method.invoke(args);

                // We should release the arguments now since we retained them before
                // to prevent memory leaks.
                for ( int i=0; i<args.length; i++){
                    if (refArgs[i] != null) {
                        Runtime.getInstance().free(refArgs[i].getPointer());
                    }
                }

                Pointer returnType = pSig.sendPointer("methodReturnType");

                String strReturnType = returnType.getString(0);


                res = TypeMapper
                        .getInstance()
                        .jToC(res, strReturnType, TypeMapper.getInstance());

                if ( !"v".equals(strReturnType)){

                    Object retVal = res == null ? new PointerByReference().getPointer() : Runtime.getInstance().getAsReference(res, strReturnType);
                    Runtime.getInstance().msg(invocation, "setReturnValue:",  retVal);
                }

                return;
            } catch (Exception ex){
                Log.e(ex);
                throw new RuntimeException(ex);
            }

            
        }
        // If we send using invokeWithTarget, then we will use the method of the parent
        // and set "self" to the parent for the method call.  We want the parent's 
        // method, but we want to use ourself as the "self".
        

        this.forwardInvocationToParent(invocation);
    }

    private static String selName(Pointer selector) {
        return Runtime.getInstance().selName(selector);
    }
   
    /**
     * Checks whether this object responds to the given selector.  This is used 
     * by the WLProxy (Objective-C peer object) to route requests for its NSProxy
     * respondsToSelector: message.  This will check to see if there is a registered
     * java method in the class that responds to the selector (based on the @Msg 
     * annotation).  Then it will check the parent object to see if it responds
     * to the selector.
     * @param selector Pointer to the selector to check.
     * 
     * @return True if either the java class or the parent Objective-c object 
     * responds to the specified selector.
     * 
     * @see RuntimeUtils.sel()
     * @see <a href="http://developer.apple.com/library/ios/#documentation/cocoa/conceptual/objectivec/Chapters/ocSelectors.html">Objective-C selectors reference</a>
     */
    @Override
    public boolean respondsToSelector(Pointer selector) {
        Method method = methodForSelector(selName(selector));
        if ( method != null){
            
            return true;
            
        }
        return (Runtime.getInstance().msgInt(parent, "respondsToSelector:", selector ) > 0);
        
    }
    

    /**
     * @deprecated
     * @return 
     */
    public NSObject dealloc(){
        
        this.send("dealloc");
        
        
        return this;
        
    }
    
    

    
}
