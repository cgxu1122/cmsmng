package com.ifhz.core.po;

import java.io.Serializable;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/17
 * Time: 21:19
 */
public class Example implements Serializable {
    private static final long serialVersionUID = -788427628491288412L;

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Example{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
