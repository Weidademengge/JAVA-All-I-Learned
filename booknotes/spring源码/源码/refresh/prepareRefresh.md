
```java
protected void prepareRefresh() {  
   // 设置容器启动的事件
   this.startupDate = System.currentTimeMillis();  
   //容器的关闭标志
   this.closed.set(false);  
   //容器的激活标志，当前容器激活
   this.active.set(true);  

	//日志判断，不重要
   if (logger.isDebugEnabled()) {  
      if (logger.isTraceEnabled()) {  
         logger.trace("Refreshing " + this);  
      }  
      else {  
         logger.debug("Refreshing " + getDisplayName());  
      }  
   }  
  
   // 留给子类覆盖，初始化属性资源，里面是空的，让我们自己进行扩展
   initPropertySources(); 
   
   //创建并获取环境对象，验证需要的属性文件是否都已经放入环境中
   getEnvironment().validateRequiredProperties();  
  
   // 判断刷新前的应用程序监听器集合是否为空，如果为空，则将监听器添加到集合中
   if (this.earlyApplicationListeners == null) {
   //在springboot中有这个监听器，需要把监听器放入到这个集合中  
      this.earlyApplicationListeners = new LinkedHashSet<>(this.applicationListeners);  
   }  
   else {  
      // 如果不为空，则清空集合元素对象
      this.applicationListeners.clear();  
      this.applicationListeners.addAll(this.earlyApplicationListeners);  
   }  
  
   // 创建刷新前的监听事件集合
   this.earlyApplicationEvents = new LinkedHashSet<>();  
}
```