/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2013-2016 the original author or authors.
 */
package com.symagic;

import com.symagic.asm.attachment.AttachmentAccess;
import com.symagic.asm.visitor.CustomerClassVisitor;
import com.symagic.service.GreetingService;
import com.symagic.service.GreetingServiceEnglishImpl;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;
import org.springframework.cglib.core.ReflectUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 * @author Cedrick Lunven (@clunven)
 */
public class Application implements org.objectweb.asm.Opcodes{
    
    public static void main(String[] args) throws Exception{

        Type type = Type.getType(GreetingServiceEnglishImpl.class);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor cv = new CustomerClassVisitor(ASM5,cw,"sayHello",1);
        ClassReader cr = new ClassReader(type.getClassName());
        cr.accept(cv,ClassReader.EXPAND_FRAMES);
        File f = new File(".\\test.class");
        FileOutputStream stream = new FileOutputStream(f);
        stream.write(cw.toByteArray());
        stream.flush();
        stream.close();

        /*ClassReader reader = new ClassReader(cw.toByteArray());
        ClassVisitor tracer = new TraceClassVisitor(new PrintWriter(System.out));
        ClassVisitor checker = new CheckClassAdapter(tracer, true);
        reader.accept(checker, 0);*/

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Class clz = ReflectUtils.defineClass(type.getClassName() + "1",cw.toByteArray(),cl);
        GreetingService greetingService = (GreetingService) clz.newInstance();
        AttachmentAccess access = (AttachmentAccess) greetingService;
        String result = greetingService.sayHello("my");
        System.out.println(result);
        result = greetingService.sayHello("my","address",18,new Object[]{"aaa","bbb","ccccc"});
        System.out.println(result);
        System.out.println(greetingService.sayHello(16,new Object[]{"asd","12515"}));
        System.out.println(access.getAttachment());
    }

}

