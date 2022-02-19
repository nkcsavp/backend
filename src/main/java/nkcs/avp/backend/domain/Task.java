package nkcs.avp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@ToString
public class Task {
  private long uid;
  private Timestamp time;
  private long status = 0;
  private String animation = "";
  private String sample;
  private String sid;
  private String lang;
  private String mode;

  public Task(long uid, Timestamp time, String sample, String lang, String mode) {
    this.uid = uid;
    this.time = time;
    this.sample = sample;
    this.lang = lang;
    this.mode = mode;
  }
}
