#include <iostream>
#include <vector>
std::vector<std::string> operations;
class BinaryTreeNode {
 public:
  int position;
  int value;
  BinaryTreeNode* left;
  BinaryTreeNode* right;
  BinaryTreeNode* father;
  bool isInit = false;

  BinaryTreeNode(int value) {
      this->value = value;
      this->position = 1;
  }

  int getValue() {
      operations.push_back("get(" + std::to_string(this->position) + ")");
      return value;
  }
  void insertLeft(int value) {
      this->left = new BinaryTreeNode(value);
      this->left->father = this;
      this->left->position = this->position * 2 + 1;
      operations.push_back("insert(" + std::to_string(this->left->position) + "," + std::to_string(value) + ")");
  }
  void insertRight(int value) {
      this->right = new BinaryTreeNode(value);
      this->right->father = this;
      this->right->position = this->position * 2 + 2;
      operations.push_back("insert(" + std::to_string(this->right->position) + "," + std::to_string(value) + ")");
  }
  void removeLeft() {
      int removePosition = this->left->position;
      this->left = NULL;
      operations.push_back("remove(" + std::to_string(removePosition) + ")");
  }
  void removeRight() {
      int removePosition = this->right->position;
      this->right = NULL;
      operations.push_back("remove(" + std::to_string(removePosition) + ")");
  }
};
class BinaryTree {
 public:
  std::vector<BinaryTreeNode> nodeList;
  BinaryTreeNode* root = NULL;
  BinaryTree() {
      this->root = initTreeByData();
  }
  ~BinaryTree(){
      for(int i = 0; i < operations.size(); i++){
          std::cout << operations[i];
          if(i != operations.size() - 1){
              std::cout << ",";
          }
      }
  }
  BinaryTreeNode* getRoot() {
      return root;
  }
  void swap(BinaryTreeNode node1, BinaryTreeNode node2) {
      int temp = node1.value;
      node1.value = node2.value;
      node2.value = temp;
      operations.push_back("swap(" + std::to_string(node1.position) + "," + std::to_string(node2.position) + ")");
  }
 private:
  BinaryTreeNode* initTreeByData() {
      std::vector<int> arr = {$(sample)};
      for (int i = 0; i < arr.size(); i++) {
          if (arr[i] <= 0) {
              nodeList.emplace_back(-1);
              continue;
          }
          nodeList.emplace_back(arr[i]);
      }
      for (int i = 0; i < nodeList.size(); i++) {
          if (nodeList[i].value != -1) {
              nodeList[i].position = i;
              if (2 * i + 1 < nodeList.size() && nodeList[2 * i + 1].value != -1) {
                  nodeList[i].left = &nodeList[2 * i + 1];
              }
              if (2 * i + 2 < nodeList.size() && nodeList[2 * i + 2].value != -1) {
                  nodeList[i].right = &nodeList[2 * i + 2];
              }
          }
      }
      return nodeList.size() == 0 ? NULL : &nodeList[0];
  }
};
$(code)


