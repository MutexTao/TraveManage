package com.halfsummer.util;

import com.halfsummer.service.IProductService;

import com.halfsummer.service.impl.ProductServiceImpl;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;

import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户推荐 工具类
 */
public class RecommendUtil {


    public static void main(String[] args) {
        Long userid=StringidToLongid("28bnd28918932uh1d98839hfdj");
        System.out.println(userid);
    }

    public List<Long> getRecommend(String uid) throws Exception{
        IProductService productService = new ProductServiceImpl();
        //将String的用户id转为Long的id，主要用于计算该用户的协同过滤的结果
        MemoryIDMigrator temp1=new MemoryIDMigrator();
        Long userid=temp1.toLongID(uid);
        System.out.println(userid);
		//打开数据库连接
        MysqlDataSource dataSource =  new  MysqlDataSource();
		//数据库连接ip
        dataSource.setServerName( "localhost" );
		//数据库账号
        dataSource.setUser( "root" );
		//数据库密码
        dataSource.setPassword( "lixin2020" );
		//数据库名字
        dataSource.setDatabaseName( "db_travel" );
		//查询user_preference 用户爱好分析表中的 uid，pid ，preference，time等数据
		//并利用apache的mahout工具，形成数据分析模型dataModel
        JDBCDataModel dataModel =  new  MySQLJDBCDataModel(dataSource,  "user_preference" ,  "uid" ,  "pid" ,  "preference" ,  "time" );
		//将数据分析模型dataModel
        DataModel model = dataModel;
		//将数据分析模型dataModel转算成用户相似度计算模型
        UserSimilarity similarity= new  PearsonCorrelationSimilarity(model);
		//从用户相似度计算模型推荐3个最为相似的模型出来
        UserNeighborhood neighborhood= new  NearestNUserNeighborhood( 3 ,similarity,model);
		//将该三个结果，形成推荐
        Recommender recommender= new  GenericUserBasedRecommender(model,neighborhood,similarity);
		
        List<RecommendedItem> recommendations = recommender.recommend(userid,  6 );
        List<Long> itemList = new ArrayList<>();
        for (int i=0;i<recommendations.size();i++){
            itemList.add(recommendations.get(i).getItemID());
        }
        return itemList;
    }
    public static Long StringidToLongid(String stringId){
        MemoryIDMigrator memoryIDMigrator=new MemoryIDMigrator();
        Long longId=memoryIDMigrator.toLongID(stringId);
        return longId;
    }
}
