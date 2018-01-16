/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc;

import com.codename1.io.Util;
import com.codename1.objc.Runtime.Block;
import com.codename1.ui.Display;
import com.codename1.ui.PeerComponent;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Rectangle2D;
import com.codename1.util.StringUtil;
import java.util.Arrays;


/**
 *
 * @author shannah
 */
public class Objc {
    
    /**
     * Interface used to build a UIView.  The primary purpose of this is as a lambda argument
     * to {@link #createPeerComponent(com.codename1.objc.Objc.UIViewBuilder) }
     */
    public static interface UIViewBuilder {
        public Pointer buildView();
    }
    
    /**
     * A wrapper for return values from {@link #eval(java.lang.String, java.lang.Object...) }
     */
    public static class ObjcResult implements Peerable {
        Object result;
        
        private ObjcResult(Object result) {
            this.result = result;
        }
        
        public boolean isPointer() {
            return result instanceof Pointer || result instanceof Peerable;
        }
        
        public boolean isString() {
            return result instanceof String;
        }
        
        /**
         * If result was a pointer, this returns the pointer.
         * @return 
         */
        public Pointer asPointer() {
            if (result instanceof Pointer) {
                return (Pointer)result;
            } else if (result instanceof Proxy) {
                return ((Proxy)result).getPeer();
            } else if (result instanceof Number) {
                return new Pointer((long)result);
            } else {
                throw new RuntimeException("Result cannot be returned as pointer");
            }
        }
        
        /**
         * If result was an int, this returns the int.
         * @return 
         */
        public int asInt() {
            Object res = result;
            if (res == null) {
                return 0;
            } else if (res instanceof Proxy) {
                return ((Proxy)res).getPeer().getInt();
            } else if (res instanceof Pointer) {
                return ((Pointer)res).getInt();
            } else if (res instanceof Number) {
                return ((Number)res).intValue();
            } else if (res instanceof String) {
                return Integer.parseInt((String)res);
            } else {
                throw new RuntimeException("Result cannot be returned as int");
            }
        }
        
        /**
         * If result was an int, this returns the int.
         * @return 
         */
        public long asLong() {
            Object res = result;
            if (res == null) {
                return 0;
            } else if (res instanceof Proxy) {
                return ((Proxy)res).getPeer().address;
            } else if (res instanceof Pointer) {
                return ((Pointer)res).address;
            } else if (res instanceof Number) {
                return ((Number)res).longValue();
            } else if (res instanceof String) {
                return Long.parseLong((String)res);
            } else {
                throw new RuntimeException("Result cannot be returned as int");
            }
        }
        
        /**
         * If result was a boolean or int, this returns a boolean.
         * @return 
         */
        public boolean asBoolean() {
            return asInt() != 0;
        }
        
        /**
         * If result was an NSString, this returns the string.
         * @return 
         */
        public String asString() {
            return String.valueOf(result);
        }
        
        /**
         * If result was a double, this returns the double.
         * @return 
         */
        public double asDouble() {
            if (result == null) {
                return 0;
            }
            return (double)result;
        }
        
        /**
         * If result was a float, this returns the float.
         * 
         * @return 
         */
        public float asFloat() {
            return (float)asDouble();
        }
        
        /**
         * If result was an NSRect/CGRect wrapped in an NSValue*, this will
         * convert it to a Rectangle and return it.
         * @return 
         */
        public Rectangle2D asRectangle2D() {
            Rectangle2D rect = new Rectangle2D();
            Runtime.Struct boundsStruct = new Runtime.Struct(asPointer(), 8, 8, 8, 8);
            rect.setBounds(
                    boundsStruct.getDouble(0), 
                    boundsStruct.getDouble(1),
                    boundsStruct.getDouble(2),
                    boundsStruct.getDouble(3)
            );
            return rect;
        }
        
        /**
         * If result was an NSPoint/CGPoint wrapped in an NSValue*, this will convert
         * it to a Point and return it.
         * @return 
         */
        public Point2D asPoint2D() {
            //Point2D out = new Point2D();
            Runtime.Struct boundsStruct = new Runtime.Struct(asPointer(), 8, 8);
            return new Point2D(
                    boundsStruct.getDouble(0), 
                    boundsStruct.getDouble(1)
            );
            
        }
        
