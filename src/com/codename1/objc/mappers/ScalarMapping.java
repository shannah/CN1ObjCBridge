/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc.mappers;

import com.codename1.objc.Objc;
import com.codename1.objc.TypeMapping;



/**
 *
 * @author shannah
 */
public class ScalarMapping implements TypeMapping {

    @Override
    public Object cToJ(Object cVar, String signature, TypeMapping root) {
        //System.out.println("C to J for signature "+signature);
        char firstChar = signature.charAt(0);
        if ( cVar instanceof Long){
            long cObj = (Long)cVar;
            switch (firstChar){
                case 'i':
                case 'I':
                case 's':
                case 'S':
                    return new Long(cObj).intValue();
                case 'c':
                    return new Long(cObj).byteValue();
                case 'B':
                    return cObj > 0L ? true:false;
            }
        }
        
        return cVar;
    }

    @Override
    public Object jToC(Object jVar, String signature, TypeMapping root) {
        if (jVar instanceof Objc.ObjcResult) {
            Objc.ObjcResult res = (Objc.ObjcResult)jVar;
            char firstChar = signature.charAt(0);
            
            switch (firstChar){
                case 'i':
                case 'I':
                case 's':
                case 'S':
                case 'l':
                case 'c':
                case 'B':
                    return res.asInt();
                case 'q':
                case 'Q':
                    return res.asLong();
                case 'f':
                    return res.asFloat();
                case 'd':
                    return res.asFloat();
                
                    
            }
            
        }
        return jVar;
    }
    
}
