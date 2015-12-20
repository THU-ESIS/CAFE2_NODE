package test;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {
	
	public static void main(String[] args) {
		String apiBaseUrl = "http://127.0.0.1:8080/datamanager";
		String apiUrl = "/api/v1/task/submit";
		
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        String url = apiBaseUrl + apiUrl;
        
        String submition = "{\"filter\":{\"institute\":[\"LASG-CESS\"],\"model\":[\"FGOALS-g2\"],\"experiment\":[\"1pctCO2\"],\"frequency\":[\"mon\"],\"modelingRealm\":[\"seaIce\"],\"ensembleMember\":[\"r1i1p1\"],\"variableName\":[\"bmelt\"]},\"script\":{\"scriptId\":\"AnnualTS\",\"temporalStart\":\"190001\",\"temporalEnd\":\"209912\",\"latMin\":0,\"latMax\":180,\"lonMin\":null,\"lonMax\":180}}";
        
        Object result = template.postForObject(url, submition, String.class);
        
        result.hashCode();
	}

}