        /**
         * Evaluates an expression in the Objective-c runtime, and returns the result.  This is just a 
         * convenience wrapper arount {@link Objc#eval(java.lang.String, java.lang.Object...) }.
         * @param script The script to evaluate.
         * @param args Arguments passed to the script.
         * @return The return value.
         */
        public ObjcResult eval(String script, Object... args) {
            return Objc.eval(script, args);
        }
        
        /**
         * Evaluates an expression in the objective-c runtime.  This is just a convenience wrapper around
         * {@link Objc#eval(com.codename1.objc.Pointer, java.lang.String, java.lang.Object...) }
         * @param target The target object on which the first message in the chain is passed.
         * @param script The script to evaluate.
         * @param args Arguments to pass to the script.
         * @return The return value.
         */
        public ObjcResult eval(Pointer target, String script, Object... args) {
            return Objc.eval(target, script, args);
        }
        
        /**
         * Sends a message to the pointer that is wrapped by this result.
         * @param script The message name.
         * @param args Arguments to pass to the message.
         * @return The result.
         */
        public ObjcResult msgSend(String script, Object... args) {
            return eval(asPointer(), script, args);
        }

        @Override
        public Pointer getPeer() {
            return asPointer();
        }

        @Override
        public void setPeer(Pointer peer) {
            result = peer;
        }
        
    }
    
    private static int countArgsForSelector(String selector) {
        int count = 0;
        int len = selector.length();
        for (int i=0; i<len; i++) {
            if (selector.charAt(i) == ':') {
                count++;
            }
        }
        return count;
        
    }
    
    
    
    /**
    * Evaluates an expression in the Objective-c runtime, and returns the result.
    * @param script The script to evaluate.
    * @param args Arguments passed to the script.
    * @return The return value.
    */
    public static ObjcResult eval(String script, Object... args) {
        return eval(null, script, args);
    }
    
    /**
     * An exception thrown in {@link #eval(java.lang.String, java.lang.Object...) } if there is a 
     * problem.
     */
    public static class ObjectiveCException extends RuntimeException {
        String expression;
        Object[] args;
        Pointer target;
        //Throwable cause;

        public ObjectiveCException(Pointer target, String expression, Object[] args, Throwable cause) {
            super(cause);
            this.expression = expression;
            this.target = target;
            this.args = args;
                    
        }

        @Override
        public String getMessage() {
            
            String out = "Exception while processing Objective-C expression ["+expression+"] on target "+target+" and args "+Arrays.toString(args);
            if (getCause() != null) {
                out += "\nCaused by "+getCause().getMessage();
            }
            return out;
        }

        @Override
        public String toString() {
            return getMessage();
        }
        
    }
    
    /**
     * Evaluates an expression in the Objective-c runtime and returns the result.
     * @param target The initial target object to send first message in chain to.
     * @param script The expression
     * @param args Args to pass to the expression.
     * @return The return value.
     */
    public static ObjcResult eval(Pointer target, String script, Object... args) {
        try {
            //Log.p("[eval] "+script);
            script = StringUtil.replaceAll(script, " ", "");
            String[] chain = Util.split(script, ".");
            //Log.p("[eval] Commands: "+Arrays.toString(chain));
            Client c = Client.getInstance();
            int len = chain.length;
            if (len == 0) {
                throw new IllegalArgumentException("Empty script passed to eval.  "+script);
            }
            int argIndex = 0;
            for (int i=0; i<len; i++) {
                String command = chain[i];
                if (target == null) {
                    target = Runtime.getInstance().cls(command);
                    if (target == null || target.address == 0) {
                        throw new Runtime.ObjCClassNotFound(command);
                    }
                } else {
                    int numArgs = countArgsForSelector(command);
                    Object[] cmdArgs = new Object[numArgs];
                    System.arraycopy(args, argIndex, cmdArgs, 0, numArgs);
                    if (i == len-1) {
                        //Log.p("[eval] Running command "+command+" with args "+Arrays.toString(cmdArgs)+" with target "+target);
                        return new ObjcResult(c.send(target, command, cmdArgs));
                    } else {
                        //Log.p("[eval] Getting pointer from command "+command+" with args "+Arrays.toString(cmdArgs)+" with target "+target);
                        target = c.sendPointer(target, command, cmdArgs);
                    }

                }
            }
        } catch (Throwable t) {
            throw new ObjectiveCException(target, script, args, t);
        }
        throw new RuntimeException("Script did not terminate");
    }
    
