<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String idlogin="";
	session.setAttribute("zipcode","");
%>

<div id="topdiv" style=text-align:center;>
	<table width=100% height=100%>
    <tr><td width=200 nowrap>
		</td><td width=200 nowrap>
		</td><td width=30 nowrap>
		<%
		try{
			idlogin=(String)session.getAttribute("idlogin");
			
			if(idlogin==null||idlogin.equals("")){
				%>
				<input type="button" class="button11" value="로그인" onClick="location.href='signin.jsp'">
				<input type="button" class="button11" value="회원가입" onClick="location.href='signup.jsp'">
				<%
			}else{
				%>
				<%=idlogin %>님 <input type="button" class="button11" value="로그아웃" onClick="location.href='logout.jsp'">
				<%
			}
		}finally{}
		
		
		%>
		</td></tr>
  	</table>
</div>

<div id="menudiv" style=text-align:center;>
	<table width=100% height=100%>
    	<tr>
    		<td width=100 nowrap>
				<a href=../mains/main.jsp><img src="../img/fooding.png" height="60px" width="100px"></a>
			</td>
			<td width=150 nowrap><font size="10px"><a href=../mains/main.jsp id="title">FOODING</a></font></td>
			<td>
				<center>
					<table id="menutable">
						<tr>
							<td><center>
								<div class="dropdown">
			
	            					<button class="dropbtn">레시피</button>
	            
	            					<div class="dropdown-content">
	            						<center>
		        							<a href="#">전체 레시피</a>
							        		<a href="#">인기 레시피</a>
							        		<a href="#">맞춤 레시피</a>
							       			<a href="#">레시피 작성</a>
						       			</center>
						       		</div>
	       						</div>
	       						</center>
	        				</td>
	        
	       					<td><center>
	        					<div class="dropdown">
	            					<button class="dropbtn">요리도우미</button>
	            					<div class="dropdown-content">
	            						<center>
		            						<a href="#">요리 강의</a>
									        <a href="#">초보용 레시피</a>
									        <a href="#">재료구매</a>
	            						</center>
						        	</div>
						        </div></center>
	        				</td>	
	        				<td><center>
						        <div class="dropdown">
						            <button class="dropbtn">공지사항</button>
						            <div class="dropdown-content">
							            <center>
							            	<a href="#">푸딩규칙</a>
									        <a href="#">이벤트</a>
							            </center>
						        	</div>
						        </div></center>
					        </td>
	        				<td><center>
	        					<div class="dropdown">
						            <button class="dropbtn">고객센터</button>
						            <div class="dropdown-content">
							            <center>
							             	<a href="#">자주하는 질문</a>
									        <a href="#">문의사항</a>
							            </center>
						        	</div>
						        </div></center>
	        				</td>
	        			</tr>
	        		</table>
				</center>
        	</td>
        </tr>
    </table>
</div>