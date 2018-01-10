/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc.mappers;


import com.codename1.objc.Pointer;
import com.codename1.objc.TypeMapping;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author shannah
 */
public class PointerMapping implements TypeMapping {
    
    Map<String,TypeMapping> mappers = new HashMap<String, TypeMapping>();
    
    public PointerMapping(){
        
        
        
    }

    @Override
    public Object cToJ(Object cVar, String signature, TypeMapping root) {
        if ( cVar instanceof Pointer) return cVar;
        return new Pointer((Long)cVar);
    }

    @Override
    public Object jToC(Object jVar, String signature, TypeMapping root) {
        // After some difficult deliberation I've decided that it is 
        // better to require a Pointer or long to be passed in places
        // where Objective-C expects a pointer.
        return jVar;
    }
    
}
