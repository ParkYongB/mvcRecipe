package com.util;

public class QnAPagingUtil {
	private int startCount;	 // �� ���������� ������ �Խñ��� ���� ��ȣ
	private int endCount;	 // �� ���������� ������ �Խñ��� �� ��ȣ
	private StringBuffer pagingHtmlQna;// ������ ǥ�� ���ڿ�

	/**
	 * currentPage : ����������
	 * totalCount : ��ü �Խù� ��
	 * rowCount : �� ��������  �Խù��� ��
	 * pageCount : �� ȭ�鿡 ������ ������ ��
	 * pageUrl : ȣ�� ������ url
	 * addKey : �ΰ����� key ���� ���� null ó�� (&num=23�������� ������ ��)
	 * */
	public QnAPagingUtil(int currentPage, int totalCount, int rowCount,
			int pageCount, String pageUrl) {
		this(null,null,currentPage,totalCount,rowCount,pageCount,pageUrl,null);
	}
	public QnAPagingUtil(int currentPage, int totalCount, int rowCount,
			int pageCount, String pageUrl, String addKey) {
		this(null,null,currentPage,totalCount,rowCount,pageCount,pageUrl,addKey);
	}
	public QnAPagingUtil(String keyfield, String keyword, int currentPage, int totalCount, int rowCount,
			int pageCount,String pageUrl) {
		this(keyfield,keyword,currentPage,totalCount,rowCount,pageCount,pageUrl,null);
	}
	public QnAPagingUtil(String keyfield, String keyword, int currentPage, int totalCount, int rowCount,
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
		pagingHtmlQna = new StringBuffer();
		if (currentPage > pageCount) {
			if(keyword==null){//�˻� �̻���
				pagingHtmlQna.append("<a href="+pageUrl+"?qnaPageNum="+ (startPage - 1) + addKey +">");
			}else{
				pagingHtmlQna.append("<a href="+pageUrl+"?search="+keyfield+"&newsPageNum="+keyword+"&qnaPageNum="+ (startPage - 1) + "&recipePageNum=" + addKey +">");
			}
			pagingHtmlQna.append("����");
			pagingHtmlQna.append("</a>");
		}
		pagingHtmlQna.append("&nbsp;|&nbsp;");
		//������ ��ȣ.���� �������� ���������� �����ϰ� ��ũ�� ����.
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtmlQna.append("&nbsp;<b> <font color='red'>");
				pagingHtmlQna.append(i);
				pagingHtmlQna.append("</font></b>");
			} else {
				if(keyword==null){//�˻� �̻���
					pagingHtmlQna.append("&nbsp;<a href='"+pageUrl+"?qnaPageNum=");
				}else{
					pagingHtmlQna.append("&nbsp;<a class='qpaging-butn' href='"+pageUrl+"?search="+keyfield+"&newsPageNum="+keyword+"&qnaPageNum=");
				}
				pagingHtmlQna.append(i);
				pagingHtmlQna.append("&recipePageNum="+ addKey +"'>");
				pagingHtmlQna.append(i);
				pagingHtmlQna.append("</a>");
			}
			pagingHtmlQna.append("&nbsp;");
		}
		pagingHtmlQna.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
		// ���� block ������
		if (totalPage - startPage >= pageCount) {
			if(keyword==null){//�˻� �̻���
				pagingHtmlQna.append("<a href="+pageUrl+"?qnaPageNum="+ (endPage + 1) + addKey +">");
			}else{
				pagingHtmlQna.append("<a class='qpaging-butn' href="+pageUrl+"?search="+keyfield+"&newsPageNum="+keyword+"&qnaPageNum="+ (endPage + 1) + "&recipePageNum=" + addKey +">");
			}
			pagingHtmlQna.append("����");
			pagingHtmlQna.append("</a>");
		}
	}
	public StringBuffer getPagingHtml() {
		return pagingHtmlQna;
	}
	public int getStartCount() {
		return startCount;
	}
	public int getEndCount() {
		return endCount;
	}

}
