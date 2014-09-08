package com.ifhz.core.service.imei.bean;

import com.google.common.collect.Sets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/8
 * Time: 20:09
 */
@XmlRootElement(name = "list")
@XmlAccessorType(XmlAccessType.FIELD)
public class PreInstallSet implements Serializable {
    private static final long serialVersionUID = 2513467643048403622L;

    @XmlElement(name = "PreInstall")
    private Set<PreInstall> preInstallSet;

    public Set<PreInstall> getPreInstallSet() {
        if (this.preInstallSet == null) {
            this.preInstallSet = Sets.newHashSet();
        }
        return preInstallSet;
    }

    public void setPreInstallSet(Set<PreInstall> preInstallSet) {
        this.preInstallSet = preInstallSet;
    }
}
