package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");//iso 8859 -1
		String password = request.getParameter("password");
		System.out.println("get��ʽ");
		//System.out.println("username:"+new String(username.getBytes("iso-8859-1"),"utf-8"));
		//System.out.println("password:"+password);
		//response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		if("zhangsan".equals(username)&&"123".equals(password)){
			//response.getOutputStream().write("��¼�ɹ�".getBytes("utf-8"));
			out.write("��¼�ɹ�");
		}else{
			//response.getOutputStream().write("��¼ʧ��".getBytes("utf-8"));
			out.write("��¼ʧ��");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post��ʽ");
		doGet(request, response);
	}

}
