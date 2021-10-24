package com.util;

import java.util.Comparator;

import com.recipe.vo.RecipeVO;

public class ListComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		
		int news_comments_count1 = ((RecipeVO)o1).getNews_comments_count();
		int news_comments_count2 = ((RecipeVO)o2).getNews_comments_count();
		
		if(news_comments_count1 > news_comments_count2) {
			return -1;
		}else if(news_comments_count1 < news_comments_count2) {
			return 1;
		}else {
			return 0;
		}
	}


}
