#include <iostream>
#include <vector>
class DataList {
  std::vector<std::string> operations;
  std::vector<int> arr = {$(sample)};
 public:

  int get(int idx) {
      operations.push_back("get(" + std::to_string(idx) + ")");
      return arr[idx];
  }
  void swap(int i, int j) {
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
      operations.push_back("swap(" + std::to_string(i) + "," + std::to_string(j) + ")");
  }
  int size() {
      return arr.size();
  }
  ~DataList(){
      for(int i = 0; i < operations.size(); i++){
          std::cout << operations[i];
          if(i != operations.size() - 1){
              std::cout << ":";
          }
      }
  }
};

$(code)