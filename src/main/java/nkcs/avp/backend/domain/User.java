package nkcs.avp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
