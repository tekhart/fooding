<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="../img/favicon.ico">
<link rel="icon" href="../img/favicon.ico">
<link rel="stylesheet" href="../css/common.css">
<link rel="stylesheet" href="../css/list.css">

<meta charset="UTF-8">

<title>맞춤 레시피</title>

</head>
<body id="body">
	<%@include file="../general_included/topbar.jsp"%>
	
	<div id="maindiv">
	<div class="writetitle1">맞춤 레시피</div>

<br>
<br><br>


	<table border="1" class="customtb" >
	<tr><td class="customfirst">상황별 요리</td>
	<td class="customfirst">나라별 요리</td>
	<td class="customfirst">재료별 요리</td>
	<td class="customfirst">조리법별 요리</td>
	<td class="customfirst">도구별 요리</td></tr>
	<tr><td class="customsecond">
		<label><input type="checkbox" value="간식">간식<br></label>
		<label><input type="checkbox" value="야식">야식<br></label>
		<label><input type="checkbox" value="안주">안주<br></label>
		<label><input type="checkbox" value="해장">해장<br></label>
		<label><input type="checkbox" value="접대">접대<br></label>
		<label><input type="checkbox" value="나들이">나들이<br></label>
		<label><input type="checkbox" value="파티">파티<br></label>
		<label><input type="checkbox" value="명절">명절<br></label>
		<label><input type="checkbox" value="실생활">실생활<br></label>
</td><td class="customsecond">
		<label><input type="checkbox" value="한식">한식<br></label>
		<label><input type="checkbox" value="중식">중식<br></label>
		<label><input type="checkbox" value="일식">일식<br></label>
		<label><input type="checkbox" value="동남아/인도">동남아/인도<br></label>
		<label><input type="checkbox" value="멕시칸">멕시칸<br></label>
		<label><input type="checkbox" value="양식">양식<br></label>
		<label><input type="checkbox" value="퓨전">퓨전<br></label>
		<label><input type="checkbox" value="이국적인">이국적인<br></label>
</td><td class="customsecond">
		<label><input type="checkbox" value="육류">육류<br></label>
		<label><input type="checkbox" value="채소류">채소류<br></label>
		<label><input type="checkbox" value="해산물">해산물<br></label>
		<label><input type="checkbox" value="콩/두부">콩/두부<br></label>
		<label><input type="checkbox" value="과일">과일<br></label>
		<label><input type="checkbox" value="달걀/유제퓸">달걀/유제퓸<br></label>
		<label><input type="checkbox" value="만두">만두<br></label>
		<label><input type="checkbox" value="밀가루">밀가루<br></label>
		<label><input type="checkbox" value="김치">김치<br></label>
		<label><input type="checkbox" value="가공식품">가공식품<br></label>
</td><td class="customsecond">
		<label><input type="checkbox" value="밥">밥<br></label>
		<label><input type="checkbox" value="면">면<br></label>
		<label><input type="checkbox" value="국물">국물<br></label>
		<label><input type="checkbox" value="찜/조리/구이">찜/조리/구이<br></label>
		<label><input type="checkbox" value="볶음/튀김/부침">볶음/튀김/부침<br></label>
		<label><input type="checkbox" value="나물/샐러드">나물/샐러드<br></label>
		<label><input type="checkbox" value="김장/절임">김장/절임<br></label>
		<label><input type="checkbox" value="베이킹/디저트">베이킹/디저트<br></label>
		<label><input type="checkbox" value="양념/소스/잼">양념/소스/잼<br></label>
		<label><input type="checkbox" value="음료/차/커피">음료/차/커피<br></label>
</td><td class="customsecond">
		<label><input type="checkbox" value="칼">칼<br></label>
		<label><input type="checkbox" value="믹서기">믹서기<br></label>
		<label><input type="checkbox" value="가스레인지">가스레인지<br></label>
		<label><input type="checkbox" value="냄비">냄비<br></label>
		<label><input type="checkbox" value="오븐">오븐<br></label>
		<label><input type="checkbox" value="전자레인지">전자레인지<br></label>
		<label><input type="checkbox" value="냉장고">냉장고<br></label>
		<label><input type="checkbox" value="타이머">타이머<br></label>
</td></tr>


<tr><td colspan="4" class="customsecond">
<ul id="myUL">
  
</ul></td><td class="customsecond"><input type="submit" value="검색" class="bt"  onclick="location.href='content.jsp?num=40&pageNum=6&fame=0'"></td></tr>


<tr><td colspan="5" class="customsecond">

<table style="width:465px; margin:auto;"><tr>
	<td>  
		<input type="text" name="myCountry" placeholder="검색..." class="searchbar" id="myInput">
	</td>
								
		<!--<td class="addBtn">
			<div > 
  				<span >추가</span>
			</div>
		</td>  -->
		</tr>

</table>

 

</td></tr>
</table>

<script>

//Create a "close" button and append it to each list item
var myNodelist = document.getElementsByTagName("text");
var i;
for (i = 0; i < myNodelist.length; i++) {
var span = document.createElement("SPAN");
var txt = document.createTextNode("\u00D7");
span.className = "close";
span.appendChild(txt);
myNodelist[i].appendChild(span);
}

//Click on a close button to hide the current list item
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
close[i].onclick = function() {
  var div = this.parentElement;
  div.style.display = "none";
}
}

//Create a new list item when clicking on the "Add" button
function newElement() {
var li = document.createElement("div");
var inputValue = document.getElementById("myInput").value;
var t = document.createTextNode(inputValue);
li.appendChild(t);
if (inputValue === '') {
  alert("재료나 도구를 검색하세요");
} else {
  document.getElementById("myUL").appendChild(li);
}
document.getElementById("myInput").value = "";

var span = document.createElement("SPAN");
var txt = document.createTextNode("\u00D7");
span.className = "close";
span.appendChild(txt);
li.appendChild(span);

for (i = 0; i < close.length; i++) {
  close[i].onclick = function() {
    var div = this.parentElement;
    div.style.display = "none";
  }
}
}




