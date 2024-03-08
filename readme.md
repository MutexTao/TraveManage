系统安装说明
1、使用idea导入traveManage项目
2、在mysql中新建名为db_travel的数据库并导入sql文件
3、idea中安装并配置tomcat,URL设置为http://localhost:8088/,idea中添加maven，导入traveManage\pom.xml,将项目SDK设置为1.8（java version “1.8.0_111”）
4、安装nginx服务器并运行
5、项目中修改数据库账号与密码为该电脑的，分别需要修改traveManage/src/main/java/com/halfsummer/util/RecommendUtil.java和traveManage/src/main/resources/properties/db.properties
6、启动项目，首页地址为localhost:8088/indexView，后台地址为localhost:8088/adminLoginView

注:在traveManage/src/main/java/com/halfsummer/util/FtpUtil.java中设置的上传和下载文件配置需要与nginx服务器配置相同，且打开页面前需要启动nginx服务器，否则首页图片不显示
