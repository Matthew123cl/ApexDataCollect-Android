package com.chinapex.analytics.aop.collect

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class AopModify {

    static byte[] modifyClasses(String className, byte[] srcByteCode) {
        byte[] classBytesCode = null
        try {
            classBytesCode = modifyClass(srcByteCode)
            //调试模式下再遍历一遍看修改的方法情况
//            if (AopLog.isDebug()) {
//                seeModifyMethod(classBytesCode)
//            }
            return classBytesCode
        } catch (Exception e) {
            e.printStackTrace()
        }
        if (classBytesCode == null) {
            classBytesCode = srcByteCode
        }
        return classBytesCode
    }

    private static byte[] modifyClass(byte[] srcClass) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        ClassVisitor adapter = new MethodTotal(classWriter)
        ClassReader classReader = new ClassReader(srcClass)
        classReader.accept(adapter, ClassReader.EXPAND_FRAMES)
        return classWriter.toByteArray()
    }

    private static void seeModifyMethod(byte[] srcClass) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        ClassVisitor visitor = new MethodTotal(classWriter)
        visitor.seeModifyMethod = true
        ClassReader cr = new ClassReader(srcClass)
        cr.accept(visitor, ClassReader.EXPAND_FRAMES)
    }

    static File modifyJar(File jarFile, File tempDir, boolean nameHex) {
        /**
         * 读取原jar
         */
        def file = new JarFile(jarFile)
        /** 设置输出到的jar */
        def hexName = ""
        if (nameHex) {
            hexName = DigestUtils.md5Hex(jarFile.absolutePath).substring(0, 8)
        }
        def outputJar = new File(tempDir, hexName + jarFile.name)
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(outputJar))
        Enumeration enumeration = file.entries()

        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) enumeration.nextElement()
            InputStream inputStream = file.getInputStream(jarEntry)

            String entryName = jarEntry.getName()
            String className

            ZipEntry zipEntry = new ZipEntry(entryName)

            jarOutputStream.putNextEntry(zipEntry)

            byte[] modifiedClassBytes = null
            byte[] sourceClassBytes = IOUtils.toByteArray(inputStream)
            if (entryName.endsWith(".class")) {
                className = entryName.replace("/", ".").replace(".class", "")
                modifiedClassBytes = modifyClasses(className, sourceClassBytes)
            }
            if (modifiedClassBytes == null) {
                jarOutputStream.write(sourceClassBytes)
            } else {
                jarOutputStream.write(modifiedClassBytes)
            }
            jarOutputStream.closeEntry()
        }
        jarOutputStream.close()
        file.close()
        return outputJar
    }
}