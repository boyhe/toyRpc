# A toy Rpc

其中使用了netty和spring，可以实现rpc功能。

需要改进的地方：

1.序列化可以使用protobuf,提升效率。

2.对于接受response可以更加优雅一点。

3.可以不使用java内置的动态代理，寻求一个更高效的方式。