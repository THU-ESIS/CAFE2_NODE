package cn.edu.tsinghua.cess.task.service.impl.executor;

import cn.edu.tsinghua.cess.component.context.ApplicationContextHolder;
import cn.edu.tsinghua.cess.datamanager.nclscript.NclScript;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class NclScriptFactory {

	private Logger logger = Logger.getLogger(NclScriptFactory.class);
	
	public NclScript getNclScript(final String scriptId) {
		logger.info("begin process getNclScript, scripId=" + scriptId);

        return this._getNclScript(scriptId).or(new Supplier<NclScript>() {
			@Override
			public NclScript get() {
			    throw new RuntimeException("no script object was found for scriptId=" + scriptId);
			}
		});
	}

	private Optional<NclScript> _getNclScript(String scriptId) {
		try {
			return Optional.fromNullable(this.getByClassName(scriptId));
		} catch (Exception e) {
			logger.error("error getting by class name, msg=" + e.getMessage(), e);
			return Optional.fromNullable(getWithinContext(scriptId));
		}
	}

	private NclScript getWithinContext(String scriptId) {
		NclScript script = (NclScript) ApplicationContextHolder.getContext().getBean(scriptId);
		logger.info("created ncl script=" + script.getClass().toString());

		return script;
	}

	private NclScript getByClassName(String scriptId) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		String className = NclScript.class.getPackage().getName() + "." + scriptId;
        Class clazz = Class.forName(className);

		return (NclScript) clazz.newInstance();
	}

	public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
		Object inst = new NclScriptFactory().getNclScript("PolarNHEF");
		System.out.println(inst);
	}

}
