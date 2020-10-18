package com.ischoolbar.programmer.servlet;

import com.ischoolbar.programmer.dao.CompanyDao;
import com.ischoolbar.programmer.model.Company;
import com.lizhou.exception.FileFormatException;
import com.lizhou.exception.NullFileException;
import com.lizhou.exception.ProtocolException;
import com.lizhou.exception.SizeException;
import com.lizhou.fileload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理类servlet
 */
public class CompanyIconServet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 3274927179113071465L;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String method = request.getParameter("method");
        if("getPhoto".equals(method)){
            getPhoto(request,response);
        }else if("SetPhoto".equals(method)){
            uploadPhoto(request,response);
        }

    }
    private void uploadPhoto(HttpServletRequest request,
                             HttpServletResponse response) {
        // TODO Auto-generated method stub
        int cid = request.getParameter("cid") == null ? 0 : Integer.parseInt(request.getParameter("cid"));
        FileUpload fileUpload = new FileUpload(request);
        fileUpload.setFileFormat("jpg");
        fileUpload.setFileFormat("png");
        fileUpload.setFileFormat("jpeg");
        fileUpload.setFileFormat("gif");
        fileUpload.setFileSize(2048);
        response.setCharacterEncoding("UTF-8");
        try {
            InputStream uploadInputStream = fileUpload.getUploadInputStream();
            if(cid != 0){
                Company company = new Company();
                company.setId(cid);
                company.setIcon(uploadInputStream);
                CompanyDao companyDao = new CompanyDao();
                if(companyDao.setCompanyPhoto(company)){
                    response.getWriter().write("<div id='message'>上传成功！</div>");
                }else{
                    response.getWriter().write("<div id='message'>上传失败！</div>");
                }
            }
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            try {
                response.getWriter().write("<div id='message'>上传协议错误！</div>");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }catch (NullFileException e1) {
            // TODO: handle exception
            try {
                response.getWriter().write("<div id='message'>上传的文件为空!</div>");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            e1.printStackTrace();
        }
        catch (SizeException e2) {
            // TODO: handle exception
            try {
                response.getWriter().write("<div id='message'>上传文件大小不能超过"+fileUpload.getFileSize()+"！</div>");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            e2.printStackTrace();
        }
        catch (IOException e3) {
            // TODO: handle exception
            try {
                response.getWriter().write("<div id='message'>读取文件出错！</div>");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            e3.printStackTrace();
        }
        catch (FileFormatException e4) {
            // TODO: handle exception
            try {
                response.getWriter().write("<div id='message'>上传文件格式不正确，请上传 "+fileUpload.getFileFormat()+" 格式的文件！</div>");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            e4.printStackTrace();
        }
        catch (FileUploadException e5) {
            // TODO: handle exception
            try {
                response.getWriter().write("<div id='message'>上传文件失败！</div>");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            e5.printStackTrace();
        }
    }
    private void getPhoto(HttpServletRequest request,
                          HttpServletResponse response) {
        // TODO Auto-generated method stub
        //File file = new File();
        int cid = request.getParameter("cid") == null ? 0 : Integer.parseInt(request.getParameter("cid"));
        if(cid != 0){
            //学生
            CompanyDao companyDao = new CompanyDao();
            Company company = companyDao.getCompany(cid);
            companyDao.closeCon();
            if(company != null){
                InputStream photo = company.getIcon();
                if(photo != null){
                    try {
                        byte[] b = new byte[photo.available()];
                        photo.read(b);
                        response.getOutputStream().write(b,0,b.length);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
        String path = request.getSession().getServletContext().getRealPath("");
        File file = new File(path+"\\file\\logo.jpg");
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            response.getOutputStream().write(b,0,b.length);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
