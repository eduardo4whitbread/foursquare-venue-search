package com.eduardosantos.foursquareexercise.base;


import com.eduardosantos.foursquareexercise.BuildConfig;
import com.eduardosantos.foursquareexercise.FourSquareExerciseApplication;
import com.eduardosantos.foursquareexercise.FourSquareExerciseApplicationTest;

import org.easymock.EasyMockRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import toothpick.testing.ToothPickRule;

@Ignore
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        application = FourSquareExerciseApplicationTest.class,
        sdk = 21,
        packageName = "com.eduardosantos.foursquareexercise")
public class BaseRobolectricTest {
    ToothPickRule toothPickRule = new ToothPickRule(this, FourSquareExerciseApplication.APPLICATION_SCOPE);
    @Rule
    public TestRule testRule = RuleChain.outerRule(toothPickRule)
            .around(new EasyMockRule(this));

    protected void runAllTasks() {
        Robolectric.flushForegroundThreadScheduler();
        Robolectric.flushBackgroundThreadScheduler();
        ShadowLooper.runUiThreadTasks();
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
    }

    protected void runSingleTask() {
        ShadowLooper.runMainLooperOneTask();
    }
}
