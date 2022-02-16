/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package asmcf17.app;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class App {
//    public static void main(String[] args) {
//        System.out.println("MAIN");
//        Simple simple = new Simple();
//        simple.setField("value");
////        CompletableFuture<Simple> simpleCf = new CompletableFuture();
//        Arrays.stream(CompletableFuture.class.getDeclaredFields()).forEach((field -> System.out.println(field.getName())));
//    }

    //private static Class klass = Simple.class;

    public static void main(String... args) throws Throwable {
        List<String> list = new ArrayList();
        Class<?> klass = Class.forName(args[0]);
        //MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandles.Lookup classLookup = privateLookupIn(Class.class);
        MethodHandle getGenericSignature0 = classLookup.findVirtual(Class.class, "getGenericSignature0", MethodType.methodType(String.class));
        Object genericSignature = getGenericSignature0.invoke(CompletableFuture.class);

        System.out.println();
        System.out.println("getGenericSignature0: " + genericSignature);
        System.out.println("expected: <T:Ljava/lang/Object;>Ljava/lang/Object;Ljava/util/concurrent/Future<TT;>;Ljava/util/concurrent/CompletionStage<TT;>;");
        System.out.println();

        Type type = App.class.getDeclaredMethod("testing").getGenericReturnType();
        System.out.println("TYPE: " + type);

        System.out.println("--- Annotations ---");
        Arrays.stream(klass.getDeclaredAnnotations()).forEach((it) -> System.out.println(it.toString()));

        System.out.println("--- Fields ---");
        Arrays.stream(klass.getDeclaredFields()).forEach((it) -> System.out.println(it.getName()) );
    }

    private static MethodHandles.Lookup privateLookupIn(Class clazz) throws IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        try {
            // Java 9+ has privateLookupIn method on MethodHandles, but since we are shipping and using Java 8
            // we need to access it via reflection. This is preferred way because it's Java 9+ public api and is
            // likely to not change
            final Method privateLookupIn = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);


            return (MethodHandles.Lookup) privateLookupIn.invoke(null, clazz, MethodHandles.lookup());
        } catch (NoSuchMethodException e) {
            // In Java 8 we first do standard lookupIn class
            final MethodHandles.Lookup lookupIn = MethodHandles.lookup().in(clazz);

            // and then we mark it as trusted for private lookup via reflection on private field
            final Field modes = MethodHandles.Lookup.class.getDeclaredField("allowedModes");
            modes.setAccessible(true);
            modes.setInt(lookupIn, -1); // -1 == TRUSTED
            return lookupIn;
        }
    }

    public CompletableFuture<List<String>> testing() {
        return null;
    }
}

