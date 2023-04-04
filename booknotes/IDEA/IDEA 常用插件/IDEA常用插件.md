# 1. Tabnine AI Code（已安装）

基于大数据的代码提示插件

# 2. Alibaba Java Coding Guidelines（已安装）

阿里出品的《Java 开发手册》时下已经成为了很多公司新员工入职必读的手册，前一段阿里发布了《Java 开发手册(终极版)》， 又一次对 Java 开发规范做了完善。不过，又臭又长的手册背下来是不可能的，但集成到 IDEA 开发工具中就方便很多。

# 3. Java Stream Debugger（IDEA已集成）

Java8 的 Stream API 很大程度的简化了我们的代码量，可在使用过程中总会出现奇奇怪怪的问题Java Stream Debugger 支持了对 Stream API 的调试，可以清晰的看到每一步操作数据的变化过程。

# 4. Easy javadoc（已安装）

Easy javadoc一个可以快速为Java的类、方法、属性加注释的插件，还支持自定义注释样式，IDEA 身的Live Templates 也支持，不过操作稍显繁琐，使用时效率不太高。在为类、方法、属性加注释时，不仅会生成注释，还是会将对应变量、类、方法翻译成中文名，不过翻译的怎么样还要取决于你的命名水平。

快捷键：crtl + \ 

快捷键：crtl + shift + \

# 5. Key promoter X（已安装）

Key promoter X 是 IDEA 的快捷键提示插件，这是我个人非常喜欢的一个功能，它让我快速的记忆了很多操作的快捷键。当你点击某个功能且该功能有快捷键时，会提示当前操作的快捷方式。

# 6. Translation（已安装）

Translation一个很方便的翻译插件，比如选中代码、控制台的报错信息可直接翻译。

