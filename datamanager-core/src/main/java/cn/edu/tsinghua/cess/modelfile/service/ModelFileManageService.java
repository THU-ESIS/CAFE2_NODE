package cn.edu.tsinghua.cess.modelfile.service;

import cn.edu.tsinghua.cess.component.remote.Remote;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileList;

/**
 * Created by kurt on 2014/8/12.
 */
public interface ModelFileManageService {
	
	@Remote("/modelfile/persist")
	public void insertModelFileList(ModelFileList modelFileList);
	
	public void deleteModelFileOfNode(Integer nodeId);
	
}
