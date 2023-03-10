SpringMVC的作用毋庸置疑，虽然我们现在都是用SpringBoot，但是SpringBoot中仍然是在使用SpringMVC来处理请求。  
  
我们在使用SpringMVC时，传统的方式是通过定义web.xml，比如：
```xml
<web-app>

  

<servlet>

<servlet-name>app</servlet-name>

<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

<init-param>

<param-name>contextConfigLocation</param-name>

<param-value>/WEB-INF/spring.xml</param-value>

</init-param>

<load-on-startup>1</load-on-startup>

</servlet>

  

<servlet-mapping>

<servlet-name>app</servlet-name>

<url-pattern>/app/*</url-pattern>

</servlet-mapping>

  

</web-app>
```

我们只要定义这样的一个web.xml，然后启动Tomcat，那么我们就能正常使用SpringMVC了。  
  
SpringMVC中，最为核心的就是DispatcherServlet，在启动Tomcat的过程中：  
1Tomcat会先创建DispatcherServlet对象  
2然后调用DispatcherServlet对象的init()  
  
而在init()方法中，会创建一个Spring容器，并且添加一个ContextRefreshListener监听器，该监听器会监听ContextRefreshedEvent事件（Spring容器启动完成后就会发布这个事件），也就是说Spring容器启动完成后，就会执行ContextRefreshListener中的onApplicationEvent()方法，从而最终会执行DispatcherServlet中的initStrategies()，这个方法中会初始化更多内容：
```java
protected void initStrategies(ApplicationContext context) {

initMultipartResolver(context);

initLocaleResolver(context);

initThemeResolver(context);

  

initHandlerMappings(context);

initHandlerAdapters(context);

  

initHandlerExceptionResolvers(context);

initRequestToViewNameTranslator(context);

initViewResolvers(context);

initFlashMapManager(context);

}
```

其中最为核心的就是HandlerMapping和HandlerAdapter。

## 什么是Handler？  
Handler表示请求处理器，在SpringMVC中有四种Handler：  
1实现了Controller接口的Bean对象  
2实现了HttpRequestHandler接口的Bean对象  
3添加了@RequestMapping注解的方法  
4一个HandlerFunction对象  
  
比如实现了Controller接口的Bean对象：

```java
@Component("/test")

public class ZhouyuBeanNameController implements Controller {

  

@Override

public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

System.out.println("zhouyu");

return new ModelAndView();

}

}
```

实现了HttpRequestHandler接口的Bean对象：

```java
@Component("/test")

public class ZhouyuBeanNameController implements HttpRequestHandler {

  

@Override

public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

System.out.println("zhouyu");

}

}
```

添加了@RequestMapping注解的方法：

```java
  

@RequestMapping

@Component

public class ZhouyuController {

  

@Autowired

private ZhouyuService zhouyuService;

  

@RequestMapping(method = RequestMethod.GET, path = "/test")

@ResponseBody

public String test(String username) {

return "zhouyu";

}

  

}
```

一个HandlerFunction对象（以下代码中有两个）：

```java
@ComponentScan("com.zhouyu")

@Configuration

public class AppConfig {

  

@Bean

public RouterFunction<ServerResponse> person() {

return route()

.GET("/app/person", request -> ServerResponse.status(HttpStatus.OK).body("Hello GET"))

.POST("/app/person", request -> ServerResponse.status(HttpStatus.OK).body("Hello POST"))

.build();

}

}
```

## 什么是HandlerMapping?  
HandlerMapping负责去寻找Handler，并且保存路径和Handler之间的映射关系。  
  
因为有不同类型的Handler，所以在SpringMVC中会由不同的HandlerMapping来负责寻找Handler，比如：  
1BeanNameUrlHandlerMapping：负责Controller接口和HttpRequestHandler接口  
2RequestMappingHandlerMapping：负责@RequestMapping的方法  
3RouterFunctionMapping：负责RouterFunction以及其中的HandlerFunction  
  
BeanNameUrlHandlerMapping的寻找流程：  
1找出Spring容器中所有的beanName  
2判断beanName是不是以“/”开头  
3如果是，则把它当作一个Handler，并把beanName作为key，bean对象作为value存入handlerMap中  
4handlerMap就是一个Map  
  
  
RequestMappingHandlerMapping的寻找流程：  
1找出Spring容器中所有beanType  
2判断beanType是不是有@Controller注解，或者是不是有@RequestMapping注解  
3判断成功则继续找beanType中加了@RequestMapping的Method  
4并解析@RequestMapping中的内容，比如method、path，封装为一个RequestMappingInfo对象  
5最后把RequestMappingInfo对象做为key，Method对象封装为HandlerMethod对象后作为value，存入registry中  
6registry就是一个Map  
  
  
RouterFunctionMapping的寻找流程会有些区别，但是大体是差不多的，相当于是一个path对应一个HandlerFunction。  
  
各个HandlerMapping除开负责寻找Handler并记录映射关系之外，自然还需要根据请求路径找到对应的Handler，在源码中这三个HandlerMapping有一个共同的父类AbstractHandlerMapping
![[Pasted image 20230222095420.png]]

