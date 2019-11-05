package com.poplar.protocol;

/**
 * Created By poplar on 2019/11/5
 */
public class CustomProtocol {

    private int length;

    private byte[] content;

    public CustomProtocol() {
    }

    public CustomProtocol(int length, byte[] content) {
        this.length = length;
        this.content = content;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
