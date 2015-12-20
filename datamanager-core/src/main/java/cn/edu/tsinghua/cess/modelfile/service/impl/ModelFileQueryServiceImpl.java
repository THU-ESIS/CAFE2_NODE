package cn.edu.tsinghua.cess.modelfile.service.impl;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import cn.edu.tsinghua.cess.modelfile.dto.ModelQueryParam;
import cn.edu.tsinghua.cess.modelfile.dto.ModelQueryResult;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import cn.edu.tsinghua.cess.component.pagination.PagedList;
import cn.edu.tsinghua.cess.component.pagination.PaginationUtil;
import cn.edu.tsinghua.cess.modelfile.dao.ModelFileDao;
import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileFields;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileFilter;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileQueryService;

@Component
public class ModelFileQueryServiceImpl implements ModelFileQueryService {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ModelFileDao modelFileDao;
	
	private static ModelFileFilter initialFilter = null;
	
	@Override
	public ModelFileFilter listFilter() {
		if (initialFilter == null) {
			synchronized (ModelFileQueryService.class) {
				if (initialFilter == null) {
					initialFilter = this.innerListFilter();
				}
			}
		}
		
		return initialFilter;
	}
	
	private ModelFileFilter innerListFilter() {
		return this.doFilter(null);
	}
	
	@SuppressWarnings("unused")
	private Map<String, String> listTemporalRange(ModelFileFilter filter) {
		String start = modelFileDao.listTemporalRangeByFilter(false, filter);
		String end = modelFileDao.listTemporalRangeByFilter(true, filter);
		
		Map<String, String> temporalRange = new LinkedHashMap<String, String>();
		
		temporalRange.put("start", start);
		temporalRange.put("end", end);
		
		return temporalRange;
	}

	public PagedList<Model> queryModel(final ModelFileFilter filter, Integer page, Integer pageSize) {
		PagedList<Model> list = PaginationUtil.pagedQuery(page, pageSize, new Callable<List<Model>>() {

			@Override
			public List<Model> call() throws Exception {
				return modelFileDao.queryModel(filter);
			}

		});
		
		for (Model model : list) {
			this.fillWithTemporalRange(model);
		}
		
		return list;
	}

	public ModelFileFilter doFilter(ModelFileFilter filter) {
		ModelFileFilter remainingFilter = new ModelFileFilter();	
		
		BeanWrapper filterWrapper = PropertyAccessorFactory.forBeanPropertyAccess(filter == null ? new ModelFileFilter() : filter);
		BeanWrapper remainingFilterWrapper = PropertyAccessorFactory.forBeanPropertyAccess(remainingFilter);
		
		for (ModelFileFields f : ModelFileFields.values()) {
			String field = f.getField();
			String column = f.getColumn();
			
			List<String> fieldFilter = (List<String>) filterWrapper.getPropertyValue(field);
			
			if (CollectionUtils.isEmpty(fieldFilter)) {
				List<String> remainingFieldFilter = modelFileDao.listFieldCandidate(column, filter);
				remainingFilterWrapper.setPropertyValue(field, remainingFieldFilter);
			} else {
				remainingFilterWrapper.setPropertyValue(field, fieldFilter);
			}
		}
		
//		remainingFilter.setTemporalRange(this.listTemporalRangeByFilter(filter));

		return remainingFilter;
	}

	@Override
	public ModelQueryResult queryModel(ModelQueryParam param) {
		PagedList<Model> modelFileList = this.queryModel(param.getFilter(), param.getPage(), param.getPageSize());
		ModelFileFilter remainingFilter = this.doFilter(param.getFilter());

		return new ModelQueryResult(modelFileList, remainingFilter);
	}

	@Override
    public Model queryModelWithTemporal(Model model) {
        this.fillWithTemporalRange(model);
        return model;
    }

    @Override
    public List<String> queryModelFile(Model model) {
        return modelFileDao.queryModelFileOfModel(model);
    }

	@Override
	public WorkerNode[] queryRelatedNodes(Model[] modelList) {
		return modelFileDao.queryRelatedNodes(Arrays.asList(modelList)).toArray(new WorkerNode[0]);
	}

	private void fillWithTemporalRange(Model model) {
		String start = modelFileDao.listTemporalRangeByModel(false, model);
		String end = modelFileDao.listTemporalRangeByModel(true, model);
		
		model.setTemporalStart(start);
		model.setTemporalEnd(end);
	}

}
