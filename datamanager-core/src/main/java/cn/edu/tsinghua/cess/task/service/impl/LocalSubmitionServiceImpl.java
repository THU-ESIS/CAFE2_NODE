package cn.edu.tsinghua.cess.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.tsinghua.cess.modelfile.dao.ModelFileDao;
import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.task.dao.TaskSubmitionDao;
import cn.edu.tsinghua.cess.task.entity.SubTask;
import cn.edu.tsinghua.cess.task.entity.SubTaskListEntry;
import cn.edu.tsinghua.cess.task.entity.Task;
import cn.edu.tsinghua.cess.task.entity.dto.TaskSubmition;
import cn.edu.tsinghua.cess.task.service.TaskExecutionService;
import cn.edu.tsinghua.cess.task.service.TaskSubmitionService;

@Component("localSubmitionService")
public class LocalSubmitionServiceImpl implements TaskSubmitionService {

    @Autowired ModelFileDao modelFileDao;
	@Autowired TaskSubmitionDao taskSubmitionDao;
	@Autowired TaskExecutionService taskExecutionService;

	@Transactional
	@Override
	public String submitTask(TaskSubmition submition) {
        String uuid = TaskIdGenerator.generateTaskId();
        Date ts = new Date();

		Task task = new Task();
        task.setUuid(uuid);
        task.setCreateTime(ts);
		task.setSubmitionEntity(submition);
        taskSubmitionDao.insert(task);

        List<Integer> subTaskIdList = this.submitSubTask(submition);

        for (Integer id :subTaskIdList) {
            SubTaskListEntry entry = new SubTaskListEntry();
            entry.setTaskId(task.getId());
            entry.setSubTaskId(id);

            taskSubmitionDao.insertSubTaskListEntry(entry);
        }

        return uuid;
	}

    @Override
    public List<Integer> submitSubTask(TaskSubmition submition) {
        List<Integer> subTaskIdList = new ArrayList<Integer>();

        for (Model model : submition.getModels()) {
            SubTask subTask = SubTask.newInstance(model, submition.getNclScript());

            taskSubmitionDao.insertSubTask(subTask);
            taskExecutionService.addTask(subTask);
            subTaskIdList.add(subTask.getId());
        }

        return subTaskIdList;
    }

}
