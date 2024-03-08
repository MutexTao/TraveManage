package com.halfsummer.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.halfsummer.common.Const;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.entity.*;
import com.halfsummer.service.*;
import com.halfsummer.util.RecommendUtil;
import com.halfsummer.vo.PriceCalendar;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sun.tools.doclint.Entity.and;

/**
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private IProductService productService;
    @Autowired
    private IThemeProductService themeProductService;
    @Autowired
    private IProductSellService productSellService;
    @Autowired
    private IProductDescService productDescService;
    @Autowired
    private IUserActionService userActionService;

    /**
     * list
     * @param map
     * @param current
     * @param size
     * @return
     *
     * 接收前端传来的参数，包括一个Map类型的map、一个整型类型的current、一个整型类型的size和一个HttpSession类型的session。
     * 创建一个EntityWrapper对象，用来构造SQL语句的where条件。
     * 从session中获取当前用户的信息。
     * 根据map中的参数，构造查询条件，如：startAreaname、endAreaname、title、activeDays、orderBy、type和status等。
     * 根据构造好的查询条件，调用productService.selectPage方法进行分页查询，并将查询结果封装成一个Page<Product>对象。
     * 判断用户是否已经登录，并且是否进行了条件查询，如果满足条件，则将用户对商品的查询行为存入数据库中。
     * 最后将查询到的商品列表通过ServerResponse.createBySuccess方法封装成一个响应返回给前端。
     */
    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse list(@RequestParam Map map,@RequestParam(value="current",defaultValue="1") int current,@RequestParam(value="size",defaultValue="10") int size,HttpSession session){
        EntityWrapper entityWrapper=new EntityWrapper();
        User loginUser = (User)session.getAttribute(Const.CURRENT_USER);
        Boolean flag = false;
        if (map.get("startAreaname")!=null&&!"".equals(map.get("startAreaname"))){
            String startAreaname= (String) map.get("startAreaname");
            entityWrapper.eq("start_areaName",startAreaname);
            flag=true;
        }
        if (map.get("endAreaname")!=null&&!"".equals(map.get("endAreaname"))){
            String endAreaname= (String) map.get("endAreaname");
            entityWrapper.eq("end_areaName",endAreaname);
            flag=true;
        }
        if (map.get("title")!=null&&!"".equals(map.get("title"))){
            String title= (String) map.get("title");
            entityWrapper.like("title",title);
            flag=true;
        }
        if (map.get("activeDays")!=null&&!"不限".equals(map.get("activeDays"))){
            String activeDays= (String) map.get("activeDays");
            int day;
            if (activeDays.equals("1-2天")){
                entityWrapper.le("active_days",2);
            }else if (activeDays.equals("3-5天")){
                entityWrapper.between("active_days",3,5);
            }else{
                entityWrapper.gt("active_days",5);
            }
            flag=true;

        }
        if (map.get("orderBy")!=null){
            String orderBy= (String) map.get("orderBy");
            if (orderBy.equals("priceAsc")){
                entityWrapper.orderBy("price",true);
            }else if (orderBy.equals("priceDesc")){
                entityWrapper.orderBy("price",false);
            }
        }else {
            entityWrapper.orderBy("create_time",false);
        }
        if (map.get("type")!=null&&!"全部".equals(map.get("type"))){
            String type= (String) map.get("type");
             List<ThemeProduct> tplist=themeProductService.selectList(new EntityWrapper<ThemeProduct>().eq("theme_name",type));
             List idlist=new ArrayList();
             for (ThemeProduct themeProduct:tplist){
                 idlist.add(themeProduct.getProductId());
             }
            entityWrapper.in("pid",idlist);
            flag=true;
        }
        if (map.get("status")!=null){
            int status= Integer.parseInt((String) map.get("status"));
            entityWrapper.eq("status",status);//上架产品
        }
        Page<Product> productPage = productService.selectPage(new Page<Product>(current,size),entityWrapper);
        //判断用户是否登陆，以及查询是否为条件查询
        if (flag==true&loginUser!=null){
            System.out.println("可以增加冷启动数据");
            //定义一个需要存入数据库的UserAction
            UserAction userAction = new UserAction();
            //获取userid
            String userid=loginUser.getId();
            //进入循环，提出所有已查询的商品信息
            for (int i =0;i<productPage.getRecords().size();i++){
                //定义一个UserAction的Wrapper
                EntityWrapper userActionWrapper=new EntityWrapper();
                //获取相对应的productid
                String productid=productPage.getRecords().get(i).getPid();
                //存入userActionWrapper并查询是否已存在
                userActionWrapper.eq("userid",userid);
                userActionWrapper.eq("productid",productid);
                UserAction tempUserAction =  userActionService.selectOne(userActionWrapper);
                if (tempUserAction==null){
                    //如果数据库没有该用户id和该商品id的信息，则插入一条
                    userAction.setUserid(userid);
                    userAction.setProductid(productid);
                    //查询则+1分
                    userAction.setScore(1);
                    userActionService.insert(userAction);
                }else if (tempUserAction!=null){
                    //如果数据库已存在该用户id和该商品id的信息，则修改
                    Integer score=tempUserAction.getScore();
                    tempUserAction.setScore(score+1);
                    userActionService.update(tempUserAction,userActionWrapper);
                }
            }

        }

        return ServerResponse.createBySuccess(productPage);
    }


    /**
     *
     * @param session
     * @param pid
     * @param model
     * @return
     *
     * 该方法的作用是获取指定 ID 的产品信息，包括产品基本信息、产品描述、价格日历、主题产品等。然后将这些信息添加到模型对象 Model 中，以便在视图中渲染。
     * 除此之外，该方法还获取当前登录用户的信息，如果存在，则查询该用户是否已对该产品进行过行为评分，如果没有，则插入一条评分记录，如果已有，则更新评分记录。
     * 最后返回视图名称 "index/product_detail"。
     */
    @RequestMapping("detailView/{pid}")
    public String detailView(HttpSession session,@PathVariable String pid, Model model){
        Product product=productService.selectById(pid);
        ProductDesc productDesc=productDescService.selectById(pid);
        List<PriceCalendar> priceCalendars=productSellService.getPriCal(product.getPid());
        List<ThemeProduct> themeProducts=themeProductService.selectByPid(product.getPid());

        model.addAttribute("product",product);
        model.addAttribute("productDesc",productDesc);
        model.addAttribute("priceCalendars",priceCalendars);
        model.addAttribute("themeProducts",themeProducts);
        EntityWrapper<ProductSell> productSellEntityWrapper = new EntityWrapper<>();
        productSellEntityWrapper.eq("pid",pid);
        productSellEntityWrapper.setSqlSelect("DISTINCT house_type_name");
        List<Object> houseTypeObjects = productSellService.selectObjs(productSellEntityWrapper);
        String houseTypeHtml="";
        for (int i = 0 ; i<houseTypeObjects.size();i++){
            houseTypeHtml+="<option value='"+houseTypeObjects.get(i)+"' >"+houseTypeObjects.get(i)+"</option>";
        }
        model.addAttribute("houseType",houseTypeHtml);
        System.out.println("priceCalendars:"+priceCalendars);
        System.out.println("themeProducts:"+themeProducts);

        //获取登陆的用户
        User loginUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(loginUser!=null){
            //查询用户行为评分是否已存在该用户对应的商品行为评分
            EntityWrapper<UserAction> entityWrapper = new EntityWrapper();
            entityWrapper.eq("userid",loginUser.getId());
            entityWrapper.eq("productid",pid);
            UserAction userAction = userActionService.selectOne(entityWrapper);
            if (userAction==null){
                //如果不存在，则插入一条数据
                userAction=new UserAction();
                userAction.setUserid(loginUser.getId());
                userAction.setProductid(pid);
                userAction.setScore(3);
                userActionService.insert(userAction);
            }else {
                //如果存在，则修改数据
                Integer score = userAction.getScore();
                userAction.setScore(score+3);
                userActionService.update(userAction,entityWrapper);
            }
        }
        return "index/product_detail";
    }

    //根据销量排行
    @RequestMapping("/hot")
    @ResponseBody
    public List<Product> hotProduct(int size){

        return productService.hotProduct(size);
    }

    //根据协同过滤算法获取5个推荐的商品

    /**
     *
     * @param session
     * @return
     * @throws Exception
     *
     * 首先根据用户登录的状态判断是否有必要进行推荐，如果未登录则返回一个空的推荐列表。
     * 如果用户已登录，调用了一个 RecommendUtil 类中的 getRecommend() 方法来获取用户的推荐商品列表，
     * 如果推荐列表中的商品数量小于 5，那么再根据用户的历史行为记录进行补充推荐。
     * 这里用户行为记录通过 UserAction 类来实现，可以查询指定用户的感兴趣商品列表，并根据历史得分进行排序。
     * 代码使用了 MyBatisPlus 中的 EntityWrapper 类来构造查询条件，查询结果包括每个商品的 id，
     * 通过调用 ProductService 类中的 selectById() 方法来获取完整的商品信息。最后返回一个最多包含 5 个推荐商品的列表。
     */
    @RequestMapping("/recommend")
    @ResponseBody
    public List<Product> recommendProduct(HttpSession session) throws Exception {
        User loginUser = (User)session.getAttribute(Const.CURRENT_USER);
        List<Product> recommendProductList = new ArrayList<>();
        if (loginUser!=null) {
            RecommendUtil recommendUtil = new RecommendUtil();
            List<Long> recommendList = recommendUtil.getRecommend(loginUser.getId());
            for (int i=0;i<recommendList.size();i++){
                System.out.println(recommendList.get(i));
                Product product = productService.selectById(recommendList.get(i));
                recommendProductList.add(product);
                if (i==4){
                    break;
                }
            }
            if (recommendProductList.size()<5){
                EntityWrapper<UserAction> userActionEntityWrapper = new EntityWrapper<>();
                //先查询用户感兴趣的商品，并降序排列
                userActionEntityWrapper.eq("userid",loginUser.getId());
                userActionEntityWrapper.orderBy("score",false);
                List<UserAction> userActionList = userActionService.selectList(userActionEntityWrapper);
                //遍历用户感兴趣的商品
                for (int i =0;i<userActionList.size();i++){
                    //获取感兴趣的商品id
                    String pid=userActionList.get(i).getProductid();
                    Boolean isExist=false;
                    //遍历推荐的商品
                    for (int j  =0;j<recommendProductList.size();j++){
                        //当推荐的商品已存在感兴趣的商品，则不存入
                        if (pid.equals(recommendProductList.get(j).getPid())){
                            isExist=true;
                        }
                    }
                    if (!isExist){
                        Product product = productService.selectById(pid);
                        recommendProductList.add(product);
                    }
                    if (recommendProductList.size()==5){
                        break;
                    }
                }
            }
        }
        return recommendProductList;
    }

    /**
     *
     * @param map
     * @param size
     * @return
     *
     * 该方法使用了Spring MVC框架的注解@RequestParam来接收一个Map类型的参数map和一个int类型的参数size，
     * 其中map存储了查询条件，size表示查询结果的数量，默认值为5。首先，创建一个实体包装器EntityWrapper，用于组装SQL语句。
     * 如果map中存在名为activeDays的参数，则根据参数值将查询条件添加到EntityWrapper中；
     * 如果存在名为type的参数，则根据参数值从ThemeProduct表中获取相应的商品id，然后添加到EntityWrapper中。
     * 最后，使用EntityWrapper查询符合条件的商品列表，并返回查询结果。
     */
   @RequestMapping("/list2")
   @ResponseBody
    public List<Product> listBycondition(@RequestParam Map map,@RequestParam(value="current",defaultValue="5") int size){
       EntityWrapper entityWrapper=new EntityWrapper();
       if (map.get("activeDays")!=null){
           String activeDays= (String) map.get("activeDays");
           int day;
           if (activeDays.equals("1-2天")){
               entityWrapper.le("active_days",2);
           }else if (activeDays.equals("3-5天")){
               entityWrapper.between("active_days",3,5);
           }else{
               entityWrapper.gt("active_days",5);
           }

       }
       if (map.get("type")!=null){
           String type= (String) map.get("type");

           List<ThemeProduct> tplist=themeProductService.selectList(new EntityWrapper<ThemeProduct>().eq("theme_name",type));
           List idlist=new ArrayList();
           for (ThemeProduct themeProduct:tplist){
               idlist.add(themeProduct.getProductId());
           }
           entityWrapper.in("pid",idlist);
       }
       entityWrapper.eq("status",1);
       String limt="limit "+size;
        entityWrapper.last(limt);
       return productService.selectList(entityWrapper);

   }

    /**
     *
     * @param houseTypeName
     * @param pid
     * @param session
     * @return
     *
     * 该方法通过pid和houseTypeName从数据库中获取一个PriceCalendar对象列表，
     * 然后将每个PriceCalendar对象的价格设置为它的数据，并返回PriceCalendar对象列表。最后返回列表。
     */
    @RequestMapping("/getDate")
    @ResponseBody
    public List<PriceCalendar> getDate(String houseTypeName, String pid, HttpSession session){
        List<PriceCalendar> priceCalendarList = productSellService.getPriCalByHouseTypeName(pid, houseTypeName);
        for(PriceCalendar priceCalendar : priceCalendarList){
            priceCalendar.setData(priceCalendar.getPrice());
        }
        return priceCalendarList;
    }

}

