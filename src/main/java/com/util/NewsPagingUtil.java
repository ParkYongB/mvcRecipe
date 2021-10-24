package com.util;

public class NewsPagingUtil {
	private int startCount;	 // �� ���������� ������ �Խñ��� ���� ��ȣ
	private int endCount;	 // �� ���������� ������ �Խñ��� �� ��ȣ
	private StringBuffer pagingHtmlNews;// ������ ǥ�� ���ڿ�

	/**
	 * currentPage : ����������
	 * totalCount : ��ü �Խù� ��
	 * rowCount : �� ��������  �Խù��� ��
	 * pageCount : �� ȭ�鿡 ������ ������ ��
	 * pageUrl : ȣ�� ������ url
	 * addKey : �ΰ����� key ���� ���� null ó�� (&num=23�������� ������ ��)
	 * */
	public NewsPagingUtil(int currentPage, int totalCount, int rowCount,
			int pageCount, String pageUrl) {
		this(null,null,currentPage,totalCount,rowCount,pageCount,pageUrl,null);
	}
	public NewsPagingUtil(int currentPage, int totalCount, int rowCount,
			int pageCount, String pageUrl, String addKey) {
		this(null,null,currentPage,totalCount,rowCount,pageCount,pageUrl,addKey);
	}
	public NewsPagingUtil(String keyfield, String keyword, int currentPage, int totalCount, int rowCount,
			int pageCount,String pageUrl) {
		this(keyfield,keyword,currentPage,totalCount,rowCount,pageCount,pageUrl,null);
	}
	public NewsPagingUtil(String keyfield, String keyword, int currentPage, int totalCount, int rowCount,
			int pageCount,String pageUrl,String addKey) {

	
		if(addKey == null) addKey = ""; //�ΰ�Ű�� null �϶� ""ó��
		
		// ��ü ������ ��
		int totalPage = (int) Math.ceil((double) totalCount / rowCount);
		if (totalPage == 0) {
			totalPage = 1;
		}
		// ���� �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		// ���� �������� ó���� ������ ���� ��ȣ ��������.
		startCount = (currentPage - 1) * rowCount + 1;
		endCount = currentPage * rowCount;
		// ���� �������� ������ ������ �� ���ϱ�.
		int startPage = (int) ((currentPage - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;
		// ������ �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		// ���� block ������
		pagingHtmlNews = new StringBuffer();
		if (currentPage > pageCount) {
			if(keyword==null){//�˻� �̻���
				pagingHtmlNews.append("<a href="+pageUrl+"?newsPageNum="+ (startPage - 1) + addKey +">");
			}else{
				pagingHtmlNews.append("<a href="+pageUrl+"?search="+keyfield+"&qnaPageNum="+keyword+"&newsPageNum="+ (startPage - 1) + "&recipePageNum=" + addKey +">");
			}
			pagingHtmlNews.append("����");
			pagingHtmlNews.append("</a>");
		}
		pagingHtmlNews.append("&nbsp;|&nbsp;");
		//������ ��ȣ.���� �������� ���������� �����ϰ� ��ũ�� ����.
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtmlNews.append("&nbsp;<b> <font color='red'>");
				pagingHtmlNews.append(i);
				pagingHtmlNews.append("</font></b>");
			} else {
				if(keyword==null){//�˻� �̻���
					pagingHtmlNews.append("&nbsp;<a href='"+pageUrl+"?newsPageNum=");
				}else{
					pagingHtmlNews.append("&nbsp;<a class='npaging-butn' href='"+pageUrl+"?search="+keyfield+"&qnaPageNum="+keyword+"&newsPageNum=");
				}
				pagingHtmlNews.append(i);
				pagingHtmlNews.append("&recipePageNum="+ addKey +"'>");
				pagingHtmlNews.append(i);
				pagingHtmlNews.append("</a>");
			}
			pagingHtmlNews.append("&nbsp;");
		}
		pagingHtmlNews.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
		// ���� block ������
		if (totalPage - startPage >= pageCount) {
			if(keyword==null){//�˻� �̻���
				pagingHtmlNews.append("<a href="+pageUrl+"?newsPageNum="+ (endPage + 1) + addKey +">");
			}else{
				pagingHtmlNews.append("<a class='npaging-butn' href="+pageUrl+"?search="+keyfield+"&qnaPageNum="+keyword+"&newsPageNum="+ (endPage + 1) + "&recipePageNum=" + addKey +">");
			}
			pagingHtmlNews.append("����");
			pagingHtmlNews.append("</a>");
		}
	}
	public StringBuffer getPagingHtml() {
		return pagingHtmlNews;
	}
	public int getStartCount() {
		return startCount;
	}
	public int getEndCount() {
		return endCount;
	}

}
