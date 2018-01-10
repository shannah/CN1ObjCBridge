/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.objc;

/**
 *
 * @author shannah
 */
public class Pointer {
    public final long address;
    public Pointer(long address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return new Long(address).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pointer) {
            return address == ((Pointer)obj).address;
        }
        return false;
    }
    
    
    
    
    public String getString(int offset) {
        return Runtime.getInstance().utf8StringToString(new Pointer(address+offset));
    }
    
    public int getInt() {
        return (int)address;
    }
    
    public int getValueAsInt() {
        return Runtime.getInstance().getPointerValueInt(this);
    }
    
    public float getValueAsFloat() {
        return Runtime.getInstance().getPointerValueFloat(this);
    }
    
    public double getValueAsDouble() {
        return Runtime.getInstance().getPointerValueDouble(this);
    }
    
    public abstract static class ByReference<T> {
        Pointer ptr;
        public abstract void setValue(T value);
        public abstract T getValue();
        public Pointer getPointer() {
            return ptr;
        }
    }
    
    public static class ByteByReference extends ByReference<Byte> {
        public ByteByReference() {
            ptr = Runtime.getInstance().malloc((byte)0);
        }
        public ByteByReference(byte value) {
            this();
            setValue(value);
        }
        public ByteByReference(Pointer ptr) {
            this.ptr = ptr;
        }

        @Override
        public void setValue(Byte value) {
            Runtime.getInstance().setPointerValue(ptr, value);
        }

        @Override
        public Byte getValue() {
            return Runtime.getInstance().getPointerValueByte(ptr);
        }
    }
    
    public static class IntByReference extends ByReference<Integer> {
        public IntByReference() {
            ptr = Runtime.getInstance().malloc((int)0);
        }
        
        public IntByReference(Pointer ptr) {
            this.ptr = ptr;
        }
        
        public IntByReference(int val) {
            this();
            setValue(val);
        }

        @Override
        public void setValue(Integer value) {
            Runtime.getInstance().setPointerValue(ptr, value);
        }

        @Override
        public Integer getValue() {
            return Runtime.getInstance().getPointerValueInt(ptr);
        }
    }
    public static class ShortByReference extends ByReference<Short> {
        public ShortByReference() {
            ptr = Runtime.getInstance().malloc((short)0);
        }
        
        public ShortByReference(Pointer ptr) {
            this.ptr = ptr;
        }
        
        public ShortByReference(short val) {
            this();
            setValue(val);
        }

        @Override
        public void setValue(Short value) {
            Runtime.getInstance().setPointerValue(ptr, value);
        }

        @Override
        public Short getValue() {
            return Runtime.getInstance().getPointerValueShort(ptr);
        }
    }
    
    public static class DoubleByReference extends ByReference<Double> {
        public DoubleByReference() {
            ptr = Runtime.getInstance().malloc((double)0);
        }
        
        
        public DoubleByReference(Pointer ptr) {
            this.ptr = ptr;
        }
        public DoubleByReference(double val) {
            this();
            setValue(val);
        }

        @Override
        public void setValue(Double value) {
            Runtime.getInstance().setPointerValue(ptr, value);
        }

        @Override
        public Double getValue() {
            return Runtime.getInstance().getPointerValueDouble(ptr);
        }
    }
    
    public static class FloatByReference extends ByReference<Float> {
        public FloatByReference() {
            ptr = Runtime.getInstance().malloc((float)0);
        }
        
        public FloatByReference(Pointer ptr) {
            this.ptr = ptr;
        }
        
        public FloatByReference(float val) {
            this();
            setValue(val);
        }

        @Override
        public void setValue(Float value) {
            Runtime.getInstance().setPointerValue(ptr, value);
        }

        @Override
        public Float getValue() {
            return Runtime.getInstance().getPointerValueFloat(ptr);
        }
    }
    
    public static class PointerByReference extends ByReference<Pointer> {
        public PointerByReference() {
            ptr = Runtime.getInstance().malloc((long)0);
        }
        public PointerByReference(Pointer value) {
            this();
            setValue(value);
        }

        
        
        @Override
        public void setValue(Pointer value) {
            Runtime.getInstance().setPointerValue(ptr, value);
        }

        @Override
        public Pointer getValue() {
            return Runtime.getInstance().getPointerValuePointer(ptr);
        }
    }
    
    public static class LongByReference extends ByReference<Long> {
        public LongByReference() {
            ptr = Runtime.getInstance().malloc((long)0);
        }
        public LongByReference(Long value) {
            this();
            setValue(value);
        }

        
        public LongByReference(Pointer ptr) {
            this.ptr = ptr;
        }
        
        @Override
        public void setValue(Long value) {
            Runtime.getInstance().setPointerValue(ptr, new Pointer(value));
        }

        @Override
        public Long getValue() {
            return Runtime.getInstance().getPointerValuePointer(ptr).address;
        }
    }
}
