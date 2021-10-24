/**
 * 
 */
package com.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.news.dao.NewsDAO;
import com.news.vo.NewsVO;
import com.qnaboard.vo.QnaBoardVO;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;
import com.util.DBUtil;
import com.util.StringUtil;

/**
 * @Package Name : com.main.dao
 * @FileName : mainDAO.java
 * @작성일 : 2021. 9. 13.
 * @작성자 : 신혜지
 * @프로그램 설명 : 통합 검색 출력 하기 위한 dao
 */
public class MainDAO {
   private static MainDAO instance = new MainDAO();

   public static MainDAO getInstance() {
      return instance;
   }

   private MainDAO() {
   }

   /**
    * @Method 메소드명 : searchNewsCount
    * @작성일 : 2021. 9. 13.
    * @작성자 : 신혜지
    * @Method 설명 : 뉴스 카운트 받아오기 위한 메서드
    */
   public int searchNewsCount(String search) throws Exception {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;
      // 뉴스 게시글 개수 보관
      int news_count = 0;
      try {
         conn = DBUtil.getConnection();
         sql = "select count(*) from news_board Where news_title Like ? or news_content like ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, '%' + search + '%');
         pstmt.setString(2, '%' + search + '%');
         rs = pstmt.executeQuery();
         if (rs.next()) {
            news_count = rs.getInt(1);
         }
      } catch (Exception e) {
         throw new Exception(e);
      } finally {
         // 자원정리
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return news_count;
   }

   /**
    * @Method 메소드명 : searchRecipeCount
    * @작성일 : 2021. 9. 13.
    * @작성자 : 신혜지
    * @Method 설명 :레시피 카운트 받아오기 위한 메서드
    */
   public int searchRecipeCount(String search) throws Exception {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;
      // 뉴스 게시글 개수 보관
      int recipe_count = 0;
      try {
         conn = DBUtil.getConnection();
         sql = "select count(*) from recipe_board b join member m on b.mem_num=m.mem_num Where title Like ? or content like ? or id like ? ";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, '%' + search + '%');
         pstmt.setString(2, '%' + search + '%');
         pstmt.setString(3, '%' + search + '%');
         rs = pstmt.executeQuery();
         if (rs.next()) {
            recipe_count = rs.getInt(1);
         }
      } catch (Exception e) {
         throw new Exception(e);
      } finally {
         // 자원정리
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return recipe_count;
   }

      public int searchQnaCount (String search) throws Exception {
         Connection conn = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         String sql = null;
         //고객센터 개시글 개수 보관
         int qna_count = 0;
         try {
            conn= DBUtil.getConnection();
            sql="select count(*) from qna_board Where qna_title Like ? or qna_content Like ? or qna_id Like ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, '%' + search + '%');
            pstmt.setString(2, '%' + search + '%');
            pstmt.setString(3, '%' + search + '%');
            rs = pstmt.executeQuery();
            if (rs.next()) {
               qna_count = rs.getInt(1);
            }
         }catch (Exception e) {
            throw new Exception(e);
         }finally {
            DBUtil.executeClose(rs, pstmt, conn);
         }
         return qna_count;
      }
      
      public List<QnaBoardVO> getQnaList(int startCount, int endCount, String search) throws Exception{
         Connection conn = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         String sql = null;
         List<QnaBoardVO> qnaList = null;
         
         try {
            conn = DBUtil.getConnection();
            sql="select * from (select a.*, rownum rnum from(select * from qna_board where qna_title like ? or qna_content like ? or qna_id like ? order by qna_num desc)a) where rnum >=? and rnum <=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + search + "%");
            pstmt.setString(2, "%" + search + "%");
            pstmt.setString(3, "%" + search + "%");
            pstmt.setInt(4, startCount);
            pstmt.setInt(5, endCount);
            rs=pstmt.executeQuery();
            qnaList = new ArrayList<QnaBoardVO>();
            while(rs.next()) {
               QnaBoardVO qnaboardVO = new QnaBoardVO();
               qnaboardVO.setQna_num(rs.getInt("qna_num"));
               qnaboardVO.setQna_title(rs.getString("qna_title"));
               qnaboardVO.setQna_passwd(rs.getString("qna_passwd"));
               qnaboardVO.setQna_id(rs.getString("qna_id"));
               qnaboardVO.setQna_date(rs.getDate("qna_date"));
               
               //자바빈(VO)을 ArrayList에 저장
               qnaList.add(qnaboardVO);
            }
         }catch (Exception e) {
            throw new Exception(e);
         } finally {
            DBUtil.executeClose(rs, pstmt, conn);
         }
         return qnaList;
      }
   /**
    * @Method 메소드명 : getNewsList
    * @작성일 : 2021. 9. 13.
    * @작성자 : 신혜지
    * @Method 설명 : 뉴스 리스트 받아오기 위한 메서드
    */
   public List<NewsVO> getNewsList(int startCount, int endCount, String search) throws Exception {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;
      List<NewsVO> newsList = null;
      NewsDAO dao = NewsDAO.getInstance();
      
      int news_comments_count = 0;

      try {
         conn = DBUtil.getConnection();
         sql = "select * from (select a.*, rownum rnum from \r\n"
               + "(select * from News_board  where news_title LIKE ? or news_content Like ? order by news_num desc) a) \r\n"
               + "where rnum >=? and rnum <=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%" + search + "%");
         pstmt.setString(2, "%" + search + "%");
         pstmt.setInt(3, startCount);
         pstmt.setInt(4, endCount);
         rs = pstmt.executeQuery();

         newsList = new ArrayList<NewsVO>();
         while (rs.next()) {
            news_comments_count = dao.getCommentsCount(rs.getInt("news_num"));
            NewsVO news = new NewsVO();
            news.setNews_num(rs.getInt("news_num"));
            news.setNews_title(rs.getString("news_title"));
            news.setNews_category(rs.getString("news_category"));
            news.setNews_content(rs.getString("news_content"));
            news.setId("관리자");
            news.setNews_date(rs.getDate("news_date"));
            news.setNews_modi(rs.getDate("news_modi"));
            news.setNews_hits(rs.getInt("news_hits"));
            news.setNews_comment_count(news_comments_count);
            
            newsList.add(news);
         }
      } catch (Exception e) {
         throw new Exception(e);
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return newsList;
   }

   /**
    * @Method 메소드명 : getRecipeList
    * @작성일 : 2021. 9. 13.
    * @작성자 : 신혜지
    * @Method 설명 : 레시피 리스트 받아오기 위한 메서드
    */
   public List<RecipeVO> getRecipeList(int startCount, int endCount, String search) throws Exception {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;
      List<RecipeVO> recipeList = null;
      RecipeDAO dao = RecipeDAO.getInstance();
      
      int news_comments_count = 0;   // 댓글 카운트 수
      
      try {
         conn = DBUtil.getConnection();
         sql="select * from (select a.*, rownum rnum from(select * from recipe_board b join member m on b.mem_num = m.mem_num  where title like ? or content like ? or id like ? order by b.board_num desc)a) where rnum >=? and rnum <=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%" + search + "%");
         pstmt.setString(2, "%" + search + "%");
         pstmt.setString(3, "%" + search + "%");
         pstmt.setInt(4, startCount);
         pstmt.setInt(5, endCount);
         rs = pstmt.executeQuery();
         recipeList = new ArrayList<RecipeVO>();
         
         while (rs.next()) {
            RecipeVO recipe = new RecipeVO();
            recipe.setBoard_num(rs.getInt("board_num"));
            recipe.setTitle(StringUtil.useNoHtml(rs.getString("title")));
            recipe.setContent(StringUtil.useBrNoHtml(rs.getString("content")));
            recipe.setHits(rs.getInt("hits"));
            recipe.setRecom_count(rs.getInt("recom_count"));
            recipe.setReport_date(rs.getDate("report_date"));
            recipe.setModify_date(rs.getDate("modify_date"));
            recipe.setIp(rs.getString("ip"));
            recipe.setFilename(rs.getString("filename"));
            recipe.setCategory(rs.getString("category"));
            recipe.setMem_num(rs.getInt("mem_num"));
            recipe.setId(rs.getString("id"));
            
            news_comments_count = dao.getRecipeReplyBoardCount(rs.getInt("board_num"));
            recipe.setNews_comments_count(news_comments_count); // 댓글 수 담기
            
            recipeList.add(recipe);
         }
      } catch (Exception e) {
         throw new Exception(e);
      } finally {
         // 자원정리
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return recipeList;
   }
}