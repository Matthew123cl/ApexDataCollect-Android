package com.chinapex.analytics.aop.util

class AopTextUtils {
    static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0
    }

    static String path2ClassName(String pathName) {
        pathName.replace(File.separator, ".").replace(".class", "")
    }

    static String changeClassNameSeparator(String classname) {
        return classname.replace('.', '/')
    }

}