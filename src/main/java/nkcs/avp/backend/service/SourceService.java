package nkcs.avp.backend.service;


import nkcs.avp.backend.domain.Source;

public interface SourceService {
    Source findSourceByDirectory(String directory);
    int addSource(Source source);
}
