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

class AVL:
    def __init__(self):
        self.root = None

    def search(self,key):
        p = self.root
        if p is None:
            return p
        while p is not None:
            if p.key==key:
                return p
            if p.key < key:
                p = p.right
            else:
                p = p.left
        if p is None:
            return p.parent
        
    def updateHeights(self,node):
        p = node
        while p is not None:
            p.height = 1 + max(p.get_left_height(),p.get_right_height())
            p = p.parent

    def balance_tree(self,node):
        return None
    
    def insert(self,key):
        n1 = Node(key=key)
        if self.root is None:
            self.root = n1
        adoptive_parent = self.search(key)
        n1.parent = adoptive_parent
        if adoptive_parent.key > n1.key:
            adoptive_parent.left = n1
        else:
            adoptive_parent.right = n1
        self.updateHeights(node=adoptive_parent)
        self.balance_tree(node=adoptive_parent)