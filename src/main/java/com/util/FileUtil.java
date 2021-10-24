package com.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

//파일 업로드 기능을 수행하기 위해 cos.jar 파일이 필수적으로 요구됨.
public class FileUtil {
	// 인코딩 타입
	public static final String ENCODING_TYPE = "utf-8";
	// 최대 업로드 사이즈
	public static final int MAX_SIZE = 10*1024*1024;
	// 업로드 경로
	public static final String UPLOAD_PATH= "/upload";	// 상대 경로
	
	// 파일 업로드
	public static MultipartRequest createFile(HttpServletRequest request)throws IOException{
		// 업로드 절대경로
		String upload = request.getServletContext().getRealPath(UPLOAD_PATH);
		
		// 절대 경로에 파일 업로드
		// MultipartRequest 생성자를 통해 생성될때 파일이 생성된다. DAO에서 호출할때 request에서 경로를 받기 때문에
		// 해당 파일이 절대 경로에 업로드 된다.
								// request, 경로,		 파일사이즈	,	 인코딩 타입, 		중복시 처리 여부
		return new MultipartRequest(request, upload, MAX_SIZE, ENCODING_TYPE, new DefaultFileRenamePolicy());
	}
	
	// 파일 삭제
	public static void removeFile(HttpServletRequest request, String filename) throws IOException{
		if(filename!=null) {	// 파일 이름이 존재할 경우
			// 경로 정보를 통해 처리
			// 업로드 절대 경로
			String upload = request.getServletContext().getRealPath(UPLOAD_PATH);
			
			// 파일 객체 생성
			File file = new File(upload+"/"+filename);	// 업로드 절대경로에 / 파일명을 적으면서 객체 생성
			// 경로에 파일이 존재한다면 삭제
			if(file.exists()) file.delete();
		}
	}
}
