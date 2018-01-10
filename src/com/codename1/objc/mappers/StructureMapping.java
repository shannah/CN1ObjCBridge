/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc.mappers;

import com.codename1.objc.TypeMapping;


/**
 *
 * @author shannah
 */
public class StructureMapping implements TypeMapping {

    @Override
    public Object cToJ(Object cVar, String signature, TypeMapping root) {
        return cVar;
    }

    @Override
    public Object jToC(Object jVar, String signature, TypeMapping root) {
        return jVar;
    }
    
}
