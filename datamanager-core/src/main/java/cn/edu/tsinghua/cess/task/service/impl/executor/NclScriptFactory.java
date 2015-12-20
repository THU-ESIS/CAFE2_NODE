package cn.edu.tsinghua.cess.task.service.impl.executor;

import org.springframework.stereotype.Component;

import cn.edu.tsinghua.cess.component.context.ApplicationContextHolder;
import cn.edu.tsinghua.cess.datamanager.nclscript.NclScript;

@Component
public class NclScriptFactory {
	
	public NclScript getNclScript(String scriptId) {
        return (NclScript) ApplicationContextHolder.getContext().getBean(scriptId);
	}

}
