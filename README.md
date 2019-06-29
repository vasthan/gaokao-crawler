## 1. 建库
首先需要有一个MySQL数据库
把项目里的[mybatis-config.xml](https://github.com/vasthan/gaokao-crawler/blob/master/src/main/resources/mybatis-config.xml)数据库连接信息改成你自己的

## 2. 建表
执行database文件夹下的[table.sql](https://github.com/vasthan/gaokao-crawler/blob/master/database/table.sql)即可

## 3. 导入项目到IDE
依赖比较少，应该不会有什么环境问题

## 4. 执行
运行App.main()方法。等待执行结束，就可以去数据库里面分析数据了😋

## 声明
数据来源：[高考数据库](https://gkcx.eol.cn/)，对于数据的准确性请自行考量。
