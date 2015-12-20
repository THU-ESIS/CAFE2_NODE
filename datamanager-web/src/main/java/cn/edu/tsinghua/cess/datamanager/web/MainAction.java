package cn.edu.tsinghua.cess.datamanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainAction {
	
	@RequestMapping("index")
	public String main() {
		return "main/index";
	}

}
