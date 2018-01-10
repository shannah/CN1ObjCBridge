/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc;

import java.io.IOException;

/**
 *
 * @author shannah
 */
public class AutoreleasePool implements AutoCloseable {
    final Pointer peer;
    
    public AutoreleasePool() {
        peer = Runtime.getInstance().createAutoreleasePool();
    } 
    
    @Override
    public void close() {
        
        Runtime.getInstance().release(peer);
    }
    
}
