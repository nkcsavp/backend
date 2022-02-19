package nkcs.avp.backend.service.Impl;

import nkcs.avp.backend.domain.Source;
import nkcs.avp.backend.mapper.SourceMapper;
import nkcs.avp.backend.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SourceServiceImpl implements SourceService {
    private SourceMapper sourceMapper;

    @Autowired
    public void setAdminMapper(SourceMapper sourceMapper) {
        this.sourceMapper = sourceMapper;
    }


    @Override
    public Source findSourceByDirectory(String directory) {
        return sourceMapper.getSourceByDirectory(directory);
    }

    @Override
    public int addSource(Source source) {
        return sourceMapper.addSource(source);
    }
}