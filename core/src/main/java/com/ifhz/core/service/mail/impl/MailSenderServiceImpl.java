package com.ifhz.core.service.mail.impl;

import com.google.common.collect.Maps;
import com.ifhz.core.service.mail.MailSenderService;
import com.ifhz.core.service.mail.enums.MailType;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 类描述
 *
 * @author chenggang.xu@qunar.com created at 13-10-28 下午3:16
 * @version 1.0.0
 */
@Service("mailSenderService")
public class MailSenderServiceImpl implements MailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderServiceImpl.class);

    @Resource(name = "commonsMailEngine")
    private CommonsMailEngine commonsMailEngine;

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    public void setCommonsMailEngine(CommonsMailEngine commonsMailEngine) {
        this.commonsMailEngine = commonsMailEngine;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }


    @Override
    public void asyncSendMail(final MailType mailType, final Map<String, Object> params) throws Exception {
        LOGGER.info("asyncSendMail param mailType:{},params:{}", mailType, params);
        taskExecutor.execute(new SendMailTask(mailType, params));
    }


    @Override
    public void sendMail(MailType mailType, Map<String, Object> params) throws Exception {
        LOGGER.info("sendMail param mailType:{},params:{}", mailType, params);
        if (MapUtils.isNotEmpty(params)) {
            commonsMailEngine.sendMail(mailType, params);
        } else {
            commonsMailEngine.sendMail(mailType);
        }
    }

    @Override
    public void sendMail(MailType mailType, List<String> tos, String subject, Map<String, Object> params) throws Exception {
        LOGGER.info("sendMail param mailType:{},tos:{},subject:{},params:{}", mailType, tos, subject, params);
        commonsMailEngine.sendMail(mailType, tos, subject, params);
    }

    @Override
    public void asyncSendMail(final MailType mailType, final List<String> tos, final String subject, final Map<String, Object> params) throws Exception {
        LOGGER.info("asyncSendMail param mailType:{},tos:{},subject:{},params:{}", mailType, tos, subject, params);
        taskExecutor.execute(new SendMailWithToUserTask(mailType, subject, tos, params));
    }


    private class SendMailTask implements Runnable {

        private MailType mailType;
        private Map<String, Object> params;

        private SendMailTask(MailType mailType, Map<String, Object> params) {
            this.mailType = mailType;
            this.params = params;
        }

        @Override

        public void run() {
            try {
                sendMail(mailType, params);
            } catch (Exception e) {
                LOGGER.error("asyncSendMail task error", e);
            }
        }
    }

    private class SendMailWithToUserTask implements Runnable {

        private MailType mailType;
        private String subject;
        private List<String> toList;
        private Map<String, Object> params;

        private SendMailWithToUserTask(final MailType mailType, final String subject, final List<String> toList, final Map<String, Object> params) {
            this.mailType = mailType;
            this.subject = subject;
            this.toList = toList;
            this.params = Maps.newHashMap(params);
        }

        @Override

        public void run() {
            try {
                commonsMailEngine.sendMail(mailType, toList, subject, params);
            } catch (Exception e) {
                LOGGER.error("asyncSendMail task error", e);
            }
        }
    }

}
