package com.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

//���� ���ε� ����� �����ϱ� ���� cos.jar ������ �ʼ������� �䱸��.
public class FileUtil {
	// ���ڵ� Ÿ��
	public static final String ENCODING_TYPE = "utf-8";
	// �ִ� ���ε� ������
	public static final int MAX_SIZE = 10*1024*1024;
	// ���ε� ���
	public static final String UPLOAD_PATH= "/upload";	// ��� ���
	
	// ���� ���ε�
	public static MultipartRequest createFile(HttpServletRequest request)throws IOException{
		// ���ε� ������
		String upload = request.getServletContext().getRealPath(UPLOAD_PATH);
		
		// ���� ��ο� ���� ���ε�
		// MultipartRequest �����ڸ� ���� �����ɶ� ������ �����ȴ�. DAO���� ȣ���Ҷ� request���� ��θ� �ޱ� ������
		// �ش� ������ ���� ��ο� ���ε� �ȴ�.
								// request, ���,		 ���ϻ�����	,	 ���ڵ� Ÿ��, 		�ߺ��� ó�� ����
		return new MultipartRequest(request, upload, MAX_SIZE, ENCODING_TYPE, new DefaultFileRenamePolicy());
	}
	
	// ���� ����
	public static void removeFile(HttpServletRequest request, String filename) throws IOException{
		if(filename!=null) {	// ���� �̸��� ������ ���
			// ��� ������ ���� ó��
			// ���ε� ���� ���
			String upload = request.getServletContext().getRealPath(UPLOAD_PATH);
			
			// ���� ��ü ����
			File file = new File(upload+"/"+filename);	// ���ε� �����ο� / ���ϸ��� �����鼭 ��ü ����
			// ��ο� ������ �����Ѵٸ� ����
			if(file.exists()) file.delete();
		}
	}
}
