# Netty中的 ReferenceCounted
- 官方文档解释
```java
A reference-counted object that requires explicit deallocation.
When a new ReferenceCounted is instantiated, it starts with the reference count of 1. 
retain() increases the reference count, and release() decreases the reference count. 
If the reference count is decreased to 0, the object will be deallocated explicitly,
and accessing the deallocated object will usually result in an access violation 违例.
```
- 实现类中的方法探索
```java
 private ByteBuf retain0(int increment) {
        for (;;) {
            int refCnt = this.refCnt;
            final int nextCnt = refCnt + increment;

            // Ensure we not resurrect (which means the refCnt was 0) and also that we encountered an overflow.
            //netty中当对象的引用数为0时对象将会被回收，访问被回收的对象是违规的
            if (nextCnt <= increment) {
                throw new IllegalReferenceCountException(refCnt, increment);
            }
            //自旋锁
            if (refCntUpdater.compareAndSet(this, refCnt, nextCnt)) {
                break;
            }
        }
        return this;
    }
//释放内存
  private boolean release0(int decrement) {
        for (;;) {
            int refCnt = this.refCnt;
            if (refCnt < decrement) {
                throw new IllegalReferenceCountException(refCnt, -decrement);
            }
            if (refCntUpdater.compareAndSet(this, refCnt, refCnt - decrement)) {
                if (refCnt == decrement) {
                    //释放内存
                    deallocate();
                    return true;
                }
                return false;
            }
        }
    }
```