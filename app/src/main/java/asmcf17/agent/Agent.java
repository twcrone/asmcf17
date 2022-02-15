package asmcf17.agent;

import com.google.common.io.ByteStreams;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

public class Agent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("PREMAIN");
        instrumentation.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            //if (className.equals("java/util/concurrent/CompletableFuture")) {
            if(className.equals("asmcf17/app/Simple")) {
                System.out.println(className);
//                ClassPrinter printer = new ClassPrinter();
//                ClassReader reader = null;
//                try {
//                    reader = new ClassReader("asmcf17.app.Simple");
//                    //reader = new ClassReader("java.util.concurrent.CompletableFuture");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                reader.accept(printer, 0);

                //InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("java/util/concurrent/CompletableFuture.class");
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("asmcf17/app/Simple.class");
                if(is == null) {
                    return classfileBuffer;
                }
                byte[] b1 = new byte[0];
                try {
                    b1 = ByteStreams.toByteArray(is);
                    System.out.println(b1.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ClassWriter cw = new ClassWriter(0);
                //ClassVisitor addFieldAdapter = new AddFieldAdapter(cw, ACC_PUBLIC, "aNewBooleanField", "");
                //ClassVisitor addAnnotationAdapter = new AddAnnotationAdapter(cw, "Lasmcf17/agent/InstrumentedClass;");
                ClassVisitor addAnnotationAdapter = new AddAnnotationAdapter(cw, "Lasmcf17/agent/InstrumentedClass;");
                ClassReader cr = new ClassReader(b1);
                cr.accept(addAnnotationAdapter, 0);
                byte[] b2 = cw.toByteArray();
                System.out.println(b2.length);
                return b2;
            }
            return classfileBuffer;
        });
    }


}
