package com.util;

public class RecipePagingUtil {
	private int startCount;	 // �� ���������� ������ �Խñ��� ���� ��ȣ
	private int endCount;	 // �� ���������� ������ �Խñ��� �� ��ȣ
	private StringBuffer pagingHtmlRecipe;// ������ ǥ�� ���ڿ�

	/**
	 * currentPage : ����������
	 * totalCount : ��ü �Խù� ��
	 * rowCount : �� ��������  �Խù��� ��
	 * pageCount : �� ȭ�鿡 ������ ������ ��
	 * pageUrl : ȣ�� ������ url
	 * addKey : �ΰ����� key ���� ���� null ó�� (&num=23�������� ������ ��)
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
		pagingHtmlRecipe = new StringBuffer();
		if (currentPage > pageCount) {
			if(keyword==null){//�˻� �̻���
				pagingHtmlRecipe.append("<a href="+pageUrl+"?recipePageNum="+ (startPage - 1) + addKey +">");
			}else{
				pagingHtmlRecipe.append("<a href="+pageUrl+"?search="+keyfield+"&newsPageNum="+keyword+"&recipePageNum="+ (startPage - 1) + "&qnaPageNum=" + addKey +">");
			}
			pagingHtmlRecipe.append("����");
			pagingHtmlRecipe.append("</a>");
		}
		pagingHtmlRecipe.append("&nbsp;|&nbsp;");
		//������ ��ȣ.���� �������� ���������� �����ϰ� ��ũ�� ����.
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtmlRecipe.append("&nbsp;<b> <font color='red'>");
				pagingHtmlRecipe.append(i);
				pagingHtmlRecipe.append("</font></b>");
			} else {
				if(keyword==null){//�˻� �̻���
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
		// ���� block ������
		if (totalPage - startPage >= pageCount) {
			if(keyword==null){//�˻� �̻���
				pagingHtmlRecipe.append("<a href="+pageUrl+"?recipePageNum="+ (endPage + 1) + addKey +">");
			}else{
				pagingHtmlRecipe.append("<a class='rpaging-butn' href="+pageUrl+"?search="+keyfield+"&newsPageNum="+keyword+"&recipePageNum="+ (endPage + 1) + "&qnaPageNum=" + addKey +">");
			}
			pagingHtmlRecipe.append("����");
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
