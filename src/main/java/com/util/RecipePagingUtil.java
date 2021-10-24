package com.util;

public class RecipePagingUtil {
	private int startCount;	 // 한 페이지에서 보여줄 게시글의 시작 번호
	private int endCount;	 // 한 페이지에서 보여줄 게시글의 끝 번호
	private StringBuffer pagingHtmlRecipe;// 페이지 표시 문자열

	/**
	 * currentPage : 현재페이지
	 * totalCount : 전체 게시물 수
	 * rowCount : 한 페이지의  게시물의 수
	 * pageCount : 한 화면에 보여줄 페이지 수
	 * pageUrl : 호출 페이지 url
	 * addKey : 부가적인 key 없을 때는 null 처리 (&num=23형식으로 전달할 것)
	 * */
	public RecipePagingUtil(int currentPage, int totalCount, int rowCount,
			int pageCount, String pageUrl) {
		this(null,null,currentPage,totalCount,rowCount,pageCount,pageUrl,null);
	}
	public RecipePagingUtil(int currentPage, int totalCount, int rowCount,
			int pageCount, String pageUrl, String addKey) {
		this(null,null,currentPage,totalCount,rowCount,pageCount,pageUrl,addKey);
	}
	public RecipePagingUtil(String keyfield, String keyword, int currentPage, int totalCount, int rowCount,
			int pageCount,String pageUrl) {
		this(keyfield,keyword,currentPage,totalCount,rowCount,pageCount,pageUrl,null);
	}
	public RecipePagingUtil(String keyfield, String keyword, int currentPage, int totalCount, int rowCount,
			int pageCount,String pageUrl,String addKey) {
		
		if(addKey == null) addKey = ""; //부가키가 null 일때 ""처리
		
		// 전체 페이지 수
		int totalPage = (int) Math.ceil((double) totalCount / rowCount);
		if (totalPage == 0) {
			totalPage = 1;
		}
		// 현재 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		// 현재 페이지의 처음과 마지막 글의 번호 가져오기.
		startCount = (currentPage - 1) * rowCount + 1;
		endCount = currentPage * rowCount;
		// 시작 페이지와 마지막 페이지 값 구하기.
		int startPage = (int) ((currentPage - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;
		// 마지막 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		// 이전 block 페이지
		pagingHtmlRecipe = new StringBuffer();
		if (currentPage > pageCount) {
			if(keyword==null){//검색 미사용시
				pagingHtmlRecipe.append("<a href="+pageUrl+"?recipePageNum="+ (startPage - 1) + addKey +">");
			}else{
				pagingHtmlRecipe.append("<a href="+pageUrl+"?search="+keyfield+"&newsPageNum="+keyword+"&recipePageNum="+ (startPage - 1) + "&qnaPageNum=" + addKey +">");
			}
			pagingHtmlRecipe.append("이전");
			pagingHtmlRecipe.append("</a>");
		}
		pagingHtmlRecipe.append("&nbsp;|&nbsp;");
		//페이지 번호.현재 페이지는 빨간색으로 강조하고 링크를 제거.
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtmlRecipe.append("&nbsp;<b> <font color='red'>");
				pagingHtmlRecipe.append(i);
				pagingHtmlRecipe.append("</font></b>");
			} else {
				if(keyword==null){//검색 미사용시
					pagingHtmlRecipe.append("&nbsp;<a href='"+pageUrl+"?recipePageNum=");
				}else{
					pagingHtmlRecipe.append("&nbsp;<a class='rpaging-butn' href='"+pageUrl+"?search="+keyfield+"&newsPageNum="+keyword+"&recipePageNum=");
				}
				pagingHtmlRecipe.append(i);
				pagingHtmlRecipe.append("&qnaPageNum="+ addKey +"'>");
				pagingHtmlRecipe.append(i);
				pagingHtmlRecipe.append("</a>");
			}
			pagingHtmlRecipe.append("&nbsp;");
		}
		pagingHtmlRecipe.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
		// 다음 block 페이지
		if (totalPage - startPage >= pageCount) {
			if(keyword==null){//검색 미사용시
				pagingHtmlRecipe.append("<a href="+pageUrl+"?recipePageNum="+ (endPage + 1) + addKey +">");
			}else{
				pagingHtmlRecipe.append("<a class='rpaging-butn' href="+pageUrl+"?search="+keyfield+"&newsPageNum="+keyword+"&recipePageNum="+ (endPage + 1) + "&qnaPageNum=" + addKey +">");
			}
			pagingHtmlRecipe.append("다음");
			pagingHtmlRecipe.append("</a>");
		}
	}
	public StringBuffer getPagingHtml() {
		return pagingHtmlRecipe;
	}
	public int getStartCount() {
		return startCount;
	}
	public int getEndCount() {
		return endCount;
	}

}
