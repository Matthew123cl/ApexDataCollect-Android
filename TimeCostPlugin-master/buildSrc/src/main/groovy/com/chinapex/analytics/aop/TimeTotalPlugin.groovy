package com.chinapex.analytics.aop


import com.android.build.gradle.AppExtension
import com.chinapex.analytics.aop.collect.AopTransform
import com.chinapex.analytics.aop.collect.ApexTechnologiesAop
import org.gradle.api.Plugin
import org.gradle.api.Project

public class TimeTotalPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        System.out.println("this is test----------Project project");
        project.extensions.create('ApexTechnologiesAop', ApexTechnologiesAop)
        project.afterEvaluate {
            println("App project after evaluate")
            println("the custom extensions happy des is " + project.ApexTechnologiesAop.matchData)

        }
        //注册transform
        project.extensions.getByType(AppExtension).registerTransform(new AopTransform())
    }




}