package cn.edu.tsing.hua.cafe.util;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: snn
 * @created: 2019-01-26 00:24
 */
@Service
public class ModelFileUtil {

    /**
     * modelFile容器
     */
    private Map<String, String> mapModelFileMap = new HashMap<String, String>(8);

    @PostConstruct
    public void setMapModelFileMap() {
        mapModelFileMap.put("institute", "institute");
        mapModelFileMap.put("model", "model");
        mapModelFileMap.put("experiment", "experiment");
        mapModelFileMap.put("frequency", "frequency");
        mapModelFileMap.put("modelingRealm", "modelingRealm");
        mapModelFileMap.put("ensembleMember", "ensembleMember");
        mapModelFileMap.put("variableName", "variableName");
        mapModelFileMap.put("mipTable", "mipTable");
    }

    public Map<String, String> getModelFileUtilMap(){
        return this.mapModelFileMap;
    }
}
