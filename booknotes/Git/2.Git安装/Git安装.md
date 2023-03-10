
# 1. 下载git

打开 [https://git-scm.com/download/win](https://git-scm.com/download/win)，

# 2. Git 的安装

## 2.1 使用许可声明

双击下载后的 `Git.exe`，开始安装，这个界面主要展示了 GPL 第 2 版协议[1](https://blog.csdn.net/mukes/article/details/115693833#fn1 "1")的内容，点击 [next] 到第二步。

![[53f97ce2268b4b21b4c56de4ab30750e.png]]

## 2.2 选择安装目录

可点击 “Browse…” 更换目录，也可直接在方框里面改，我一般直接将 “C” 改为 “D:\software\Git”，这样就直接安装在 D 盘里了。点击 next 到第三步。
![[ab24c66c9a514bd4bbd52e320ba3e1ab.png]]
​
## 2.3 选择安装组件

图中这些英文都比较简单，我已经把大概意思翻译出来了，大家根据自己的需要选择勾选。点击next到第四步。
![[f29f77f5497b4d4dbccd67fe5eb388f6 1.png]]
​

我这里选择了默认的： 

![[51ea682ebaa74e269ba940c853a39683 1.png]]​
​
## 2.4 选择开始菜单文件夹

方框内 Git 可改为其他名字，也可点击 “Browse...” 选择其他文件夹或者给"Don't create a Start Menu folder" 打勾不要文件夹，点击 [next] 到第五步。

![[64fcfc5611b84c1fa46fd495f8a5ffe2.png]]​
 ## 2.5 选择 Git 默认编辑器

Git 安装程序里面内置了 10 种编辑器供你挑选，比如 Atom、Notepad、Notepad++、Sublime Text、Visual Studio Code、Vim 等等，默认的是 Vim ，选择 Vim 后可以直接进行到下一步，但是 Vim 是纯命令行，操作有点难度，需要学习。如果选其他编辑器，则还需要去其官网安装后才能进行下一步。

下图为默认编辑器 Vim.可直接点击 [next] 到第六步 

​ ![[4f359a7d844f4a2fa59ef28d332c906e.png]]

## 2.6 决定初始化新项目(仓库)的主干名字

 第一种是让 Git 自己选择，名字是 master ，但是未来也有可能会改为其他名字；第二种是我们自行决定，默认是 main，当然，你也可以改为其他的名字。一般默认第一种，点击 next 到第七步。

 ![[72634b9b036a4ed0b5a9d82f507a9d23.png]]​

    注： 第二个选项下面有个 NEW！ ，说很多团队已经重命名他们的默认主干名为 main . 这是因为2020 年非裔男子乔治·弗洛伊德因白人警察暴力执法惨死而掀起的 Black Lives Matter(黑人的命也是命)运动，很多人认为 master 不尊重黑人，呼吁改为 main.

 ## 2.7 调整你的 path 环境变量

​![[9b41f74caf104c9e9c74ab1462a5bd89.png]]
第一种是仅从 Git Bash 使用 Git。这个的意思就是你只能通过 Git 安装后的 Git Bash 来使用 Git ，其他的什么命令提示符啊等第三方软件都不行。

第二种是从命令行以及第三方软件进行 Git。这个就是在第一种基础上进行第三方支持，你将能够从 Git Bash，命令提示符(cmd) 和 Windows PowerShell 以及可以从 Windows 系统环境变量中寻找 Git 的任何第三方软件中使用 Git。推荐使用这个。

第三种是从命令提示符使用 Git 和可选的 Unix 工具。选择这种将覆盖 Windows 工具，如 “ find 和 sort ”。只有在了解其含义后才使用此选项。一句话，适合比较懂的人折腾。

## 2.8 选择 SSH 执行文件


![[5681bbf93c484f19964a8ecf7c627cdc.png]]

是否选择安全的连接，在这里我们选择是，即默认第一个

## 2.9 选择HTTPS后端传输

![[1431b51174fb45dca253ccd182ab07f3.png]]

也就是说，作为普通用户，只是用 Git 来访问 Github、GitLab 等网站，选择前者就行了。点击 [next] 到第十步。

## 2.10 配置行尾符号转换

![[48c823027a13462ba923725a0a6becd7.png]]

-   [务](https://dev-portal.csdn.net/welcome?utm_source=toolbar)
-   [猿如意](https://devbit.csdn.net?source=csdn_toolbar)

[![](https://profile.csdnimg.cn/0/D/B/2_weixin_47934655)](https://blog.csdn.net/weixin_47934655)

[会员中心 ![](https://img-home.csdnimg.cn/images/20210918025138.gif)](https://mall.csdn.net/vip) 

[足迹](https://i.csdn.net/#/user-center/history)

[动态](https://blink.csdn.net)

[消息](https://i.csdn.net/#/msg/index)

[创作中心](https://mp.csdn.net/ "创作中心")

[发布](https://mp.csdn.net/edit)

# Git 详细安装教程（保姆级详细教程）

![](https://csdnimg.cn/release/blogv2/dist/pc/img/original.png)

[Siobhan. 明鑫](https://blog.csdn.net/weixin_46474921 "Siobhan. 明鑫") ![](https://csdnimg.cn/release/blogv2/dist/pc/img/newUpTime2.png) 已于 2022-09-29 17:16:34 修改 ![](https://csdnimg.cn/release/blogv2/dist/pc/img/articleReadEyes2.png) 18525 ![](https://csdnimg.cn/release/blogv2/dist/pc/img/tobarCollect2.png) 收藏 137

分类专栏： [软件、工具安装调试](https://blog.csdn.net/weixin_46474921/category_11406565.html) [Git学习、安装教程](https://blog.csdn.net/weixin_46474921/category_12033567.html) 文章标签： [git](https://so.csdn.net/so/search/s.do?q=git&t=all&o=vip&s=&l=&f=&viparticle=) [github](https://so.csdn.net/so/search/s.do?q=github&t=all&o=vip&s=&l=&f=&viparticle=)

版权

 [![](https://img-blog.csdnimg.cn/cf8c071d6a2e487da981ab6a507b77e5.jpg?x-oss-process=image/resize,m_fixed,h_224,w_224) 软件、工具安装调试 同时被 2 个专栏收录![](https://csdnimg.cn/release/blogv2/dist/pc/img/newArrowDown1White.png)](https://blog.csdn.net/weixin_46474921/category_11406565.html "软件、工具安装调试")

8 篇文章 0 订阅

订阅专栏

 [![](https://img-blog.csdnimg.cn/20201014180756930.png?x-oss-process=image/resize,m_fixed,h_64,w_64) Git学习、安装教程](https://blog.csdn.net/weixin_46474921/category_12033567.html "Git学习、安装教程")

5 篇文章 1 订阅

订阅专栏

**目录**

[1. 下载git](https://blog.csdn.net/weixin_46474921/article/details/127091723#t0)

[2. Git 的安装](https://blog.csdn.net/weixin_46474921/article/details/127091723#t1)

[2.1 使用许可声明](https://blog.csdn.net/weixin_46474921/article/details/127091723#t2)

[2.2 选择安装目录](https://blog.csdn.net/weixin_46474921/article/details/127091723#t3)

 [2.3 选择安装组件](https://blog.csdn.net/weixin_46474921/article/details/127091723#t4)

[2.4 选择开始菜单文件夹](https://blog.csdn.net/weixin_46474921/article/details/127091723#t5)

[2.5 选择 Git 默认编辑器](https://blog.csdn.net/weixin_46474921/article/details/127091723#t6)

[2.6 决定初始化新项目(仓库)的主干名字](https://blog.csdn.net/weixin_46474921/article/details/127091723#t7)

[2.7 调整你的 path 环境变量](https://blog.csdn.net/weixin_46474921/article/details/127091723#t8)

[2.8 选择 SSH 执行文件](https://blog.csdn.net/weixin_46474921/article/details/127091723#t9)

[2.9 选择HTTPS后端传输](https://blog.csdn.net/weixin_46474921/article/details/127091723#t10)

[2.10 配置行尾符号转换](https://blog.csdn.net/weixin_46474921/article/details/127091723#t11) 

[2.11 配置终端模拟器以与 Git Bash 一起使用](https://blog.csdn.net/weixin_46474921/article/details/127091723#t12) 

[2.12 选择默认的 “git pull” 行为](https://blog.csdn.net/weixin_46474921/article/details/127091723#t13)

[2.13 选择一个凭证帮助程序](https://blog.csdn.net/weixin_46474921/article/details/127091723#t14) 

[2.14 配置额外的选项](https://blog.csdn.net/weixin_46474921/article/details/127091723#t15) 

[2.15 配置实验性选项](https://blog.csdn.net/weixin_46474921/article/details/127091723#t16)

---

# 1. 下载git

打开 [git官网] [Git](https://git-scm.com/ "Git")，下载git对应操作系统的版本。

所有东西下载慢的话就可以去找镜像！

官网下载太慢，我们可以使用淘宝镜像下载：[CNPM Binaries Mirror](http://npm.taobao.org/mirrors/git-for-windows/ "CNPM Binaries Mirror")

![](https://img-blog.csdnimg.cn/img_convert/f9e7fc3868f802dfd5c33aa667ada863.jpeg)​

![](https://img-blog.csdnimg.cn/7724fc4217d742759dad8f84c02b1ff5.png)​

![](https://img-blog.csdnimg.cn/534702af4c7649ce8141e09870ec5a63.png)​

# 2. Git 的安装

## 2.1 使用许可声明

双击下载后的 `Git-2.37.3-64-bit.exe`，开始安装，这个界面主要展示了 GPL 第 2 版协议[1](https://blog.csdn.net/mukes/article/details/115693833#fn1 "1")的内容，点击 [next] 到第二步。

![](https://img-blog.csdnimg.cn/53f97ce2268b4b21b4c56de4ab30750e.png)​ 

## 2.2 选择安装目录

可点击 “Browse…” 更换目录，也可直接在方框里面改，我一般直接将 “C” 改为 “D:\software\Git”，这样就直接安装在 D 盘里了。点击 [next] 到第三步。

![](https://img-blog.csdnimg.cn/ab24c66c9a514bd4bbd52e320ba3e1ab.png)​

##  2.3 选择安装组件

图中这些英文都比较简单，我已经把大概意思翻译出来了，大家根据自己的需要选择勾选。点击 [next] 到第四步。

![](https://img-blog.csdnimg.cn/f29f77f5497b4d4dbccd67fe5eb388f6.png)​

我这里选择了默认的： 

 ![](https://img-blog.csdnimg.cn/51ea682ebaa74e269ba940c853a39683.png)​

 **`注：最后一个选项打勾的话，需要下载 Windows Terminal 配合 Git Bash使用`，如图：**

![](https://img-blog.csdnimg.cn/0a80a27cbfcf4086b06f7c496ae16e8f.png)​

## 2.4 选择开始菜单文件夹

方框内 Git 可改为其他名字，也可点击 “`Browse...`” 选择其他文件夹或者给"`Don't create a Start Menu folder`" 打勾不要文件夹，点击 [next] 到第五步。

![](https://img-blog.csdnimg.cn/64fcfc5611b84c1fa46fd495f8a5ffe2.png)​

##  2.5 选择 Git 默认编辑器

Git 安装程序里面内置了 10 种编辑器供你挑选，比如 Atom、Notepad、Notepad++、Sublime Text、Visual Studio Code、Vim 等等，默认的是 Vim ，选择 Vim 后可以直接进行到下一步，但是 Vim 是纯命令行，操作有点难度，需要学习。如果选其他编辑器，则还需要去其官网安装后才能进行下一步。

下图为默认编辑器 `Vim`.可直接点击 [next] 到第六步 

![](https://img-blog.csdnimg.cn/4f359a7d844f4a2fa59ef28d332c906e.png)​ 

如果你不想用 `Vim` 当默认编辑器，换一个，比如 `Notepad++` ，那么你则需要点击下面的蓝色字体 " Notepad++ " 去其官网下载安装好才能进行下一步 [next].

在这里，我选择使用notepad++

![](https://img-blog.csdnimg.cn/d19f8a2fa88a45be8c7c48a4c0ab5e7a.png)​ 

安装后还要配置在我的电脑->属性->高级系统设置->高级->环境变量->系统变量->Path->编辑添加 Notepad++ 的安装地址，如 C:\Program Files\notepad++.  
这样才能在 Git Bash 里面直接调用 Notepad++.

$ notepad++ 文件名.后缀  //在 git bash 调用 notepad++ 打开文件  
  
新手建议使用 Notepad++ 、Sublime Text，这两个比 Windows 自带的记事本功能多太多了。点击 [next] 到第六步。

## 2.6 决定初始化新项目(仓库)的主干名字

 第一种是让 Git 自己选择，名字是 master ，但是未来也有可能会改为其他名字；第二种是我们自行决定，默认是 main，当然，你也可以改为其他的名字。一般默认第一种，点击 [next] 到第七步。

 ![](https://img-blog.csdnimg.cn/72634b9b036a4ed0b5a9d82f507a9d23.png)​

> 注： 第二个选项下面有个 NEW！ ，说很多团队已经重命名他们的默认主干名为 main . 这是因为2020 年非裔男子乔治·弗洛伊德因白人警察暴力执法惨死而掀起的 Black Lives Matter(黑人的命也是命)运动，很多人认为 master 不尊重黑人，呼吁改为 main.

##  2.7 调整你的 path 环境变量

![](https://img-blog.csdnimg.cn/9b41f74caf104c9e9c74ab1462a5bd89.png)​

翻译如下：

```cobol
Use Git from Git Bash only This is the most cautious choice as your PATH will not be modified at all. You w only be able to use the Git command line tools from Git Bash.仅从 Git Bash 使用 Git这是最谨慎的选择，因为您的 PATH 根本不会被修改。您将只能使用 Git Bash 中的 Git 命令行工具。  Git from the command line and also from 3rd-party software(Recommended) This option adds only some minimal Git wrappers to your PATH to avoid cluttering your environment with optional Unix tools.You will be able to use Git from Git Bash, the Command Prompt and the Windov PowerShell as well as any third-party software looking for Git in PATH.从命令行以及第三方软件进行 Git（推荐）此选项仅将一些最小的 Git 包装器添加到PATH中，以避免使用可选的 Unix 工具使环境混乱。您将能够使用 Git Bash 中的 Git，命令提示符和 Windov PowerShell 以及在 PATH 中寻找 Git 的任何第三方软件。  Use Git and optional Unix tools from the Command Prompt Both Git and the optional Unix tools will be added to your PATH.Warning: This will override Windows tools like "find"and "sort". Only use this option if you understand the implications.使用命令提示符中的 Git 和可选的 Unix 工具Git 和可选的 Unix 工具都将添加到您的 PATH 中。警告：这将覆盖 Windows 工具，例如 "find" and "sort". 仅在了解其含义后使用此选项。 
```

第一种是仅从 Git Bash 使用 Git。这个的意思就是你只能通过 Git 安装后的 Git Bash 来使用 Git ，其他的什么命令提示符啊等第三方软件都不行。

第二种是从命令行以及第三方软件进行 Git。这个就是在第一种基础上进行第三方支持，你将能够从 Git Bash，命令提示符(cmd) 和 Windows PowerShell 以及可以从 Windows 系统环境变量中寻找 Git 的任何第三方软件中使用 Git。**推荐使用这个。**

第三种是从命令提示符使用 Git 和可选的 Unix 工具。选择这种将覆盖 Windows 工具，如 “ find 和 sort ”。只有在了解其含义后才使用此选项。一句话，适合比较懂的人折腾。

## 2.8 选择 SSH 执行文件

 ![](https://img-blog.csdnimg.cn/5681bbf93c484f19964a8ecf7c627cdc.png)​

翻译如下：

```cobol
Use bundled OpenSSH This uses ssh. exe that comes with Git.使用捆绑的 OpenSSH这使用的 ssh.exe 是 Git 自带的   Use external OpenSSH NEW! This uses an external ssh. exe. Git will not install its own OpenSSH(and related) binaries but use them as found on the PATH.使用外部 OpenSSH新！这使用外部 ssh.exe 文件。 Git 不会安装自己的 OpenSSH（和相关）二进制文件，而是使用在环境变量 PATH 中找到的它们。
```

 是否选择安全的连接，在这里我们选择是，即默认第一个

## 2.9 选择HTTPS后端传输

![](https://img-blog.csdnimg.cn/1431b51174fb45dca253ccd182ab07f3.png)​ 

翻译如下：

```cobol
use the OpenSSL library Server certificates will be validated using the ca-bundle. crt file.使用 OpenSSL 库服务器证书将使用 ca-bundle.crt 文件进行验证。	Use the native Windows Secure Channel library Server certificates will be validated using Windows Certificate Stores.This option also allows you to use your company's internal Root CA certificates distributed e.g. via Active Directory Domain Services.使用本机 Windows 安全通道库服务器证书将使用 Windows 证书存储进行验证。此选项还允许您使用公司内部分发的内部根 CA 证书，例如通过 Active Directory 域服务。
```

这两种选项有什么区别呢？

来自https://stackoverflow.com/questions/62456484/whats-the-difference-between-openssl-and-the-native-windows-secure-channel-libr

> 如果在具有企业管理证书的组织中使用 Git，则将需要使用安全通道。如果你仅使用 Git 来访问公共存储库（例如 GitHub ），或者你的组织不管理自己的证书，那么使用 SSL 后端（它们只是同一协议的不同实现）就可以了。

也就是说，作为普通用户，只是用 Git 来访问 Github、GitLab 等网站，选择前者就行了。点击 [next] 到第十步。

## 2.10 配置行尾符号转换 

![](https://img-blog.csdnimg.cn/48c823027a13462ba923725a0a6becd7.png)

这三种选择分别是：  
签出 Windows 样式，提交 Unix 样式的行结尾。  
按原样签出，提交Unix样式的行结尾。  
按原样签出，按原样提交。

## 2.11 配置终端模拟器以与 Git Bash 一起使用

![[9f3f46773cda4120a0b081060791bf64.png]]

建议选择第一种，MinTTY 3功能比 cmd 多，cmd 只不过 比 MinTTY 更适合处理 Windows 的一些接口问题，这个对 Git 用处不大，除此之外 Windows 的默认控制台窗口（cmd）有很多劣势，比如 cmd 具有非常有限的默认历史记录回滚堆栈和糟糕的字体编码等等。
相比之下，MinTTY 具有可调整大小的窗口和其他有用的可配置选项，可以通过右键单击的工具栏来打开它们 git-bash 。点击 [next] 到第十二步。

## 2.12 选择默认的 “git pull” 行为

![[14f54faf11c5473193cd2e12dcac8c74.png]]

上面给了三个 “git pull” 的行为：
第一个是 merge
第二个是 rebase
第三个是 直接获取

第一种 git pull = git fetch + git merge
第二种 git pull = git fetch + git rebase
第三种 git pull = git fetch ？(这个没试过，纯属猜测

## 2.13 选择一个凭证帮助程序

![[2dfa6e6575714e9882ffbce424240c16.png]]

一共两个选项：  
`Git 凭证管理`  
`不使用凭证助手`

第一个选项是提供`登录凭证`帮助的，Git 有时需要用户的凭据才能执行操作；例如，可能需要输入`用户名`和`密码`才能通过 HTTP 访问远程存储库（GitHub，GItLab 等等）。

## 2.14 配置额外的选项

![[9897a0cdccaa452d9a106cff8a10496c.png]]

选第一个会快一点

## 2.15 配置实验性选项

![[4e0bda155f234504bad44f24a9831303.png]]

这是实验性功能，可能会有一些小错误之类的，建议不用开启。  
点击 [install] 进行安装。