    /**
     * Gets a property of an objective-c object.  Note that this can only be used
     * on primitive or pointer properties.  For struct properties (e.g. CGPoint, CGRect), 
     * you must used {@link #getBoxedProperty(com.codename1.objc.Pointer, java.lang.String) }.
     * @param target The object.
     * @param property The property name to retrieve.
     * @return The property value.
     */
    public static ObjcResult getProperty(Pointer target, String property) {
        return eval(target, property);
    }
    
    /**
     * Gets a property of an objective-c object.  This is the same as {@link #getProperty(com.codename1.objc.Pointer, java.lang.String) }
     * effectively, except that it supports struct properties, which are returned as NSValue pointers.
     * @param target The object.
     * @param property THe property name to retrieve.
     * @return 
     */
    public static ObjcResult getBoxedProperty(Pointer target, String property) {
        return eval(target, "valueForKey:", property);
    }
    
    
    /**
     * Sets a property on an objective-c object.  Note that this can only be used
     * on primitive or pointer properties.  For struct properties (e.g. CGPoint, CGRect), 
     * you must used {@link #setBoxedProperty(com.codename1.objc.Pointer, java.lang.String, java.lang.Object) ) }.
     * @param target The object
     * @param property The property name
     * @param value The value to set.
     */
    public static void setProperty(Pointer target, String property, Object value) {
        String msg = "set" + property.substring(0, 1).toUpperCase() + property.substring(1)+":";
        eval(target, msg, value);
    }
    
    /**
     * Sets a property on an objective-c object. This is the same as {@link #setProperty(com.codename1.objc.Pointer, java.lang.String, java.lang.Object)  }
     * effectively, except that it supports struct properties which have been wrapped in NSValue pointers.
     * @param target The object
     * @param property The property name
     * @param value The property value.
     */
    public static void setBoxedProperty(Pointer target, String property, Object value) {
        eval(target, "setValue:forKey:", value, property);
    }
    
    /**
     * Creates an Objective-C class that can be used to receive a no-arg callback
     * from objective-c.  This is handy in cases where you need to pass a callback
     * in the form of a target object and a selector.  The returned callback method
     * includes a {@link CallbackMethod#getObjectPointer() } and {@link CallbackMethod#getSelector() }
     * which can be passed in those cases.
     * <p>In cases where the callback needs to be a block, you should use {@link #makeBlock(java.lang.Runnable) }
     * instead.</p>
     * @param r A runnable that will be run when the callback is called.
     * @return A callback method.
     * 
     * @see #makeBlock(java.lang.Runnable) 
     */
    public static CallbackMethod makeCallback(Runnable r) {
        return new CallbackMethod(r);
    }

    /**
     * Creates a block that can be passed to methods as a callback.  
     * @param r The runnable to be executed inside the block
     * @return A block.
     */
    public static Block makeBlock(Runnable r) {
        return Runtime.getInstance().createBlock(r);
    }
    
    public static Block makeBlock(Method m) {
        return Runtime.getInstance().createBlock(m);
    }
    
    /**
     * A convenience wrapper for creating an objective-C delegate.  Objects
     * of this type can be passed to {@link 
     */
    public static class DelegateObject implements Peerable {
        private final NSObject cls;
        
        public DelegateObject(Method... methods) {
            cls = new NSObject("NSObject");
            for (Method m : methods) {
                if (m.getSelectorName() == null) {
                    throw new IllegalArgumentException("Method must have a selector name");
                }
                cls.addMethod(m.getSelectorName(), m);
            }
            
            
        }
        
        public Method getMethodForSelector(String selector) {
            return cls.methodForSelector(selector);
        }
        
        public Pointer getObjectPointer() {
            return cls.peer;
        }
        
        public NSObject getObject() {
            return cls;
        }
        
        public DelegateObject add(String selector, Method m) {
            cls.addMethod(selector, m);
            return this;
        }
        
        public DelegateObject add(Method m) {
            cls.addMethod(m.getSelectorName(), m);
            return this;
        }

        @Override
        public Pointer getPeer() {
            return getObjectPointer();
        }

        @Override
        public void setPeer(Pointer peer) {
            cls.setPeer(peer);
        }
        
    }
    
    public static DelegateObject makeDelegate(Method... methods) {
        return new DelegateObject(methods);
    }
    
    /**
     * A convenience NSObject subclass that can be used to pass a
     * callback to an objective-c message.  This is handy for cases
     * where you need to pass a callback as a target object and a selector.  In cases
     * where the callback is expected to be a block, you should use {@link Block} instead.
     * 
     * @see #makeCallback(java.lang.Runnable) 
     * @see #makeBlock(java.lang.Runnable) 
     * @see Block
     */
    public static class CallbackMethod implements Peerable {
        private final NSObject cls;
        private final String selectorName;
        
