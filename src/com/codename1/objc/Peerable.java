/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc;
/**
 * An interface for an object that has an Objective-C peer.
 * @author shannah
 */
public interface Peerable {
    public Pointer getPeer();
    public void setPeer(Pointer peer);
}