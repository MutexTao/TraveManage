<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>订单详情</title>
    <link rel="stylesheet" href="/css/allstyle.css" />
</head>
<link href="/css/bootstrap.css" rel="stylesheet">
<!-- FontAwesome core CSS -->
<link href="/css/font-awesome.min.css" rel="stylesheet">
<style>
    a {
        color: #f1c40f;
    }

    a:hover,
    a:active,
    a:focus {
        color: #dab10d;
    }

    .rating-stars {
        width: 100%;
        text-align: center;
    }

    .rating-stars .rating-stars-container {
        font-size: 0px;
    }

    .rating-stars .rating-stars-container .rating-star {
        display: inline-block;
        font-size: 32px;
        color: #555555;
        cursor: pointer;
        padding: 5px 10px;
    }

    .rating-stars .rating-stars-container .rating-star.is--active,
    .rating-stars .rating-stars-container .rating-star.is--hover {
        color: #f1c40f;
    }

    .rating-stars .rating-stars-container .rating-star.is--no-hover {
        color: #555555;
    }
</style>
<body>
<jsp:include page="navigation.jsp" flush="true" />

<div id="content" class="make_content">
    <div class="crumbs_menu">
        <span>填写预定信息</span> &gt;&gt; <span class="themecru_span">付款</span> &gt;&gt; <span>预定成功</span>
    </div>
    <div class="clear">
        <div class="order_con fl">
            <!--上面部分-->
            <div class="order_top">
                <h2>${product.title}</h2>
                <div class="clear theme_s_wrap">
                    <div class="info_list_img">
                        <img src="${product.mainImage}">
                    </div>
                    <!--文字说明-->
                    <div class="info_desc_txt">
                        <ul class="info_list_attr">
                            <li>
                                <span class="info_desc_price">&yen;${productSell.pPrice}</span>/人起
                            </li>
                            <li>
                                <span class="info_attr_txt">旅游天数</span>
                                <span class="info_attr_val">${product.activeDays}</span>
                            </li>
                            <li>
                                <span class="info_attr_txt">出发城市</span>
                                <span class="info_attr_val">${product.startAreaname}</span>
                            </li>
                            <li>
                                <span class="info_attr_txt">目的城市</span>
                                <span class="info_attr_val">${product.endAreaname}</span>
                            </li>
                            <li>
                                <span class="info_attr_txt">房型</span>
                                <span class="info_attr_val">${productSell.houseTypeName}</span>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
            <!--上面部分-->
            <!--form 表单-->
            <form action="/Order/create/id/3511" method="post" id="theme_form">
                    <h2 class="order_theme_title">订购信息</h2>
                    <div class="order_myinfo">
                        <table class="payment_table" cellspacing="0">
                            <thead>
                            <tr style="text-align: center">
                                <th>出发时间</th>
                                <th>订购数量</th>
                                <th>联系人</th>
                                <th>手机号</th>
                                <th>备注</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr style="text-align: left">
                                <td><fmt:formatDate value="${productSell.startDate}" pattern="yyyy-MM-dd"/></td>
                                <td>${productOrder.pruductNum}</td>
                                <td>${productOrder.username}</td>
                                <td>${productOrder.phone}</td>
                                <td>${productOrder.remarks}</td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="col-md-5 p-lg-5 mx-auto my-5" style="">
                            <h3>旅游评分</h3>
                            <c:if test="${productOrder.stars==null}">
                            <div class="rating-stars block" id="rating">
                                <input type="number" readonly class="form-control rating-value hidden" name="rating-stars-value" id="rating-stars-value">
                                <div class="rating-stars-container">
                                    <div class="rating-star">
                                        <i class="fa fa-star">★</i>
                                    </div>
                                    <div class="rating-star">
                                        <i class="fa fa-star">★</i>
                                    </div>
                                    <div class="rating-star">
                                        <i class="fa fa-star">★</i>
                                    </div>
                                    <div class="rating-star">
                                        <i class="fa fa-star">★</i>
                                    </div>
                                    <div class="rating-star">
                                        <i class="fa fa-star">★</i>
                                    </div>
                                </div>
                                <input type="button" class="btn-block" onclick="submitStars()" value="提交"/>
                            </div>
                            </c:if>
                            <c:if test="${productOrder.stars!=null}">
                            <div class="rating-stars block" id="rating">
                                <div class="rating-stars-container" id="showStars">

                                </div>
                            </div>
                            </c:if>
                        </div>
                <br/><br/><br/>
                <br/><br/><br/>
                <br/><br/><br/>

                    <div class="book_note">
                        <h3>预定须知</h3>
                        <p>付款完成后，如需取消如需取消或修改订单，请尽快通知美旅旅行，否则我们将扣除您全额或部分房费。</p>
                        <p>退款金额说明</p>
                        <p>若您所预定的套餐产品内含优惠，当您退订其中任何一项保险以外的项目（酒店或门票）我们将从退款金额中扣除优惠部分，敬请见谅</p>
                    </div>
                </div>
            </form>

            <!--form 表单 end-->

        </div>
        <!--order_con end-->
        <!--订单信息及支付按钮-->
        <div class="order_info fr">
            <div class="order_r_posi">
                <h4 class="order_theme_title">金额信息</h4>
                <div class="order_pay_info">

                    <div class="theme_r_wrap">
                        <div class="theme_r_info">
                            <span class="theme_r_txt">订单金额：</span>
                            <span class="theme_r_allprice">&yen;${productOrder.payment}</span>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!--订单信息及支付按钮 end-->
    </div>
