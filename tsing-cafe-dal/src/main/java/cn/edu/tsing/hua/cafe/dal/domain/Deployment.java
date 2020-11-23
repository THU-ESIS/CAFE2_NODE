package cn.edu.tsing.hua.cafe.dal.domain;

public class Deployment {
    private String mode;

    private Integer nodeId;

    private String nodeName;

    private String nodeIp;

    private Integer nodePort;

    private String rootPath;

    private String centralNodeIp;

    private Integer centralNodePort;

    private String centralNodeRootPath;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode == null ? null : mode.trim();
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp == null ? null : nodeIp.trim();
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
        this.rootPath = rootPath == null ? null : rootPath.trim();
    }

    public String getCentralNodeIp() {
        return centralNodeIp;
    }

    public void setCentralNodeIp(String centralNodeIp) {
        this.centralNodeIp = centralNodeIp == null ? null : centralNodeIp.trim();
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
        this.centralNodeRootPath = centralNodeRootPath == null ? null : centralNodeRootPath.trim();
    }
}