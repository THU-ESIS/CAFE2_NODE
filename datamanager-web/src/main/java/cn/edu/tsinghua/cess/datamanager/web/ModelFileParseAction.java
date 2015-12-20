package cn.edu.tsinghua.cess.datamanager.web;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.tsinghua.cess.datamanager.api.ApiResult;
import cn.edu.tsinghua.cess.datamanager.api.ApiUtil;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileParseService;

@Controller
public class ModelFileParseAction {
	
	@Autowired ModelFileParseService modelFileParseService;
	
	@RequestMapping(value = "/parser/run", method = RequestMethod.POST)
	public @ResponseBody ApiResult runParser(@RequestParam final String path) {
		return ApiUtil.execute(new Callable<Object>() {
			
			@Override
			public Object call() throws Exception {
				modelFileParseService.parse(path);
				return null;
			}
			
		});
	}
	
	@RequestMapping("/parser")
	public ModelAndView parse() {
		return new ModelAndView("parser/parse");
	}

}
