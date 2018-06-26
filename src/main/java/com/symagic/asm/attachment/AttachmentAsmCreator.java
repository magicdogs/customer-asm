package com.symagic.asm.attachment;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author magic
 * @date 2018/6/26 16:24
 * @version 1.0
 * Description AttachmentAsmCreator
 */
public class AttachmentAsmCreator implements Opcodes{
    
    public static void makeAttachByteCode(ClassVisitor cv, String clzName){
        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "setAttachment", "(Ljava/lang/Object;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(PUTFIELD, clzName, "attach", "Ljava/lang/Object;");
            mv.visitInsn(RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "L"+ clzName +";", null, l0, l1, 0);
            mv.visitLocalVariable("attach", "Ljava/lang/Object;", null, l0, l1, 1);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "getAttachment", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, clzName, "attach", "Ljava/lang/Object;");
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
                "com/symagic/asm/attachment/AttachmentAccess");
        return nJoin.split(",");
    }

    public static void makeFieldByteCode(ClassVisitor cv) {
        cv.visitField(ACC_PUBLIC,"attach","Ljava/lang/Object;",null,null);
    }

}
