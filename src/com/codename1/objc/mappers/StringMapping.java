/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc.mappers;

import com.codename1.objc.Pointer;
import com.codename1.objc.TypeMapping;



/**
 *
 * @author shannah
 */
public class StringMapping implements TypeMapping{

    @Override
    public Object cToJ(Object cVar, String signature, TypeMapping root) {
        System.out.println("Mapping string from cVar");
        return new Pointer((Long)cVar).getString(0);
    }

    @Override
    public Object jToC(Object jVar, String signature, TypeMapping root) {
        return jVar;
    }
    
}
