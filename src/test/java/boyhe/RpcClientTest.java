package boyhe;

import rpc.client.RpcClientProxy;
import rpc.service.HelloService;
import rpc.service.PersonService;
import org.junit.Test;

public class RpcClientTest {

    @Test
    public void testHelloService(){
        RpcClientProxy proxy = new RpcClientProxy();
        HelloService service = proxy.create(HelloService.class);
        System.out.println(service.hello("boyhe"));
    }

    @Test
    public void testPersonService() throws Exception{
        RpcClientProxy proxy = new RpcClientProxy();
        PersonService service = proxy.create(PersonService.class);
        System.out.println(service.getInfo(0));
        System.out.println(service.getInfo(1));
        System.out.println(service.getInfo(2));
        System.out.println(service.getInfo(5));
        System.out.println(service.getInfo(50));
    }


}
