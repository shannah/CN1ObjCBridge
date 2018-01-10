/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc;

/**
 * Interface to be implemented by objects that map Java types to Objective-C
 * types.  
 * @author shannah
 */
public interface TypeMapping {
    /**
     * Converts a C variable to the corresponding Java variable given the
     * context of the specified signature.
     * @param cVar The C variable to be converted.
     * @param signature The signature that tells what type of variable we are dealing with according to <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Conceptual/ObjCRuntimeGuide/Articles/ocrtTypeEncodings.html">Objective-C Type Encoding</a> conventions.
     * 
     * @param root The root TypeMapping object
     * @return The converted Java object.
     */
    public Object cToJ(Object cVar, String signature, TypeMapping root);
    
    /**
     * Converts a Java variable to the corresponding Java variable given the
     * context of the specified signature.
     * @param jVar The Java variable to be converted.
     * @param signature The signature that tells what type of variable we are dealing with according to <a href="https://developer.apple.com/library/mac/#documentation/Cocoa/Conceptual/ObjCRuntimeGuide/Articles/ocrtTypeEncodings.html">Objective-C Type Encoding</a> conventions.
     * 
     * @param root
     * @return The converted C variable
     */
    public Object jToC(Object jVar, String signature, TypeMapping root);
}
