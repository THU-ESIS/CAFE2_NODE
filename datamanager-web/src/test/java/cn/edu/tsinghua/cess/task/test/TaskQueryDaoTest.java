package cn.edu.tsinghua.cess.task.test;

import cn.edu.tsinghua.cess.task.dao.TaskQueryDao;
import cn.edu.tsinghua.cess.task.entity.SubTask;
import cn.edu.tsinghua.cess.task.entity.SubTaskListEntry;
import cn.edu.tsinghua.cess.task.service.TaskQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class TaskQueryDaoTest {

    @Autowired
    TaskQueryDao taskQueryDao;
    @Autowired
    TaskQueryService taskQueryService;

    //	@Test
    public void testQuerySubTask() {
        @SuppressWarnings("unused")
        List<SubTaskListEntry> entryList = taskQueryDao.querySubTaskList(Integer.valueOf(8));
        taskQueryService.queryTaskResult("1");
    }

    @Test
    public void testQuerySubTaskResult() {
        SubTask st = taskQueryDao.querySubTaskResult(Integer.valueOf(8));
        st.hashCode();
    }

}
