<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>房型</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css" />
</head>
<body>
<%@ include file="nav.jsp"%>
<section class="rt_wrap content mCustomScrollbar">
    <div class="rt_content">
        <div class="page_title">
            <h2 class="fl">房型列表</h2>
            <a href="http://localhost:8088/manage/theme/addView" class="fr top_rt_btn add_icon">添加房型</a>
        </div>

        <table class="table table-bordered">
            <thead>
            <tr>

                <th>房型名称</th>
                <th>房型简介</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="showtable">
            </tbody>


        </table>

        <div id="pags">
            <!-- 分页  -->
            <div id="pagination" style="float:left;"></div>
            <div id="showpagecount" style="display: inline-block;margin-left: 20px;height: 35px;line-height: 35px;"></div>
        </div>


    </div>
</section>


<script type="text/javascript" src="/js/jq-paginator.js"></script>
<script>
    function init(){
        $.ajax({
            url:"http://localhost:8088/houseType/list",
            async: false,
            type:"get",
            dataType:"json",
            success:function(result){
                console.log(result);
                var data=result.data;
                //分页插件
                $.jqPaginator('#pagination', {
                    totalCounts:data.total,//总条目数
                    pageSize:10,//每一页的条目数
                    visiblePages:6,//最多显示的页码数
                    currentPage: 1,//当前的页码
                    wrapper:'<ul class="pagination" style="display: inline-block;padding-left: 0; margin: 0;border-radius: 4px;"></ul>',
                    first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                    prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                    next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                    last: '<li class="last"><a href="javascript:void(0);">页尾</a></li>',
                    page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
                    onPageChange: function (pageIndex) {

                        $.ajax({
                            url:"http://localhost:8088/houseType/list",
                            async: false,
                            data:{
                                current:pageIndex,
                                size:10
                            },
                            type:"post",
                            dataType:"json",
                            success:function(result){
                                console.log(result);
                                var data=result.data;
                                totalData = data.total;
                                pageCount = data.pages;
                                var showTableHtml = "";

                                $.each(data.records,function(i,item){
                                    var edtior="<td class='center'><a href='http://localhost:8088/manage/houseType/detailView/"+item.id+"' title='编辑' class='link_icon' >&#101;</a><a  title='删除' class='link_icon'value='"+item.id+"' onclick='del(this)'>&#100;</a></td>";
                                    showTableHtml += "<tr>"+
                                        "<td style=\"padding-left: 20px;\">"+item.houseTypeName+"</td>"+
                                        "<td style=\"padding-left: 20px;\">"+item.houseTypeDetail+"</td>"+
                                        edtior+"</tr>";
                                });

                                $("#showtable").html(showTableHtml);
                                //显示分页数字
                                $("#showpagecount").html("<span> 共"+pageCount+"页，"+totalData+"条</span>");
                            },
                            error:function(){
                                $("#showtable").html("<tr><td style='padding: 160px;text-align: center;' colspan='5'>网络异常...</td></tr>");
                                //隐藏分页div块
                                $("#pags").css("display","none");
                            }
                        });

                    }
                });
            },
            error:function(){
                $("#showtable").html("<tr><td style='padding: 160px;text-align: center;' colspan='5'>网络异常...</td></tr>");
                //隐藏分页div块
                $("#pags").css("display","none");
            }
        });
    }

    $(function() {
        //初始化分页
        init();

    });

    function del(obj) {
        var id=$(obj).attr("value");
        console.log(id);
        if(confirm("确实要删除吗？")){
            $.ajax({
                type:"GET",
                url:"http://localhost:8088/manage/houseType/delete/"+id,
                dataType:"json",
                success:function(obj){
                    if(obj.status==200){
                        alert("删除成功");
                        window.location.href="http://localhost:8088/admin/houseTypelist";
                    }

                }
            })

        }else {
            alert("已经取消了删除操作");
        }

    }
</script>
</body>
</html>
