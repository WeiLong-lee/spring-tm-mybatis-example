

## 这是mybatis 3.0 使用样例

    1.使用代码生成器快速生成mapper，domain
    
    2. 常用注解使用规范
        2.1 @Insert @Delete @Update @Select  
              value 值为数组 ，可以拼接 ,同时使用<script> </script>标签包围，可以像xml语法一样书写
        2.2 @SelectKey  返回主键  主键在参数类中获取 <selectKey>
            用在注解了@Insert，@InsertProvider，@Updateor@UpdateProvider的方法上,其他方法忽略
            作用：属性：statement是要执行的sql语句的字符串数组，
                      keyProperty是需要更新为新值的参数对象属性，
                      before可以是true或者false分别代表sql语句应该在执行insert之前或者之后，
                      resultType是keyProperty的Java类型，
                      statementType是语句的类型，取Statement，PreparedStatement和CallableStatement对应的STATEMENT，PREPARED或者CALLABLE其中一个，默认是PREPARED。
        2.3 @ResultMap  可以用来复用XML映射中 <resultMap> 的id ，会覆盖@Results ,@ConstructorArgs 
        
        2.4 @Results,@Result
        2.5 @One,@Many
              @One 类似于 xml中<association>
              @Many xml中为 <collection> 
        2.6 @MapKey
            它能够将存放对象的 List 转化为 key 值为对象的某一属性的 Map。属性有： value，填入的是对象的属性名，作为 Map 的 key 值。
            
        2.7 @CacheNamespace  相当于xml中的cache （对实时性要求较高的不建议使用）
               配置缓存 属性：
                  implemetation，
                  eviction(缓存策略)： LUR（最近最少使用）：移除最长时间不被使用的对象，这是默认值。
                                     FIFO（先进先出）：按对象进入缓存的顺序来析出它们。
                                     SOFT（软引用）：移除基于垃圾回收器状态和软引用规则的对象。
                                     WEAK（弱引用）：更积极的移除基于垃圾收集器状态和弱引用规则的对象。
                  flushInterval（刷新时间） 默认不设置，调用语句才刷新
                  size（引用数目） 默认值是 1024
                  readWrite（是否只读）默认为true readonly
                  blocking
                  properties 
            @CacheNamespaceRef(AnnotationUserMapper.class)V3.4.2+ 出现xml 和注解同时使用时，兼容覆盖
        2.8 @Property 
               指定属性值 在 mybatisv3.4.2+ 可用
               
        2.9 常用查询中 list 参数 map参数 处理
            list： id in list:#{list}   map同理,  xml 使用foreach   

*** 使用注意：
    @SelectProvide 方法类中 不要使用重载 
    pgSql 使用大小写注意 注意数据库关键字
    
    
# spring 事务管理

    事务：单个逻辑单元执行一系列的事，要么全成功，要么全不执行。
    
    事务特性：ACID
    
    1.原子性（atomicity）： 事务是最小的执行单位，不允许分割。事务的原子性确保动作要么全部完成，要么完全不起作用；
    2.一致性（consistency）： 执行事务前后，数据保持一致；
    3.隔离性（isolation）： 并发访问数据库时，一个用户的事物不被其他事物所干扰，各并发事务之间数据库是独立的；
    4.持久性（durability）: 一个事务被提交之后。它对数据库中数据的改变是持久的，即使数据库发生故障也不应该对其有任何影响。
    spring 事务本质：spring事务的本质就是对数据库事务的支持，没有数据库的事务支持，spring是无法完成事务的。
    
    spring实现事务管理有两种方式：编程式事务管理（不常用）、申明式事务管理（AOP实现）
    
    事务隔离级别：
              DEFAULT ：这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是： READ_COMMITTED 。
              READ_UNCOMMITTED ：该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读和不可重复读，因此很少使用该隔离级别。
              READ_COMMITTED ：该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。
              REPEATABLE_READ ：该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读。
              SERIALIZABLE ：所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。
    	*指定方法：通过使用 isolation 属性设置，例如：@Transactional(isolation = Isolation.DEFAULT)
    传播行为： 
              REQUIRED ：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。 
              SUPPORTS ：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。 
              MANDATORY ：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。 
              REQUIRES_NEW ：创建一个新的事务，如果当前存在事务，则把当前事务挂起。 
              NOT_SUPPORTED ：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
              NEVER ：以非事务方式运行，如果当前存在事务，则抛出异常。 
              NESTED ：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于 REQUIRED 。
       *指定方法：通过使用 propagation 属性设置，例如：@Transactional(propagation = Propagation.REQUIRED)
    
    注意事项：
    
      1. spring 的事务管理默认是针对Error异常和RuntimeException异常以及其子类进行事务回滚，对runtimeException并不需要抛出，error需要抛出异常，并进行捕获。
        所以通常的写法是抛出Exception基类，如 @Transactional(rollbackFor = Exception.class)
      2. 在事务中我们不需要主动捕获异常，只需向上抛出，可以在controller层捕获处理，否则事务会失效
      3. 事务只对public 方法生效
      4。我们在使用事物注解的时候，尽量不要在类上面使用，这会使得类里面的所有方法都会有事物进行处理。比如说，我们一些方法只做查询操作，我们就没有必要再进行事物，
        我们应该在需要事物处理的方法上面加事物，并且指定回滚的异常类型