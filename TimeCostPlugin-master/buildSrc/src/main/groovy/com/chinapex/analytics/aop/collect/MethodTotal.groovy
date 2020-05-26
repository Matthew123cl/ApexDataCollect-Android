package com.chinapex.analytics.aop.collect

import com.chinapex.analytics.aop.util.AopLog
import com.chinapex.analytics.aop.util.FilterUtils;
import org.objectweb.asm.*;

/**
 * Created by zjy on 2019-05-03
 */
 class MethodTotal extends ClassVisitor {
    public boolean seeModifyMethod = false;
    private boolean isMatchClass = false;
    private String mClassName;
    private String[] mInterfaces;
    private String mSuperName;
     MethodTotal(ClassVisitor classVisitor) {
           super(Opcodes.ASM5, classVisitor)

    }

    @Override
     void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        isMatchClass = FilterUtils.isMatchClass(name, superName, interfaces);
        mClassName = name;
        mInterfaces = interfaces;
        mSuperName = superName;
        super.visit(version, access, name, signature, superName, interfaces);
    }

     @Override
     void visitInnerClass(String name, String outerName, String innerName, int access) {
         super.visitInnerClass(name, outerName, innerName, access)
     }
    @Override
     MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
//    visitMethod(修饰符 , 方法名 , 方法签名 , 泛型信息 , 抛出的异常)
        MethodVisitor adapter = null
        AopLog.info("||-----------------开始修改方法前${mClassName}#${name}${desc}--------------------------")

        if ((isMatchClass && FilterUtils.isMatchMethod(name, desc))) {
            //指定方法名，根据满足的类条件和方法名
            AopLog.info("||-----------------开始修改方法${mClassName}#${name}${desc}--------------------------")
            try {
                adapter = FilterUtils.getMethodVisitor(mInterfaces, mClassName, mSuperName, methodVisitor, access, name, desc)
            } catch (Exception e) {
                e.printStackTrace()
                adapter = null
            }
        }
        if (adapter != null) {
            return adapter
        }
        return methodVisitor
    }
     @Override
     void visitEnd() {
         super.visitEnd()
     }


//     methodVisitor = new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, desc) {
//            boolean inject;
//
//            @Override
//             AnnotationVisitor visitAnnotation(String s, boolean b) {
//                //自定义的注解用来判断方法上的注解与TimeTotal是否为同一个注解，是否需要统计耗时
//                if (Type.getDescriptor(TimeTotal.class).equals(s)) {
//                    inject = true;
//                }
//                System.out.println("this is test name:"+name+"/desc/"+desc+"/s/"+s);
//
//                return super.visitAnnotation(s, b);
//            }
//
//            @Override
//             void visitCode() {
//                super.visitCode();
//                if (inject) {
//                    mv.visitVarInsn(ALOAD, 1);
//                    mv.visitMethodInsn(INVOKESTATIC, "com/example/asmdemo/TimeManager", "abcd", "()V", false);
//
//                }
//
//
//
//            }

//            @Override
//            protected void onMethodEnter() {
//                //方法进入时期
//                if (inject) {
//                    //这里就是之前使用ASM插件生成的统计时间代码
//                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                    mv.visitLdcInsn("this is asm input");
//                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//
//                    mv.visitTypeInsn(NEW, "java/lang/Throwable");
//                    mv.visitInsn(DUP);
//                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Throwable", "<init>", "()V", false);
//                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Throwable", "getStackTrace", "()[Ljava/lang/StackTraceElement;", false);
//                    mv.visitInsn(ICONST_1);
//                    mv.visitInsn(AALOAD);
//                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StackTraceElement", "getMethodName", "()Ljava/lang/String;", false);
//                    mv.visitVarInsn(ASTORE, 1);
//
//                    mv.visitVarInsn(ALOAD, 1);
//                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
//                    mv.visitMethodInsn(INVOKESTATIC, "com/example/asmdemo/TimeManager", "addStartTime", "(Ljava/lang/String;J)V", false);
//
//
//
////                    mv.visitVarInsn(ALOAD, 1);
////                    mv.visitMethodInsn(INVOKESTATIC, "com/example/asmdemo/TimeManager", "testbbtc", "(Ljava/lang/String;)V", false);
//                }
//            }
//
//            @Override
//            protected void onMethodExit(int i) {
//                System.out.println("this is test:----------------------");
//
//                //方法结束时期
//                if (inject) {
//                    //计算方法耗时
//                    mv.visitVarInsn(ALOAD, 1);
//                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
//                    mv.visitMethodInsn(INVOKESTATIC, "com/example/asmdemo/TimeManager", "addEndTime", "(Ljava/lang/String;J)V", false);
//
//                    mv.visitVarInsn(ALOAD, 1);
//                    mv.visitMethodInsn(INVOKESTATIC, "com/example/asmdemo/TimeManager", "calcuteTime", "(Ljava/lang/String;)V", false);
//
//
//                    mv.visitVarInsn(ALOAD, 1);
//                    mv.visitMethodInsn(INVOKESTATIC, "com/example/asmdemo/TimeManager", "testbbtc", "(Ljava/lang/String;)V", false);
//                }
//            }
//        };
//        return methodVisitor;
//    }
}
