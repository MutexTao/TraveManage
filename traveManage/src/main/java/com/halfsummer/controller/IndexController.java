package com.halfsummer.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.halfsummer.common.Const;
import com.halfsummer.entity.*;
import com.halfsummer.service.*;
import com.halfsummer.util.RecommendUtil;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *register()：用于返回index/register视图。
 * customized()：用于返回index/customized视图。
 * navigation()：用于返回index/navigation视图。
 * plView()：用于返回index/product_list视图，并将Theme列表添加到Model中，以便在视图中使用。
 * indexView()：用于返回index/index视图，并将产品列表、热门产品列表、推荐产品列表添加到Model中，以便在视图中使用。
 * userinfoView()：用于返回index/user_info视图。
 * myorder()：用于返回index/my_order视图。
 * plistView()：用于返回index/plist视图。
 * adloginView()：用于返回backend/admin_login视图。
 * 该类中的各个方法通过注入的IProductService、IThemeService和IUserActionService来使用相关服务的功能。其中，indexView()方法包括获取推荐商品的逻辑，
 * 会通过调用RecommendUtil来获取用户的推荐商品列表，如果推荐商品数量不够，将从用户感兴趣的商品中选择一些来作为推荐商品，最终将推荐商品列表添加到Model中，以便在视图中使用。
 */
@Controller
public class IndexController {

    @Autowired
    private IProductService productService;
    @Autowired
    private IThemeService themeService;
    @Autowired
    private IUserActionService userActionService;


    @RequestMapping("/register")
    public String register(){
        return "index/register";
    }

    @RequestMapping("/customized")
    public String customized(){
        return "index/customized";
    }

    @RequestMapping("/navigation")
    public String navigation(){
        return "index/navigation";
    }

    @RequestMapping("/productlistView")
    public String plView(Model model){
        List<Theme> themes=themeService.selectList(null);
        model.addAttribute("themes",themes);
        return "index/product_list";
    }

    @RequestMapping("/indexView")
    public String indexView(HttpSession session,Model model) throws Exception {
        List<Product> productList=productService.getIndexproduct(6);
        List<Product> hotPList=productService.hotProduct(6);
        User loginUser = (User)session.getAttribute(Const.CURRENT_USER);
        List<Product> recommendProductList = new ArrayList<>();
        //获取推荐的商品
        if (loginUser!=null) {
            RecommendUtil recommendUtil = new RecommendUtil();
            List<Long> recommendList = recommendUtil.getRecommend(loginUser.getId());
            for (int i=0;i<recommendList.size();i++){
                Product product = productService.selectById(recommendList.get(i));
                recommendProductList.add(product);
            }
            Integer flag = 6-recommendList.size();
            if (flag>0){
                //推荐数量不够，使用用户冷启动
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
                    if (recommendProductList.size()==6){
                        break;
                    }
                }

            }
        }
        model.addAttribute("recommendProductList",recommendProductList);
        model.addAttribute("hotPList",hotPList);
        model.addAttribute("productList",productList);
        return "index/index";
    }

    @RequestMapping("/userinfoView")
    public String userinfoView(){
        return "index/user_info";
    }

    @RequestMapping("/myorder")
    public String myorder(){
        return "index/my_order";
    }
    @RequestMapping("/plistView")
    public String plistView(){

        return "index/plist";
    }

    @RequestMapping("/adminLoginView")
    public String adloginView(){
        return "backend/admin_login";
    }

}
