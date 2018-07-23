package boyhe;

import rpc.proxy.RpcClientProxy;
import rpc.service.HelloService;
import rpc.service.PersonService;

import org.junit.Test;
import org.springframework.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcClientTest {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientTest.class);
    private String remoteServerIP = "127.0.0.1";
    private int remoteServerPort = 9090;

    @Test
    public void testHelloService() {
        StopWatch stopWatch = new StopWatch("testHelloService");
        stopWatch.start();
        RpcClientProxy proxy = new RpcClientProxy().setServiceInterface(HelloService.class).setRemoteServerIP(remoteServerIP).setRemoteServerPort(remoteServerPort);
        HelloService service = proxy.createObject();
        System.out.println(service.hello("boyhe"));
        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());

    }

    @Test
    public void testPersonService() throws Exception {
        StopWatch stopWatch = new StopWatch("testPersonService");
        stopWatch.start();
        RpcClientProxy proxy = new RpcClientProxy().setServiceInterface(PersonService.class).setRemoteServerIP(remoteServerIP).setRemoteServerPort(remoteServerPort);
        PersonService service = proxy.createObject();
        System.out.println(service.getInfo(0));
        System.out.println(service.getInfo(1));
        System.out.println(service.getInfo(2));
        System.out.println(service.getInfo(5));
        System.out.println(service.getInfo(50));
        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());
    }
}
