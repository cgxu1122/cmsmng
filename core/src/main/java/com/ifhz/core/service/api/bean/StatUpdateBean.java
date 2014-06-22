package com.ifhz.core.service.api.bean;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/22
 * Time: 17:51
 */
public class StatUpdateBean {
    private boolean isUpdateTotalNum;
    private boolean isUpdateValidNum;
    private boolean isUpdateInvalidNum;
    private boolean isUpdateReplaceNum;
    private boolean isUpdateUninstallNum;

    private String md5Key;

    public StatUpdateBean(boolean isUpdateTotalNum, String md5Key) {
        this.isUpdateTotalNum = isUpdateTotalNum;
        this.md5Key = md5Key;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public boolean isUpdateTotalNum() {
        return isUpdateTotalNum;
    }

    public boolean isUpdateValidNum() {
        return isUpdateValidNum;
    }

    public void setUpdateValidNum(boolean isUpdateValidNum) {
        this.isUpdateValidNum = isUpdateValidNum;
    }

    public boolean isUpdateInvalidNum() {
        return isUpdateInvalidNum;
    }

    public void setUpdateInvalidNum(boolean isUpdateInvalidNum) {
        this.isUpdateInvalidNum = isUpdateInvalidNum;
    }

    public boolean isUpdateReplaceNum() {
        return isUpdateReplaceNum;
    }

    public void setUpdateReplaceNum(boolean isUpdateReplaceNum) {
        this.isUpdateReplaceNum = isUpdateReplaceNum;
    }

    public boolean isUpdateUninstallNum() {
        return isUpdateUninstallNum;
    }

    public void setUpdateUninstallNum(boolean isUpdateUninstallNum) {
        this.isUpdateUninstallNum = isUpdateUninstallNum;
    }


}
