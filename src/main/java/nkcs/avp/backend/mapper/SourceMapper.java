package nkcs.avp.backend.mapper;

import nkcs.avp.backend.domain.Source;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SourceMapper {
    Source getSourceByDirectory(String directory);
    int addSource(Source source);
}
