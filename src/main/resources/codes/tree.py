class BinaryTreeNode:
    def __init__(self, value) -> None:
        self.value = value
        self.position = None
        self.left : BinaryTreeNode = None
        self.right : BinaryTreeNode = None
        self.father : BinaryTreeNode= None
        self.isInit = True

    def getValue(self):
        BinaryTree.operations.append("get(" + str(self.position) + ")")
        return self.value

    def insertLeft(self, value):
        self.left = BinaryTreeNode(value)
        self.left.father = self
        self.left.position = self.position * 2 + 1
        BinaryTree.operations.append("insert(" + str(self.left.position) + "," + str(value) + ")")

    def insertRight(self, value):
        self.right = BinaryTreeNode(value)
        self.right.father = self
        self.right.position = self.position * 2 + 2
        BinaryTree.operations.append("insert(" + str(self.right.position) + "," + str(value) + ")")

    def removeLeft(self):
        removePosition = self.left.position
        self.left = None
        BinaryTree.operations.append("remove(" + str(removePosition) + ")")

    def removeRight(self):
        removePosition = self.right.position
        self.right = None
        BinaryTree.operations.append("remove(" + str(removePosition) + ")")


class BinaryTree:
    operations = []

    def __init__(self) -> None:
        self.root = self.initTreeByData()

    @staticmethod
    def initTreeByData():
        node_list = []
        for i in [$(sample)]:
            if i <= 0:
                node_list.append(None)
            else:
                node_list.append(BinaryTreeNode(i))
        for i in range(len(node_list)):
            if node_list[i] is not None:
                node_list[i].position = i
                if 2 * i + 1 < len(node_list) and node_list[2 * i + 1] is not None:
                    node_list[i].left = node_list[2 * i + 1]
                if 2 * i + 2 < len(node_list) and node_list[2 * i + 2] is not None:
                    node_list[i].right = node_list[2 * i + 2]
        if len(node_list) == 0:
            return None
        else:
            return node_list[0]

    @staticmethod
    def swap(node1: BinaryTreeNode, node2: BinaryTreeNode):
        temp = node1.value
        node1.value = node2.value
        node2.value = temp
        BinaryTree.operations.append("swap(" + str(node1.position) + "," + str(node2.position) + ")");

    def getRoot(self) -> BinaryTreeNode:
        return self.root


$(code)
print(":".join(BinaryTree.operations),end="")


