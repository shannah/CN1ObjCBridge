/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.ui;

import com.codename1.objc.Pointer;

/**
 *
 * @author shannah
 */
public class ComCodename1ObjcAccessor {
    public static PeerComponent createPeerComponent(Pointer uiViewPointer) {
        return Display.getInstance().getImplementation().createNativePeer(new long[]{uiViewPointer.address});
    }
}
