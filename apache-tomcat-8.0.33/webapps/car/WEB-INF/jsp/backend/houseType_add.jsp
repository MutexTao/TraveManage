<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <h2 class="fl">房型信息</h2>
        </div>
        <form class="form-horizontal" id="form1">
            <div class="form-group">
                <label  class="col-sm-2 control-label">房型名称</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="houseTypeName" name="houseTypeName" placeholder="houseTypeName" value="${houseType.houseTypeName}">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">房型简介</label>
                <div class="col-sm-4">
                    <textarea class="form-control" rows="3" id="houseTypeDetail" name="houseTypeDetail">${houseType.houseTypeDetail}</textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">

                    <c:choose>
                        <c:when test="${empty houseType}">
                            <button type="button" class="btn btn-default" id="sub" >保存</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button"  class="btn btn-default" id="update" >修改</button>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>

        </form>
    </div>
</section>
<script>

    $("#sub").click(function(){
        var houseTypeName = $("#houseTypeName").val();//取值
        var houseTypeDetail = $("#houseTypeDetail").val();
        if(!houseTypeName){
            alert("名称必填!");
            $("#houseTypeName").focus();//获取焦点
            return false;
        }
        if(!houseTypeDetail){
            alert("简介必填!");
            $("#houseTypeDetail").focus();//获取焦点
            return false;
        }
        var data = $("#form1").serialize();
        var targetUrl="http://localhost:8088/manage/houseType/save";
        $.ajax({
            type:'post',
            url:targetUrl,
            cache: false,
            data:data,
            dataType:'json',
            success:function(result){
                console.log(result);
                if(result.status==200){
                    alert("保存成功");
                     window.location.href="http://localhost:8088/admin/houseTypelist";
                }

            },
            error:function(error){

                console.log(error);

            }
        })


    })

    $("#update").click(function(){
        var houseTypeName = $("#houseTypeName").val();//取值
        var houseTypeDetail = $("#houseTypeDetail").val();
        if(!houseTypeName){
            alert("名称必填!");
            $("#houseTypeName").focus();//获取焦点
            return false;
        }
        if(!houseTypeDetail){
            alert("简介必填!");
            $("#houseTypeDetail").focus();//获取焦点
            return false;
        }
        var data = $("#form1").serialize();
        var id="${houseType.id}";
        var targetUrl="http://localhost:8088/manage/houseType/update/"+id;
        $.ajax({
            type:'post',
            url:targetUrl,
            cache: false,
            data:data,
            dataType:'json',
            success:function(result){
                if(result.status==200){
                    alert("保存成功");
                    window.location.href="http://localhost:8088/admin/houseTypelist";
                }

            },
            error:function(error){
                console.log(error);

            }
        })
    })
</script>
</body>
</html>
