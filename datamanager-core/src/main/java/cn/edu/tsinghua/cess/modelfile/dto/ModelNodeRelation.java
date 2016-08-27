package cn.edu.tsinghua.cess.modelfile.dto;

import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;

/**
 * Created by yanke on 2016/8/27.
 */
public class ModelNodeRelation {

    private Model model;
    private WorkerNode workerNode;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public WorkerNode getWorkerNode() {
        return workerNode;
    }

    public void setWorkerNode(WorkerNode workerNode) {
        this.workerNode = workerNode;
    }

}