AbstractHandlerMapping实现了HandlerMapping接口，并实现了getHandler(HttpServletRequest request)方法。  
  
AbstractHandlerMapping会负责调用子类的getHandlerInternal(HttpServletRequest request)方法从而找到请求对应的Handler，然后AbstractHandlerMapping负责将Handler和应用中所配置的HandlerInterceptor整合成为一个HandlerExecutionChain对象。  
  
所以寻找Handler的源码实现在各个HandlerMapping子类中的getHandlerInternal()中，根据请求路径找到Handler的过程并不复杂，因为路径和Handler的映射关系已经存在Map中了。  
  
比较困难的点在于，当DispatcherServlet接收到一个请求时，该利用哪个HandlerMapping来寻找Handler呢？看源码：
```java
protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {

if (this.handlerMappings != null) {

for (HandlerMapping mapping : this.handlerMappings) {

HandlerExecutionChain handler = mapping.getHandler(request);

if (handler != null) {

return handler;

}

}

}

return null;

}
```

很简单，就是遍历，找到就返回，默认顺序为：
![[Pasted image 20230222095453.png]]

所以BeanNameUrlHandlerMapping的优先级最高，比如：
```java
@Component("/test")

public class ZhouyuBeanNameController implements Controller {

  

@Override

public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

System.out.println("Hello zhouyu");

return new ModelAndView();

}

}
```

```java
@RequestMapping(method = RequestMethod.GET, path = "/test")

@ResponseBody

public String test(String username) {

return "Hi zhouyu";

}
```

请求路径都是/test，但是最终是Controller接口的会生效。

## 什么是HandlerAdapter？  
找到了Handler之后，接下来就该去执行了，比如执行下面这个test()
```java
@RequestMapping(method = RequestMethod.GET, path = "/test")

@ResponseBody

public String test(String username) {

return "zhouyu";

}
```

但是由于有不同种类的Handler，所以执行方式是不一样的，再来总结一下Handler的类型：  
1. 实现了Controller接口的Bean对象，执行的是Bean对象中的handleRequest()  
2. 实现了HttpRequestHandler接口的Bean对象，执行的是Bean对象中的handleRequest()  
3. 添加了@RequestMapping注解的方法，具体为一个HandlerMethod，执行的就是当前加了注解的方法  
4. 一个HandlerFunction对象，执行的是HandlerFunction对象中的handle()  
  
所以，按逻辑来说，找到Handler之后，我们得判断它的类型，比如代码可能是这样的：
```java
Object handler = mappedHandler.getHandler();

if (handler instanceof Controller) {

((Controller)handler).handleRequest(request, response);

} else if (handler instanceof HttpRequestHandler) {

((HttpRequestHandler)handler).handleRequest(request, response);

} else if (handler instanceof HandlerMethod) {

((HandlerMethod)handler).getMethod().invoke(...);

} else if (handler instanceof HandlerFunction) {

((HandlerFunction)handler).handle(...);

}
```

但是SpringMVC并不是这么写的，还是采用的适配模式，把不同种类的Handler适配成一个HandlerAdapter，后续再执行HandlerAdapter的handle()方法就能执行不同种类Hanlder对应的方法。  
  
针对不同的Handler，会有不同的适配器：  
1. HttpRequestHandlerAdapter  
2. SimpleControllerHandlerAdapter  
3. RequestMappingHandlerAdapter  
4. HandlerFunctionAdapter  
  
适配逻辑为：
```java
protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {

if (this.handlerAdapters != null) {

for (HandlerAdapter adapter : this.handlerAdapters) {

if (adapter.supports(handler)) {

return adapter;

}

}

}

throw new ServletException("No adapter for handler [" + handler +

"]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");

}
```

传入handler，遍历上面四个Adapter，谁支持就返回谁，比如判断的代码依次为：
```java
public boolean supports(Object handler) {

return (handler instanceof HttpRequestHandler);

}

  

public boolean supports(Object handler) {

return (handler instanceof Controller);

}

  

public final boolean supports(Object handler) {

return (handler instanceof HandlerMethod && supportsInternal((HandlerMethod) handler));

}

  

public boolean supports(Object handler) {

return handler instanceof HandlerFunction;

}
```


根据Handler适配出了对应的HandlerAdapter后，就执行具体HandlerAdapter对象的handle()方法了，比如：  
HttpRequestHandlerAdapter的handle()： 
  ```java
public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
	
	((HttpRequestHandler) handler).handleRequest(request, response);
	
	return null;

} 
```

SimpleControllerHandlerAdapter的handle()：
```java
public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {

	return ((Controller) handler).handleRequest(request, response);

}
```
因为这三个接收的直接就是Requeset对象，不用SpringMVC做额外的解析，所以比较简单，比较复杂的是RequestMappingHandlerAdapter，它执行的是加了@RequestMapping的方法，而这种方法的写法可以是多种多样，SpringMVC需要根据方法的定义去解析Request对象，从请求中获取出对应的数据然后传递给方法，并执行。  

@RequestMapping方法参数解析  
当SpringMVC接收到请求，并找到了对应的Method之后，就要执行该方法了，不过在执行之前需要根据方法定义的参数信息，从请求中获取出对应的数据，然后将数据传给方法并执行。  
  