        public CallbackMethod(Runnable r) {
            cls = new NSObject("NSObject");
            cls.addMethod("invoke", new Method("v@:") {

                @Override
                public Object invoke(Object... args) {
                    r.run();
                    return null;
                }
                
            });
            selectorName = "invoke";
            
        }
        
        public CallbackMethod(Method m) {
            cls = new NSObject("NSObject");
            if (cls == null || cls.getPeer() == null || cls.getPeer().address == 0) {
                throw new RuntimeException("Failed to create peer object in CallbackMethod");
            }
            String selector = "invoke";
            int numArgs = m.getNumberOfArguments();
            if (numArgs > 0) {
                selector += "WithArg:";
            }
            for (int i=1; i<numArgs; i++) {
                selector += "arg"+i+":";
            }
            
            
            cls.addMethod(selector, m);
            selectorName = selector;
        }
        
        
        /**
         * Gets the name of the selector for this callback.
         * @return The selector name.
         */
        public String getSelectorName() {
            return selectorName;
        }
        
        /**
         * Gets the selector for this callback. 
         * @return The selector.
         */
        public Pointer getSelector() {
            return sel(selectorName);
        }
        
        /**
         * Gets the NSObject for this callback.
         * @return 
         */
        public NSObject getObject() {
            return cls;
        }
        
        /**
         * Gets the object pointer.
         * @return 
         */
        public Pointer getObjectPointer() {
            return cls.getPeer();
        }

        @Override
        public Pointer getPeer() {
            return getObjectPointer();
        }

        @Override
        public void setPeer(Pointer peer) {
            cls.setPeer(peer);
        }
        
        
        
    }
    
    /**
     * Returns the selector for the given selector name.
     * @param selector
     * @return 
     */
    public static Pointer sel(String selector) {
        return Runtime.getInstance().sel(selector);
    }
    
    /**
     * Runs runnable on the main thread asynchronously.
     * @param r 
     */
    public static void dispatch_async(Runnable r) {
        Runtime.getInstance().dispatch_async(r);
    }
    
    /**
     * Runs the runnable on the main thread synchronously.
     * @param r 
     */
    public static void dispatch_sync(Runnable r) {
        Runtime.getInstance().dispatch_sync(r);
    }
    
    /**
     * Creates a PeerComponent using the given builder.  The builder's {@link UIViewBuilder#buildView() }
     * method should be implemented to return a Pointer to a UIView.  It will be executed synchronously 
     * on the main thread.  
     * <p>This method should only be run on the EDT.</p>
     * @param builder
     * @return 
     */
    public static PeerComponent createPeerComponent(UIViewBuilder builder) {
        if (!Display.getInstance().isEdt()) {
            throw new RuntimeException("createPeerComponent() must be run on the EDT");
        }
        Pointer[] p = new Pointer[1];
        dispatch_sync(()->{
            p[0] = builder.buildView();
            Runtime.getInstance().retain(p[0]);
        });
        PeerComponent out = Runtime.getInstance().createPeerComponent(p[0]);
        Runtime.getInstance().release(p[0]);
        return out;
    }
    
    
    public static void presentViewController(Pointer controller, boolean animated, Runnable onCompletion) {
        Objc.eval(
                "CodenameOne_GLViewController.instance.presentViewController:animated:completion:",
                controller,
                animated,
                onCompletion
        );
        
    }
    
    public static void dismissViewController(Pointer controller, boolean animated, Runnable onCompletion) {
        Objc.eval(controller, "dismissViewControllerAnimated:completion:", animated, onCompletion);
    }
    
    /**
     * Checks if objective-c is supported on this platform.
     * @return 
     */
    public static boolean isSupported() {
        return Runtime.getInstance().isSupported();
    }
    
    /**
     * Checks if pointer is null.
     * @param pointer
     * @return 
     */
    public static boolean isNull(Pointer pointer) {
        return Pointer.isNull(pointer);
    }
    
    /**
     * Gets pointer to Objective-C class - or null.
     * @param className The name of the class.
     * @return 
     */
    public static Pointer getClass(String className) {
        return Runtime.getInstance().cls(className);
    }
    
    /**
     * Checks if a class exists in Objective-C runtime.
     * @param className
     * @return 
     */
    public static boolean classExists(String className) {
        return !isNull(getClass(className));
    }
}


