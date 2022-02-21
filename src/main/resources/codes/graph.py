class Graph:
    operations = []

    def __init__(self) -> None:
        self.data = [$(sample)]
        temp = [$(relation)]
        self.relation = []
        for i in range(len(self.data)):
            line = []
            for j in range(len(self.data)):
                line.append(temp[i * len(self.data) + j])
            self.relation.append(line)

    def emphasizeLink(self, i, j):
        if i >= len(self.data) or j >= len(self.data):
            raise IndexError
        Graph.operations.append("link(" + str(i) + "," + str(j) + ")")

    def emphasizeNode(self, i):
        if i >= len(self.data):
            raise IndexError
        Graph.operations.append("emp(" + str(i) + ")")

    def deEmphasizeLink(self, i, j):
        if i >= len(self.data) or j >= len(self.data):
            raise IndexError
        Graph.operations.append("unlink(" + str(i) + "," + str(j) + ")")

    def deEmphasizeNode(self, i):
        if i >= len(self.data):
            raise IndexError
        Graph.operations.append("unemp(" + str(i) + ")")


$(code)
print(",".join(Graph.operations),end="")
