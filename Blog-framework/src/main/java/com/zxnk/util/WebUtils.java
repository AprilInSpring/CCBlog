package com.zxnk.util;

import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebUtils
{
    /**
     * 将字符串渲染到客户端
     * 
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static void renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param filename 下载的文件名
     * @param response 响应对象
     * @return: void
     * @decription 创建exel表下载请求
     * @date 2023/5/9 20:56
    */
    public static void setDownLoadHeader(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        //设置下载的响应类型
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置编码
        response.setCharacterEncoding("utf-8");
        //设置文件名，防止乱码
        String fname= URLEncoder.encode(filename,"UTF-8").replaceAll("\\+", "%20");
        //设置下载的响应头
        response.setHeader("Content-disposition","attachment; filename="+fname);
    }
}