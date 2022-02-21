#include <iostream>
#include <vector>

std::vector<std::string> operations;

class Graph {
 public:
  std::vector<std::vector<int>> relation;
  std::vector<int> data = {$(sample)};

  Graph() {
      std::vector<int> temp = {0, 0, 1, 0, 0, 0, 0, 0, 0};
      relation.resize(this->data.size());
      for (int i = 0; i < this->data.size(); i++) {
          relation[i].resize(data.size());
          for (int j = 0; j < this->data.size(); j++) {
              relation[i][j] = temp[i * data.size() + j];
          }
      }
  }

  ~Graph(){
      for(int i = 0; i < operations.size(); i++){
          std::cout << operations[i];
          if(i != operations.size() - 1){
              std::cout << ",";
          }
      }
  }

  void emphasize(int i, int j) {
      if (i >= data.size() || j >= data.size()) {
          exit(-1);
      }
      operations.push_back("link(" + std::to_string(i) + "," + std::to_string(j) + ")");
  }

  void emphasize(int i) {
      if (i >= data.size()) {
          exit(-1);
      }
      operations.push_back("emp(" + std::to_string(i) + ")");
  }

  void deEmphasize(int i, int j) {
      if (i >= data.size() || j >= data.size()) {
          exit(-1);
      }
      operations.push_back("unlink(" + std::to_string(i) + "," + std::to_string(j) + ")");
  }

  void deEmphasize(int i) {
      if (i >= data.size()) {
          exit(-1);
      }
      operations.push_back("unemp(" + std::to_string(i) + ")");
  }
};

$(code)