package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.tsinghua.cess.task.entity.dto.TaskSubmition;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RawSubmitionTest {
	
	public static void main(String[] args) throws IOException {
		String submition = "{\"filter\":{\"institute\":\"LASG-CESS\",\"model\":\"FGOALS-g2\",\"experiment\":\"1pctCO2\",\"frequency\":\"mon\",\"modelingRealm\":\"seaIce\",\"ensembleMember\":\"r1i1p1\",\"variableName\":\"bmelt\"},\"script\":{\"scriptId\":\"AnnualTS\",\"temporalStart\":\"044002\",\"temporalEnd\":\"050012\",\"latMin\":0,\"latMax\":180,\"lonMin\":0,\"lonMax\":180}}";
		ObjectMapper mapper = new ObjectMapper();
		TaskSubmition submitionEntity = mapper.readValue(submition, TaskSubmition.class);
		System.out.println(submitionEntity);
	}
	
	@SuppressWarnings("unused")
	private static List<String> toList(String str) {
		return new ArrayList<String>(Arrays.asList(new String[] { str }));
	}

}
