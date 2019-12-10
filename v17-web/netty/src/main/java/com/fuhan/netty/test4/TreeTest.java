package com.fuhan.netty.test4;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/12/6
 */
public class TreeTest {
    public static void main(String[] args) {
        TreeNode treeNode0 = new TreeNode("A");
        TreeNode treeNode1 = new TreeNode("B");
        TreeNode treeNode2 = new TreeNode("C");
        TreeNode treeNode3 = new TreeNode("D");
        TreeNode treeNode4 = new TreeNode("E");
        TreeNode treeNode5 = new TreeNode("F");
        TreeNode treeNode6 = new TreeNode("G");
        TreeNode treeNode7 = new TreeNode("H");
        TreeNode treeNode8 = new TreeNode("I");
        TreeNode treeNode9 = new TreeNode("J");
        treeNode0.left=treeNode1;
        treeNode0.right=treeNode2;

        treeNode1.left=treeNode3;

        treeNode3.left=treeNode4;

        treeNode2.left=treeNode5;
        treeNode2.right=treeNode6;

        treeNode5.left=treeNode7;

        treeNode6.left=treeNode8;
        treeNode6.right=treeNode9;

        //middleOrder(treeNode0);

        backOrder(treeNode0);


    }


    public static void preOrder(TreeNode node){
        if(node != null){
            System.out.println(node.val);
        }
        if(node.left != null){
            preOrder(node.left);
        }
        if(node.right != null){
            preOrder(node.right);
        }

    }

    public static void middleOrder(TreeNode node){
        if(node.left != null){
            middleOrder(node.left);
        }
        if(node != null){
            System.out.println(node.val);
        }
        if(node.right != null){
            middleOrder(node.right);
        }

    }

    public static void backOrder(TreeNode node){
        if(node.left != null){
            backOrder(node.left);
        }
        if(node.right != null){
            backOrder(node.right);
        }
        if(node != null){
            System.out.println(node.val);
        }

    }


}
