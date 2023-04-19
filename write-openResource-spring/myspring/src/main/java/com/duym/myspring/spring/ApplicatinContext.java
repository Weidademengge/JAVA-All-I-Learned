package com.duym.myspring.spring;

import lombok.extern.slf4j.Slf4j;

import java.beans.Introspector;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author duym
 * @version $ Id: ApplicatinContext, v 0.1 2023/04/17 13:16 duym Exp $
 */
@Slf4j
public class ApplicatinContext {


    private Class configClass;

    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();

    private ArrayList<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();


    public ApplicatinContext(Class configClass) {
        this.configClass = configClass;

        // 扫描----->BeanDefinition-->beanDefinitionMap
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);

            // 扫描路径 com.duym.myspring.service
            String path = componentScanAnnotation.value();
            log.debug("path:{}",path);

            // com/duym/myspring/service
            path = path.replace(".", "/");

            ClassLoader classLoader = ApplicatinContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
//            log.debug("resource:{}",resource);

            File file = new File(resource.getFile());
//            log.debug("file:{}",file);
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for (File f:files){
                    String fileName = f.getAbsolutePath();
//                    log.debug("fileName,{}",fileName);

                    if(fileName.endsWith(".class")){
                        //com\duym.myspring\USerService.class
                        String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
//                        log.debug("className:{}",className);

                        className = className.replace("\\",".");

                        try {
                            Class<?> clazz = classLoader.loadClass(className);
                            if(clazz.isAnnotationPresent(Component.class)){
                                if(BeanPostProcessor.class.isAssignableFrom(clazz)){
                                    BeanPostProcessor instancce = (BeanPostProcessor) clazz.newInstance();
                                    beanPostProcessorList.add(instancce);
                                }
                                Component component = clazz.getAnnotation(Component.class);
                                String beanName = component.value();

                                // 没名给名
                                if(beanName.equals("")){
                                    beanName = Introspector.decapitalize(clazz.getSimpleName());
                                }

                                // BeanDefinition
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(clazz);

                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scopeAnnotation.value());
                                }else{
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitionMap.put(beanName,beanDefinition);

                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        // 实例化单例Bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

            if(beanDefinition.getScope().equals("singleton")){
                Object bean = createBean(beanName,beanDefinition);
                singletonObjects.put(beanName,bean);
            }
        }

    }


    private Object createBean(String beanName,BeanDefinition beanDefinition){

        Class clazz = beanDefinition.getType();

        try {
            Object instance = clazz.getConstructor().newInstance();
            
            //依赖注入
            for (Field f : clazz.getDeclaredFields()) {
                if(f.isAnnotationPresent(Autowired.class)){
                    f.setAccessible(true);
                    f.set(instance,getBean(f.getName()));
                }
            }
            // Aware回调
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware)instance).setBeanName(beanName);
            }

            for(BeanPostProcessor beanPostProcessor:beanPostProcessorList){
                instance = beanPostProcessor.postProcessBeforeInitialization(beanName,instance);
            }

            // 初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean)instance).afterPropertiesSet();
            }

            for(BeanPostProcessor beanPostProcessor:beanPostProcessorList){
                instance = beanPostProcessor.postProcessAfterInitialization(beanName, instance);
            }
            // BeanPostProcessor 初始化后 AOP

            return instance;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object getBean(String beanName){

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        if(beanDefinition == null){
            throw new NullPointerException();
        }else{
            String scope = beanDefinition.getScope();
            if (scope.equals("singleton")) {
                Object bean = singletonObjects.get(beanName);
                if(bean == null){
                    Object o = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName,o);
                }
                return bean;
            }else{
                return createBean(beanName,beanDefinition);
            }
        }
    }
}
