
package com.l2a.api.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.l2a.api.init.test.InitTests;

@RunWith(Suite.class)
@SuiteClasses({
        InitTests.class,
        ApiIntentHelpersTest.class,
        AssertTests.class,
})
public class AllTests {

}
