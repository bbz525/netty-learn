# netty-learn
- 一些学习netty时的Demo by version 4.1.41.Final
- 第一个demo是实现一个B/S的浏览器请求后返回数据 代码包：`cpm.poplar.hello`
- 第二个demo实现C/S模式的数据交互 为此包下的代码：`cpm.poplar.cs`
- 第三个demo实现C/S模式的数据交互，以聊天为需求 为此包下的代码：`cpm.poplar.chat`
- 在使用谷歌的protoc编译时，编译路径很重要否则无法编译,参考：`E:\idea-workspace\netty-learn> protoc --java_out=src/main/java src/proto/Person.proto`

  1. [protocol-buffer官网](https://developers.google.cn/protocol-buffers/)
  2. 需要导入的包：
  ```
       // https://mvnrepository.com/artifact/io.netty/netty-all
         compile group: 'io.netty', name: 'netty-all', version: '4.1.15.Final'
         compile group: 'com.google.protobuf', name: 'protobuf-java', version: '3.9.2'
         // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java-util
         compile group: 'com.google.protobuf', name: 'protobuf-java-util', version: '3.9.2'
  ```
  