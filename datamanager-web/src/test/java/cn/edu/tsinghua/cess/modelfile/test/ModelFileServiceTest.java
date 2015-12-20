package cn.edu.tsinghua.cess.modelfile.test;

import cn.edu.tsinghua.cess.component.pagination.PagedList;
import cn.edu.tsinghua.cess.modelfile.dao.ModelFileDao;
import cn.edu.tsinghua.cess.modelfile.dto.ModelQueryParam;
import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileFields;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileFilter;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileManageService;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileQueryService;
import cn.edu.tsinghua.cess.modelfile.service.impl.ModelFileQueryServiceImpl;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class ModelFileServiceTest {
	
	@Autowired ModelFileQueryService modelFileQueryService;
	@Autowired ModelFileManageService modelFileManageService;
	@Autowired ModelFileDao modelFileDao;
	
	@Test
	public void testListFilter() {
		ModelFileFilter filter = modelFileQueryService.listFilter();
		Assert.assertNotNull(filter);
	}
	
	@Test
	public void testQueryModel() {
		ModelFileFilter filter = modelFileQueryService.listFilter();
		PagedList<Model> modelList = modelFileQueryService.queryModel(new ModelQueryParam(filter, 0, 100)).getModelList();
		
		Assert.assertNotNull(modelList);
	}
	
	@Test
	public void testQueryModelWithTemporal() {
		ModelFileFilter filter = modelFileQueryService.listFilter();
		ModelFileFilter single = getSingleFilter(filter);
        PagedList<Model> modelList =  modelFileQueryService.queryModel(new ModelQueryParam(single, 0, 10)).getModelList();

        Assert.assertEquals(modelList.getList().size(), 1);

		Model model = modelFileQueryService.queryModelWithTemporal(modelList.getList().get(0));
		Assert.assertNotNull(model);
	}
	
	@Test
	public void testQueryModelFile() {
        ModelFileFilter filter = modelFileQueryService.listFilter();
        ModelFileFilter single = getSingleFilter(filter);
        PagedList<Model> modelList =  modelFileQueryService.queryModel(new ModelQueryParam(single, 0, 10)).getModelList();
		modelFileQueryService.queryModelFile(modelList.getList().get(0));
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testQueryRelatedNodes() {
        Object result;

		ModelFileFilter filter = modelFileQueryService.listFilter();
        PagedList<Model> model = modelFileQueryService.queryModel(new ModelQueryParam(filter, 0, 100)).getModelList();
        result = modelFileDao.queryRelatedNodes(model.getList());

		ModelFileFilter single = getSingleFilter(filter);
        model = modelFileQueryService.queryModel(new ModelQueryParam(filter, 0, 100)).getModelList();
        result = modelFileDao.queryRelatedNodes(model.getList());
	}
	
	@SuppressWarnings("unchecked")
	private ModelFileFilter getSingleFilter(ModelFileFilter filter) {
		List<ModelFileFields> fList = new ArrayList<ModelFileFields>();
		ModelFileFilter single = null;

		for (ModelFileFields f : ModelFileFields.values()) {
			fList.add(f);

			single = new ModelFileFilter();
			BeanWrapper singleWrapper = PropertyAccessorFactory.forBeanPropertyAccess(single);
			for (ModelFileFields ff : fList) {
				List<String> value = (List<String>) PropertyAccessorFactory.forBeanPropertyAccess(filter).getPropertyValue(ff.getField());
				singleWrapper.setPropertyValue(ff.getField(), new ArrayList<String>(value.subList(0, 1)));
			}

			filter = ((ModelFileQueryServiceImpl)modelFileQueryService).doFilter(single);
		}

		return filter;
	}



}
