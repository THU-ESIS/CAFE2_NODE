package cn.edu.tsinghua.cess.task.service.impl.executor;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.edu.tsinghua.cess.component.context.ApplicationContextHolder;
import cn.edu.tsinghua.cess.datamanager.nclscript.NclScript;

@Component
public class NclScriptFactory {

	private Logger logger = Logger.getLogger(NclScriptFactory.class);
	
	public NclScript getNclScript(String scriptId) {
        NclScript script =  (NclScript) ApplicationContextHolder.getContext().getBean(scriptId);
        logger.info("created ncl script=" + script.getClass().toString());

        return script;
	}

}
