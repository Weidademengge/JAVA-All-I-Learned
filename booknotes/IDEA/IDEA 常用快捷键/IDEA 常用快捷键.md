
# 1. 类注释模板（必要）

类注释信息中@author等信息是通用的。可以抽取出来自动生成。IDEA允许我们根据需要定制这类文档注释模板。首先我们在左上角file中找到setting，找到Editor中的File and Code Templates，在右侧点击+,添加Class，将下面的内容复制到右侧即可。（把author 名字换掉）

![[Pasted image 20230317135341.png]]

```java
/**  
 ** @author duym  
 * @version $ Id: ${NAME}, v 0.1 ${YEAR}/${MONTH}/${DAY} ${TIME} ${USER} Exp $  
 */public class ${NAME} {  
}
```

# 2. Live Templates（选用）

在setting中，我们可以看到IDEA给我提供的快捷键。
![[Pasted image 20230317135914.png]]

**fori（正序）**

![[1579225422946-2ca257ad-2080-46a9-a8a3-6479790b91ee.gif]]

**forr（倒序，r表示reverse）**
![[1579225838148-b0b74fb6-d881-49a1-9a50-3b3468b9fff5 1.gif]]

上面说的是List，数组同理：

![[1579226055544-394de529-150d-4f5f-9791-690398803aff.gif]]

### 2.1 其他

**main**

![[1579226494104-0ed5db41-76d9-4802-9f9b-13bacefd7c38.gif]]

**psf**

![[1579767538450-825afe3c-c8b6-45e2-8db5-3f0cca814401.gif]]

**ifn**

![[1579226684716-a3489aa4-ebdb-4435-afd0-e59c637ec3ba.gif]]

## 2.2 自定义快捷键

可以看出，快捷键确实能大大加快我们的开发速度，但是IDEA提供的快捷键不一定满足我们的需求，此时可以自定义Live Template。
点击+，可以添加自己想要的快捷键。
![[Pasted image 20230317140514.png]]

按照步骤自定义快捷键
![[Pasted image 20230317141034.png]]

可以看到实现的效果
![[kuaijiejian1.gif]]

如果想要调整光标的顺序，可以点击Edit variables 然后通过上下调整就可以实现。效果如下

![[kuaijiejian2.gif]]


# 3.Eclipse快捷键

由于之前使用的都是Eclipse快捷键，所以在使用IDEA时，选择的快捷键模式还是Eclipse。

![[Pasted image 20230317144904.png]]

下表是Eclipse快捷键。

| 快捷键           | 命令                   |
|:---------------- |:---------------------- |
| alt+r            | 执行                   |
| alt+/            | 补全提示               |
| ctrl+/           | 单行注释               |
| ctrl+shift+/     | 多行注释               |
| ctrl+alt+down    | 向下移动行             |
| ctrl+d           | 删除一行或选中行       |
| alt+down         | 向下移动行             |
| alt+up           | 向上移动行             |
| shift+enter      | 向下开始新的一行       |
| ctrl+shift+enter | 向上开始新的一行       |
| ctrl+单击        | 查看源码               |
| alt+center       | 万能解错               |
| alt+left         | 退回到前一个编辑的页面 |
| alt+right        | 进入到下一个编辑的页面 |
| F4               | 查看继承关系           |
| ctrl+shift+F     | 格式化代码             |
| ctrl+alt+/       | 提示方法参数类型       |
| ctrl+c           | 复制                   |
| ctrl+z           | 撤销                   |
| ctrl+y           | 反向撤销               |
| ctrk+x           | 剪切                   |
| ctrl+v           | 粘贴                   |
| ctrl+s           | 保存                   |
| ctrl+a           | 全选                   |
| tab              | 选中数行，整体往后移动 |
| shift+tab        | 选中数行，整体往前移动 |
| ctrl+o           | 查看类的结构           |
| alt+shift+r      | 重构（rename）         |
| ctrl+shift+y     | 大小写转换             |
| alt+shift+s      | 和右键一样             |
| F2               | 查看文档说明           |
| alt+shift+c      | 收起所有的方法         |
| alt+shift+x      | 打开所有的方法         |
| ctrl+shift+x     | 打开代码所在硬盘文件夹 |
| alt+shift+z      | 生成try-catch          |
| alt+shift+f      | 局部变量抽取为成员变量 |
| ctrl+f           | 查找（当前）           |
| ctrl+h           | 查找（全局）           |
| double Shift     | 查找文件               |
| ctrl+shift+u     | 查看类的继承结构图     |
| ctrl+alt+h       | 查看方法的多层重写结构 |
| alt+shift+m      | 抽取方法               |
| ctrl+E           | 打开最近修改的文件     |
| ctrl+w           | 关闭当前打开的代码栏   |
| ctrl+shift+w     | 关闭打开的所有代码栏   |
| ctrl+shift+q     | 快速搜索类中的错误     |
| ctrl+shift+v     | 选择要粘贴的内容       |
| ctrl+shift+h     | 查找方法在哪里被调用                       |
