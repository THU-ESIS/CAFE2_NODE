package cn.edu.tsinghua.cess.modelfile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.tsinghua.cess.modelfile.dao.ModelFileDao;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileList;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileManageService;

@Component
public class ModelFileManageServiceImpl implements ModelFileManageService {
	
	@Autowired ModelFileDao modelFileDao;

	@Transactional
	@Override
	public void insertModelFileList(ModelFileList modelFileList) {
		Integer nodeId = modelFileList.nodeId;

		for (ModelFile modelFile : modelFileList.list) {
			modelFile.setNodeId(nodeId);
		}

		modelFileDao.insertModelFileList(modelFileList.list);
	}

	@Transactional
	@Override
	public void deleteModelFileOfNode(Integer nodeId) {
		modelFileDao.deleteModelFileOfNode(nodeId);
	}

}
