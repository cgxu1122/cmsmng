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

import com.caikee.client.manager.TemplateManger;
import com.caikee.core.dataObject.SmsMessageDto;
import com.caikee.core.util.SmsUtils;

/**
 * @author radish
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class TemplateImplTest extends AbstractJUnit4SpringContextTests {
//    @Autowired
//    private PointAccountManager pointService;
//    @Autowired
//    private LoanManager loanManger;
//    @Autowired
//    private LoanService loanService;
//    @Autowired
//    private UserAccountManager accountManager;

    
    @Autowired
    private TemplateManger templateManger;
    
//  @Test
//    public void renderString() {
//        Map<String, String> model = new HashMap<>();
//        model.put("verify", "123456");
//        String result = templateManger.getContentOfTemplateByType(TemplateApplicationEnum.BINDING_MOBILE_TEMPLATE.getType(), model);
//        System.out.println(result);
////        assertEquals("hello calvin", result);
//    }
    
    @Test
    public void emailRegTest() {
        String  result = templateManger.getFinContractContent(8l,21l);
        System.out.println(result);
        System.out.println("##########################");
    }
    
    @Test
    public void dateTest(){
        SmsMessageDto smsMessageDto = new SmsMessageDto();
        smsMessageDto.setTemplateIdType(1);
        smsMessageDto.setUserName("test");
        smsMessageDto.setContext("1234");
        smsMessageDto.setMobile("13051636935");
        SmsUtils.addSmsMessage(smsMessageDto);
    }
    

}