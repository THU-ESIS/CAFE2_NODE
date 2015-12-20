package test;

import cn.edu.tsinghua.cess.component.remote.RemoteServer;
import cn.edu.tsinghua.cess.component.remote.RemoteServiceFactory;
import org.springframework.beans.PropertyAccessorFactory;

public class LocalTestUtil {

    static RemoteServiceFactory factory;

    static  {
        factory = new RemoteServiceFactory();
        PropertyAccessorFactory.forDirectFieldAccess(factory).setPropertyValue("apiPath", "/api/v1");
    }

	
	public static <T> T getService(RemoteServer server, Class<T> c) {
		return factory.getRemoteService(server, c);
	}
	
	public static RemoteServer cess = new RemoteServer() {
		
		@Override
		public String getRootPath() {
			return "datamanager-web";
		}
		
		@Override
		public Integer getPort() {
			return 8088;
		}
		
		@Override
		public String getAddress() {
			return "166.111.7.72";
		}
	};

    public static RemoteServer cessWorker = new RemoteServer() {

        @Override
        public String getRootPath() {
            return "datamanager-worker";
        }

        @Override
        public Integer getPort() {
            return 8088;
        }

        @Override
        public String getAddress() {
            return "166.111.7.72";
        }
    };
	
	public static RemoteServer qaCentral = new RemoteServer() {
		
		@Override
		public String getRootPath() {
			return "datamanager-web-qa-central";
		}
		
		@Override
		public Integer getPort() {
			return 8088;
		}
		
		@Override
		public String getAddress() {
			return "106.185.25.190";
		}
		
	};

    public static RemoteServer kurtLaptop = new RemoteServer() {
        @Override
        public String getAddress() {
            return "127.0.0.1";
        }

        @Override
        public Integer getPort() {
            return 8088;
        }

        @Override
        public String getRootPath() {
            return "/";
        }
    };

}
