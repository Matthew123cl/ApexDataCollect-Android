package com.chinapex.analytics.aop.collect

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.chinapex.analytics.aop.util.FilterUtils
import com.chinapex.analytics.aop.util.AopLog
import com.chinapex.analytics.aop.util.AopTextUtils
import groovy.io.FileType
import jdk.internal.org.objectweb.asm.ClassReader
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.*;

public class AopTransform extends Transform{


    @Override
    String getName() {

        //task名字
//        return "asm"
        return AopTransform.simpleName

    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }
    private static File modifyClassFile(File dir, File classFile, File tempDir) {
        File modified = null
        try {
            String className = AopTextUtils.path2ClassName(classFile.absolutePath.replace(dir.absolutePath + File.separator, ""))
            byte[] sourceClassBytes = IOUtils.toByteArray(new FileInputStream(classFile))
            byte[] modifiedClassBytes = AopModify.modifyClasses(className, sourceClassBytes)
            if (modifiedClassBytes) {
                modified = new File(tempDir, className.replace('.', '') + '.class')
                if (modified.exists()) {
                    modified.delete()
                }
                modified.createNewFile()
                new FileOutputStream(modified).write(modifiedClassBytes)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return modified
    }

    private static File modifyJarFile(File jarFile, File tempDir) {
        if (jarFile) {
            return AopModify.modifyJar(jarFile, tempDir, true)
        }
        return null
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        println("SteelCabbage注册的Transform  =======start")

        // 分开对目录和Jar进行遍历
        transformInvocation.getInputs().each { TransformInput transformInput ->

            // 遍历目录
            transformInput.directoryInputs.each { DirectoryInput directoryInput ->
                File dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                AopLog.info("||-->开始遍历特定目录  ${dest.absolutePath}")
                File dir = directoryInput.file
                if (dir) {
                    HashMap<String, File> modifyMap = new HashMap<>()
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) {
                        File classFile ->
                            File modified = modifyClassFile(dir, classFile, transformInvocation.getContext().getTemporaryDir())
                            if (modified != null) {
                                //key为相对路径
                                modifyMap.put(classFile.absolutePath.replace(dir.absolutePath, ""), modified)
                            }
                    }

                    org.apache.commons.io.FileUtils.copyDirectory(directoryInput.file, dest)

                    modifyMap.entrySet().each {
                        Map.Entry<String, File> en ->
                            File target = new File(dest.absolutePath + en.getKey())
                            if (target.exists()) {
                                target.delete()
                            }
                            org.apache.commons.io.FileUtils.copyFile(en.getValue(), target)
                            en.getValue().delete()
                    }
                }
                AopLog.info("||-->结束遍历特定目录  ${dest.absolutePath}")
            }

            // 遍历Jar
            transformInput.jarInputs.each { JarInput jarInput ->
                String destName = jarInput.file.name
                /** 截取文件路径的md5值重命名输出文件,因为可能同名,会覆盖*/
                def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath).substring(0, 8)
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4)
                }
                /** 获得输出文件*/
                File dest = transformInvocation.getOutputProvider().getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                def modifiedJar = modifyJarFile(jarInput.file, transformInvocation.getContext().getTemporaryDir())
                if (modifiedJar == null) {
                    modifiedJar = jarInput.file
                }
                org.apache.commons.io.FileUtils.copyFile(modifiedJar, dest)
            }
        }

        println("SteelCabbage注册的Transform=======end")
        /**
         * Transform中的核心方法
         * inputs中是传过来的输入流，其中有两种格式，一种是jar包格式一种是目录格式。
         * outputProvider 获取到输出目录，最后将修改的文件复制到输出目录，这一步必须做不然编译会报错
         */

//        transformInvocation.inputs.each {
//            it.directoryInputs.each {
////                AopLog.info("||-----------------it.namefileName1${it.name}#-------------------------")
//                AopLog.info("directoryInput name = " + it.name +"，metaClass="+ it.metaClass.getMetaClass().toString() +"，contentTypes="+it.contentTypes.toString() +", path = " + it.file.absolutePath)
//
//                if(it.file.isDirectory()){
//                    //遍历文件夹
//                    it.file.eachFileRecurse {
//                        def fileName=it.name
//
////                        if("WebViewClient.class"){
////
////                        }
////                        if(fileName.indexOf("android")&&!fileName.startsWith("R\$")
////                                && fileName != "BuildConfig.class"&&fileName!="R.class"){
////                                def fileName2=it.name
////                            AopLog.info("||-----------------android-fileName->${it.file.isDirectory()}#${fileName2}-------------------------")
////
////                            listDir(it.file)
////
////
////                        }
////                        else
//                        if(fileName.endsWith(".class")&&!fileName.startsWith("R\$")
//                                && fileName != "BuildConfig.class"&&fileName!="R.class"){
//                            //各种过滤类，关联classVisitor
////                            AopLog.info("||-----------------fileName2${fileName}#-------------------------")
//
//                            handleFile(it)
//                        }else{
//                            if(fileName==("android")){
////                                handleFile(it)
//
//                                AopLog.info("||-----------------fileName1${fileName}#-------------------------")
//
//                            }else{
////                                AopLog.info("||-----------------fileName2${fileName}#-------------------------")
//
//                            }
//
//                        }
//                    }
//                }
//                def dest=transformInvocation.outputProvider.getContentLocation(it.name,it.contentTypes,it.scopes, Format.DIRECTORY)
//                FileUtils.copyDirectory(it.file,dest)
//            }
//            it.jarInputs.each { jarInput->
//                def jarName = jarInput.name
////                AopLog.info("||-----------------jarName--》${jarName}#-------------------------")
//
//                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
//                if (jarName.endsWith(".jar")) {
//                    jarName = jarName.substring(0, jarName.length() - 4)
//                    AopLog.info("||-----------------jarName修改成--》${jarName}#-------------------------")
//
//                }
//                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
//                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
//                FileUtils.copyFile(jarInput.file, dest)
//            }
//        }
    }
    private boolean  listDir(File dirFile) {
        dirFile.eachLine { File file ->
//            println "${file.getAbsolutePath()}"
            AopLog.info("||-----------------android-fileName->${file.name}#-------------------------")

            if(file.name!="WebViewClient.class"){
                listDir(file)
            return true;
            }
            return false;

//            if (file.isDirectory())
//                listDir(file)
        }
    }

    private void handleFile(File file){
        def cr=new ClassReader(file.bytes)
        /*** 2、执行分析与插桩*/
        //class字节码的读取与分析引擎
        // 写出器 COMPUTE_FRAMES 自动计算所有的内容，后续操作更简单
        def cw=new ClassWriter(cr,ClassWriter.COMPUTE_MAXS)

        //分析，处理结果写入cw EXPAND_FRAMES：栈图以扩展格式进行访问
        def classVisitor=new MethodTotal(cw)
        cr.accept(classVisitor,ClassReader.EXPAND_FRAMES)
        /** 3、获得结果并输出*/
        def bytes=cw.toByteArray()
        //写回原来这个类所在的路径
        FileOutputStream fos=new FileOutputStream(file.getParentFile().getAbsolutePath()+File.separator+file.name)
        fos.write(bytes)
        fos.close()
    }
}