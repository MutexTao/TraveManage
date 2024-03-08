package com.halfsummer.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.halfsummer.common.Const;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.*;
import com.halfsummer.service.IProductOrderService;
import com.halfsummer.service.IProductSellService;
import com.halfsummer.service.IProductService;
import com.halfsummer.service.IUserPreferenceService;
import com.halfsummer.util.RecommendUtil;
import com.halfsummer.vo.OrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("/productOrder")
public class ProductOrderController {

    @Autowired
    private IProductOrderService productOrderService;
    @Autowired
    private IProductSellService productSellService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserPreferenceService userPreferenceService;

    /**
     *
     * @param psid
     * @param healthcodeimage
     * @param productOrder
     * @param session
     * @return
     *
     * 首先通过Session获取当前用户的信息。然后根据传入的psid（产品销售记录id）从数据库中查询对应的ProductSell记录。
     * 接着将用户的id、商品的id、产品销售记录id等信息设置到productOrder对象中。然后调用productOrderService的createOrder方法，
     * 将productOrder和productSell作为参数传递进去，生成订单。如果订单创建成功，则返回成功的响应；否则，返回失败的响应。
     */
    @RequestMapping("/create")
    @ResponseBody
    public ServerResponse buy(String psid,String healthcodeimage,ProductOrder productOrder, HttpSession session)  {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //获取对应sell记录
        ProductSell productSell=productSellService.selectById(psid);
        //更新order其他信息
        productOrder.setUserId(user.getId());
        productOrder.setProductId(productSell.getPid());
        productOrder.setProductsellId(productSell.getId());
        productOrder.setHealthCodeImage(healthcodeimage);
//        productOrder.setPruductNum(num);
        ServerResponse serverResponse;
        try {
           serverResponse= productOrderService.createOrder(productOrder,productSell);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("购买失败");
        }
        return serverResponse;
    }

    /**
     *
     * @param session
     * @return
     *
     * 首先通过HttpSession获取当前用户，如果用户未登录，则返回一个错误信息的响应。如果用户已登录，
     * 则调用productOrderService的myOrderList方法来获取该用户的订单列表，将其封装成一个成功信息的响应返回。
     * 该响应的主体部分为一个List，其中包含该用户所有的订单。
     */
    @ResponseBody
    @RequestMapping("/myOrder")
    public ServerResponse myOrderList(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return ServerResponse.createBySuccess(productOrderService.myOrderList(user.getId()));
    }

