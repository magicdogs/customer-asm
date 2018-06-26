package com.symagic.asm.visitor;

import com.symagic.asm.interceptor.TypeConstant;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * @author magic
 * @date 2018/6/25 8:50
 * @version 1.0
 * Description CustomerMethodVisitor
 */
public class CustomerMethodVisitor extends AdviceAdapter {

    private AnalyzerAdapter analyzerAdapter;
    private LocalVariablesSorter localVariablesSorter;

    Label startFinally = new Label();
    private Integer m_index;
    private Integer m_args;
    private Integer m_throws;
    private Integer m_interceptor;
    private Integer m_result;

    private String methodName;
    private Type returnType;
    private Type[] argumentTypes;

    public CustomerMethodVisitor(String clzName, int access, MethodVisitor mv, String methodName, String description, Integer index) {
        super(Opcodes.ASM5, mv, access, methodName, description);
        this.m_index = index;
        this.analyzerAdapter = new AnalyzerAdapter(clzName, access, methodName, description, mv);
        this.localVariablesSorter = new LocalVariablesSorter(access, description, this.analyzerAdapter);
        this.returnType = Type.getReturnType(description);
        this.argumentTypes =  Type.getArgumentTypes(description);
        this.methodName = methodName;

    }

    @Override
    public void visitCode() {
        super.visitCode();

        m_args = newLocal(TypeConstant.OBJECT_ARRAY_ONE.getType());
        mv.visitIntInsn(BIPUSH,argumentTypes.length);
        mv.visitTypeInsn(ANEWARRAY, TypeConstant.OBJECT.getInternalName());
        mv.visitVarInsn(ASTORE, m_args);

        for(int args_index = 0; args_index < argumentTypes.length; args_index ++){
            Type type = argumentTypes[args_index];
            if(type.getSort() == Type.OBJECT || type.getSort() == Type.ARRAY){
                mv.visitVarInsn(ALOAD, m_args);
                mv.visitIntInsn(BIPUSH,args_index);
                mv.visitVarInsn(ALOAD, args_index + 1);
                mv.visitInsn(AASTORE);
            }else{
                parserArgumentType(type.getSort(),args_index,mv);
            }
        }

        m_throws = newLocal(TypeConstant.THROWABLE.getType());
        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ASTORE, m_throws);

        m_interceptor = newLocal(TypeConstant.INTERCEPTOR.getType());
        mv.visitIntInsn(BIPUSH,m_index);
        mv.visitMethodInsn(INVOKESTATIC, TypeConstant.INTEGER.getInternalName(),
                TypeConstant.INTEGER_VALUE_OF_BOXING_METHOD.getMethodName(),
                TypeConstant.INTEGER_VALUE_OF_BOXING_METHOD.getDescriptor(), false);
        mv.visitMethodInsn(INVOKESTATIC, TypeConstant.REGISTER_INTERCEPTOR.getInternalName(),
                TypeConstant.REGISTER_INTERCEPTOR_GET_INTERCEPTOR_METHOD.getMethodName(),
                TypeConstant.REGISTER_INTERCEPTOR_GET_INTERCEPTOR_METHOD.getDescriptor(), false);
        mv.visitVarInsn(ASTORE, m_interceptor);

