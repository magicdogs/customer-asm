package com.symagic.asm.attachment;

import com.symagic.asm.interceptor.TypeConstant;
import org.objectweb.asm.*;

import static com.symagic.asm.interceptor.TypeConstant.ATTACHMENT_ACCESS;
import static com.symagic.asm.interceptor.TypeConstant.ATTACHMENT_ACCESS_GET_ATTACHMENT_METHOD;
import static com.symagic.asm.interceptor.TypeConstant.ATTACHMENT_ACCESS_SET_ATTACHMENT_METHOD;

/**
 * @author magic
 * @date 2018/6/26 16:24
 * @version 1.0
 * Description AttachmentAsmCreator
 */
public class AttachmentAsmCreator implements Opcodes{
    
    public static void makeAttachByteCode(ClassVisitor cv, String clzName){

        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC,
                    ATTACHMENT_ACCESS_SET_ATTACHMENT_METHOD.getMethodName(),
                    ATTACHMENT_ACCESS_SET_ATTACHMENT_METHOD.getDescriptor(), null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(PUTFIELD, clzName, "attach", TypeConstant.OBJECT.getDescriptor());
            mv.visitInsn(RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "L"+ clzName +";", null, l0, l1, 0);
            mv.visitLocalVariable("attach", TypeConstant.OBJECT.getDescriptor(), null, l0, l1, 1);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC,
                    ATTACHMENT_ACCESS_GET_ATTACHMENT_METHOD.getMethodName(),
                    ATTACHMENT_ACCESS_GET_ATTACHMENT_METHOD.getDescriptor(), null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, clzName, "attach", TypeConstant.OBJECT.getDescriptor());
            mv.visitInsn(ARETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "L"+ clzName +";", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
    }

    public static String[] makeInterfaces(String[] interfaces) {
        String nJoin = String.join(",",
                String.join(",",interfaces),
                ATTACHMENT_ACCESS.getInternalName());
        return nJoin.split(",");
    }

    public static void makeFieldByteCode(ClassVisitor cv) {
        cv.visitField(ACC_PUBLIC,"attach",TypeConstant.OBJECT.getDescriptor(),null,null);
    }

}
