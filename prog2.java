//Aleksandra Trifonova
//Computer Science 282
//Monday Wednesday 2:00 pm - 3:15pm
//Assignment #2
//10/ 08/2018
//This is an AVL tree program, that creates an AVL tree and performs the necessary
//rotates and updates of node balance in order to have a correct AVL tree.


import java.util.Objects;

class StringAVLNode {
    private String item;
    private int balance;
    private StringAVLNode left, right;

    //just one constructor please
    public StringAVLNode(String str) {
        item = str;
        setBalance(0);
        setLeft(null);
        setRight(null);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int bal) {
        balance = bal;
    }

    public String getItem() {
        return item;
    }

    //no setItem
    public StringAVLNode getLeft() {
        return left;
    }

    public void setLeft(StringAVLNode pt) {
        this.left = pt;
    }

    public StringAVLNode getRight() {
        return right;
    }

    public void setRight(StringAVLNode pt) {
        this.right = pt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringAVLNode that = (StringAVLNode) o;
        return balance == that.balance && Objects.equals(item, that.item) && Objects.equals(left, that.left) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {

        return Objects.hash(item, balance, left, right);
    }
} // StringAVLNode


class StringAVLTree {
    //should really be private but I need access for my test program to work

    StringAVLNode root;

    //Just one constructor

    public StringAVLTree() {
        StringAVLTree root = null;
    }

    /**
     * @param t is the node that we are performing the rotate on.
     * @return
     */
    // Rotate node to the right
    public static StringAVLNode rotateRight(StringAVLNode t) {
        StringAVLNode reference = t.getLeft();
        StringAVLNode temp = reference.getRight();
        reference.setRight(t);
        t.setLeft(temp);

        return reference;
    }

    public static StringAVLNode rotateLeft(StringAVLNode t) {
        StringAVLNode reference = t.getRight();
        StringAVLNode temporary = reference.getLeft();
        reference.setLeft(t);
        t.setRight(temporary);

        return reference;
    }

    // Who are you? Put your name here!
    public static String myName() {
        return "Aleksandra Trifonova";
    }

    // For these next four be sure not to use any global variables
    //Return the height of the tree - not to be used anywhere in insert or delete
    public int height() {
        return height(root);
    }

    public int height(StringAVLNode node) {
        int count = 0;
        int leftchildcount = 0;
        int rightchildcount = 0;

        if (node != null) {
            if (node.getLeft() != null) {
                leftchildcount = height(node.getLeft());
            }
            if (node.getRight() != null) {
                rightchildcount = height(node.getRight());
            }

            if (rightchildcount > leftchildcount) {
                count = rightchildcount;
            } else {
                count = leftchildcount;
            }
            count++;
        }
        return count;
    }

    // Return the number of leaves in the tree
    public int leafCt() {
        return leafCt(root);
    }

    public int leafCt(StringAVLNode node) {
        int count = 0;
        int leafleft = 0;
        int leafright = 0;

        if (node != null) {
            if (node.getLeft() != null) {
                leafleft = leafCt(node.getLeft());
            }
            if (node.getRight() != null) {
                leafright = leafCt(node.getRight());
            }
            count = leafleft + leafright;

            if (node.getLeft() == null && node.getRight() == null) {
                count = 1;
            }
        }
        return count;
    }

    // Return the number of perfectly balanced AVL Nodes
    public int balanced() {
        return balanced(root);
    }

    // Return the inorder successor or null if there is none or str is not in the tree

    public int balanced(StringAVLNode node) {
        int count = 0;

        if (node != null) {
            if (node.getLeft() != null) {
                count = balanced(node.getLeft());
            }
            if (node.getRight() != null) {
                count += balanced(node.getRight());
            }
            if (node.getBalance() == 0) {
                count++;
            }

        }
        return count;
    }

    /**
     * @param str contains the data insdie the node whos successor we will return
     * @return
     */
    public String successor(String str) {
        String successor = null;
        StringAVLNode parent = null;
        StringAVLNode current = root;
        StringAVLNode target = null;


        while (current != null) {
            if (current.getItem().compareTo(str) == 0) {
                target = current;
                current = null;
            } else if (current.getItem().compareTo(str) > 0) {
                parent = current;
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        if (target != null) {
            if (target.getRight() == null) {
                if (parent != null) {
                    successor = parent.getItem();
                }
            } else {
                current = target.getRight();
                while (current.getLeft() != null) {
                    current = current.getLeft();
                }
                successor = current.getItem();
            }

        }
        return successor;
    }

    /**
     * @param str holds the data we are trying to insert in the tree.
     */
    public void insert(String str) {
        if (root == null) {
            root = new StringAVLNode(str);
        } else {
            root = insert(str, root);
        }
    }

    /**
     * this method will recursively insert values into the AVL tree
     *
     * @param str is the data that we are trying to insert
     * @param t   is the node that is being looked at for insertion
     * @return the node we created
     */
    private StringAVLNode insert(String str, StringAVLNode t) {
        StringAVLNode insertedNode = t;
        boolean isduplicate = false;
        // if t is null the value you are trying to insert becomes the new node and is returned
        if (t == null) {
            insertedNode = new StringAVLNode(str);
        } else {
            // This means that str is smaller than t.getItem()
            if (t.getItem().compareTo(str) > 0) {
                StringAVLNode node = insert(str, t.getLeft());
                t.setLeft(node);
            } else if (t.getItem().compareTo(str) < 0) {
                t.setRight(insert(str, t.getRight()));
            } else {
                isduplicate = true;
            }
        }

        if (!isduplicate) {
            //reset balance
            insertedNode.setBalance(updateNodeBalance(insertedNode));

            if (insertedNode.getBalance() < -1) {
                //leftTree larger than the right tree =>tree is unbalanced
                if (insertedNode.getLeft().getBalance() > 0) {
                    StringAVLNode temp = rotateLeft(insertedNode.getLeft());
                    insertedNode.setLeft(temp);
                }
                insertedNode = rotateRight(insertedNode);
                insertedNode.setBalance(updateNodeBalance(insertedNode));
                insertedNode.getLeft().setBalance(updateNodeBalance(insertedNode.getLeft()));
                insertedNode.getRight().setBalance(updateNodeBalance(insertedNode.getRight()));
            } else if (insertedNode.getBalance() > 1) {

                // right tree is larger than left tree ==> tree is unbalanced
                if (insertedNode.getRight().getBalance() < 0) {
                    StringAVLNode temp = rotateRight(insertedNode.getRight());
                    insertedNode.setRight(temp);
                }
                insertedNode = rotateLeft(insertedNode);
                insertedNode.setBalance(updateNodeBalance(insertedNode));
                insertedNode.getLeft().setBalance(updateNodeBalance(insertedNode.getLeft()));
                insertedNode.getRight().setBalance(updateNodeBalance(insertedNode.getRight()));
            }
        }
        return insertedNode;
    }

    public int updateNodeBalance(StringAVLNode nodeToUpdate) {
        int leftHeight = height(nodeToUpdate.getLeft());
        int rightHeight = height(nodeToUpdate.getRight());
        return rightHeight - leftHeight;
    }
}

