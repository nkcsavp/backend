package nkcs.avp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class User {
  private long id;
  private String mail;
  private String pwd;

  public User(String mail, String pwd) {
    this.mail = mail;
    this.pwd = pwd;
  }
}
