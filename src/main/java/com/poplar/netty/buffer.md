# Netty Buffer
- 当我们进行数据传输的时候，往往需要缓冲区。java NIO 中自带的提供的就是java.nio.Buffer但是由于java自带的过于复杂，
而且自身也有一定的缺陷（定长，一个标识位position等）。Netty便提供的自己的缓冲ByteBufNio 
- ByteBuffer 和 Netty ByteBuf 对比具有如下优点

   ```
  Sequential Access Indexing
  Netty中提供了两个指针来操作读和写
  ByteBuf provides two pointer variables to support sequential read and write operations - readerIndex for a read operation 
  and writerIndex for a write operation respectively. The following diagram shows how a buffer is segmented into three areas 
  by the two pointers:
          +-------------------+------------------+------------------+
          | discardable bytes |  readable bytes  |  writable bytes  |
          |                   |     (CONTENT)    |                  |
          +-------------------+------------------+------------------+
          |                   |                  |                  |
          0      <=      readerIndex   <=   writerIndex    <=    capacity
  ```
  - 操作NIO的时候，当我们对缓冲区put的时候，如果缓冲区空间不够，将会抛出异常。为了避免这个问题。Netty在write数据的时候，
  首先会对数据的长度和可写空间做个校验。如果不足，就会创建一个新的ByteBuf,并把之前的复制到新建的这个ByteBuf。最后释放老的ByteBuf。
```java
 static ByteBuf encodeString0(ByteBufAllocator alloc, boolean enforceHeap, CharBuffer src, Charset charset, int extraCapacity) {
        final CharsetEncoder encoder = CharsetUtil.encoder(charset);
        //根据编码的最大字节数计算Capacity
        int length = (int) ((double) src.remaining() * encoder.maxBytesPerChar()) + extraCapacity;
        ...
        }
    //每次写入都会校验是否需要扩容
    @Override
    public ByteBuf writeBytes(ByteBuffer src) {
        int length = src.remaining();
        ensureWritable0(length);
        setBytes(writerIndex, src);
        writerIndex += length;
        return this;
    }


@Override
    public ByteBuf capacity(int newCapacity) {
        checkNewCapacity(newCapacity);
        byte[] oldArray = array;
        int oldCapacity = oldArray.length;
        if (newCapacity == oldCapacity) {
            return this;
        }

        int bytesToCopy;
        if (newCapacity > oldCapacity) {
            bytesToCopy = oldCapacity;
        } else {
            trimIndicesToCapacity(newCapacity);
            bytesToCopy = newCapacity;
        }
        byte[] newArray = allocateArray(newCapacity);
        //把老数组中的数据拷贝到新数组中，然后释放掉老数组中的数据
        System.arraycopy(oldArray, 0, newArray, 0, bytesToCopy);
        setArray(newArray);
        freeArray(oldArray);
        return this;
    }
```

## 总结：

- HeapBuffer(堆缓冲区)

    - 优点:由于 据是存储在jvm的堆中,因此可以快速的创建与快速的释放,并且它提供了直接访问内部字节数组的方法；
    - 缺点:每次读写数据时,都需要先特数据复制到直接缓冲区中再进行网络传输.
    
- DirectBuffer 冲区(直接继冲区）
    - 在堆之外直接分配内存空间,直接缓冲区并不会占用堆的容量空间,因为它是由操作系统在本地内存进行的数据分配.
    - 优点:在使用socket进行数据传递时,性能非常好,因为数据直接位于操作系统的本地内存中,所以不需要从Jvm将数据复制直接狐冲区中,性续很好;
    - 缺点:因为Direct Buffer是直接在操作系统内存中的,所以内存空间的分配与释放要比堆空间更加复杂,而且速度要慢一些.
    
- netty通过提供内存池来解决这个问题。直接缓冲区并不支持通过字节数组的方式来访问效援；
   - 优点:对于后端的业务消息的维解码来说,推荐使用HeapByteBuffer;对于I/O通信编程在读写缓冲区时,推荐使用DirectByteBuffer
   
- CompositeBuffer(复合冲区）

   - JDKByteBuffer与Netty的ByteBuf之间的差异比对:
   - netty的ByteBuf采用了读写索引分离的策略(readerIndex与writerIndex) ,一个初给化(E面尚未有任何播)的readerIndex writerIndex为0。
   - 当读索引与写索引处于同一个位置时,如果我们继续读取,那么就会抛出indexOutOfBounderException,
   - 对于ByteBuf的任何读写操作都会分别校验维护读索引与写案引. maxCapacity大容量默认的限制就是Integer.Value.
   
- JDK的ByteBuffer与netty的ByteBuf的比较

    - `final byte[] hb;` 这是JDK的ByteBuffer对象中用于存储数据的对象声明;可以看别,其字节数组是被声明为final,也就是长度是固定不变的,
    一旦分配好后不动态扩容与收缩;而且当待存储的数据宇节很大时就很有可能出现indexOutOfBounderException,如果类预防这个异常,
    那就得在存健之前完全确定好特存字节太小，如果ByteBuffer的空间不足,我们只有一种解决方案:创建一个全新的ByteBuffer对象,
    然后再将之前的ByteBuffer 的数据复制过来,这一切操作都需要开发者自已手动完成。

    - ByteBuffer使用一个position指针来标识位置信息,在进行读写切换时就需要调用flip方法或是rewind方法,使用起来很不方便

- NettyByteBuf的优点:

    - 存储字节的数组是动态的,其最大值默认是integer.MaxValue.这里的动态性是体现在write方法中的, write方法在执行时会判断ByteBuf的容量，
    如果不足，会自动扩容；

    - ByteBuf的读写索引是完全分开的,使用起来就很方便

