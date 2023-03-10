传播特性有几种？7种

Required，Requires_new, nested, Spport, Not_Support, Never, Mandatory

某一个事务嵌套另一个事务的时候怎么办?
A方法调用B方法，AB方法都有事务，并且传播特性不同，那么A如果有异常，B怎么办，B如果有异常，A怎么办？

事务的传播特性指的是不同方法的嵌套调用过程中，事务应该如何进行处理，是用同一个事务还是不同的事务，当出现异常的时候会回滚还是提交，两个方法之间的相关印象。
在日常工作中，使用较多的是required，Requires_new,nested

1. 先说事务的不同分类，可以分为三类：支持当前事务、不支持当前事务、嵌套事务
2. 如果外层方法是required，内层方法是requires_new，nested
3. 如果外层方法是requires_new,内层方法是requires_new，nested
4. 如果外层方法是nested,内层方法是requires_new，nested

核心处理逻辑：
1. 判断内外方法是否是同一事务：
	是：异常同意在外层方法处理
	不是：内层方法有可能影响到外层方法， 但是外层方法不会影响内层方法
	（大致，有个别不同）

