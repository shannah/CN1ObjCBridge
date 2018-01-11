/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc.mappers;


import com.codename1.objc.*;
import com.codename1.objc.Runtime;

/**
 *
 * @author shannah
 */
public class NSObjectMapping implements TypeMapping{
    
    
    

    @Override
    public Object cToJ(Object cVar, String signature, TypeMapping root) {
        //System.out.println("Mapping NSObject to Java "+cVar+" sig: "+signature);
        Pointer cObj = new Pointer(0);
        if ( cVar instanceof Pointer ){
            cObj = (Pointer)cVar;
        } else if (cVar instanceof Long ){
            cObj = new Pointer((Long)cVar);
        } else {
            return cVar;
        }
        if ( (cObj.address == 0) || (cVar == null) || (cObj == null)){
            //System.out.println("The java value will be null");
            return null;
        }
        String clsName = Runtime.getInstance().object_getClassName(cObj);
        boolean isString = false;
        if ( "NSString".equals(clsName) || "__NSCFString".equals(clsName)){
            isString = true;
        }
        
        
        ////System.out.println("Checking if object is a string "+isString+", "+clsName);
        if ( isString  ){
            return Runtime.getInstance().str(cObj);
        }
        Object peer = Runtime.getInstance().getJavaPeer(cObj);
        if ( peer == null ){
            return Proxy.load((Pointer)cObj);
        } else {
            return peer;
        }
        
        
        
    }

    @Override
    public Object jToC(Object jVar, String signature, TypeMapping root) {
        if ( jVar == null ){
            return new Pointer(0);
        }
        if ( jVar instanceof String){
            //////System.out.println("Converting string ["+jVar+"] to string");
            return Runtime.getInstance().str((String)jVar);
        }
        if ( jVar instanceof Peerable){
            return ((Peerable)jVar).getPeer();
        } else {
            return (Pointer)jVar;
        }
    }
    
}
