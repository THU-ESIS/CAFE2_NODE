package cn.edu.tsinghua.cess.datamanager.web;

import cn.edu.tsinghua.cess.deployment.entity.DeployMode;
import cn.edu.tsinghua.cess.deployment.entity.Deployment;
import cn.edu.tsinghua.cess.deployment.service.DeploymentService;
import cn.edu.tsinghua.cess.deployment.service.NotYetDeployedException;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;
import cn.edu.tsinghua.cess.workernode.service.WorkerNodeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/deployment")
@Controller
public class DeploymentAction {

    @Autowired
    DeploymentService deploymentService;
    @Autowired
    WorkerNodeManageService workerNodeManageService;

    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public String deploy(
            @RequestParam("mode") String mode,
            @RequestParam String nodeName,
            @RequestParam String nodeIp,
            @RequestParam Integer nodePort,
            @RequestParam String rootPath,
            @RequestParam String centralNodeIp,
            @RequestParam Integer centralNodePort,
            @RequestParam String centralNodeRootPath
    ) {
        final Deployment deployment = new Deployment();

        deployment.setMode(DeployMode.valueOf(mode));
        deployment.setNodeName(nodeName);
        deployment.setNodeIp(nodeIp);
        deployment.setNodePort(nodePort);
        deployment.setRootPath(rootPath);
        deployment.setCentralNodeIp(centralNodeIp);
        deployment.setCentralNodePort(centralNodePort);
        deployment.setCentralNodeRootPath(centralNodeRootPath);

        deploymentService.deploy(deployment);

        return "redirect:";  // after deployment, redirect the browser to "/web/deployment" as to show the result
    }

    @RequestMapping("")
    public ModelAndView showDeployment() {
        try {
            Deployment deployment = deploymentService.get();

            ModelAndView mv = new ModelAndView("deployment/show");
            mv.addObject("deployment", deployment);

            if (deployment.getMode() == DeployMode.DISTRIBUTED_CENTRAL) {
                List<WorkerNode> workerNodeList = workerNodeManageService.listAll();
                mv.addObject("workerNodeList", workerNodeList);
            }

            return mv;
        } catch (NotYetDeployedException e) {
            return new ModelAndView("deployment/add");
        }
    }


}
