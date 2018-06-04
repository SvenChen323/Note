备忘录
====
1.基础功能
---
	1.1显示时间戳
	
![](https://github.com/SvenChen323/Note/blob/master/screen/1.png)
![](https://github.com/SvenChen323/Note/blob/master/screen/2.png)
<br>
通过修改数据库数据信息，添加一个修改时间的long字段
添加了一个TextView显示时间戳
	
	1.2搜索功能
![](https://github.com/SvenChen323/Note/blob/master/screen/3.png)
<br>
	
可根据title进行搜索
使用了toolbar + searchview + recyclerview 实现搜索功能
	
	
2.附加功能
----
	2.1美化UI
	
      使用了 DialogFragment + Recyclerview + 自定义监听器
	
	2.2一键换肤
	
       通过设置多个主题（Theme），使用代码动态改变主题颜色实现一键换肤
	
	2.3数据备份

        点击数据备份  数据库db文件会保存到手机内存中

        删除所有数据后可点击恢复数据 从保存到手机内存中的db文件恢复数据。
      
        使用SQLite对数据的增删改查，然后通过读写手机内存，把要备份的db文件写到手机内存中
	
        恢复数据时再次把db文件读回到数据库中，从而实现数据备份和恢复

        

![](https://github.com/SvenChen323/Note/blob/master/screen/4.png)
![](https://github.com/SvenChen323/Note/blob/master/screen/5.png)
![](https://github.com/SvenChen323/Note/blob/master/screen/6.png)
	
	



<br>


<br>

	
	



	
	
	
	
	

	

	