function autocomplete(inp, arr) {
	  /*the autocomplete function takes two arguments,
	  the text field element and an array of possible autocompleted values:*/
	  var currentFocus;
	  /*execute a function when someone writes in the text field:*/
	  inp.addEventListener("input", function(e) {
	      var a, b, i, val = this.value;
	      /*close any already open lists of autocompleted values*/
	      closeAllLists();
	      if (!val) { return false;}
	      currentFocus = -1;
	      /*create a DIV element that will contain the items (values):*/
	      a = document.createElement("DIV");
	      a.setAttribute("id", this.id + "autocomplete-list");
	      a.setAttribute("class", "autocomplete-items");
	      /*append the DIV element as a child of the autocomplete container:*/
	      this.parentNode.appendChild(a);
	      /*for each item in the array...*/
	      for (i = 0; i < arr.length; i++) {
	        /*check if the item starts with the same letters as the text field value:*/
	        if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
	          /*create a DIV element for each matching element:*/
	          b = document.createElement("DIV");
	          /*make the matching letters bold:*/
	          b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
	          b.innerHTML += arr[i].substr(val.length);
	          /*insert a input field that will hold the current array item's value:*/
	          b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
	          /*execute a function when someone clicks on the item value (DIV element):*/
	          b.addEventListener("click", function(e) {
	              /*insert the value for the autocomplete text field:*/
	              inp.value = this.getElementsByTagName("input")[0].value;
	              /*close the list of autocompleted values,
	              (or any other open lists of autocompleted values:*/
	              closeAllLists();
	          });
	          a.appendChild(b);
	        }
	      }
	  });
	  /*execute a function presses a key on the keyboard:*/
	  inp.addEventListener("keydown", function(e) {
	      var x = document.getElementById(this.id + "autocomplete-list");
	      if (x) x = x.getElementsByTagName("div");
	      if (e.keyCode == 40) {
	        /*If the arrow DOWN key is pressed,
	        increase the currentFocus variable:*/
	        currentFocus++;
	        /*and and make the current item more visible:*/
	        addActive(x);
	      } else if (e.keyCode == 38) { //up
	        /*If the arrow UP key is pressed,
	        decrease the currentFocus variable:*/
	        currentFocus--;
	        /*and and make the current item more visible:*/
	        addActive(x);
	      } else if (e.keyCode == 13) {
	        /*If the ENTER key is pressed, prevent the form from being submitted,*/
	        e.preventDefault();
	        if (currentFocus > -1) {
	          /*and simulate a click on the "active" item:*/
	          if (x) x[currentFocus].click();
	        }
	      }
	  });
	  function addActive(x) {
	    /*a function to classify an item as "active":*/
	    if (!x) return false;
	    /*start by removing the "active" class on all items:*/
	    removeActive(x);
	    if (currentFocus >= x.length) currentFocus = 0;
	    if (currentFocus < 0) currentFocus = (x.length - 1);
	    /*add class "autocomplete-active":*/
	    x[currentFocus].classList.add("autocomplete-active");
	  }
	  function removeActive(x) {
	    /*a function to remove the "active" class from all autocomplete items:*/
	    for (var i = 0; i < x.length; i++) {
	      x[i].classList.remove("autocomplete-active");
	    }
	  }
	  function closeAllLists(elmnt) {
	    /*close all autocomplete lists in the document,
	    except the one passed as an argument:*/
	    var x = document.getElementsByClassName("autocomplete-items");
	    for (var i = 0; i < x.length; i++) {
	      if (elmnt != x[i] && elmnt != inp) {
	        x[i].parentNode.removeChild(x[i]);
	      }
	    }
	  }
	  /*execute a function when someone clicks in the document:*/
	  document.addEventListener("click", function (e) {
	      closeAllLists(e.target);
	  });
	}

	/*An array containing all the country names in the world:*/
	var countries = ["아이스크림","당근","두부","콩","치킨","피자","짜장면","탕수육","밀가루","피망","박력분","오이","참치","양상추","양배추","감자","고구마","귤","곤약","스파게티","블루베리","꿀","파인애플","사과","오렌지","한라봉","멜론","수박","바나나","고기","키위","목살","안심","등심","삼겹살","양파","토마토","대파","쪽파","감","아보카도","토스트","참외","방울토마토","밤","복숭아","살구","자두","앵두","망고","딸기","물","무화과","자몽","피스타치오","연어","시나몬","계피","땅콩","가지","호박","브로콜리","상추","깻잎","대추","배추","시금치","참기름","부추","죽순","옥수수","팝콘","치즈","우엉","마","고구마","연근","무","미나리","미역","김","다시마","멸치","앞다리살","뒷다리살","갈비","오징어","문어","김치","새우","게","갈치","고등어","넙치","대구","농어","미더덕","복어","깨","오이지","후추","소금","케찹","머스타드","설탕","미림","조미료","계란","우유","라면","콩나물","식초","간장","버터","떡","식용유","식빵","초콜릿","고춧가루","베이킹파우더","굴소스","미나리","마요네즈","햄","스팸","마늘","치킨스톡","비엔나소시지"];

	/*initiate the autocomplete function on the "myInput" element, and pass along the countries array as possible autocomplete values:*/
	autocomplete(document.getElementById("myInput"), countries);
</script>
<%@include file="../general_included/footer.jsp"%>
</body>
</html>
