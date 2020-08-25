package com.cdxt;

import com.cdxt.backend.CdxtHelperApplication;
import com.cdxt.common.utils.SVNUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: TengGang
 * @Date: 2020/6/20 11:53
 * @Description:
 */

@SpringBootTest(classes = CdxtHelperApplication.class)
public class SVNUtilTest {
    @Autowired
    SVNUtil svnUtil;


    /**
     * SVN 是否存在文件
     */
    @Test
    public  void isExistFileTest(){
        List<String>  srcList = Arrays.asList(
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/打包entity和manager.xml",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties",
                "svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties"
                );
//        for (int i=0;i<20;i++){
//            srcList.add("svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统/cdxtHisService/src/redis.properties");
//        }
        long startTime = System.currentTimeMillis();
        srcList.forEach(item ->{
            Boolean flag = svnUtil.isExistFile(item);
            System.out.println("请求成功值为:" + flag);
        });
        long endTime = System.currentTimeMillis();
        System.out.println("--------------"+(endTime - startTime)+" ms-----------------");
    }


    /**
     * 测试SVN 文件下载
     */
    @Test
    public void downloadFileTest(){
        try {
            svnUtil.downloadFile("svn://171.223.209.166/数字化医院/5项目编码/1源代码/数字化医院系统","/cdxtHisService/src/log4j2.xml","F:\\svn\\0001\\数字化医院系统\\cdxtHisService\\src\\log4j2.xml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
