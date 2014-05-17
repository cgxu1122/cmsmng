/*
 * *
 *  * Copyright(c) 2013-2013 by Puhuifinance Inc.
 *  * All Rights Reserved
 *
 */
package service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.caikee.mgmt.work.UserInviteTask;

/**
 * @author radish
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class UserInviteTaskTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private UserInviteTask userInviteTask;

    @Test
    public void testRuns() {
    	userInviteTask.run(10);
    }
}