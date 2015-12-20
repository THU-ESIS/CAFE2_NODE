package cn.edu.tsinghua.cess.deployment.entity;

import cn.edu.tsinghua.cess.component.remote.RemoteServer;

/**
 * Created by kurt on 2014/9/6.
 */
public class Deployment {

    private DeployMode mode;

    private Integer nodeId;
    private String nodeName;
    private String nodeIp;
    private Integer nodePort;
    private String rootPath;

    private String centralNodeIp;
    private Integer centralNodePort;
    private String centralNodeRootPath;
    
    public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public DeployMode getMode() {
        return mode;
    }

    public void setMode(DeployMode mode) {
        this.mode = mode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public Integer getNodePort() {
        return nodePort;
    }

    public void setNodePort(Integer nodePort) {
        this.nodePort = nodePort;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getCentralNodeIp() {
        return centralNodeIp;
    }

    public void setCentralNodeIp(String centralNodeIp) {
        this.centralNodeIp = centralNodeIp;
    }

	public Integer getCentralNodePort() {
		return centralNodePort;
	}

	public void setCentralNodePort(Integer centralNodePort) {
		this.centralNodePort = centralNodePort;
	}

	public String getCentralNodeRootPath() {
		return centralNodeRootPath;
	}

	public void setCentralNodeRootPath(String centralNodeRootPath) {
		this.centralNodeRootPath = centralNodeRootPath;
	}
	
	public RemoteServer getLocalServer() {
		return new RemoteServer() {
			
			@Override
			public String getRootPath() {
				return rootPath;
			}
			
			@Override
			public Integer getPort() {
				return nodePort;
			}
			
			@Override
			public String getAddress() {
				return nodeIp;
			}
		};
		
	}
	
	public RemoteServer getCentralServer() {
		return new RemoteServer() {
			
			@Override
			public String getRootPath() {
				return centralNodeRootPath;
			}
			
			@Override
			public Integer getPort() {
				return centralNodePort;
			}
			
			@Override
			public String getAddress() {
				return centralNodeIp;
			}
		};
	}
    
}
