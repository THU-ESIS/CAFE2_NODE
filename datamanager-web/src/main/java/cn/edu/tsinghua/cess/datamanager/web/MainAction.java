package cn.edu.tsinghua.cess.datamanager.web;

import cn.edu.tsing.hua.cafe.service.ModelFileJust;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class MainAction {

    @Resource
    private ModelFileJust modelFileJust;

    @RequestMapping("index")
    public String main() {
        return "main/index";
    }

    @RequestMapping(value = "get/mfs/part")
    @ResponseBody
    public String each(@RequestParam("model") String mode, @RequestParam("value") String value) {
        return JSONObject.toJSONString(modelFileJust.getModelFile(mode, value));
    }

    @RequestMapping(value = "get/mfs/part/multi")
    @ResponseBody
    public String multi(@RequestParam("model") String mode, @RequestParam("value") String value) {
        return JSONObject.toJSONString(modelFileJust.getModelFileByMulti(mode, value));
    }

    @RequestMapping(value = "get/mfs/intact")
    @ResponseBody
    public String intact() {
        return JSONObject.toJSONString(modelFileJust.getModelFileAll());
    }
}
