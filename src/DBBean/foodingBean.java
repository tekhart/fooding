package DBBean;

import java.sql.*;
import java.util.*;

public class foodingBean {

	Connection con=null;
	Statement stmt=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	String str=null;
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(Exception e) {
			System.out.println("Driver Loading Error");
		}
	
		try {
			String jdbcUrl="jdbc:mysql://localhost:3306/fooding_db";
			String dbId="foodingid";
			String dbPass="foodingpw";
			
			con=DriverManager.getConnection(jdbcUrl,dbId,dbPass);
		}catch (Exception e) {
            e.printStackTrace();
		}
	}
	
	public ResultSet resultQuery(String sql) {
		DBclose();
		try {
			con = getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(sql);
		}catch(Exception e) {
            e.printStackTrace();
			rs=null;
		}
		return rs;
	}
	
	public void nonResultQuery(String sql) {
		DBclose();
		try {
			con = getConnection();
			stmt=con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception e) {
            e.printStackTrace();
		}finally {
			DBclose();
        }
	}
	
	public String findnkname(String id) {
		DBclose();
		try {
			con = getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select nkname from user where id='"+id+"';");
			if(rs.next()) {
				str=rs.getString("nkname");
			}
		}catch(Exception e) {
            e.printStackTrace();
			rs=null;
			str="";
		}finally {
			DBclose();
        }
		return str;
	}
	public void DBclose() {
		stmtClosing();
		pstmtClosing();
		resultclosing();
		DBClosing();
	}
	public void stmtClosing() {
		if(stmt!=null) {
			try {
				stmt.close();
			}catch(Exception e) {}
		}
	}
	public void pstmtClosing() {
		if(pstmt!=null) {
			try {
				pstmt.close();
			}catch(Exception e) {}
		}
	}
	public void resultclosing() {
		if(rs!=null) {
			try {
				rs.close();
			}catch(Exception e) {}
		}
	}
	
	public void DBClosing() {
		if(con!=null) {
			try {
				con.close();
			}catch(Exception e) {}
		}
	}
    
	private static foodingBean instance = new foodingBean();
    //.jsp�럹�씠吏��뿉�꽌 DB�뿰�룞鍮덉씤 recipesDBBean�겢�옒�뒪�쓽 硫붿냼�뱶�뿉 �젒洹쇱떆 �븘�슂
    public static foodingBean getInstance() {
        return instance;
    }
	
    //而ㅻ꽖�뀡��濡쒕��꽣 Connection媛앹껜瑜� �뼸�뼱�깂
    private Connection getConnection() throws Exception {
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(Exception e) {
			System.out.println("Driver Loading Error");
		}
    	String jdbcUrl="jdbc:mysql://localhost:3306/fooding_db";
		String dbId="foodingid";
		String dbPass="foodingpw";
		
        return DriverManager.getConnection(jdbcUrl,dbId,dbPass);
    }
 
    //recipes�뀒�씠釉붿뿉 湲��쓣 異붽�(insert臾�)<=writePro.jsp�럹�씠吏��뿉�꽌 �궗�슜
    public void insertArticle(BoardDataBean article,int fame) 
            throws Exception {
		DBclose();
        con = null;
        pstmt = null;
		rs = null;

		int num=article.getNum();
		int number=0;
        String sql="";

        try {
            con = getConnection();

            pstmt = con.prepareStatement("select max(num) from recipes");
			rs = pstmt.executeQuery();
			
			if (rs.next())
		      number=rs.getInt(1)+1;
		    else
		      number=1; 
		   
		    
            // 荑쇰━瑜� �옉�꽦
            sql = "insert into recipes(title,contury,foodtype,ingredients,tools,writerid,reg_date,content";
		    sql+=") values(?,?,?,?,?,?,?,?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getContury());
            pstmt.setString(3, article.getFoodtype());
            pstmt.setString(4, article.getIngredients());
            pstmt.setString(5, article.getTools());
            pstmt.setString(6, article.getWriterid());
			pstmt.setTimestamp(7, article.getReg_date());
            pstmt.setString(8, article.getContent());
			
            pstmt.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
    }
    public int getArticleCount(String type,String search,int fame)
            throws Exception {
		DBclose();
       con = null;
       pstmt = null;
       rs = null;
       String sql="";

       int x=0;

       try {
           con = getConnection();
           
           if(type.equals("제목")) {
        	   sql="select * from recipes  where (contury like '%"+search+"%' or foodtype like '%"+search+"%' or title like '%"+search+"%') ";
           }else if(type.equals("글쓴이")) {
        	   sql="select * from recipes  where writerid in(select id from user where nkname like '%"+search+"%') ";
           }else if(type.equals("재료")) {
        	   sql="select * from recipes  where ingredients like '%"+search+"%'";
           }else if(type.equals("도구")) {
        	   sql="select * from recipes  where tools like '%"+search+"%'";
           }
           if(fame==1) {
        	   sql+=" and reg_date>=(select date_add(now(),INTERVAL -1 DAY) from dual)";
           }
           pstmt = con.prepareStatement(sql);
           rs = pstmt.executeQuery();
           if (rs.next()) {
              x= rs.getInt(1);
            }
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           DBclose();
       }
        return x;
   }
    public List<BoardDataBean> getArticles(int start, int end,String type,String search,int fame)
            throws Exception {
		DBclose();
       con = null;
       pstmt = null;
       rs = null;
       String sql="";
       List<BoardDataBean> articleList=null;
       try {
           con = getConnection();
           
           if(type.equals("제목")) {
        	   sql="select * from recipes  where (contury like '%"+search+"%' or foodtype like '%"+search+"%' or title like '%"+search+"%') ";
           }else if(type.equals("글쓴이")) {
        	   sql="select * from recipes  where writerid in(select id from user where nkname like '%"+search+"%') ";
           }else if(type.equals("재료")) {
        	   sql="select * from recipes  where ingredients like '%"+search+"%'";
           }else if(type.equals("도구")) {
        	   sql="select * from recipes  where tools like '%"+search+"%'";
           }
           if(fame==1) {
        	   sql+="and reg_date>=(select date_add(now(),INTERVAL -1 DAY) from dual) order by readcount desc limit ?,?";
           }else {
        	   sql+="order by num desc limit ?,?";
           }
           pstmt = con.prepareStatement(sql);
           pstmt.setInt(1, start-1);
           pstmt.setInt(2, end);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               articleList = new ArrayList<BoardDataBean>(end);
               do{
                 BoardDataBean article= new BoardDataBean();
                 article.setNum(rs.getInt("num"));
                 article.setContury(rs.getString("contury"));
                 article.setFoodtype(rs.getString("foodtype"));
                  article.setTitle(rs.getString("title"));
                  article.setWriterid(rs.getString("writerid"));
                  article.setReg_date(rs.getTimestamp("reg_date"));
                  article.setReadcount(rs.getInt("readcount"));

                 article.setContent(rs.getString("content"));


                 articleList.add(article);
                }while(rs.next());
            }
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           DBclose();
       }
        return articleList;
   }
    public int getArticleCount(int fame)
             throws Exception {
		DBclose();
        con = null;
        pstmt = null;
        rs = null;
        String sql="";

        int x=0;

        try {
            con = getConnection();
            
            sql="select count(*) from recipes";
            if(fame==1) {
         	   sql+="where reg_date>=(select date_add(now(),INTERVAL -1 DAY) from dual)";
            }else {}

            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
               x= rs.getInt(1);
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
		return x;
    }
	public List<BoardDataBean> getArticles(int start, int end,int fame)
            throws Exception {
		DBclose();
       con = null;
       pstmt = null;
       rs = null;
       String sql="";
       List<BoardDataBean> articleList=null;
       try {
           con = getConnection();
           
           sql="select * from recipes";
           if(fame==1) {
        	   sql+="where reg_date>=(select date_add(now(),INTERVAL -1 DAY) from dual) order by readcount desc limit ?,?";
           }else {}
           
           pstmt = con.prepareStatement(sql);
           pstmt.setInt(1, start-1);
			pstmt.setInt(2, end);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               articleList = new ArrayList<BoardDataBean>(end);
               do{
                 BoardDataBean article= new BoardDataBean();
                 article.setNum(rs.getInt("num"));
                 article.setContury(rs.getString("contury"));
                 article.setFoodtype(rs.getString("foodtype"));
				  article.setTitle(rs.getString("title"));
				  article.setWriterid(rs.getString("writerid"));
                 
                 
			      article.setReg_date(rs.getTimestamp("reg_date"));
				  article.setReadcount(rs.getInt("readcount"));
                 
                 article.setContent(rs.getString("content"));
			       
				  
                 articleList.add(article);
			    }while(rs.next());
			}
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
    	   DBclose();
       }
		return articleList;
   }
	public BoardDataBean getArticle(int num)
            throws Exception {
		DBclose();
        con = null;
        pstmt = null;
        rs = null;
        BoardDataBean article=null;
        try {
            con = getConnection();

            pstmt = con.prepareStatement(
            	"update recipes set readcount=readcount+1 where num = ?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

            pstmt = con.prepareStatement(
            	"select * from recipes where num = ?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                article = new BoardDataBean();
                article.setNum(rs.getInt("num"));
				article.setTitle(rs.getString("title"));
                article.setContury(rs.getString("contury"));
                article.setFoodtype(rs.getString("foodtype"));
                article.setIngredients(rs.getString("ingredients"));
			    article.setTools(rs.getString("tools"));
				article.setWriterid(rs.getString("writerid"));
                article.setReg_date(rs.getTimestamp("reg_date"));
			    article.setReadcount(rs.getInt("readcount"));     
                article.setContent(rs.getString("content"));
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
		return article;
    }
	public BoardDataBean updateGetArticle(int num)
	          throws Exception {
		DBclose();
	        con = null;
	        pstmt = null;
	        rs = null;
	        BoardDataBean article=null;
	        try {
	            con = getConnection();

	            pstmt = con.prepareStatement(
	            	"select * from recipes where num = ?");
	            pstmt.setInt(1, num);
	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	            	article = new BoardDataBean();
	                article.setNum(rs.getInt("num"));
					article.setTitle(rs.getString("title"));
	                article.setContury(rs.getString("contury"));
	                article.setFoodtype(rs.getString("foodtype"));
	                article.setIngredients(rs.getString("ingredients"));
				    article.setTools(rs.getString("tools"));
					article.setWriterid(rs.getString("writerid"));
	                article.setReg_date(rs.getTimestamp("reg_date"));
				    article.setReadcount(rs.getInt("readcount"));     
	                article.setContent(rs.getString("content"));     
				}
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        } finally {
	        	DBclose();
	        }
			return article;
	    }
    public int updateArticle(BoardDataBean article)
            throws Exception {
		DBclose();
          con = null;
          pstmt = null;
          rs= null;

          String sql="";
  		int x=-1;
          try {
              con = getConnection();

  			 
                  sql="update recipes set title=?,contury=?,foodtype=?,ingredients=?";
  			    sql+=",tools=? ,content=? where num=?";
                  pstmt = con.prepareStatement(sql);

                  pstmt.setString(1, article.getTitle());
                  pstmt.setString(2, article.getContury());
                  pstmt.setString(3, article.getFoodtype());
                  pstmt.setString(4, article.getIngredients());
                  pstmt.setString(5, article.getTools());
                  pstmt.setString(6, article.getContent());
  			    pstmt.setInt(7, article.getNum());
                  pstmt.executeUpdate();
  				x= 1;
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
        	  DBclose();
          }
  		return x;
      }
    public int deleteArticle(int num)
          throws Exception {
		DBclose();
          con = null;
          pstmt = null;
          rs= null;
          int x=-1;
          try {
  			con = getConnection();

  					pstmt = con.prepareStatement(
              	      "delete from recipes where num=?");
                      pstmt.setInt(1, num);
                      pstmt.executeUpdate();
  					x= 1; //湲��궘�젣 �꽦怨�
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
        	  DBclose();
          }
  		return x;
      }
    public void insertCommentsArticle(commentDataBean article,int rootin) 
              throws Exception {
		DBclose();
          con = null;
          pstmt = null;
  		  rs = null;
  		
  		int num=article.getNum();
		int ref=article.getRef();
		int re_step=article.getRe_step();
		int re_level=article.getRe_level();
		int number=0;

          String sql="";

          try {
              con = getConnection();

              pstmt = con.prepareStatement("select max(num) from recipe_comment");
  			rs = pstmt.executeQuery();
  			
  			if (rs.next())
  		      number=rs.getInt(1)+1;
  		    else
  		      number=1; 
  			
  			if (num!=0) {  
  		      sql="update recipe_comment set re_step=re_step+1 ";
  		      sql += "where ref=? and re_step >? and rootin=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, ref);
                pstmt.setInt(2, re_step);
                pstmt.setInt(3, rootin);
  			  pstmt.executeUpdate();
  			  re_step=re_step+1;
  			  re_level=re_level+1;
  		     }else{
  		  	  ref=number;
  			  re_step=0;
  			  re_level=0;
  		     }	
  		   
  		    
              sql = "insert into recipe_comment(rootin,writerid,reg_date,ref,re_step,re_level,content";
  		    sql+=") values(?,?,?,?,?,?,?)";

              pstmt = con.prepareStatement(sql);
              pstmt.setInt(1, rootin);
              pstmt.setString(2, article.getWriterid());
              pstmt.setTimestamp(3, article.getReg_date());
              pstmt.setInt(4, ref);
              pstmt.setInt(5, re_step);
              pstmt.setInt(6, re_level);
              pstmt.setString(7, article.getContent());
  			
              pstmt.executeUpdate();
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
          	DBclose();
          }
      }
    public List<commentDataBean> getCommentsArticles(int start, int end,int num)
              throws Exception {
		DBclose();
         con = null;
         pstmt = null;
         rs = null;
         List<commentDataBean> articleList=null;
         try {
             con = getConnection();
             
             pstmt = con.prepareStatement(
             	"select * from recipe_comment where rootin=? order by ref desc, re_step asc limit ?,? ");
             pstmt.setInt(1, num);
             pstmt.setInt(2, start-1);
  			 pstmt.setInt(3, end);
             rs = pstmt.executeQuery();

             if (rs.next()) {
                 articleList = new ArrayList<commentDataBean>(end);
                 do{
                	 commentDataBean article= new commentDataBean();
                	 article.setNum(rs.getInt("num"));
                	 article.setRootin(rs.getInt("rootin"));
                	 article.setWriterid(rs.getString("writerid"));
                	 article.setReg_date(rs.getTimestamp("reg_date"));
                	 article.setRef(rs.getInt("ref"));
                	 article.setRe_step(rs.getInt("re_step"));
                	 article.setRe_level(rs.getInt("re_level"));
                	 article.setContent(rs.getString("content"));
                   
  			       
  				  
                   articleList.add(article);
  			    }while(rs.next());
  			}
         } catch(Exception ex) {
             ex.printStackTrace();
         } finally {
      	   DBclose();
         }
  		return articleList;
     }
    public int getCommentArticleCount(int num)
              throws Exception {
		DBclose();
         con = null;
         pstmt = null;
         rs = null;

         int x=0;

         try {
             con = getConnection();
             
             pstmt = con.prepareStatement("select count(*) from recipe_comment where rootin=?");
             pstmt.setInt(1, num);
             rs = pstmt.executeQuery();

             if (rs.next()) {
                x= rs.getInt(1);
 			}
         } catch(Exception ex) {
             ex.printStackTrace();
         } finally {
         	DBclose();
         }
 		return x;
     }

    public void insertexrecipeArticle(BoardDataBean article) 
            throws Exception {
		DBclose();
        con = null;
        pstmt = null;
		rs = null;

		int num=article.getNum();
		int number=0;
        String sql="";

        try {
            con = getConnection();

            pstmt = con.prepareStatement("select max(num) from exrecipe");
			rs = pstmt.executeQuery();
			
			if (rs.next())
		      number=rs.getInt(1)+1;
		    else
		      number=1; 
		   
		    
            // 荑쇰━瑜� �옉�꽦
            sql = "insert into exrecipe(title,contury,foodtype,ingredients,tools,writerid,reg_date,content,difficulty";
		    sql+=") values(?,?,?,?,?,?,?,?,?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getContury());
            pstmt.setString(3, article.getFoodtype());
            pstmt.setString(4, article.getIngredients());
            pstmt.setString(5, article.getTools());
            pstmt.setString(6, article.getWriterid());
			pstmt.setTimestamp(7, article.getReg_date());
            pstmt.setString(8, article.getContent());
            pstmt.setString(9, article.getDifficulty());
			
            pstmt.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
    }
    public int getexrecipeArticleCount(String search)
            throws Exception {
		DBclose();
       con = null;
       pstmt = null;
       rs = null;

       int x=0;

       try {
           con = getConnection();

           pstmt = con.prepareStatement("select count(*) from exrecipe  where (contury like '%"+search+"%' or foodtype like '%"+search+"%' or title like '%"+search+"%' or writerid in(select id from user where nkname like '%"+search+"%'))");
           rs = pstmt.executeQuery();

           if (rs.next()) {
              x= rs.getInt(1);
            }
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           DBclose();
       }
        return x;
   }
    public List<BoardDataBean> getexrecipeArticles(int start, int end,String search)
            throws Exception {
		DBclose();
       con = null;
       pstmt = null;
       rs = null;
       List<BoardDataBean> articleList=null;
       try {
           con = getConnection();

           pstmt = con.prepareStatement(
               "select * from exrecipe where (contury like '%"+search+"%' or foodtype like '%"+search+"%' or title like '%"+search+"%' or writerid in(select id from user where nkname like '%"+search+"%')) order by num desc limit ?,? ");
           pstmt.setInt(1, start-1);
           pstmt.setInt(2, end);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               articleList = new ArrayList<BoardDataBean>(end);
               do{
            	   	BoardDataBean article= new BoardDataBean();
					article.setNum(rs.getInt("num"));
					article.setContury(rs.getString("contury"));
					article.setFoodtype(rs.getString("foodtype"));
					article.setTitle(rs.getString("title"));
					article.setWriterid(rs.getString("writerid"));
					article.setReg_date(rs.getTimestamp("reg_date"));
					article.setReadcount(rs.getInt("readcount"));
					article.setContent(rs.getString("content"));
					article.setDifficulty(rs.getString("difficulty"));
                 articleList.add(article);
                }while(rs.next());
            }
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           DBclose();
       }
        return articleList;
   }
    public int getexrecipeArticleCount()
             throws Exception {
		DBclose();
        con = null;
        pstmt = null;
        rs = null;

        int x=0;

        try {
            con = getConnection();
            
            pstmt = con.prepareStatement("select count(*) from exrecipe");
            rs = pstmt.executeQuery();

            if (rs.next()) {
               x= rs.getInt(1);
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
		return x;
    }
	public List<BoardDataBean> getexrecipeArticles(int start, int end)
            throws Exception {
		DBclose();
       con = null;
       pstmt = null;
       rs = null;
       List<BoardDataBean> articleList=null;
       try {
           con = getConnection();
           
           pstmt = con.prepareStatement(
           	"select * from exrecipe order by num desc limit ?,? ");
           pstmt.setInt(1, start-1);
			pstmt.setInt(2, end);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               articleList = new ArrayList<BoardDataBean>(end);
               do{
                 BoardDataBean article= new BoardDataBean();
                 article.setNum(rs.getInt("num"));
                 article.setContury(rs.getString("contury"));
                 article.setFoodtype(rs.getString("foodtype"));
				 article.setTitle(rs.getString("title"));
				 article.setWriterid(rs.getString("writerid"));
			     article.setReg_date(rs.getTimestamp("reg_date"));
				 article.setReadcount(rs.getInt("readcount"));
                 article.setContent(rs.getString("content"));
                 article.setDifficulty(rs.getString("difficulty"));
                 
                 articleList.add(article);
			    }while(rs.next());
			}
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
    	   DBclose();
       }
		return articleList;
   }
	public BoardDataBean getexrecipeArticle(int num)
            throws Exception {
		DBclose();
        con = null;
        pstmt = null;
        rs = null;
        BoardDataBean article=null;
        try {
            con = getConnection();

            pstmt = con.prepareStatement(
            	"update exrecipe set readcount=readcount+1 where num = ?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

            pstmt = con.prepareStatement(
            	"select * from exrecipe where num = ?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                article = new BoardDataBean();
                article.setNum(rs.getInt("num"));
				article.setTitle(rs.getString("title"));
                article.setContury(rs.getString("contury"));
                article.setFoodtype(rs.getString("foodtype"));
                article.setIngredients(rs.getString("ingredients"));
			    article.setTools(rs.getString("tools"));
				article.setWriterid(rs.getString("writerid"));
                article.setReg_date(rs.getTimestamp("reg_date"));
			    article.setReadcount(rs.getInt("readcount"));     
                article.setContent(rs.getString("content"));
                article.setDifficulty(rs.getString("difficulty"));
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
		return article;
    }
	public BoardDataBean updateexrecipeGetArticle(int num)
	          throws Exception {
	        con = null;
	        pstmt = null;
	        rs = null;
	        BoardDataBean article=null;
	        try {
	            con = getConnection();

	            pstmt = con.prepareStatement(
	            	"select * from exrecipe where num = ?");
	            pstmt.setInt(1, num);
	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	            	article = new BoardDataBean();
	                article.setNum(rs.getInt("num"));
					article.setTitle(rs.getString("title"));
	                article.setContury(rs.getString("contury"));
	                article.setFoodtype(rs.getString("foodtype"));
	                article.setIngredients(rs.getString("ingredients"));
				    article.setTools(rs.getString("tools"));
					article.setWriterid(rs.getString("writerid"));
	                article.setReg_date(rs.getTimestamp("reg_date"));
				    article.setReadcount(rs.getInt("readcount"));     
	                article.setContent(rs.getString("content"));     
	                article.setDifficulty(rs.getString("difficulty"));
				}
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        } finally {
	        	DBclose();
	        }
			return article;
	    }
    public int updateexrecipeArticle(BoardDataBean article)
            throws Exception {
          con = null;
          pstmt = null;
          rs= null;

          String sql="";
  		int x=-1;
          try {
              con = getConnection();

  			 
                  sql="update exrecipe set title=?,contury=?,foodtype=?,ingredients=?";
  			    sql+=",tools=? ,content=?,difficulty=? where num=?";
                  pstmt = con.prepareStatement(sql);

                  pstmt.setString(1, article.getTitle());
                  pstmt.setString(2, article.getContury());
                  pstmt.setString(3, article.getFoodtype());
                  pstmt.setString(4, article.getIngredients());
                  pstmt.setString(5, article.getTools());
                  pstmt.setString(6, article.getContent());
                  pstmt.setString(7, article.getDifficulty());
  			      pstmt.setInt(8, article.getNum());

                  pstmt.executeUpdate();
  				x= 1;
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
        	  DBclose();
          }
  		return x;
      }
    public int deleteexrecipeArticle(int num)
          throws Exception {
          con = null;
          pstmt = null;
          rs= null;
          int x=-1;
          try {
  			con = getConnection();

  					pstmt = con.prepareStatement(
              	      "delete from exrecipe where num=?");
                      pstmt.setInt(1, num);
                      pstmt.executeUpdate();
  					x= 1; //湲��궘�젣 �꽦怨�
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
        	  DBclose();
          }
  		return x;
      }
    public void insertexrecipeCommentsArticle(commentDataBean article,int rootin) 
              throws Exception {
          con = null;
          pstmt = null;
  		  rs = null;
  		
  		int num=article.getNum();
		int ref=article.getRef();
		int re_step=article.getRe_step();
		int re_level=article.getRe_level();
		int number=0;

          String sql="";

          try {
              con = getConnection();

              pstmt = con.prepareStatement("select max(num) from exrecipe_comment");
  			rs = pstmt.executeQuery();
  			
  			if (rs.next())
  		      number=rs.getInt(1)+1;
  		    else
  		      number=1; 
  			
  			if (num!=0) {  
  		      sql="update exrecipe_comment set re_step=re_step+1 ";
  		      sql += "where ref=? and re_step >? and rootin=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, ref);
                pstmt.setInt(2, re_step);
                pstmt.setInt(3, rootin);
  			  pstmt.executeUpdate();
  			  re_step=re_step+1;
  			  re_level=re_level+1;
  		     }else{
  		  	  ref=number;
  			  re_step=0;
  			  re_level=0;
  		     }	
  		   
  		    
              // 荑쇰━瑜� �옉�꽦
              sql = "insert into exrecipe_comment(rootin,writerid,reg_date,ref,re_step,re_level,content";
  		    sql+=") values(?,?,?,?,?,?,?)";

              pstmt = con.prepareStatement(sql);
              pstmt.setInt(1, rootin);
              pstmt.setString(2, article.getWriterid());
              pstmt.setTimestamp(3, article.getReg_date());
              pstmt.setInt(4, ref);
              pstmt.setInt(5, re_step);
              pstmt.setInt(6, re_level);
              pstmt.setString(7, article.getContent());
  			
              pstmt.executeUpdate();
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
          	DBclose();
          }
      }
    public List<commentDataBean> getexrecipeCommentsArticles(int start, int end,int num)
              throws Exception {
         con = null;
         pstmt = null;
         rs = null;
         List<commentDataBean> articleList=null;
         try {
             con = getConnection();
             
             pstmt = con.prepareStatement(
             	"select * from exrecipe_comment where rootin=? order by ref desc, re_step asc limit ?,? ");
             pstmt.setInt(1, num);
             pstmt.setInt(2, start-1);
  			 pstmt.setInt(3, end);
             rs = pstmt.executeQuery();

             if (rs.next()) {
                 articleList = new ArrayList<commentDataBean>(end);
                 do{
                	 commentDataBean article= new commentDataBean();
                	 article.setNum(rs.getInt("num"));
                	 article.setRootin(rs.getInt("rootin"));
                	 article.setWriterid(rs.getString("writerid"));
                	 article.setReg_date(rs.getTimestamp("reg_date"));
                	 article.setRef(rs.getInt("ref"));
                	 article.setRe_step(rs.getInt("re_step"));
                	 article.setRe_level(rs.getInt("re_level"));
                	 article.setContent(rs.getString("content"));
  				  
                   articleList.add(article);
  			    }while(rs.next());
  			}
         } catch(Exception ex) {
             ex.printStackTrace();
         } finally {
      	   DBclose();
         }
  		return articleList;
     }
    public int getexrecipeCommentArticleCount(int num)
              throws Exception {
         con = null;
         pstmt = null;
         rs = null;

         int x=0;

         try {
             con = getConnection();
             
             pstmt = con.prepareStatement("select count(*) from exrecipe_comment where rootin=?");
             pstmt.setInt(1, num);
             rs = pstmt.executeQuery();

             if (rs.next()) {
                x= rs.getInt(1);
 			}
         } catch(Exception ex) {
             ex.printStackTrace();
         } finally {
         	DBclose();
         }
 		return x;
     }
    
    public void insertcookhelpArticle(BoardDataBean article) 
            throws Exception {
        con = null;
        pstmt = null;
		rs = null;

		int num=article.getNum();
		int number=0;
        String sql="";

        try {
            con = getConnection();

            pstmt = con.prepareStatement("select max(num) from cookhelp");
			rs = pstmt.executeQuery();
			
			if (rs.next())
		      number=rs.getInt(1)+1;
		    else
		      number=1; 
		   
		    
            // 荑쇰━瑜� �옉�꽦
            sql = "insert into cookhelp(title,contury,foodtype,ingredients,tools,writerid,reg_date,content";
		    sql+=") values(?,?,?,?,?,?,?,?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getContury());
            pstmt.setString(3, article.getFoodtype());
            pstmt.setString(4, article.getIngredients());
            pstmt.setString(5, article.getTools());
            pstmt.setString(6, article.getWriterid());
			pstmt.setTimestamp(7, article.getReg_date());
            pstmt.setString(8, article.getContent());
			
            pstmt.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
    }
    public int getcookhelpArticleCount(String search)
            throws Exception {
       con = null;
       pstmt = null;
       rs = null;

       int x=0;

       try {
           con = getConnection();

           pstmt = con.prepareStatement("select count(*) from cookhelp  where (contury like '%"+search+"%' or foodtype like '%"+search+"%' or title like '%"+search+"%' or writerid in(select id from user where nkname like '%"+search+"%'))");
           rs = pstmt.executeQuery();

           if (rs.next()) {
              x= rs.getInt(1);
            }
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           DBclose();
       }
        return x;
   }
    public List<BoardDataBean> getcookhelpArticles(int start, int end,String search)
            throws Exception {
       con = null;
       pstmt = null;
       rs = null;
       List<BoardDataBean> articleList=null;
       try {
           con = getConnection();

           pstmt = con.prepareStatement(
               "select * from cookhelp where (contury like '%"+search+"%' or foodtype like '%"+search+"%' or title like '%"+search+"%' or writerid in(select id from user where nkname like '%"+search+"%')) order by num desc limit ?,? ");
           pstmt.setInt(1, start-1);
           pstmt.setInt(2, end);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               articleList = new ArrayList<BoardDataBean>(end);
               do{
                 BoardDataBean article= new BoardDataBean();
                article.setNum(rs.getInt("num"));
                article.setContury(rs.getString("contury"));
                article.setFoodtype(rs.getString("foodtype"));
                article.setTitle(rs.getString("title"));
				article.setWriterid(rs.getString("writerid"));
				article.setReg_date(rs.getTimestamp("reg_date"));
				article.setReadcount(rs.getInt("readcount"));
				article.setContent(rs.getString("content"));


                 articleList.add(article);
                }while(rs.next());
            }
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           DBclose();
       }
        return articleList;
   }
    public int getcookhelpArticleCount()
             throws Exception {
        con = null;
        pstmt = null;
        rs = null;

        int x=0;

        try {
            con = getConnection();
            
            pstmt = con.prepareStatement("select count(*) from cookhelp");
            rs = pstmt.executeQuery();

            if (rs.next()) {
               x= rs.getInt(1);
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
		return x;
    }
	public List<BoardDataBean> getcookhelpArticles(int start, int end)
            throws Exception {
       con = null;
       pstmt = null;
       rs = null;
       List<BoardDataBean> articleList=null;
       try {
           con = getConnection();
           
           pstmt = con.prepareStatement(
           	"select * from cookhelp order by num desc limit ?,? ");
           pstmt.setInt(1, start-1);
			pstmt.setInt(2, end);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               articleList = new ArrayList<BoardDataBean>(end);
               do{
                BoardDataBean article= new BoardDataBean();
                article.setNum(rs.getInt("num"));
                article.setContury(rs.getString("contury"));
                article.setFoodtype(rs.getString("foodtype"));
                article.setTitle(rs.getString("title"));
 				article.setWriterid(rs.getString("writerid"));
 				article.setReg_date(rs.getTimestamp("reg_date"));
 				article.setReadcount(rs.getInt("readcount"));
 				article.setContent(rs.getString("content"));
			       
				  
                 articleList.add(article);
			    }while(rs.next());
			}
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
    	   DBclose();
       }
		return articleList;
   }
	public BoardDataBean getcookhelpArticle(int num)
            throws Exception {
        con = null;
        pstmt = null;
        rs = null;
        BoardDataBean article=null;
        try {
            con = getConnection();

            pstmt = con.prepareStatement(
            	"update cookhelp set readcount=readcount+1 where num = ?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

            pstmt = con.prepareStatement(
            	"select * from cookhelp where num = ?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                article = new BoardDataBean();
                
                article.setNum(rs.getInt("num"));
				article.setTitle(rs.getString("title"));
                article.setContury(rs.getString("contury"));
                article.setFoodtype(rs.getString("foodtype"));
                article.setIngredients(rs.getString("ingredients"));
			    article.setTools(rs.getString("tools"));
				article.setWriterid(rs.getString("writerid"));
                article.setReg_date(rs.getTimestamp("reg_date"));
			    article.setReadcount(rs.getInt("readcount"));     
                article.setContent(rs.getString("content"));
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
		return article;
    }
	public BoardDataBean updatecookhelpGetArticle(int num)
	          throws Exception {
	        con = null;
	        pstmt = null;
	        rs = null;
	        BoardDataBean article=null;
	        try {
	            con = getConnection();

	            pstmt = con.prepareStatement(
	            	"select * from cookhelp where num = ?");
	            pstmt.setInt(1, num);
	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	            	article = new BoardDataBean();

	                article.setNum(rs.getInt("num"));
					article.setTitle(rs.getString("title"));
	                article.setContury(rs.getString("contury"));
	                article.setFoodtype(rs.getString("foodtype"));
	                article.setIngredients(rs.getString("ingredients"));
				    article.setTools(rs.getString("tools"));
					article.setWriterid(rs.getString("writerid"));
	                article.setReg_date(rs.getTimestamp("reg_date"));
				    article.setReadcount(rs.getInt("readcount"));     
	                article.setContent(rs.getString("content"));
				}
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        } finally {
	        	DBclose();
	        }
			return article;
	    }
    public int updatecookhelpArticle(BoardDataBean article)
            throws Exception {
          con = null;
          pstmt = null;
          rs= null;

          String sql="";
  		int x=-1;
          try {
              con = getConnection();

  			 
                  sql="update cookhelp set title=?,contury=?,foodtype=?,difficulty=?,ingredients=?";
  			    sql+=",tools=? ,content=? where num=?";
                  pstmt = con.prepareStatement(sql);

                  pstmt.setString(1, article.getTitle());
                  pstmt.setString(2, article.getContury());
                  pstmt.setString(3, article.getFoodtype());
                  pstmt.setString(4, article.getDifficulty());
                  pstmt.setString(5, article.getIngredients());
                  pstmt.setString(6, article.getTools());
                  pstmt.setString(7, article.getContent());
  			      pstmt.setInt(8, article.getNum());
                  pstmt.executeUpdate();
                  
  				  x= 1;
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
        	  DBclose();
          }
  		return x;
      }
    public int deletecookhelpArticle(int num)
          throws Exception {
          con = null;
          pstmt = null;
          rs= null;
          int x=-1;
          try {
  			con = getConnection();

  					pstmt = con.prepareStatement(
              	      "delete from cookhelp where num=?");
                      pstmt.setInt(1, num);
                      pstmt.executeUpdate();
  					x= 1; //湲��궘�젣 �꽦怨�
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
        	  DBclose();
          }
  		return x;
      }
    public void insertcookhelpCommentsArticle(commentDataBean article,int rootin) 
              throws Exception {
          con = null;
          pstmt = null;
  		  rs = null;
  		
  		int num=article.getNum();
		int ref=article.getRef();
		int re_step=article.getRe_step();
		int re_level=article.getRe_level();
		int number=0;

          String sql="";

          try {
              con = getConnection();

              pstmt = con.prepareStatement("select max(num) from cookhelp_comment");
  			rs = pstmt.executeQuery();
  			
  			if (rs.next())
  		      number=rs.getInt(1)+1;
  		    else
  		      number=1; 
  			
  			if (num!=0) {  
  		      sql="update cookhelp_comment set re_step=re_step+1 ";
  		      sql += "where ref=? and re_step >? and rootin=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, ref);
                pstmt.setInt(2, re_step);
                pstmt.setInt(3, rootin);
  			  pstmt.executeUpdate();
  			  re_step=re_step+1;
  			  re_level=re_level+1;
  		     }else{
  		  	  ref=number;
  			  re_step=0;
  			  re_level=0;
  		     }	
  		   
  		    
              // 荑쇰━瑜� �옉�꽦
              sql = "insert into cookhelp_comment(rootin,writerid,reg_date,ref,re_step,re_level,content";
  		    sql+=") values(?,?,?,?,?,?,?)";

              pstmt = con.prepareStatement(sql);
              pstmt.setInt(1, rootin);
              pstmt.setString(2, article.getWriterid());
              pstmt.setTimestamp(3, article.getReg_date());
              pstmt.setInt(4, ref);
              pstmt.setInt(5, re_step);
              pstmt.setInt(6, re_level);
              pstmt.setString(7, article.getContent());
  			
              pstmt.executeUpdate();
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
          	DBclose();
          }
      }
    public List<commentDataBean> getcookhelpCommentsArticles(int start, int end,int num)
              throws Exception {
         con = null;
         pstmt = null;
         rs = null;
         List<commentDataBean> articleList=null;
         try {
             con = getConnection();
             
             pstmt = con.prepareStatement(
             	"select * from cookhelp_comment where rootin=? order by ref desc, re_step asc limit ?,? ");
             pstmt.setInt(1, num);
             pstmt.setInt(2, start-1);
  			 pstmt.setInt(3, end);
             rs = pstmt.executeQuery();

             if (rs.next()) {
                 articleList = new ArrayList<commentDataBean>(end);
                 do{
                	 commentDataBean article= new commentDataBean();
                	 article.setNum(rs.getInt("num"));
                	 article.setRootin(rs.getInt("rootin"));
                	 article.setWriterid(rs.getString("writerid"));
                	 article.setReg_date(rs.getTimestamp("reg_date"));
                	 article.setRef(rs.getInt("ref"));
                	 article.setRe_step(rs.getInt("re_step"));
                	 article.setRe_level(rs.getInt("re_level"));
                	 article.setContent(rs.getString("content"));
                   
  			       
  				  
                   articleList.add(article);
  			    }while(rs.next());
  			}
         } catch(Exception ex) {
             ex.printStackTrace();
         } finally {
      	   DBclose();
         }
  		return articleList;
     }
    public int getcookhelpCommentArticleCount(int num)
              throws Exception {
         con = null;
         pstmt = null;
         rs = null;

         int x=0;

         try {
             con = getConnection();
             
             pstmt = con.prepareStatement("select count(*) from cookhelp_comment where rootin=?");
             pstmt.setInt(1, num);
             rs = pstmt.executeQuery();

             if (rs.next()) {
                x= rs.getInt(1);
 			}
         } catch(Exception ex) {
             ex.printStackTrace();
         } finally {
         	DBclose();
         }
 		return x;
     }

    public void insertquestionArticle(QuestionDataBean article) 
            throws Exception {
        con = null;
        pstmt = null;
		rs = null;

		int num=article.getNum();
		int ref=article.getRef();
		int re_step=article.getRe_step();
		int re_level=article.getRe_level();
		int number=0;

          String sql="";

          try {
              con = getConnection();

              pstmt = con.prepareStatement("select max(num) from recipe_comment");
  			rs = pstmt.executeQuery();
  			
  			if (rs.next())
  		      number=rs.getInt(1)+1;
  		    else
  		      number=1; 
  			
  			if (num!=0) {  
  		      sql="update recipe_comment set re_step=re_step+1 ";
  		      sql += "where ref=? and re_step >?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, ref);
                pstmt.setInt(2, re_step);
                
  			  pstmt.executeUpdate();
  			  re_step=re_step+1;
  			  re_level=re_level+1;
  		     }else{
  		  	  ref=number;
  			  re_step=0;
  			  re_level=0;
  		     }	
		   
		    
            sql = "insert into question(title,writerid,quesType,content,reg_date,ref,re_step,re_level";
		    sql+=") values(?,?,?,?,?,?,?,?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getWriterid());
            pstmt.setString(3, article.getQuesType());
            pstmt.setString(4, article.getContent());
            
			pstmt.setTimestamp(5, article.getReg_date());
			pstmt.setInt(6, ref);
			pstmt.setInt(7, re_step);
			pstmt.setInt(8, re_level);
			
            pstmt.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
    }
    public int getquestionArticleCount(String search)
            throws Exception {
       con = null;
       pstmt = null;
       rs = null;

       int x=0;

       try {
           con = getConnection();

           pstmt = con.prepareStatement("select count(*) from question  where (contury like '%"+search+"%' or foodtype like '%"+search+"%' or title like '%"+search+"%' or writerid in(select id from user where nkname like '%"+search+"%'))");
           rs = pstmt.executeQuery();

           if (rs.next()) {
              x= rs.getInt(1);
            }
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           DBclose();
       }
        return x;
   }
    public List<QuestionDataBean> getquestionArticles(int start, int end,String search)
            throws Exception {
       con = null;
       pstmt = null;
       rs = null;
       List<QuestionDataBean> articleList=null;
       try {
           con = getConnection();

           pstmt = con.prepareStatement(
               "select * from question where (contury like '%"+search+"%' or foodtype like '%"+search+"%' or title like '%"+search+"%' or writerid in(select id from user where nkname like '%"+search+"%')) order by ref desc, re_step asc limit ?,? ");
           pstmt.setInt(1, start-1);
           pstmt.setInt(2, end);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               articleList = new ArrayList<QuestionDataBean>(end);
               do{
                 QuestionDataBean article= new QuestionDataBean();
                 article.setNum(rs.getInt("num"));
                 article.setTitle(rs.getString("title"));
                 article.setWriterid(rs.getString("writerid"));
                 article.setQuesType(rs.getString("quesType"));
                 article.setContent(rs.getString("content"));
                 article.setIsComplete(rs.getInt("isComplete"));
                 article.setReg_date(rs.getTimestamp("reg_date"));
                 article.setRef(rs.getInt("ref"));
                 article.setRe_step(rs.getInt("re_step"));
                 article.setRe_level(rs.getInt("re_level"));

                 articleList.add(article);
                }while(rs.next());
            }
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           DBclose();
       }
        return articleList;
   }
    public int getquestionArticleCount()
             throws Exception {
        con = null;
        pstmt = null;
        rs = null;

        int x=0;

        try {
            con = getConnection();
            
            pstmt = con.prepareStatement("select count(*) from question");
            rs = pstmt.executeQuery();

            if (rs.next()) {
               x= rs.getInt(1);
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
		return x;
    }
	public List<QuestionDataBean> getquestionArticles(int start, int end)
            throws Exception {
       con = null;
       pstmt = null;
       rs = null;
       List<QuestionDataBean> articleList=null;
       try {
           con = getConnection();
           
           pstmt = con.prepareStatement(
           	"select * from question order by ref desc, re_step asc limit ?,? ");
           pstmt.setInt(1, start-1);
			pstmt.setInt(2, end);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               articleList = new ArrayList<QuestionDataBean>(end);
               do{
                 QuestionDataBean article= new QuestionDataBean();
                 article.setNum(rs.getInt("num"));
                 article.setTitle(rs.getString("title"));
                 article.setWriterid(rs.getString("writerid"));
                 article.setQuesType(rs.getString("quesType"));
                 article.setContent(rs.getString("content"));
                 article.setIsComplete(rs.getInt("isComplete"));
                 article.setReg_date(rs.getTimestamp("reg_date"));
                 article.setRef(rs.getInt("ref"));
                 article.setRe_step(rs.getInt("re_step"));
                 article.setRe_level(rs.getInt("re_level"));
			       
				  
                 articleList.add(article);
			    }while(rs.next());
			}
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
    	   DBclose();
       }
		return articleList;
   }
	public QuestionDataBean getquestionArticle(int num)
            throws Exception {
        con = null;
        pstmt = null;
        rs = null;
        QuestionDataBean article=null;
        try {
            con = getConnection();

            pstmt = con.prepareStatement(
            	"update question set readcount=readcount+1 where num = ?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

            pstmt = con.prepareStatement(
            	"select * from question where num = ?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                article = new QuestionDataBean();
                article.setNum(rs.getInt("num"));
                article.setTitle(rs.getString("title"));
                article.setWriterid(rs.getString("writerid"));
                article.setQuesType(rs.getString("quesType"));
                article.setContent(rs.getString("content"));
                article.setIsComplete(rs.getInt("isComplete"));
                article.setReg_date(rs.getTimestamp("reg_date"));
                article.setRef(rs.getInt("ref"));
                article.setRe_step(rs.getInt("re_step"));
                article.setRe_level(rs.getInt("re_level"));
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
		return article;
    }
	public QuestionDataBean updatequestionGetArticle(int num)
	          throws Exception {
	        con = null;
	        pstmt = null;
	        rs = null;
	        QuestionDataBean article=null;
	        try {
	            con = getConnection();

	            pstmt = con.prepareStatement(
	            	"select * from question where num = ?");
	            pstmt.setInt(1, num);
	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	            	 article = new QuestionDataBean();
	            	 article.setNum(rs.getInt("num"));
	                 article.setTitle(rs.getString("title"));
	                 article.setWriterid(rs.getString("writerid"));
	                 article.setQuesType(rs.getString("quesType"));
	                 article.setContent(rs.getString("content"));
	                 article.setIsComplete(rs.getInt("isComplete"));
	                 article.setReg_date(rs.getTimestamp("reg_date"));
	                 article.setRef(rs.getInt("ref"));
	                 article.setRe_step(rs.getInt("re_step"));
	                 article.setRe_level(rs.getInt("re_level"));
				}
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        } finally {
	        	DBclose();
	        }
			return article;
	    }
    public int updatequestionArticle(QuestionDataBean article)
            throws Exception {
          con = null;
          pstmt = null;
          rs= null;

          String sql="";
  		int x=-1;
          try {
              con = getConnection();

  			 
                  sql="update question set title=?,contury=?,foodtype=?,ingredients=?";
  			    sql+=",tools=? ,content=? where num=?";
                  pstmt = con.prepareStatement(sql);

                  pstmt.setString(1, article.getTitle());
                  pstmt.setString(2, article.getWriterid());
                  pstmt.setString(3, article.getQuesType());
                  pstmt.setString(4, article.getContent());
                  pstmt.setInt(5, article.getIsComplete());
      			pstmt.setTimestamp(6, article.getReg_date());
      			pstmt.setInt(7, article.getRef());
      			pstmt.setInt(8, article.getRe_step());
      			pstmt.setInt(9, article.getRe_level());
  			    pstmt.setInt(7, article.getNum());
                  pstmt.executeUpdate();
  				x= 1;
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
        	  DBclose();
          }
  		return x;
      }
    public int deletequestionArticle(int num)
          throws Exception {
          con = null;
          pstmt = null;
          rs= null;
          int x=-1;
          try {
  			con = getConnection();

  					pstmt = con.prepareStatement(
              	      "delete from question where num=?");
                      pstmt.setInt(1, num);
                      pstmt.executeUpdate();
  					x= 1; 
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
        	  DBclose();
          }
  		return x;
      }
    public void insertquestionCommentsArticle(commentDataBean article,int rootin) 
              throws Exception {
          con = null;
          pstmt = null;
  		  rs = null;
  		
  		int num=article.getNum();
		int ref=article.getRef();
		int re_step=article.getRe_step();
		int re_level=article.getRe_level();
		int number=0;

          String sql="";

          try {
              con = getConnection();

              pstmt = con.prepareStatement("select max(num) from question_comment");
  			rs = pstmt.executeQuery();
  			
  			if (rs.next())
  		      number=rs.getInt(1)+1;
  		    else
  		      number=1; 
  			
  			if (num!=0) {  
  		      sql="update question_comment set re_step=re_step+1 ";
  		      sql += "where ref=? and re_step >? and rootin=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, ref);
                pstmt.setInt(2, re_step);
                pstmt.setInt(3, rootin);
  			  pstmt.executeUpdate();
  			  re_step=re_step+1;
  			  re_level=re_level+1;
  		     }else{
  		  	  ref=number;
  			  re_step=0;
  			  re_level=0;
  		     }	
  		   
  		    
           
              sql = "insert into question_comment(rootin,writerid,reg_date,ref,re_step,re_level,content";
  		    sql+=") values(?,?,?,?,?,?,?)";

              pstmt = con.prepareStatement(sql);
              pstmt.setInt(1, rootin);
              pstmt.setString(2, article.getWriterid());
              pstmt.setTimestamp(3, article.getReg_date());
              pstmt.setInt(4, ref);
              pstmt.setInt(5, re_step);
              pstmt.setInt(6, re_level);
              pstmt.setString(7, article.getContent());
  			
              pstmt.executeUpdate();
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
          	DBclose();
          }
      }
    public List<commentDataBean> getquestionCommentsArticles(int start, int end,int num)
              throws Exception {
         con = null;
         pstmt = null;
         rs = null;
         List<commentDataBean> articleList=null;
         try {
             con = getConnection();
             
             pstmt = con.prepareStatement(
             	"select * from question_comment where rootin=? order by ref desc, re_step asc limit ?,? ");
             pstmt.setInt(1, num);
             pstmt.setInt(2, start-1);
  			 pstmt.setInt(3, end);
             rs = pstmt.executeQuery();

             if (rs.next()) {
                 articleList = new ArrayList<commentDataBean>(end);
                 do{
                	 commentDataBean article= new commentDataBean();
                	 article.setNum(rs.getInt("num"));
                	 article.setRootin(rs.getInt("rootin"));
                	 article.setWriterid(rs.getString("writerid"));
                	 article.setReg_date(rs.getTimestamp("reg_date"));
                	 article.setRef(rs.getInt("ref"));
                	 article.setRe_step(rs.getInt("re_step"));
                	 article.setRe_level(rs.getInt("re_level"));
                	 article.setContent(rs.getString("content"));
                   
  			       
  				  
                   articleList.add(article);
  			    }while(rs.next());
  			}
         } catch(Exception ex) {
             ex.printStackTrace();
         } finally {
      	   DBclose();
         }
  		return articleList;
     }
    public int getquestionCommentArticleCount(int num)
              throws Exception {
         con = null;
         pstmt = null;
         rs = null;

         int x=0;

         try {
             con = getConnection();
             
             pstmt = con.prepareStatement("select count(*) from question_comment where rootin=?");
             pstmt.setInt(1, num);
             rs = pstmt.executeQuery();

             if (rs.next()) {
                x= rs.getInt(1);
 			}
         } catch(Exception ex) {
             ex.printStackTrace();
         } finally {
         	DBclose();
         }
 		return x;
     }
    
    public void insertannounceArticle(announceDataBean article) 
            throws Exception {
        con = null;
        pstmt = null;
		rs = null;

		int num=article.getNum();
		int number=0;
		int periode=Integer.parseInt(article.getPeriode());
        String sql="";

        try {
            con = getConnection();

            pstmt = con.prepareStatement("select max(num) from announce");
			rs = pstmt.executeQuery();
			
			if (rs.next())
		      number=rs.getInt(1)+1;
		    else
		      number=1; 
		   
		    
            sql = "insert into announce(title,writerid,isEvent,reg_date,content,periode";
		    sql+=") values(?,?,?,?,?,?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getWriterid());
            pstmt.setString(3, article.getIsEvent());
			pstmt.setTimestamp(4, article.getReg_date());
            pstmt.setString(5, article.getContent());
            pstmt.setString(6, article.getPeriode());
			
            pstmt.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
    }
    public int getannounceArticleCount(String search)
            throws Exception {
       con = null;
       pstmt = null;
       rs = null;

       int x=0;

       try {
           con = getConnection();

           pstmt = con.prepareStatement("select count(*) from announce  where (title like '%"+search+"%' or writerid in(select id from user where nkname like '%"+search+"%'))");
           rs = pstmt.executeQuery();

           if (rs.next()) {
              x= rs.getInt(1);
            }
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           DBclose();
       }
        return x;
   }
    public List<announceDataBean> getannounceArticles(int start, int end,String search)
            throws Exception {
       con = null;
       pstmt = null;
       rs = null;
       List<announceDataBean> articleList=null;
       try {
           con = getConnection();

           pstmt = con.prepareStatement(
               "select * from announce where (title like '%"+search+"%' or writerid in(select id from user where nkname like '%"+search+"%')) order by num desc limit ?,? ");
           pstmt.setInt(1, start-1);
           pstmt.setInt(2, end);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               articleList = new ArrayList<announceDataBean>(end);
               do{
                 announceDataBean article= new announceDataBean();
                article.setNum(rs.getInt("num"));
                article.setTitle(rs.getString("title"));
                article.setIsEvent(rs.getString("isEvent"));
				article.setWriterid(rs.getString("writerid"));
				article.setReg_date(rs.getTimestamp("reg_date"));
				article.setReadcount(rs.getInt("readcount"));
				article.setContent(rs.getString("content"));


                 articleList.add(article);
                }while(rs.next());
            }
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           DBclose();
       }
        return articleList;
   }
    public int getannounceArticleCount()
             throws Exception {
        con = null;
        pstmt = null;
        rs = null;

        int x=0;

        try {
            con = getConnection();
            
            pstmt = con.prepareStatement("select count(*) from announce");
            rs = pstmt.executeQuery();

            if (rs.next()) {
               x= rs.getInt(1);
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
		return x;
    }
	public List<announceDataBean> getannounceArticles(int start, int end)
            throws Exception {
       con = null;
       pstmt = null;
       rs = null;
       List<announceDataBean> articleList=null;
       try {
           con = getConnection();
           
           pstmt = con.prepareStatement(
           	"select * from announce order by num desc limit ?,? ");
           pstmt.setInt(1, start-1);
			pstmt.setInt(2, end);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               articleList = new ArrayList<announceDataBean>(end);
               do{
                announceDataBean article= new announceDataBean();
                article.setNum(rs.getInt("num"));
                article.setTitle(rs.getString("title"));
                article.setIsEvent(rs.getString("isEvent"));
				article.setWriterid(rs.getString("writerid"));
				article.setReg_date(rs.getTimestamp("reg_date"));
				article.setReadcount(rs.getInt("readcount"));
				article.setContent(rs.getString("content"));
			       
				  
                 articleList.add(article);
			    }while(rs.next());
			}
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
    	   DBclose();
       }
		return articleList;
   }
	public announceDataBean getannounceArticle(int num)
            throws Exception {
        con = null;
        pstmt = null;
        rs = null;
        announceDataBean article=null;
        try {
            con = getConnection();

            pstmt = con.prepareStatement(
            	"update announce set readcount=readcount+1 where num = ?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

            pstmt = con.prepareStatement(
            	"select * from announce where num = ?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                article = new announceDataBean();
                
                article.setNum(rs.getInt("num"));
                article.setTitle(rs.getString("title"));
                article.setIsEvent(rs.getString("isEvent"));
				article.setWriterid(rs.getString("writerid"));
				article.setReg_date(rs.getTimestamp("reg_date"));
				article.setReadcount(rs.getInt("readcount"));
				article.setContent(rs.getString("content"));
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	DBclose();
        }
		return article;
    }
	public announceDataBean updateannounceGetArticle(int num)
	          throws Exception {
	        con = null;
	        pstmt = null;
	        rs = null;
	        announceDataBean article=null;
	        try {
	            con = getConnection();

	            pstmt = con.prepareStatement(
	            	"select * from announce where num = ?");
	            pstmt.setInt(1, num);
	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	            	article = new announceDataBean();

	            	article.setNum(rs.getInt("num"));
	                article.setTitle(rs.getString("title"));
	                article.setIsEvent(rs.getString("isEvent"));
					article.setWriterid(rs.getString("writerid"));
					article.setReg_date(rs.getTimestamp("reg_date"));
					article.setReadcount(rs.getInt("readcount"));
					article.setContent(rs.getString("content"));
				}
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        } finally {
	        	DBclose();
	        }
			return article;
	    }
    public int updateannounceArticle(announceDataBean article)
            throws Exception {
          con = null;
          pstmt = null;
          rs= null;

          String sql="";
  		int x=-1;
          try {
              con = getConnection();

  			 
                  sql="update announce set title=?,isEvent=?,periode=?,content=? where num=?";
                  pstmt = con.prepareStatement(sql);

                  pstmt.setString(1, article.getTitle());
                  pstmt.setString(2, article.getIsEvent());
                  pstmt.setString(3, article.getPeriode());
                  pstmt.setString(4, article.getContent());
  			      pstmt.setInt(5, article.getNum());
                  pstmt.executeUpdate();
                  
  				  x= 1;
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
        	  DBclose();
          }
  		return x;
      }
    public int deleteannounceArticle(int num)
          throws Exception {
          con = null;
          pstmt = null;
          rs= null;
          int x=-1;
          try {
  			con = getConnection();

  					pstmt = con.prepareStatement(
              	      "delete from announce where num=?");
                      pstmt.setInt(1, num);
                      pstmt.executeUpdate();
  					x= 1; //湲��궘�젣 �꽦怨�
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
        	  DBclose();
          }
  		return x;
      }
    public void insertannounceCommentsArticle(commentDataBean article,int rootin) 
              throws Exception {
          con = null;
          pstmt = null;
  		  rs = null;
  		
  		int num=article.getNum();
		int ref=article.getRef();
		int re_step=article.getRe_step();
		int re_level=article.getRe_level();
		int number=0;

          String sql="";

          try {
              con = getConnection();

              pstmt = con.prepareStatement("select max(num) from announce_comment");
  			rs = pstmt.executeQuery();
  			
  			if (rs.next())
  		      number=rs.getInt(1)+1;
  		    else
  		      number=1; 
  			
  			if (num!=0) {  
  		      sql="update announce_comment set re_step=re_step+1 ";
  		      sql += "where ref=? and re_step >? and rootin=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, ref);
                pstmt.setInt(2, re_step);
                pstmt.setInt(3, rootin);
  			  pstmt.executeUpdate();
  			  re_step=re_step+1;
  			  re_level=re_level+1;
  		     }else{
  		  	  ref=number;
  			  re_step=0;
  			  re_level=0;
  		     }	
  		   
  		    
              // 荑쇰━瑜� �옉�꽦
              sql = "insert into announce_comment(rootin,writerid,reg_date,ref,re_step,re_level,content";
  		    sql+=") values(?,?,?,?,?,?,?)";

              pstmt = con.prepareStatement(sql);
              pstmt.setInt(1, rootin);
              pstmt.setString(2, article.getWriterid());
              pstmt.setTimestamp(3, article.getReg_date());
              pstmt.setInt(4, ref);
              pstmt.setInt(5, re_step);
              pstmt.setInt(6, re_level);
              pstmt.setString(7, article.getContent());
  			
              pstmt.executeUpdate();
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
          	DBclose();
          }
      }
    public List<commentDataBean> getannounceCommentsArticles(int start, int end,int num)
              throws Exception {
         con = null;
         pstmt = null;
         rs = null;
         List<commentDataBean> articleList=null;
         try {
             con = getConnection();
             
             pstmt = con.prepareStatement(
             	"select * from announce_comment where rootin=? order by ref desc, re_step asc limit ?,? ");
             pstmt.setInt(1, num);
             pstmt.setInt(2, start-1);
  			 pstmt.setInt(3, end);
             rs = pstmt.executeQuery();

             if (rs.next()) {
                 articleList = new ArrayList<commentDataBean>(end);
                 do{
                	 commentDataBean article= new commentDataBean();
                	 article.setNum(rs.getInt("num"));
                	 article.setRootin(rs.getInt("rootin"));
                	 article.setWriterid(rs.getString("writerid"));
                	 article.setReg_date(rs.getTimestamp("reg_date"));
                	 article.setRef(rs.getInt("ref"));
                	 article.setRe_step(rs.getInt("re_step"));
                	 article.setRe_level(rs.getInt("re_level"));
                	 article.setContent(rs.getString("content"));
                   
  			       
  				  
                   articleList.add(article);
  			    }while(rs.next());
  			}
         } catch(Exception ex) {
             ex.printStackTrace();
         } finally {
      	   DBclose();
         }
  		return articleList;
     }
    public int getannounceCommentArticleCount(int num)
            throws Exception {
       con = null;
       pstmt = null;
       rs = null;

       int x=0;

       try {
           con = getConnection();
           
           pstmt = con.prepareStatement("select count(*) from cookhelp_comment where rootin=?");
           pstmt.setInt(1, num);
           rs = pstmt.executeQuery();

           if (rs.next()) {
              x= rs.getInt(1);
			}
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
       	DBclose();
       }
		return x;
   }
}