        m_result = newLocal(TypeConstant.OBJECT.getType());
        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ASTORE, m_result);


        mv.visitVarInsn(ALOAD, m_interceptor);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitLdcInsn(methodName);
        mv.visitVarInsn(ALOAD, m_args);
        mv.visitMethodInsn(INVOKEINTERFACE, TypeConstant.INTERCEPTOR.getInternalName(),
                TypeConstant.INTERCEPTOR_BEFORE_METHOD.getMethodName(),
                TypeConstant.INTERCEPTOR_BEFORE_METHOD.getDescriptor(), true);



        mv.visitLabel(startFinally);
    }

    private void parserArgumentType(int sort, int args_index, MethodVisitor mv) {
        switch (sort){
            case Type.BOOLEAN:
                //TODO
                break;
            case Type.CHAR:
                //TODO
                break;
            case Type.BYTE:
                //TODO
                break;
            case Type.SHORT:
                //TODO
                break;
            case Type.INT:
                mv.visitVarInsn(ALOAD, m_args);
                mv.visitIntInsn(BIPUSH,args_index);
                mv.visitVarInsn(ILOAD, args_index + 1);
                mv.visitMethodInsn(INVOKESTATIC, TypeConstant.INTEGER.getInternalName(),
                        TypeConstant.INTEGER_VALUE_OF_BOXING_METHOD.getMethodName(),
                        TypeConstant.INTEGER_VALUE_OF_BOXING_METHOD.getDescriptor(), false);
                mv.visitInsn(AASTORE);
                break;
            case Type.FLOAT:
                //TODO
                break;
            case Type.LONG:
                //TODO
                break;
            case Type.DOUBLE:
                //TODO
                break;
            default:
                break;
        }
    }


    private void convertReturnValue(MethodVisitor mv, int sort) {
        switch (sort){
            case Type.BOOLEAN:
                //TODO
                break;
            case Type.CHAR:
                //TODO
                break;
            case Type.BYTE:
                //TODO
                break;
            case Type.SHORT:
                //TODO
                break;
            case Type.INT:
                mv.visitMethodInsn(INVOKESTATIC, TypeConstant.INTEGER.getInternalName(),
                        TypeConstant.INTEGER_VALUE_OF_BOXING_METHOD.getMethodName(),
                        TypeConstant.INTEGER_VALUE_OF_BOXING_METHOD.getDescriptor(), false);
                invokeInterceptorAfter(mv);
                mv.visitMethodInsn(INVOKEVIRTUAL, TypeConstant.INTEGER.getInternalName(),
                        TypeConstant.INTEGER_VALUE_OF_UNBOXING_METHOD.getMethodName(),
                        TypeConstant.INTEGER_VALUE_OF_UNBOXING_METHOD.getDescriptor(), false);
                break;
            case Type.FLOAT:
                //TODO
                break;
            case Type.LONG:
                //TODO
                break;
            case Type.DOUBLE:
                //TODO
                break;
            default:
                break;
        }
    }

    private void invokeInterceptorAfter(MethodVisitor mv) {
        mv.visitVarInsn(ASTORE, m_result);

        mv.visitVarInsn(ALOAD, m_interceptor);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitLdcInsn(this.methodName);
        mv.visitVarInsn(ALOAD, m_args);
        mv.visitVarInsn(ALOAD, m_throws);
        mv.visitVarInsn(ALOAD, m_result);
        mv.visitMethodInsn(INVOKEINTERFACE, TypeConstant.INTERCEPTOR.getInternalName(),
                TypeConstant.INTERCEPTOR_AFTER_METHOD.getMethodName(),
                TypeConstant.INTERCEPTOR_AFTER_METHOD.getDescriptor(), true);

        mv.visitVarInsn(ALOAD, m_result);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        Label endFinally = new Label();
        mv.visitTryCatchBlock(startFinally, endFinally, endFinally, null);
        mv.visitLabel(endFinally);
        onFinally(Opcodes.ATHROW);
        mv.visitInsn(Opcodes.ATHROW);
        super.visitMaxs(maxStack + 4, maxLocals);
    }

    @Override
    protected void onMethodEnter() {
        // If required, add some code when a method begin
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (opcode != ATHROW) {
            onFinally(opcode);
        }
    }


    private void onFinally(int opcode) {
        int last_index;
        if (opcode == Opcodes.ATHROW) {
            last_index = m_throws;
        } else {
            last_index = m_result;
            if(returnType.getSort() != Type.OBJECT
                    && returnType.getSort() != Type.ARRAY){
                convertReturnValue(mv,returnType.getSort());
                return;
            }
        }
        // Exception thrown or Return by the method
        mv.visitVarInsn(ASTORE, last_index);

        mv.visitVarInsn(ALOAD, m_interceptor);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitLdcInsn(this.methodName);
        mv.visitVarInsn(ALOAD, m_args);
        mv.visitVarInsn(ALOAD, m_throws);
        mv.visitVarInsn(ALOAD, m_result);
        mv.visitMethodInsn(INVOKEINTERFACE, TypeConstant.INTERCEPTOR.getInternalName(),
                TypeConstant.INTERCEPTOR_AFTER_METHOD.getMethodName(),
                TypeConstant.INTERCEPTOR_AFTER_METHOD.getDescriptor(), true);

        mv.visitVarInsn(ALOAD, last_index);

    }

}