![](https://aliyuque.antfin.com/api/filetransfer/images?url=https%3A%2F%2Flang-image-bed.oss-cn-hangzhou.aliyuncs.com%2F20230303133902.png&sign=ed5852ac11c7f348b7127574ea6b6b9fb609012b76b4090f7f3c5a844c2113df)

![](https://aliyuque.antfin.com/api/filetransfer/images?url=https%3A%2F%2Flang-image-bed.oss-cn-hangzhou.aliyuncs.com%2F20230303133919.png&sign=7163dda12c0966f8efcd115146575e14b50fc294d79ededff0f25af31518589a)

# 7. Git Auto Pull

团队多人开发项目时，由于频繁提交代码，等我在 commit 本地代码的时必须先进行 pull，否则就会代码冲突产生 merge 记录。

Git Auto Pull 插件帮我们在 push 前先进行 pull，避免了不必要的代码冲突。

# 8. String Manipulation

String Manipulation 一个比较实用的字符串转换工具，比如我们平时的变量命名可以一键转换驼峰等格式，还支持对字符串的各种加、解密（MD5、Base64 等）操作。

快捷键：alt + m

# 9. .ignore

当我们在向 github 提交代码时，有一些文件不希望一并提交，这时候我们可以创建一个 .gitignore 文件来忽略某些文件的提交。

也可以添加指定文件到 .gitignore 中，被忽略的文件将变成灰色。

# 10. GenerateAllSetter（已安装）

实际的开发中，可能会经常为某个对象中多个属性进行 set 赋值，尽管可以用BeanUtil.copyProperties() 方式批量赋值，但这种方式有一些弊端，存在属性值覆盖的问题，所以不少场景还是需要手动 set。如果一个对象属性太多 set 起来也很痛苦，GenerateAllSetter 可以一键将对象属性都 set 出来。

快捷键：Alt+Enter

# 11. GsonFormat

GsonFormat个人觉得是一个非常非常实用的插件，它可以将 JSON 字符串自动转换成 Java 实体类。特别是在和其他系统对接时，往往以 JSON 格式传输数据，而我们需要用 Java 实体接收数据入库或者包装转发，如果字段太多一个一个编写那就太麻烦了。

快捷键：Alt+ S

# 12. Maven Helper（已安装）（强烈建议）

Maven Helper 是解决 Maven 依赖冲突的利器，可以快速查找项目中的依赖冲突。安装后打开 pom 文件，底部有 Dependency Analyzer 视图。显示红色表示存在依赖冲突，点进去直接在包上右键Exclude 排除，pom 文件中会做出相应排除包的操作。

装上Maven Helper后，点击Dependency Analyzer后可以看到冲突的依赖。我们可以在冲突的依赖中把其中一个依赖排除。
![[Pasted image 20230317111729.png]]

- Conflicts(冲突)
- All Dependencies as List(列表形式查看所有依赖)
- All Dependencies as Tree(树结构查看所有依赖)，并且这个页面还支持搜索。

# 13. Jadx Class Decompiler（已安装）

Java 反编译器和字节码分析器

# 14. Lombok（项目中不建议）

Lombok 插件应该比较熟，它替我们解决了那些繁琐又重复的代码，比如Setter、Getter、toString、equals等方法。

# 15. Material Theme UI（已安装，需考虑）

使用插件后界面图标样式都会变的很漂亮。

# 16. Grep Console（已安装）(建议，不装就得一顿找输出日志)

IDEA控制台，过滤日志、给不同级别的日志或者给不同pattern的日志加上背景颜色与上层颜色

# 17. JUnitGenerator V2.0（IDEA已集成）
单元测试生成工具

# 18. POJO to JSON（已安装）

根据Java对象生成JSON字符串

# 19. GenerateSerialVersionUID（已安装）

生成serialVersionUID

# 20. CamelCase（已安装）

驼峰转换快捷键：shift + option + u

# 21. MyBatis Log Free

可执行SQL

# 22. MybatisX 快速开发插件（已安装）

MybatisX 是一款基于 IDEA 的快速开发插件，为效率而生。

功能：
- Java 与 XML 调回跳转 
- Mapper 方法自动生成XML

# 23.QAPlug（已安装）

![[Pasted image 20230317111045.png]]
![](https://aliyuque.antfin.com/api/filetransfer/images?url=https%3A%2F%2Flang-image-bed.oss-cn-hangzhou.aliyuncs.com%2F202303141833960.png&sign=14602d2e5785fa151a7270072a4b5004b2a9ad249b3de5173dba58c67960b51a)

- CheckStyle：检查Java源文件是否与代码规范相符
- FindBugs：检查Java编译文件中的潜在问题
- PMD：检查Java源文件中的潜在问题

# 24. CheckStyle

CheckStyle（官网：http://checkstyle.sourceforge.net/）是SourceForge下的一个项目，提供了一个帮助JAVA开发人员遵守某些编码规范的工具。它能够自动化代码规范检查过程，从而使得开发人员从这项重要但是枯燥的任务中解脱出来。CheckStyle检验的主要内容：
- Javadoc注释
- 命名约定
- 标题
- Import语句
- 体积大小
- 空白
- 修饰符
- 块
- 代码问题
- 类设计
- 混合检查

# 25. FindBugsFindBugs

（官网：http://findbugs.sourceforge.net/）是一个能静态分析源代码中可能会出现Bug 的静态分析工具，它检查类或者 JAR  文件，将字节码与一组缺陷模式进行对比以发现可能的问题。有了静态分析工具，就可以在不实际运行程序的情况对软件进行分析。不是通过分析类文件的形式或结构来确定程序的意图，而是通常使用 Visitor 模式。
- FindBugs详细规则[参见](http://findbugs.sourceforge.net/bugDescriptions.html)
- FindBugs中文解释[参见](http://blog.csdn.net/jdsjlzx/article/details/21472253/)

# 26. PMDPMD

（官网：https://pmd.github.io/）是一款采用BSD协议发布的Java程序代码检查工具，扫描java源代码，查找潜在的问题，如：
- 可能的bugs，如空的 try/catch/finally/switch 声明
- 死亡的代码，没有使用的本地变量，参数和私有方法
- 不合标准的代码，如 String/StringBuffer 用法
- 过于复杂的表达式，如不必要的if表达式
- 重复的代码，拷贝、粘贴的代码

​