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

import com.caikee.client.manager.LoanManager;
import com.caikee.client.manager.PointAccountManager;
import com.caikee.client.manager.UserAccountManager;
import com.caikee.client.service.LoanService;
import com.caikee.core.dao.UserMgmtDao;

/**
 * @author radish
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class LoanServiceImplTest extends AbstractJUnit4SpringContextTests {



    @Autowired
    private PointAccountManager pointService;
    @Autowired
    private LoanManager loanManger;
    @Autowired
    private LoanService loanService;
    @Autowired
    private UserAccountManager accountManager;

    @Autowired
    private UserMgmtDao UserMgmtDao;

    @Test
    public void bid() {
        System.out.print(loanService.bid(470, 623, 500000));// 1用户 2标 3.买多少钱
    }

    // @Test
    // public void test1() {
    // List<String> headImages = UserMgmtDao.getHeadImageByUserId(265l);
    //
    // System.out.println(headImages.get(0));
    // }

    // @Test
    // public void passLoan() {
    //
    // }
    //
    // @Test
    // public void repay() throws Exception {
    // loanService.repay(2, 1);
    // }
    //
    // @Test
    // public void buyLoan() {
    // loanService.bid(104L, 182, 100);
    // }

}