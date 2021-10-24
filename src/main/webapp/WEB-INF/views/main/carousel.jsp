<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>캐러셀</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<div class="container-fluid">
  	<div id="carousel" class="carousel slide" data-ride="carousel">
  		<ol class="carousel-indicators">
  			<!-- active는 한번만 써야 함 -->
  			<li data-target="#carousel" data-slide-to="0" class="active"></li>
  			<li data-target="#carousel" data-slide-to="1"></li>
  			<li data-target="#carousel" data-slide-to="2"></li>
  		</ol>
  			
  		<div class="carousel-inner">
  			<div class="carousel-item active">
  				<img src="../images/event1.png" class="d-block w-100">
  				<div class="carousel-caption d-none d-md-block">
	  				<!-- <h5>슬라이드 제목1</h5>
	  				<p>무슨 사진 넣을까여어ㅓㅓ</p> -->
  				</div>
  			</div>
  				
  			<div class="carousel-item">
  				<img src="../images/event2.png" class="d-block w-100">
  				<div class="carousel-caption d-none d-md-block">
	  				<!-- <h5>슬라이드 제목2</h5>
	  				<p>맛있는 거어어ㅓㅓ</p> -->
  				</div>
  			</div>
  				
  			<div class="carousel-item">
  				<img src="../images/event3.png" class="d-block w-100">
  				<div class="carousel-caption d-none d-md-block">
	  				<!-- <h5>슬라이드 제목3</h5>
	  				<p>맛있는 게 뭔데에에ㅔ</p> -->
  				</div>
  			</div>
  		</div>
  				
  		<a class="carousel-control-prev" href="#carousel" data-slide="prev">
  			<span class="carousel-control-prev-icon" aria-hidden="true"></span>
  		</a>
  		<a class="carousel-control-next" href="#carousel" data-slide="next">
  			<span class="carousel-control-next-icon" aria-hidden="true"></span>
  		</a>
	</div><!-- end of carousel -->
</div><!-- end of container -->
<script>
	// 캐러셀 동작
	$('#carousel').carousel({
		interval: 3000, // 속도
		pause: 'hover' // 커서 올렸을 때 멈춤
	})
</script>