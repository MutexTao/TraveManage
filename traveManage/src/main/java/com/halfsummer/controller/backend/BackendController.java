package com.halfsummer.controller.backend;

import com.halfsummer.entity.Customization;
import com.halfsummer.entity.Product;
import com.halfsummer.entity.ProductOrder;
import com.halfsummer.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * indexView方法处理"/index"路径下的请求，获取了用户、产品、订单和定制需求的数量，并将这些数量添加到模型中，然后返回"backend/index"视图名称。
 * userlist方法处理"/userlist"路径下的请求，直接返回"backend/user_list"视图名称。
 * navigation方法处理"/nav"路径下的请求，直接返回"backend/nav"视图名称。
 * userupdate方法处理"/userupdate"路径下的请求，直接返回"backend/user_update"视图名称。
 * cuslist方法处理"/cuslist"路径下的请求，直接返回"backend/customization_list"视图名称。
 * thememlist方法处理"/themelist"路径下的请求，直接返回"backend/theme_list"视图名称。
 * houseTypelist方法处理"/houseTypelist"路径下的请求，直接返回"backend/houseType_list"视图名称。
 * fileupload方法处理"/fileupload"路径下的请求，直接返回"backend/fileupload"视图名称。
 */
@Controller
@RequestMapping("/admin")
public class BackendController {



    @RequestMapping("/index")
    public String indexView(Model model){

        int usernum=new User().selectCount(null);
        int pnum=new  Product().selectCount(null);
        int onum= new ProductOrder().selectCount(null);
        int cusnum= new Customization().selectCount(null);
        model.addAttribute("usernum",usernum);
        model.addAttribute("pnum",pnum);
        model.addAttribute("onum",onum);
        model.addAttribute("cusnum",cusnum);
        return "backend/index";
    }

    @RequestMapping("/userlist")
    public String userlist(){
        return "backend/user_list";
    }

    @RequestMapping("/nav")
    public String navigation(){
        return "backend/nav";
    }

    @RequestMapping("userupdate")
    public String userupdate(){
        return "backend/user_update";
    }

    @RequestMapping("cuslist")
    public String cuslist(){
        return "backend/customization_list";
    }

    @RequestMapping("/themelist")
    public String thememlist(){
        return "backend/theme_list";
    }

    @RequestMapping("/houseTypelist")
    public String houseTypelist(){
        return "backend/houseType_list";
    }

    @RequestMapping("/fileupload")
    public String fileupload(){
        return "backend/fileupload";
    }


}
