console.clear();

// 기존 버튼형 슬라이더
$('.slider-1 > .page-btns > div').click(function(){
    var $this = $(this);
    var index = $this.index();
    
    $this.addClass('active');
    $this.siblings('.active').removeClass('active');
    
    var $slider = $this.parent().parent();
    
    var $current = $slider.find(' > .slides > div.active');
    
    var $post = $slider.find(' > .slides > div').eq(index);
    
    $current.removeClass('active');
    $post.addClass('active');
});

// 좌/우 버튼 추가 슬라이더
$('.slider-1 > .side-btns > div').click(function(){
    var $this = $(this);
    var $slider = $this.closest('.slider-1');
    
    var index = $this.index();
    var isLeft = index == 0;
    
    var $current = $slider.find(' > .page-btns > div.active');
    var $post;
    
    if ( isLeft ){
        $post = $current.prev();
    }
    else {
        $post = $current.next();
    };
    
    if ( $post.length == 0 ){
        if ( isLeft ){
            $post = $slider.find(' > .page-btns > div:last-child');
        }
        else {
            $post = $slider.find(' > .page-btns > div:first-child');
        }
    };
    
    $post.click();
});

setInterval(function(){
    $('.slider-1 > .side-btns > div').eq(1).click();
}, 3000);


//current position
var pos = 0;
//number of slides
var totalSlides = $('#slider-wrap ul li').length;
//get the slide width
var sliderWidth = $('#slider-wrap').width();


$(document).ready(function(){
  
  
  /*****************
   BUILD THE SLIDER
  *****************/
  //set width to be 'x' times the number of slides
  $('#slider-wrap ul#slider').width(sliderWidth*totalSlides);
  
    //next slide  
  $('#next').click(function(){
    slideRight();
  });
  
  //previous slide
  $('#previous').click(function(){
    slideLeft();
  });
  
  
  
  /*************************
   //*> OPTIONAL SETTINGS
  ************************/
  //automatic slider
  var autoSlider = setInterval(slideRight, 3000);
  
  //for each slide 
  $.each($('#slider-wrap ul li'), function() { 

     //create a pagination
     var li = document.createElement('li');
     $('#pagination-wrap ul').append(li);    
  });
  
  //counter
  countSlides();
  
  //pagination
  pagination();
  
  //hide/show controls/btns when hover
  //pause automatic slide when hover
  $('#slider-wrap').hover(
    function(){ $(this).addClass('active'); clearInterval(autoSlider); }, 
    function(){ $(this).removeClass('active'); autoSlider = setInterval(slideRight, 2000); }
  );
  
  

});//DOCUMENT READY
  


/***********
 SLIDE LEFT
************/
function slideLeft(){
  pos--;
  if(pos==-1){ pos = totalSlides-1; }
  $('#slider-wrap ul#slider').css('left', -(sliderWidth*pos));  
  
  //*> optional
  countSlides();
  pagination();
}


/************
 SLIDE RIGHT
*************/
function slideRight(){
  pos++;
  if(pos==totalSlides){ pos = 0; }
  $('#slider-wrap ul#slider').css('left', -(sliderWidth*pos)); 
  
  //*> optional 
  countSlides();
  pagination();
}

  
/************************
 //*> OPTIONAL SETTINGS
************************/
function countSlides(){
//   $('#counter').html(pos+1 + ' / ' + totalSlides);
}

function pagination(){
  $('#pagination-wrap ul li').removeClass('active');
  $('#pagination-wrap ul li:eq('+pos+')').addClass('active');
}
    


//mmmmmmmmmmmmmmmmmmmmm

//current position
var poss = 0;
//number of slides
var totalSlidess = $('#slider-wrap1 ul li').length;
//get the slide width
var sliderWidthh = $('#slider-wrap1').width();


$(document).ready(function(){
  
  
  /*****************
   BUILD THE SLIDER
  *****************/
  //set width to be 'x' times the number of slides
  $('#slider-wrap1 ul#slider1').width(sliderWidthh*totalSlidess);
  
    //next slide  
  $('#next1').click(function(){
    slideRightt();
  });
  
  //previous slide
  $('#previous1').click(function(){
    slideLeftt();
  });
  
  
  
  /*************************
   //*> OPTIONAL SETTINGS
  ************************/
  //automatic slider
  var autoSliderr = setInterval(slideRightt, 3000);
  
  //for each slide 
  $.each($('#slider-wrap1 ul li'), function() { 

     //create a pagination
     var li = document.createElement('li');
     $('#pagination-wrap1 ul').append(li);    
  });
  
  //counter
  countSlidess();
  
  //pagination
  paginationn();
  
  //hide/show controls/btns when hover
  //pause automatic slide when hover
  $('#slider-wrap1').hover(
    function(){ $(this).addClass('active'); clearInterval(autoSlider); }, 
    function(){ $(this).removeClass('active'); autoSlider = setInterval(slideRight, 2000); }
  );
  
  

});//DOCUMENT READY
  


/***********
 SLIDE LEFT
************/
function slideLeftt(){
  poss--;
  if(pos==-1){ poss = totalSlidess-1; }
  $('#slider-wrap1 ul#slider1').css('left', -(sliderWidthh*poss));  
  
  //*> optional
  countSlides();
  pagination();
}


/************
 SLIDE RIGHT
*************/
function slideRightt(){
  poss++;
  if(poss==totalSlidess){ poss = 0; }
  $('#slider-wrap1 ul#slider1').css('left', -(sliderWidthh*poss)); 
  
  //*> optional 
  countSlidess();
  paginationn();
}

  
/************************
 //*> OPTIONAL SETTINGS
************************/
function countSlidess(){
//   $('#counter').html(pos+1 + ' / ' + totalSlides);
}

function paginationn(){
  $('#pagination-wrap1 ul li').removeClass('active');
  $('#pagination-wrap1 ul li:eq('+poss+')').addClass('active');
}