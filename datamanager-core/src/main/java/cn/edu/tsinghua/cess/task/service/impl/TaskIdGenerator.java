package cn.edu.tsinghua.cess.task.service.impl;

import java.util.UUID;

/**
 * Created by yanke on 2015-12-08 19:54
 */
public class TaskIdGenerator {

    public static String generateTaskId() {
        return UUID.randomUUID().toString();
    }

}
