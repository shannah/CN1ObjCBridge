package com.codename1.demos.ObjCDemo;


import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.objc.Client;
import com.codename1.objc.Method;
import com.codename1.objc.Method.ArgType;
import com.codename1.objc.NSObject;
import com.codename1.objc.Objc;
import com.codename1.objc.Objc.CallbackMethod;
import com.codename1.objc.Objc.ObjcResult;
import static com.codename1.objc.Objc.createPeerComponent;
import static com.codename1.objc.Objc.eval;
import static com.codename1.objc.Objc.makeCallback;
import com.codename1.objc.Proxy;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.objc.Runtime;
import com.codename1.ui.PeerComponent;
import com.codename1.ui.layouts.BorderLayout;

import com.codename1.objc.Pointer;
import com.codename1.objc.Runtime.Struct;
import com.codename1.ui.Button;
import com.codename1.ui.geom.Rectangle2D;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class ObjCDemo {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);
    }
    
    public void testBlocks() {
        Form hi = new Form("Test Blocks");
        Button btn = new Button("Test Blocks");
        btn.addActionListener(e->{
            Objc.eval("CN1ObjcTester.runIntBlock:withArg:", Method.create(ArgType.Int, args->{
                Log.p("Running int block with arg "+args[0]);
            }), 27);
            
            Objc.eval("CN1ObjcTester.runFloatBlock:withArg:", Method.create(ArgType.Float, args->{
                Log.p("Running float block with arg "+Method.getArgAsDouble(args[0]));
            }), 27.5f);
            
            Objc.eval("CN1ObjcTester.runDoubleBlock:withArg:", Method.create(ArgType.Double, args->{
                Log.p("Running double block with arg "+Method.getArgAsDouble(args[0]));
            }), 27.5f);
            
            Objc.eval("CN1ObjcTester.runObjectBlock:withArg:", Method.create(ArgType.Object, args->{
                Log.p("Running Object block.  Main screen scale is "+
                        Objc.eval(Method.getArgAsPointer(args[0]), "scale").asDouble()
                );
            }), Objc.eval("UIScreen.mainScreen").asPointer());
        });
        hi.add(btn);
        hi.show();
        
    }
    
    
    public void test2() {
        if(current != null){
            current.show();
            return;
        }
        Form hi = new Form("Hi World", new BorderLayout());
        
        if (Runtime.getInstance().isSupported()) {
            
            PeerComponent cmp = createPeerComponent(()->{
                Pointer button = eval("UIButton.buttonWithType:", 0).asPointer();
                eval(button, "setTitle:forState:", "Show View", 0);
                eval(button, "setTitleColor:forState:", eval("UIColor.blueColor").asPointer(), 0);
                eval(button, "retain");
                CallbackMethod cb = makeCallback(()->{
                    Log.p("Button was clicked");
                    Display.getInstance().callSerially(()->{
                        Runtime.getInstance().dispatch_sync(Runtime.getInstance().createBlock(()->{
                            Log.p("This is running inside a block");
                        }));
                    });
                    
                    Rectangle2D bounds = Objc.getBoxedProperty(button, "bounds").asRectangle2D();
                    Log.p("Bounds is "+bounds);
                    
                    
                    
                    
                });
                eval(button, "addTarget:action:forControlEvents:", cb.getObjectPointer(), cb.getSelector(), 1<<6);
                
                return button;
            });
            
            hi.add(BorderLayout.CENTER, cmp);
             
        } else {
            hi.add(BorderLayout.CENTER, "Objective-C Runtime Not supported");
        }
        hi.show();
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        if (true) {
            testBlocks();
            return;
        }
        if (true) {
            test2();
            return;
        }
        Form hi = new Form("Hi World", new BorderLayout());
        
        if (Runtime.getInstance().isSupported()) {
            /**
             * UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
               [button addTarget:self 
                          action:@selector(aMethod:)
                forControlEvents:UIControlEventTouchUpInside];
               [button setTitle:@"Show View" forState:UIControlStateNormal];
               button.frame = CGRectMake(80.0, 210.0, 160.0, 40.0);
               [view addSubview:button];
            * */
            
            
            
            Proxy[] buttons = new Proxy[1];
            Runtime.getInstance().dispatch_sync(()->{
                
                
                Proxy button = Client.getInstance().sendProxy("UIButton", "buttonWithType:", 0);
                NSObject callback = new NSObject("NSObject");
                callback.addMethod("aMethod", new Method("v@:") {

                    @Override
                    public Object invoke(Object... args) {
                        Log.p("Button was clicked");
                        return null;
                    }

                });
                button.send("addTarget:action:forControlEvents:", 
                        callback.getPeer(), 
                        Runtime.getInstance().sel("aMethod"), 
                        1<<6
                );
                button.send("setTitle:forState:", "Show View", 0);
                button.send("setTitleColor:forState:", Client.getInstance().sendPointer("UIColor", "blueColor"),0);
                button.send("retain");
                buttons[0] = button;
            });
            
            
            PeerComponent cmp = Runtime.getInstance().createPeerComponent(buttons[0].getPeer());
            hi.add(BorderLayout.CENTER, cmp);
             
        } else {
            hi.add(BorderLayout.CENTER, "Objective-C Runtime Not supported");
        }
        hi.show();
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }


}
