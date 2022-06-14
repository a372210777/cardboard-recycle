package com.br.service.impl;

import lombok.RequiredArgsConstructor;
import com.br.domain.GenConfig;
import com.br.repository.GenConfigRepository;
import com.br.service.GenConfigService;
import com.br.utils.StringUtils;
import org.springframework.stereotype.Service;
import java.io.File;

/**
 *
 * @date 2019-01-14
 */
@Service
@RequiredArgsConstructor
public class GenConfigServiceImpl implements GenConfigService {

    private final GenConfigRepository genConfigRepository;

    @Override
    public GenConfig find(String tableName) {
        GenConfig genConfig = genConfigRepository.findByTableName(tableName);
        if(genConfig == null){
            return new GenConfig(tableName);
        }
        return genConfig;
    }

    @Override
    public GenConfig update(String tableName, GenConfig genConfig) {
        String separator = File.separator;
        String[] paths;
        String symbol = "\\";
        if (symbol.equals(separator)) {
            paths = genConfig.getPath().split("\\\\");
        } else {
            paths = genConfig.getPath().split(File.separator);
        }
        StringBuilder api = new StringBuilder();
        for (String path : paths) {
            api.append(path);
            api.append(separator);
            if ("src".equals(path)) {
                api.append("api");
                break;
            }
        }
        genConfig.setApiPath(api.toString());
        return genConfigRepository.save(genConfig);
    }
}
