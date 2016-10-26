package cn.edu.tsinghua.cess.task.service.impl;

import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.task.entity.ModelProvider;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanke on 2016-10-22 21:10
 */
public class ModelSubmitionOrderSortingComparator implements Comparator<ModelProvider> {

    Map<Model, Integer> modelIndexMap;

    public ModelSubmitionOrderSortingComparator(List<Model> submitionModels) {
        modelIndexMap = new HashMap<Model, Integer>();
        int i = 0;
        for (Model model : submitionModels) {
            modelIndexMap.put(model, Integer.valueOf(i));
            i++;
        }

    }


    @Override
    public int compare(ModelProvider o1, ModelProvider o2) {
        int idx1 = modelIndexMap.get(o1.getModelEntity());
        int idx2 = modelIndexMap.get(o2.getModelEntity());

        return idx1 - idx2;
    }
}
