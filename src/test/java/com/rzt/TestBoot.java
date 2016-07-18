package com.rzt;

import com.rzt.junit.ProjectTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Entry point to start running the test Cases
 * Created by tara on 7/18/16.
 */
public class TestBoot {

    public static void main(String[] args){
        Result result = JUnitCore.runClasses(ProjectTest.class);
        for(Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }
    }
}