    /**
     *
     * @param session
     * @param model
     * @return
     *
     * 当浏览器请求“/orderView”时会调用该方法。该方法的作用是渲染名为“my_order”的视图模板，
     * 并向模板中传递一个名为“user”的用户对象和一个名为“orderLists”的订单列表对象。如果当前用户没有登录，该方法会重定向到“index/index”页面。
     */
    @RequestMapping("/orderView")
    public String myOrderListView(HttpSession session, Model model){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return "index/index";
        }
        List<OrderList> orderLists=productOrderService.myOrderList(user.getId());
        model.addAttribute("user",user);
        model.addAttribute("orderLists",orderLists);
        return "index/my_order";
    }


    //根据销量排行

    /**
     *
     * @param size
     * @return
     *
     * 在方法内部，首先创建了一个 "EntityWrapper" 实例，这是一个 MyBatis Plus 框架提供的查询构造器。
     * 然后，将查询构造器的 "groupBy" 方法设置为 "product_id"，这将对查询结果进行分组，按照产品 ID 进行分组.
     * 接下来，将查询构造器的 "orderBy" 方法设置为 "COUNT(*)" 和 "false"，这将按照销售数量降序排序产品，以获取热门产品。
     * 最后，使用 "productOrderService.selectList()" 方法查询数据库，并将查询结果返回给调用方。
     */
    @RequestMapping("/hot")
    @ResponseBody
    public List<Product> hotProduct(int size){
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.groupBy("product_id");
        entityWrapper.orderBy("COUNT(*)",false);
//        entityWrapper.orderDesc()

        return productOrderService.selectList(entityWrapper);
    }

    /**
     *
     * @param productOrder
     * @return
     *
     * 它首先从页面获取了用户对产品的评分，并根据旅游订单ID查询旅游订单。然后，它定义了一个用于操作协同过滤模型的Wrapper，
     * 以及一个获取协同过滤工具类的对象RecommendUtil。接着，它将原始的用户ID和商品ID转换为Long类型，
     * 并将这两个ID存储到用于操作协同过滤模型的Wrapper中，查询是否已经存在该旅游商品的评分。
     * 如果数据库没有存储该用户对该旅游商品的评分，则将评分存入数据库中；否则，更新该评分。
     * 最后，它将最初存储的评分存储到订单模型中，并提交订单的评分。该方法返回一个Integer类型的标志，表示操作是否成功。
     */
    @RequestMapping("/stars")
    @ResponseBody
    public Integer productStars(ProductOrder productOrder){
        //先获取页面上的评分
        Integer stars = productOrder.getStars();
        //根据旅游订单id查询旅游订单
        productOrder=productOrderService.selectById(productOrder.getId());
        //定义用于操作协同过滤模型的wrapper
        EntityWrapper<UserPreference> userPreferenceEntityWrapper = new EntityWrapper<>();
        //定义获取协同过滤的工具类
        RecommendUtil recommendUtil = new RecommendUtil();
        //将原先的用户id和商品id转为Long
        Long userId =recommendUtil.StringidToLongid(productOrder.getUserId());
        Long productId =Long.parseLong(productOrder.getProductId());
        //将两个id存到用于操作协同过滤模型的wrapper,查询是否已存在对该旅游商品的评分
        userPreferenceEntityWrapper.eq("uid",userId);
        userPreferenceEntityWrapper.eq("pid",productId);
        UserPreference userPreference = userPreferenceService.selectOne(userPreferenceEntityWrapper);
        //定义第二个协同过滤模型
        UserPreference userPreference1 = new UserPreference();
        //定义第二个用于操作协同过滤模型的wrapper2
        EntityWrapper<UserPreference> userPreferenceEntityWrapper2 = new EntityWrapper<>();
        if (userPreference==null){
            //如果数据库没有存储该用户对该旅游商品的评分，则存入
            userPreference1.setUid(userId);
            userPreference1.setPid(productId);
            userPreference1.setPreference(stars);
            userPreferenceService.insert(userPreference1);
        }else if (userPreference!=null){
            //如果数据库已存入该用户对该旅游商品的评分，则更改
            userPreference1.setUid(userId);
            userPreference1.setPid(productId);
            userPreference1.setPreference(stars);
            userPreferenceEntityWrapper2.eq("uid",userId);
            userPreferenceEntityWrapper2.eq("pid",productId);
            userPreferenceService.update(userPreference1,userPreferenceEntityWrapper2);
        }
        //将最开始存储的评分存入到订单模型,并提交订单的评分
        productOrder.setStars(stars);
        Integer flag = productOrderService.submitStars(productOrder);
        return flag;
    }

    //填写订单

    /**
     *
     * @param session
     * @param model
     * @param psid
     * @param num
     * @return
     *
     * 当用户访问该路径时，服务器将根据当前用户的身份和请求参数psid、num来判断是否能够成功生成订单，并返回填写订单的页面。
     * 如果当前用户未登录，将重定向到主页。如果请求参数psid为空或零、num为零，也将重定向到主页。
     * 如果满足条件，则从数据库中获取商品售卖信息、商品信息和数量，并通过模型传递给填写订单页面。
     * 最后，该方法将返回填写订单的页面路径。
     */
    @RequestMapping("/fillView")
    public String fillView(HttpSession session,Model model,String psid,int num){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return "index/index";
        }
        if (null==psid||"".equals(psid)||num==0){
            return "index/index";
        }
        ProductSell productSell=productSellService.selectById(psid);
        Product product=productService.selectById(productSell.getPid());
        model.addAttribute("productSell",productSell);
        model.addAttribute("product",product);
        model.addAttribute("num",num);
        double sum=num*productSell.getpPrice();
        model.addAttribute("sum",sum);
        return "index/fill_order";
    }

    //订单详情页面

    /**
     *
     * @param session
     * @param model
     * @param pid
     * @param oid
     * @return
     *
     * 方法首先从 HttpSession 中获取当前用户，如果用户未登录，则跳转到首页 "index/index"。然后检查 pid 和 oid 参数是否为空，如果为空，则同样跳转到首页 "index/index"。
     * 接下来，通过调用 productOrderService、productService 和 productSellService 的 selectById 和 selectOne 方法，
     * 分别查询指定 oid、pid 的 ProductOrder、Product 和 ProductSell 对象，并将它们存储到 Model 中。
     * 最后，该方法返回一个字符串 "index/order_detail"，用于指示返回的视图的名称。
     */
    @RequestMapping("/detailView")
    public String detailView(HttpSession session,Model model,String pid,String oid){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return "index/index";
        }
        if (null==pid||null==oid){
            return "index/index";
        }
        ProductOrder productOrder=productOrderService.selectById(oid);
        Product product=productService.selectById(pid);
        ProductSell productSell=productSellService.selectOne(new EntityWrapper<ProductSell>().eq("pid",pid));
        model.addAttribute("productOrder",productOrder);
        model.addAttribute("product",product);
        model.addAttribute("productSell",productSell);
        return "index/order_detail";
    }
}

