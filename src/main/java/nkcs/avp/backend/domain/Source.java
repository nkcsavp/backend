package nkcs.avp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Source {
  private String code;
  private String directory;
}
