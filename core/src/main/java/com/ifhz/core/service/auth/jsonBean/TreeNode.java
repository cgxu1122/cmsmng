package com.ifhz.core.service.auth.jsonBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 23:16
 */
public class TreeNode implements Serializable {
    private static final long serialVersionUID = 6639105047829034375L;
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "text")
    private String text;
    @JSONField(name = "open")
    private String open;
    @JSONField(name = "checked")
    private String checked;
    @JSONField(name = "item")
    private List<TreeNode> treeNodeList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public List<TreeNode> getTreeNodeList() {
        return treeNodeList;
    }

    public void setTreeNodeList(List<TreeNode> treeNodeList) {
        this.treeNodeList = treeNodeList;
    }

    public static void main(String[] args) throws Exception {
        TreeNode root = new TreeNode();
        root.setId(0);
        TreeNode p = new TreeNode();
        root.setTreeNodeList(Lists.newArrayList(p));
        p.setId(1);
        p.setText("1-");
        p.setOpen("1");
        TreeNode n2 = gen(2);
        TreeNode n3 = gen(3);
        TreeNode n4 = gen(4);
        TreeNode n5 = gen(5);
        TreeNode n6 = gen(6);
        TreeNode n7 = gen(7);
        List<TreeNode> list = Lists.newArrayList(n2, n3, n4);
        List<TreeNode> list2 = Lists.newArrayList(n5, n6);
        List<TreeNode> list3 = Lists.newArrayList(n7);
        n3.setTreeNodeList(list2);
        n5.setTreeNodeList(list3);
        p.setTreeNodeList(list);

        System.out.println(JSON.toJSONString(root));

    }

    private static TreeNode gen(int id) {
        TreeNode node = new TreeNode();
        node.setId(id);
        node.setText(id + "-");
        node.setOpen("1");

        return node;
    }
}
