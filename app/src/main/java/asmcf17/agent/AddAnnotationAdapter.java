package asmcf17.agent;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class AddAnnotationAdapter extends ClassVisitor {
    private final String annotationDesc;
    private boolean isAnnotationPresent;

    public AddAnnotationAdapter(ClassVisitor cv, String annotationDesc) {
        super(ASM9, cv);
        this.annotationDesc = annotationDesc;
    }

    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        int v = (version & 0xFF) < V1_8 ? V1_8 : version;
        cv.visit(v, access, name, signature, superName, interfaces);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc,
                                             boolean visible) {
        if (visible && desc.equals(annotationDesc)) {
            isAnnotationPresent = true;
        }
        return cv.visitAnnotation(desc, visible);
    }

    @Override
    public void visitInnerClass(String name, String outerName,
                                String innerName, int access) {
        addAnnotation();
        cv.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc,
                                   String signature, Object value) {
        addAnnotation();
        return cv.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name,
                                     String desc, String signature, String[] exceptions) {
        addAnnotation();
        return cv.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        addAnnotation();
        cv.visitEnd();
    }

    private void addAnnotation() {
        if (!isAnnotationPresent) {
            System.out.println("NOT Present");
            AnnotationVisitor av = cv.visitAnnotation(annotationDesc, true);
            if (av != null) {
                System.out.println("Annotation visitor...");
                av.visitEnd();
            }
            isAnnotationPresent = true;
        }
    }
}