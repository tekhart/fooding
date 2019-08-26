<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
	String idlogin="";
%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>FOODING</title>

<style>
	#body{
	font-family:"Bauhaus ITC";
	}

	#topdiv
		{background-color:#FFBB00;
        height:40px;}
        
 	#menudiv
    	{background-color:white;
         height:100%;
         border-collapse:collapse;
         }
		
			.button11{
				background-color:#FFBB00;
				border:none;
				margin-right:-4.5px;
				color:#813D00;
				}
				
				.button2{
				margin-right:-9px;
				width:250px;
				height:85px;
				font-size:20px;
				background-color:white;
				border:none;
				
				}
				
			.button2:hover{background-color:#FFE08C;}
        
 	#maindiv
    	{background-color:#EAEAEA;
        height:0px;}
        #menutable{
			margin:0;
			padding:0px;
			border:0;
			height:85px;
			width:250px;
			border-collapse:collapse;
		}
	
        
	#bottomdiv
    	{background-color:#FFFFFF;
    	border-style:solid;
    	color:#FFBB00;
        height:300px;
        width:50%;
        float:left;
         border-collapse:collapse;
        }
        
        th, td{padding:10px;}
        
	#eventdiv
    	{background-color:#A6A6A6;
        height:300px;
        width:50%;
        display:inline-block;
        line-height: 200px;
        }
        
    .dotdot{margin-top:-25px;
			margin-right:10px;
			}
			
	.slide-text{
	
		position:relative;
		background-color:#FFFFFF;
		opacity:0.7;
		width:600px;
		height:600px;
		text-align:left;
		margin-top:-604px;
		text-align:center;
		
		font-size:30px;
		}
		
	.dot {
  		position:relative;
	
  		cursor: pointer;
  		height: 15px;
  		width: 15px;
  		margin: 0 2px;
  		background-color: #bbb;
  		border-radius: 50%;
  		display: inline-block;
  		transition: background-color 0.6s ease;
       	}    

	.active, .dot:hover {
  		background-color: #717171;
		}

	.fade {
  		-webkit-animation-name: fade;
  		-webkit-animation-duration: 1.5s;
  		animation-name: fade;
  		animation-duration: 1.5s;
		}

	@-webkit-keyframes fade {
  		from {opacity: .4} 
  		to {opacity: 1}
		}

	@keyframes fade {
  		from {opacity: .4} 
  		to {opacity: 1}
		}
</style>

</head>

<body id="body">

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
				<%=idlogin %>님 <input type="button" value="로그아웃"onClick="location.href='logout.jsp'">
				<%
			}
		}finally{}
		
		
		%>
		</td></tr>
  	</table>
</div>

<div id="menudiv" style=text-align:center;>
	<table width=100% height=100%>
    <tr><td width=100 nowrap>
		<img src="../img/fooding.png" height="60px" width="100px"></td>
		<td width=150 nowrap><font size="10px">FOODING</font></td><td>
		
		<table id="menutable" ><tr><td>
		<input type="button" class="button2" value="레시피"></td><td>
		<input type="button" class="button2" value="요리도우미"></td><td>
		<input type="button" class="button2" value="공지사항"></td><td>
    	<input type="button" class="button2" value="고객센터"></td></tr>
        </table>
        
        </td></tr>
 	</table>
		
</div>

<div id="maindiv">
	
    
	<div class="slideshow">

		<div class="mySlides fade">
  			<img src="../img/cake-1914463_1920.jpg" style="width:1500px; height:600px;">
  			<div class="slide-text"><br><br>
  			겨울느낌의 초코 파이
    	  	</div>
		</div>

		<div class="mySlides fade">
  			<img src="../img/egg-sandwich-2761894_1920.jpg"" style="width:1500px; height:600px;">
  			<div class="slide-text"><br><br>
  			계란과 토마토마토 토스트스트
       		</div>
		</div>

		<div class="mySlides fade">
  			<img src="../img/cupcakes-1850628_1920.jpg" style="width:1500px; height:600px;">
  			<div class="slide-text"><br><br>
  			예쁜 컵케잌과 말랑말랑 마쉬멜롱
        	</div>
		</div>

	</div>
	

	<div class="dotdot"style="text-align:right">
  	<span class="dot" onclick="currentSlide(1)"></span> 
  	<span class="dot" onclick="currentSlide(2)"></span> 
  	<span class="dot" onclick="currentSlide(3)"></span> 
	</div>

    <script>
        var slideIndex = 1;
        showSlides(slideIndex);

        function plusSlides(n) {
          showSlides(slideIndex += n);
        }

        function currentSlide(n) {
          showSlides(slideIndex = n);
        }

        function showSlides(n) {
          var i;
          var slides = document.getElementsByClassName("mySlides");
          var dots = document.getElementsByClassName("dot");
          if (n > slides.length) {slideIndex = 1}    
          if (n < 1) {slideIndex = slides.length}
          for (i = 0; i < slides.length; i++) {
              slides[i].style.display = "none";  
          }
          for (i = 0; i < dots.length; i++) {
              dots[i].className = dots[i].className.replace(" active", "");
          }
          slides[slideIndex-1].style.display = "block";  
          dots[slideIndex-1].className += " active";
        }
    </script>
</div>
<br><br><br>
<div="botevent">

<div id="bottomdiv">
	<table id="bottomdiv" border="1">
		<tr><th><font  color="black">오늘의 레시피 TOP 5</font></th><th><font  color="black">작성자</font></th></tr>
    	<tr><td><font  color="black">맛있는 토마토 피자 만들기</font></td><td><font  color="black">domino</font></td></tr>
        <tr><td><font  color="black">생일상에 빠질 수 없는 초코케이크!</font></td><td><font  color="black">bird2</font></td></tr>
        <tr><td><font  color="black">집에서 즐기는 미국의 맛~</font></td><td><font  color="black">trump</font></td></tr>
        <tr><td><font  color="black">연어덕후 소리질러~!</font></td><td><font  color="black">hihello</font></td></tr>
    </table>
</div>

<div id="eventdiv" align="center">
	이벤트 배너
</div>

</div>

<div id="footer" align="right" style="color:#cccccc; font-size:12px;">
Create by FOODING<br>
고객문의 1544-XXXX<br>
JSP Project 2019 2A03
</div>

</html>