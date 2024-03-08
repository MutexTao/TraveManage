<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>后台管理系统</title>
    <meta name="author" content="DeathGhost" />
    <link rel="stylesheet" href="/css/style.css">
    <!--[if lt IE 9]>
    <script src="/js/html5.js"></script>
    <![endif]-->
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
    <script>

        (function($){
            $(window).load(function(){

                $("a[rel='load-content']").click(function(e){
                    e.preventDefault();
                    var url=$(this).attr("href");
                    $.get(url,function(data){
                        $(".content .mCSB_container").append(data); //load new content inside .mCSB_container
                        //scroll-to appended content
                        $(".content").mCustomScrollbar("scrollTo","h2:last");
                    });
                });

                $(".content").delegate("a[href='top']","click",function(e){
                    e.preventDefault();
                    $(".content").mCustomScrollbar("scrollTo",$(this).attr("href"));
                });

            });
        })(jQuery);
    </script>
</head>
<body>
<header>
    <h1><img src="/images/admin_logo.png"/></h1>
    <ul class="rt_nav">
        <li><a href="${pageContext.request.contextPath}/indexView" target="_blank" class="website_icon">站点首页</a></li>

        <li><a href="${pageContext.request.contextPath}/adminLoginView" class="quit_icon">安全退出</a></li>
    </ul>
</header>
<!--aside nav-->
<!--aside nav-->
<aside class="lt_aside_nav content mCustomScrollbar">
    <h2><a href="http://localhost:8088/admin/index">起始页</a></h2>
    <ul>
        <li>
            <dl>
                <dt>商品信息</dt>
                <!--当前链接则添加class:active-->
                <dd><a href="http://localhost:8088/manage/product/listView" class="active">商品列表</a></dd>
                <dd><a href="http://localhost:8088/manage/product/addView">添加商品</a></dd>
            </dl>
        </li>
        <li>
            <dl>
                <dt>房型信息</dt>
                <dd><a href="http://localhost:8088/admin/houseTypelist">房型列表</a></dd>
                <dd><a href="http://localhost:8088/manage/houseType/addView">添加房型</a></dd>
            </dl>
        </li>
        <li>
            <dl>
                <dt>主题信息</dt>
                <dd><a href="http://localhost:8088/admin/themelist">主题列表</a></dd>
                <dd><a href="http://localhost:8088/manage/theme/addView">添加主题</a></dd>
            </dl>
        </li>
        <li>
            <dl>
                <dt>用户管理</dt>
                <dd><a href="http://localhost:8088/admin/userlist">用户列表</a></dd>

            </dl>
        </li>
        <li>
            <dl>
                <dt>定制信息</dt>
                <dd><a href="http://localhost:8088/admin/cuslist">定制信息列表</a></dd>
            </dl>
        </li>
        <li>
            <dl>
                <dt>订单信息</dt>
                <dd><a href="http://localhost:8088/manage/productOrder/listView">订单列表</a></dd>

            </dl>
        </li>



    </ul>
</aside>

</body>
</html>


