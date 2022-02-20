package nkcs.avp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;

 /*
  About Status:
   0 stands for Pending
   1 stands for Successful
   2 stands for Error
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {
  private long uid;
  private Timestamp time;
  private long status = 0;
  private String sample;
  private String identifier;
  private String code;
  private String lang;
  private String mode;
  private String animation;

  public Task(long uid, String sample, String code, String lang, String mode) {
    this.uid = uid;
    this.time = new Timestamp(new Date().getTime());
    this.sample = sample;
    this.code = code;
    this.lang = lang;
    this.mode = mode;
  }
}
