package com.chinapex.analytics.aop.util

class AopLog {
    private static boolean IS_DEBUG = true

    static void setDebug(boolean isDebug) {
        IS_DEBUG = isDebug
    }

    static boolean isDebug() {
        return IS_DEBUG
    }

    def static info(Object msg) {
        if (IS_DEBUG) {
            try {
                println "${msg}"
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }


}