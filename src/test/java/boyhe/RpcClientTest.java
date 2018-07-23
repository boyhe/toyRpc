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
        stopWatch.start("1");
        RpcClientProxy proxy = new RpcClientProxy(HelloService.class, remoteServerIP, remoteServerPort);
        HelloService service = proxy.createObject();
        System.out.println(service.hello("boyhe1"));
        stopWatch.stop();
        stopWatch.start("2");
        System.out.println(service.hello("boyhe2"));
        stopWatch.stop();
        stopWatch.start("3");
        System.out.println(service.hello("boyhe3"));
        stopWatch.stop();
        stopWatch.start("4");
        System.out.println(service.hello("boyhe4"));
        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());

    }

    @Test
    public void testPersonService() throws Exception {
        StopWatch stopWatch = new StopWatch("testPersonService");
        stopWatch.start("0");
        RpcClientProxy proxy = new RpcClientProxy(PersonService.class, remoteServerIP, remoteServerPort);
        PersonService service = proxy.createObject();
        System.out.println(service.getInfo(0));
        stopWatch.stop();
        stopWatch.start("1");
        System.out.println(service.getInfo(1));
        stopWatch.stop();
        stopWatch.start("2");
        System.out.println(service.getInfo(2));
        stopWatch.stop();
        stopWatch.start("5");
        System.out.println(service.getInfo(5));
        stopWatch.stop();
        stopWatch.start("50");
        System.out.println(service.getInfo(50));
        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());
    }
}
