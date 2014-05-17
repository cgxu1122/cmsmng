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

import com.caikee.client.service.FinancePlanService;
import com.caikee.core.enumerate.FinancePlanGainsProcessEnum;

/**
 * @author radish
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class FinancePlanServiceImplTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private FinancePlanService financePlanService;

    @Test
    public void buyFinancePlan() {
        try {
            financePlanService.buyPlan(470l, 86, 3000, FinancePlanGainsProcessEnum.COMPOUNDDING);// 用户id,planId,金额,处理方式
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Test
    // public void getPlan4Show() {
    // try {
    // Result result = financePlanService.getPlan4Show();
    // System.out.println(result.toString());
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    // @Test
    // public void financePlanBuyLoan() {
    // long loanId = 529l;
    //
    // try {
    // financePlanService.planBuyLoan(loanId);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
}