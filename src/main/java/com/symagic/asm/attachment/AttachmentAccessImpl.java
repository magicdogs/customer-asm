package com.symagic.asm.attachment;

/**
 * @author magic
 * @date 2018/6/26 15:45
 * @version 1.0
 * Description AttachmentAccessImpl
 */
public class AttachmentAccessImpl implements AttachmentAccess {

    private Object attach;

    @Override
    public void setAttachment(Object attach) {
        this.attach = attach;
    }

    @Override
    public Object getAttachment() {
        return this.attach;
    }
}
