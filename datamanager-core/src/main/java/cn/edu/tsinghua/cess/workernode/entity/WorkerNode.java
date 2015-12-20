package cn.edu.tsinghua.cess.workernode.entity;

import cn.edu.tsinghua.cess.component.remote.RemoteServer;
import cn.edu.tsinghua.cess.deployment.entity.Deployment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class WorkerNode implements RemoteServer {
	
	private Integer id;		// id of workerNode
	private String name;	// name of workerNode
	private String ip;
    private Integer port;
    private String rootPath;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

    @JsonIgnore
    @Override
    public String getAddress() {
        return this.getIp();
    }

    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

    public static WorkerNode fromDeployment(Deployment deployment) {
        WorkerNode node = new WorkerNode();

        node.setName(deployment.getNodeName());
        node.setIp(deployment.getNodeIp());
        node.setPort(deployment.getNodePort());
        node.setRootPath(deployment.getRootPath());

        return node;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WorkerNode) {
            return this.hashCode() == obj.hashCode();
        }

        return false;
    }


    @Override
    public String toString() {
        return "WorkerNode{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", ip='" + ip + '\'' +
            ", port=" + port +
            ", rootPath='" + rootPath + '\'' +
            '}';
    }

}
