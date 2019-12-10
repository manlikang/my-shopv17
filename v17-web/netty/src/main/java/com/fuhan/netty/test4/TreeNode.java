package com.fuhan.netty.test4;

import lombok.val;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/12/6
 */
public class TreeNode {
    // 数据域
    public String val;
    // 左子树根节点
    public TreeNode left;
    // 右子树根节点
    public TreeNode right;

    public TreeNode() {

    }

    public TreeNode(String val) {
        this.val = val;
    }

}