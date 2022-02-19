package nkcs.avp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class User {
  private long id;
  private String name;
  private String pwd;
  private boolean operating;

  public User(String name, String pwd) {
    this.name = name;
    this.pwd = pwd;
  }
}