一个HttpServletRequest通常有：  
1request parameter  
2request attribute  
3request session  
4reqeust header  
5reqeust body  
  
比如如下几个方法：  

表示要从request parameter中获取key为username的value  
  

表示要从request parameter中获取key为uname的value  
  

表示要从request attribute中获取key为username的value  
  
  

表示要从request session中获取key为username的value  
  

表示要从request header中获取key为username的value  
  

表示获取整个请求体  
  
所以，我们发现SpringMVC要去解析方法参数，看该参数到底是要获取请求中的哪些信息。  
  
而这个过程，源码中是通过HandlerMethodArgumentResolver来实现的，比如：  
1RequestParamMethodArgumentResolver：负责处理@RequestParam  
2RequestHeaderMethodArgumentResolver：负责处理@RequestHeader  
3SessionAttributeMethodArgumentResolver：负责处理@SessionAttribute  
4RequestAttributeMethodArgumentResolver：负责处理@RequestAttribute  
5RequestResponseBodyMethodProcessor：负责处理@RequestBody  
6还有很多其他的...  
  
而在判断某个参数该由哪个HandlerMethodArgumentResolver处理时，也是很粗暴：  

  
就是遍历所有的HandlerMethodArgumentResolver，哪个能支持处理当前这个参数就由哪个处理。  
  
比如：  

以上代码的username将对应RequestParam中的username，而不是session中的，因为在源码中RequestParamMethodArgumentResolver更靠前。  
  
当然HandlerMethodArgumentResolver也会负责从request中获取对应的数据，对应的是resolveArgument()方法。  
  
比如RequestParamMethodArgumentResolver：  

  
核心是：  

  
按同样的思路，可以找到方法中每个参数所要求的值，从而执行方法，得到方法的返回值。  
  

@RequestMapping方法返回值解析  
  
而方法返回值，也会分为不同的情况。比如有没有加@ResponseBody注解，如果方法返回一个String:  
1加了@ResponseBody注解：表示直接将这个String返回给浏览器  
2没有加@ResponseBody注解：表示应该根据这个String找到对应的页面，把页面返回给浏览器  
  
在SpringMVC中，会利用HandlerMethodReturnValueHandler来处理返回值：  
1RequestResponseBodyMethodProcessor：处理加了@ResponseBody注解的情况  
2ViewNameMethodReturnValueHandler：处理没有加@ResponseBody注解并且返回值类型为String的情况  
3ModelMethodProcessor：处理返回值是Model类型的情况  
4还有很多其他的...  
  
我们这里只讲RequestResponseBodyMethodProcessor，因为它会处理加了@ResponseBody注解的情况，也是目前我们用得最多的情况。  
  
RequestResponseBodyMethodProcessor相当于会把方法返回的对象直接响应给浏览器，如果返回的是一个字符串，那么好说，直接把字符串响应给浏览器，那如果返回的是一个Map呢？是一个User对象呢？该怎么把这些复杂对象响应给浏览器呢？  
  
处理这块，SpringMVC会利用HttpMessageConverter来处理，比如默认情况下，SpringMVC会有4个HttpMessageConverter：  
1ByteArrayHttpMessageConverter：处理返回值为字节数组的情况，把字节数组返回给浏览器  
2StringHttpMessageConverter：处理返回值为字符串的情况，把字符串按指定的编码序列号后返回给浏览器  
3SourceHttpMessageConverter：处理返回值为XML对象的情况，比如把DOMSource对象返回给浏览器  
4AllEncompassingFormHttpMessageConverter：处理返回值为MultiValueMap对象的情况  
  
StringHttpMessageConverter的源码也比较简单：  

  
先看有没有设置Content-Type，如果没有设置则取默认的，默认为ISO-8859-1，所以默认情况下返回中文会乱码，可以通过以下来中方式来解决：  

Java

1

2

3

4

5

@RequestMapping(method = RequestMethod.GET, path = "/test", produces = {"application/json;charset=UTF-8"})

@ResponseBody

public String test() {

return "周瑜";

}

  

Java

1

2

3

4

5

6

7

8

9

10

11

12

@ComponentScan("com.zhouyu")

@Configuration

@EnableWebMvc

public class AppConfig implements WebMvcConfigurer {

  

@Override

public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();

messageConverter.setDefaultCharset(StandardCharsets.UTF_8);

converters.add(messageConverter);

}

}

  
不过以上四个Converter是不能处理Map对象或User对象的，所以如果返回的是Map或User对象，那么得单独配置一个Converter，比如MappingJackson2HttpMessageConverter，这个Converter比较强大，能把String、Map、User对象等等都能转化成JSON格式。  
  

Java

1

2

3

4

5

6

7

8

9

10

11

12

@ComponentScan("com.zhouyu")

@Configuration

@EnableWebMvc

public class AppConfig implements WebMvcConfigurer {

  

@Override

public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

messageConverter.setDefaultCharset(StandardCharsets.UTF_8);

converters.add(messageConverter);

}

}

  
具体转化的逻辑就是Jackson2的转化逻辑。