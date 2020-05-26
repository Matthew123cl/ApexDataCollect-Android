package com.chinapex.analytics.aop.collect

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class AopMethodVisitor extends AdviceAdapter {

    public AopMethodVisitor(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc)
    }
}