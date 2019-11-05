# Handler
- Netty处理器重要概念:

1. Netty的处理器可以分为两类:入站处理器与出站处理器.
2. 入站处理器的顶层是channelInboundHandler,出站处理器的顶层是ChannelOutboundHandler.
3. 跳据处理时常用的各种输解码器本质上都是处理器.
4. 编解码器:无论我们向网络中写入的数据是什么类型(int, char, String、二进制等) ,数据在网络中传递时,
其都是以字节流的形式呈现的:将数据由原本的形式转换为字节流的操作成为编码(encode) ,
将效据由字节转换为它原本的格式或是其他格式的操作或为解码(decode ,解码统一称为codec
和
5. 编码:本质上是一种出站处理影;因此,编码一定是一种ChannelOutboundHandler
6. 解码:本质上是一种入站处理器;因此,解码一定是-科channelInboundHandler.
7. 在Netty中,编码器通常以xxxncoder命名;解码错通常以xxxDecoder命名.

- 编解码器
    
    - 当我们需要自己定义编码器时一般只需要继承`MessageToByteEncoder`实现其encode（）方法即可，解码时一般继承`ByteToMessageCodec
    `实现其decode方法即可，    