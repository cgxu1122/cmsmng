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
        root.setId(1);
        root.setOpen("1");
        root.setText("root");
        List<TreeNode> item = Lists.newArrayList();
        List<TreeNode> item2 = Lists.newArrayList();
        TreeNode node1 = new TreeNode();
        node1.setId(8);
        node1.setText("8");
        node1.setOpen("1");
        TreeNode node2 = new TreeNode();
        node2.setId(7);
        node2.setText("7");
        node2.setOpen("1");
        TreeNode node3 = new TreeNode();
        node3.setId(9);
        node3.setText("9");
        node3.setOpen("1");

        TreeNode node4 = new TreeNode();
        node4.setId(5);
        node4.setText("5");
        node4.setOpen("1");
        TreeNode node5 = new TreeNode();
        node5.setId(6);
        node5.setText("6");
        node5.setOpen("1");
        TreeNode node6 = new TreeNode();
        node6.setId(10);
        node6.setText("10");
        node6.setOpen("1");
        node6.setChecked("1");

        item.add(node1);
        item.add(node2);
        item2.add(node4);
        item2.add(node5);
        item2.add(node6);
        node3.setTreeNodeList(item2);
        item.add(node3);

        root.setTreeNodeList(item);


        String json = JSON.toJSONString(root);
        System.out.println(json);
    }
}