</div>
<!--footer-->
<style>
    /*字体图标*/
    @font-face {
        font-family: 'iconfont';  /* project id 258222 */
        src: url('//at.alicdn.com/t/font_1qtzkjg92s0v0a4i.eot');
        src: url('//at.alicdn.com/t/font_1qtzkjg92s0v0a4i.eot?#iefix') format('embedded-opentype'),
        url('//at.alicdn.com/t/font_1qtzkjg92s0v0a4i.woff') format('woff'),
        url('//at.alicdn.com/t/font_1qtzkjg92s0v0a4i.ttf') format('truetype'),
        url('//at.alicdn.com/t/font_1qtzkjg92s0v0a4i.svg#iconfont') format('svg');
    }
    .iconfont{
        font-family:"iconfont";
        font-size:16px;
        font-style:normal;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        padding-left:20px
    }
</style>




<%@ include file="foot.jsp"%>
<script src="/js/jquery-1.11.0.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.js"></script>

<script src="/js/jquery.rating-stars.min.js"></script>

<script>
    var ratingOptions = {
        selectors: {
            starsSelector: '.rating-stars',
            starSelector: '.rating-star',
            starActiveClass: 'is--active',
            starHoverClass: 'is--hover',
            starNoHoverClass: 'is--no-hover',
            targetFormElementSelector: '.rating-value'
        }
    };

    $(".rating-stars").ratingStars(ratingOptions);
</script>
<script>

    if (${productOrder.stars != null}){
        showStars(${productOrder.stars});
    }
    function showStars(stars){
        var html="";
        $("#showStars").html(html);
        for (i=0;i<stars;i++){
            html+='<div class="rating-star is--active">'+
                '<i class="fa fa-star">★</i>'+
                '</div>'
        }
        $("#showStars").html(html);
    }
    function submitStars() {
        var url="http://localhost:8088/productOrder/stars";
        var stars = document.getElementById("rating-stars-value").value;
        var id='${productOrder.id}';
        var data={
            stars:stars,
            id:id
        };
        $.post(url,data,function(result){
            if (result==1){
                alert("提交成功")
                window.location.reload();
            }else {
                alert("提交失败")
            }
        });
    }
</script>
</body>
</html>
