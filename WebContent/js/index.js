var main = function () {
    
    var productsContainer = $("#products");
    
    
    //key bindings
    
    $(document).keyup(function(e) {
     if (e.keyCode == 27) { // escape key maps to keycode `27`
        $("#loginmodal").css("display", "none");
        $("#registermodal").css("display", "none");
        $(".dimmer").hide();
        }
    });
    
    
    // LEFT-SIDE NAVIGATION
    
    $("#submenuMonitors").click(function(){
        loadAllMonitors();
    });
    
    $("#submenuCPU").click(function(){
        loadAllCPUs();
    });
    
    $("#submenuRAM").click(function(){
        loadAllRAMs();
    });
    
    $("#submenuMB").click(function(){
        loadAllMotherboards();
    });
    
    $("#submenuVGA").click(function(){
        loadAllVGAs();
    });
    
    $("#submenuHDD").click(function(){
        loadAllHDDs();
    });
    
    $("#submenuCasing").click(function(){
        loadAllCasings();
    });
    
    
    
    
    
    
    //fixed navigation
    $(document).ready(function () {
        $('#back-to-top').hide();
        var navpos = $('.main-nav').offset();
        $(window).bind('scroll', function () {
            if ($(window).scrollTop() > navpos.top) {
                $('.main-nav').addClass('fixed');
                $('.main-nav').addClass('main-nav-bottom-border');
                $('#back-to-top').fadeIn();
            } else {
                $('.main-nav').removeClass('fixed');
                $('.main-nav').removeClass('main-nav-bottom-border');
                $('#back-to-top').fadeOut();
            }
        });
    });
    
    // Slide show mechanism
    $('.arrow-next').click(function () {
        var currentSlide = $('.active-slide');
        var nextSlide = currentSlide.next();

        var currentDot = $('.active-dot');
        var nextDot = currentDot.next();
        if (nextSlide.next().length == 0) {
            nextSlide = $('.slide').first();
            nextDot = $('.dot').first();
        }

        currentSlide.fadeOut(600).removeClass('active-slide');
        currentSlide.hide();
        nextSlide.fadeIn(600).addClass('active-slide');

        currentDot.removeClass('active-dot');
        nextDot.addClass('active-dot');
    });


    $('.arrow-prev').click(function () {
        var currentSlide = $('.active-slide');
        var prevSlide = currentSlide.prev();

        var currentDot = $('.active-dot');
        var prevDot = currentDot.prev();

        if (prevSlide.prev().length == 0) {
            prevSlide = $('.slide').last();
            prevDot = $('.dot').last();
        }

        currentSlide.fadeOut(600).removeClass('active-slide');
        currentSlide.hide();
        prevSlide.fadeIn(600).addClass('active-slide');

        currentDot.removeClass('active-dot');
        prevDot.addClass('active-dot');
    });


    //Login Modal
    $("#login").click(function () {
        $("#loginmodal").css("display", "block");
        $("#loginmodal #username").focus();
        $(".dimmer").show();
    });

    $("#loginmodal").find(".cancel-btn").click(function () {
        $("#loginmodal").css("display", "none");
        $(".dimmer").hide();
    });

    //Register Modal    
    
    $("#register").click(function () {
        $("#registermodal").css("display", "block");
        $("#registermodal #firstname").focus();
        $(".dimmer").show();
    });

    $("#registermodal").find(".cancel-btn").click(function () {
        $("#registermodal").css("display", "none");
        $(".dimmer").hide();
    });
    
    
    $('#passCheck').click(function () {
        if ($(this).is(':checked')) {
            $('#registermodal #passwordd').attr('type', 'text');
        } else {
            $('#registermodal #passwordd').attr('type', 'password');
        }
    });
    
    $('#clearBtn').click(function () {
        $('#registerform').trigger("reset");
    });
    
    //-----------------------------------
  
    $("#search-text").focus(function () {
        $(".search-sector").css("border-color", "#F65B5C");
        $(".search-sector").animate({width: "220px"}, 300);
        $("#search-text").animate({width: "180px"}, 300);
    });
    
    $("#search-text").blur(function () {
        $(".search-sector").css("border-color", "#67686a");
        $(".search-sector").animate({width: "200px"}, 300);
        $("#search-text").animate({width: "160px"}, 300);
    });
    
    
    $(".product-add-to-cart").hover(function () {
        $(this).find(".druga-celija").css("background-color", "#F65B5C");
        $(this).css("cursor", "pointer");
    },
                                    function () {
            $(".druga-celija").css("background-color", "rgba(52, 179, 160, 0.5);");
            $(this).css("cursor", "hand");
        });
    
   
    
    
    

    
    
    
    
    
//-----------------------------------------------------------

  //Slider intervals
    setInterval(function () {
        $('.arrow-next').trigger("click");
    }, 6000);

    //plava boja fonta navigacije
    $(".main-nav a:not(.fa)").hover(function () {
        $(this).css("color", "#1F4056");
    },
                      function () {
            $(this).css("color", "white");
        }, 500);
    
    
    //back to top
    $("a[href=#top]").click(function () {
        $('html, body').animate({scrollTop: 0}, 'slow');
    });
    
    //search bar
    
    $("#searchInput").focusin(function () {
        $(".fa-search").css("color", "#3CA68F");
    });
    
    $("#searchInput").blur(function () {
        $(".fa-search").css("color", "white");
    });
    
    
};



    
// play with database ======================================
// =========================================================

    

function loadAllMonitors(){
    $.ajax({
        method: "GET",
        url: "ComponentController",
        dataType: "json",
        data:{purpose: "getMonitors"},
        success: function (response) {
            $("#products").empty();
            var monitors = response;
            //appenduj
            alert(monitors.length);
            for (var i = 0; i < monitors.length; i++){
                alert(i);
                var productAddId = "addToCart-" + monitors[i].code.toString();
                var productDetailsId = "showDetails-" + monitors[i].code.toString();
                // kao id kartice proizvoda stavljamo componentID
                var product = $("<div class='product' id="+monitors[i].code.toString()+">"+
                                    "<div class='product-title'> "+
                                        "<a href='product-details.html' class='productDetails' id="+productDetailsId+">"+monitors[i].name.toString()+"</a>"+
                                    "</div"+

                                    "<a id="+productDetailsId+" href='product-details.html' class='product-image productDetails'>"+
                                        "<img src="+monitors[i].image.toString()+" class='product-icon'/>"+
                                    "</a>"+

                                    "<div class='product-price'>"+
                                        "<span class='price'>"+monitors[i].price.toString()+"</span>"+
                                        "<br>"+
                                        "<span> class='pdv'>sa PDV-om</span>"+
                                        "<br>"+
                                    "</div>"+

                                    "<div id="+productAddId+" class='product-add-to-cart'>"+
                                        "<a href='#' title='Kupi'>"+
                                            "<img src='images/shopping-cart.png' class='shopping-cart'/>"+
                                        "</a>"+
                                    "</div>"+

                                "</div>" )
                productsContainer.append(product);                  
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(errorThrown);
        }
    });
}

function loadAllCPUs(){

}

function loadAllRAMs(){

}

function loadAllMotherboards(){

}

function loadAllVGAs(){

}

function loadAllHDDs(){

}

function loadAllCasings(){

}

function loadAllMachines(){

}



$(document).ready(function() {
    loadAllMonitors();
});

$(document).ready(main);