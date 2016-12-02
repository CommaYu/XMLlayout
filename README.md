# Andorid中关于布局XML优化的一些问题
XML性能优化问题
要实现一个好的布局，不只是实现了、显示出来就完了，不管层次，堆砌代码也可以实现功能，
但是这显然违背了Android布局设计的原则。可能你会说，Android布局设计哪有什么原则，
我可以明确告诉你，当然有，只要有利于提高最终效果的方法、意识，我们都可以把它提升为原则。
在Android布局设计中，这个最终效果就是快的页面加载速度，好的流畅度，而这个方法就太多了。

# 出现问题

嵌套层次过甚，导致栈溢出。加载比较慢，点进去，老半天才能进去。

# 解决办法

无法提高栈的大小，那么就只能提高栈的使用率

- 缩减层级（提高页面加载速度）因为Android中的布局是嵌套加载的，多一层布局就要耗费很长的加载时间。

- Hierarchy View层级视图工具讲述merge、ViewStub、include在布局优化中的作用
  关于Hierarchy View层级视图工具中各个参数介绍：http://blog.csdn.net/xyz_lmn/article/details/14222975

# 从性能上优化XML布局

上面说了很多概念和理论性的东西，下面带大家看些实际性的东西

1.使用LinearLayoutCompat，实现线性布局元素之间的分割线，减少使用View实现分割线效果。（详细见test_linearlayoutcompat.xml 这个需要在高版本Android才可以用）
2.使用style主题，定义通用属性，减少重复利用代码，减少代码量。（详见costomstyle.xml）
3.使用TextView替换ImageView，如果一个图片和文字在一起，我们直接用一个TextView代替本来需要一个tv和iv才能做出来的布局，这样大大减少了布局的复杂性，减少了嵌套就可以加快加载xml速度
（1.图片和文字的距离用这个_android:drawablePadding=""_属性控制 2.TextView用selector点击背景要改变，必须要有android:clickable="true")
4.使用 include 标签加载底部菜单栏布局，include 标签的目的是重复利用布局，来减少代码了。
5.懒得加载布局ViewStub，有些布局是仅在需要时才加载，比如小米手机的添加联系人功能就有在编辑姓名的时候有一个下拉按钮显示更多输入信息。
遇到这种情况，我们首先想到的 就是将不常用的元素使用INVISIBLE或者GONE进行隐藏，这样是否真的好呢？是否达到了 布局优化的最终效果呢？
利用 INVISIBLE只是隐藏布局，但是布局还是占居当前位置，且系统在加载布局的时候这一部分还是会绘制出来，同样花费绘制时间。
那么有没有好的办法来解决这一问题呢？不言而喻，我们可以使用懒加载布局 ViewStub。
为什么用ViewStub？
ViewStub是Android为此提供了一种非常轻量级的控件。ViewStub虽说也是View的一种，但是它没有大小，没有绘制功能，也不参与布局，资源消耗非常低，将它放置在布局当中基本可以认为是完全不会影响性能的。
（以前我经常用的是android:visibility="invisible/gone"来设置，需要的时候就在java中用setVisibility(View.VISIBLE)这么做的）
（如果看过Hierarchy View，就能够知道貌似有个地方是专门放这些仅在需要的时候才加载的布局，用用那个软件就知道了）
重点：要学会ViewStub的隐藏和显示，查查inflate有什么局限。（只能inflate一次，再次inflate会报错）
6.Android Lint 工具去除不用的代码
