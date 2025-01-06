class Node:
    def __init__(self,key):
        self.key = key
        self.left = None
        self.right = None
        self.parent = None
        self.height = 0
        
    def get_right_height(self):
        if self.right is None:
            return -1
        return self.right.height
    
    def get_left_height(self):
        if self.left is None:
            return -1
        return self.left.height
    
    def get_balance_factor(self):
        return self.get_left_height()-self.get_right_height()

class AVL:
    def __init__(self):
        self.root = None

    #search returns the node if exists, else returns insertion point(would-be parent)
    #if the tree is not empty, and None if the tree is empty
    def search(self,key):
        p = self.root
        if p is None:
            return None
        while p is not None:
            if p.key==key:
                return p
            if p.key < key:
                if p.right is None:
                    return p
                p = p.right
            else:
                if p.left is None:
                    return p
                p = p.left
        return None
        
    def updateHeights(self,node):
        p = node
        while p is not None:
            p.height = 1 + max(p.get_left_height(),p.get_right_height())
            p = p.parent

    def right_rotate(self,node):
        parent = node.parent
        y = node.left
        t3 = y.right
        
        y.right = node
        node.parent = y
        node.left = t3
        if t3 is not None:
            t3.parent = node
        if parent is not None:
            y.parent = parent
            if parent.key > y.key:
                parent.left = y
            else:
                parent.right = y

    def left_rotate(self,node):
        parent = node.parent
        y = node.right
        t2 = y.left

        y.left = node
        node.parent = y
        node.right = t2
        if t2 is not None:
            t2.parent = node
        if parent is not None:
            if parent.key > y.key:
                parent.left = y
            else:
                parent.right = y

    def balance_tree_insert(self,node):
        x = node
        y = node.parent
        if y is None:
            return None
        z = y.parent
        while z is not None:
            if abs(z.get_balance_factor()) < 2:
                z = z.parent
                y = y.parent
                x = x.parent
            else:
                if z.left is y and y.left is x:
                    #LL: perform RIGHT rotate on Z
                    self.right_rotate(z)
                if z.left is y and y.right is x:
                    #LR: perform LEFT rotate on Y then RIGHT rotate on Z
                    self.left_rotate(y)
                    self.right_rotate(z)
                if z.right is y and y.right is x:
                    ##RR: perform LEFT rotate on Z
                    self.left_rotate(z)
                if z.right is y and y.left is x:
                    #RL: perform RIGHT rotate on Y then LEFT rotate on Z
                    self.right_rotate(y)
                    self.left_rotate(z)
    
    def insert(self,key):
        n1 = Node(key=key)
        if self.root is None:
            self.root = n1
            return
        adoptive_parent = self.search(key)
        if adoptive_parent.key == key:#Don't insert if key already exist
            return
        n1.parent = adoptive_parent
        if adoptive_parent.key > n1.key:
            adoptive_parent.left = n1
        else:
            adoptive_parent.right = n1
        self.updateHeights(node=adoptive_parent)
        self.balance_tree_insert(node=adoptive_parent)

    def succesor(self,node):#TODO - implement successor
        if node.right is None:
            return None
        p = node.right
        while p.left is not None:
            p = p.left
        return p

    #assumes node is an actual Node != None, and returns its parent if it exists
    def single_delete(self,node):
        parent = node.parent
        if parent is not None:
            #first - parent is disavowing its soon to be deleted, good-for-nothing child.
            if node.key < parent.key:
                parent.left = None
            else:
                parent.right = None
        #now, if the node itself is a leaf - done.

        if node.left is None and node.right is None:
            return parent#null if node is root
        #Otherwise, need to diffrentiate between 1 child and 2 children

        #2 children
        if node.right and node.left:
            node_succesor = self.succesor(node)
            node.key = node_succesor.key
            #TODO - make sure what needs to be done after deleting node_succesor
            return self.single_delete(node_succesor)
        
        #single child
        singleChild = node.left if node.right is None and node.left else node.right
        if parent:
            if parent.key > node.key:
                parent.left = singleChild
            else:
                parent.right = singleChild
            singleChild.parent = parent

        return parent
    
    def balance_tree_delete(self,node):
        z = node
        while z is not None:
            if z.get_balance_factor() > 1:
                bf = z.left.get_balance_factor()
                if bf>=0:
                    self.right_rotate(node)
                else:
                    self.left_rotate(node.left)
                    self.right_rotate(node)
            elif z.get_balance_factor()<-1:
                bf = z.right.get_balance_factor()
                if bf<=0:
                    self.left_rotate(node)
                else:
                    self.right_rotate(z.right)
                    self.left_rotate(node)
            if z.parent is None:
                self.root = z
            z = z.parent
            

    def delete(self,key):
        node = self.search(key)
        if node is None:
            return None
        parent = self.single_delete(node)
        self.updateHeights(parent)
        self.balance_tree_delete(node=node)
        