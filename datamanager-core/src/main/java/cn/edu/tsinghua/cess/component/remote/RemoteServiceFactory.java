package cn.edu.tsinghua.cess.component.remote;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import cn.edu.tsinghua.cess.component.context.ApplicationContextHolder;
import cn.edu.tsinghua.cess.datamanager.api.ApiException;
import cn.edu.tsinghua.cess.task.entity.dto.TaskSubmition;
import cn.edu.tsinghua.cess.task.service.TaskSubmitionService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by kurt on 2014/9/6.
 */
@Component
public class RemoteServiceFactory {
	
	private Logger log = Logger.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    @Qualifier("apiPath")
    private String apiPath;

    @SuppressWarnings("unchecked")
	public <T> T getRemoteService(final RemoteServer target, Class<T> remoteServiceType) {
        if (!remoteServiceType.isInterface()) {
            throw new IllegalArgumentException();
        }
        InvocationHandler remoteHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Remote remote = method.getAnnotation(Remote.class);

                if (remote == null || StringUtils.isEmpty(remote.value())) {
                    throw new IllegalArgumentException();
                }

                String result = makeInvocation(remote, target, args);
                return parseResult(method.getReturnType(), result);
            }
        };

        return (T) Proxy.newProxyInstance(remoteServiceType.getClassLoader(), new Class<?>[]{remoteServiceType}, remoteHandler);
    }

    @SuppressWarnings("rawtypes")
	private String makeInvocation(Remote remote, RemoteServer target, Object[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + target.getAddress() + ":" + target.getPort() + "/" + target.getRootPath();
        url = url + apiPath + remote.value();
        
        log.info("will begin to execute [method=" + remote.method() + "] on [url=" + url + "]");

        if (remote.method() == RequestMethod.GET) {
            String[] properties = remote.paramProperties();
            
            StringBuilder builder = new StringBuilder();
            builder.append(url);
            builder.append("?");

            for (int i = 0; i < properties.length; i++) {

                Object argsi = args[i];
                if (argsi instanceof Object[]) {
                    argsi = Arrays.asList((Object[]) argsi);
                }

                if (argsi instanceof Iterable) {
                    for (Object obj : (Iterable) argsi) {
                        builder.append(properties[i])
                                .append("=")
                                .append(obj)
                                .append("&");
                    }
                } else {
                    builder.append(properties[i])
                            .append("=")
                            .append(args[i])
                            .append("&");
                }
            }

            return restTemplate.getForObject(builder.toString(), String.class);
        } else if (remote.method() == RequestMethod.POST) {
            return restTemplate.postForObject(
                url,
                args == null || args.length == 0
                    ? null : args[0],
                String.class
            );
        } else {
            throw new IllegalArgumentException();
        }
    }

    private <T> T parseResult(Class<T> returnType, String apiResponse) throws IOException {
    	log.info("will parse response=" + apiResponse);
    	
        JsonNode node = mapper.readValue(apiResponse, JsonNode.class);
        boolean success = node.get("success").booleanValue();

        if (!success) {
            throw new ApiException(node.get("errorMsg").toString());
        } else {
        	if (returnType.getName().equals("void")) {
        		return null;
        	}
            JsonNode dataNode = node.get("data");
            String text = dataNode.toString();

            if (StringUtils.isEmpty(text)) {
                return null;
            } else {
                return mapper.readValue(text, returnType);
            }
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, IOException {
        String resposne = "{\"success\":true,\"data\":[6,7]}";
        Class<?> returnType = TaskSubmitionService.class.getMethod("submitSubTask", TaskSubmition.class).getReturnType();

        Object parsed = new RemoteServiceFactory().parseResult(returnType, resposne);
        System.out.println(parsed);
    }

}
