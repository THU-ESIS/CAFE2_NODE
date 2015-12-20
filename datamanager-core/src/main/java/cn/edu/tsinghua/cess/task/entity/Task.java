package cn.edu.tsinghua.cess.task.entity;

import java.util.Date;
import java.util.concurrent.Callable;

import cn.edu.tsinghua.cess.component.exception.ExceptionHandler;
import cn.edu.tsinghua.cess.task.entity.dto.TaskSubmition;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Task {
	
	private static ObjectMapper mapper= new ObjectMapper();
	
	private Integer id;
	private String uuid;
	private TaskSubmition submitionEntity;
	private Date createTime;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	
	public String getSubmition() {
		return ExceptionHandler.unchecked(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return mapper.writeValueAsString(submitionEntity);
            }
        });
	}
	
	public void setSubmition(final String submition) {
		ExceptionHandler.unchecked(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Task.this.submitionEntity = mapper.readValue(submition, TaskSubmition.class);
                return null;
            }
        });
	}

	public TaskSubmition getSubmitionEntity() {
		return submitionEntity;
	}
	public void setSubmitionEntity(TaskSubmition submition) {
		this.submitionEntity = submition;
	}

}
