class DataList:
    operations = []

    def __init__(self) -> None:
        self.arr = [$(sample)]

    def get(self, i):
        DataList.operations.append("get" + "(" + str(i) + ")")
        return self.arr[i]

    def swap(self, i, j):
        DataList.operations.append("swap" + "(" + str(i) + "," + str(j) + ")")
        temp = self.arr[i]
        self.arr[i] = self.arr[j]
        self.arr[j] = temp


$(code)
print(":".join(DataList.operations),end="")
