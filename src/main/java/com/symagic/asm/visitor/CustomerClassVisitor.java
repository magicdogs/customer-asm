package com.symagic.asm.visitor;

import com.symagic.asm.attachment.AttachmentAsmCreator;
import org.objectweb.asm.*;

/**
 * @author magic
 * @date 2018/6/25 8:42
 * @version 1.0
 * Description CustomerClassVisitor
 */
public class CustomerClassVisitor extends ClassVisitor implements Opcodes {


    private String clzName;
    private String methodName;
    private Integer interceptorId;
    private boolean create = false;

    public CustomerClassVisitor(int api, ClassVisitor cv, String methodName, Integer interceptorId) {
        super(api, cv);
        this.methodName = methodName;
        this.interceptorId = interceptorId;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        String[] modifyInterfaces = AttachmentAsmCreator.makeInterfaces(interfaces);
        super.visit(version, access, name + "1", signature, superName, modifyInterfaces);
        this.clzName = name + "1";
    }

    @Override
    public void visitEnd() {
        AttachmentAsmCreator.makeAttachByteCode(cv,this.clzName);
        super.visitEnd();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        checkField();
        MethodVisitor visitor = cv.visitMethod(access, name, desc, signature, exceptions);
        if(name.equals(this.methodName)){
            CustomerMethodVisitor methodVisitor = new CustomerMethodVisitor(clzName,access,visitor,name,desc,this.interceptorId);
            return methodVisitor;
        }
        return visitor;
    }

    private void checkField() {
        if(!create){
            AttachmentAsmCreator.makeFieldByteCode(cv);
            create = !create;
        }
    }
}
