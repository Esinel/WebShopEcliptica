//Custom functions


$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};




//-------------------------


var ajaxCalls = function () {
    //HTML elements
    
    //login
    var loginLink = $("#login");
    var loginIcon = $(".top-header-content .fa-sign-in");
    var loginForm = $('#loginform');
    var loginModal = $('#loginmodal');
    //register
    var registerButton = $(".top-header-content #register");
    var registerIcon = $(".top-header-content .fa-user-plus");
    var registerForm = $('#registerform');
    var registerModal = $('#registermodal');
    var dimmer = $('.dimmer');
    //search
    var searchInput = $('#searchInput');
    var searchButton = $('#searchButton');
    //shopping
    var shoppingCart = $('#shoppingCart');
    
    
    
    //Login
    
    function postLoginState() {
        var loginLink = $('#login');
        var logoutLink = $('<a />');
        logoutLink.attr('href', 'LogoutController');
        logoutLink.attr('id', 'logout')
        logoutLink.text('Logout');
        loginLink.replaceWith(logoutLink);
        $('.fa-sign-in').removeClass('fa-sign-in').addClass('fa-sign-out');
        
        loginModal.trigger("reset");
        loginModal.css("display", "none");
        registerForm.trigger("reset");
        registerModal.css("display", "none");
        dimmer.hide();
    }
    
    function postLogoutState() {
        var logoutLink = $('#logout');
        var loginLink = $('<a />');
        loginLink.attr('href', '#');
        loginLink.attr('id', 'login')
        loginLink.text('Login');
        logoutLink.replaceWith(loginLink);
        $('.fa-sign-out').removeClass('fa-sign-out').addClass('fa-sign-in');
    }
    
    $('#loginform').submit(function( event ) {
        var user = JSON.stringify($(this).serializeObject());
        $.ajax({
            method: "POST",
            url: "LoginController",
            dataType: "json",
            data: {'loggedUser' : user},
            success: function (response){
            	var ulogovaniUser = response;
                if (jQuery.isEmptyObject(ulogovaniUser)){
                    alert('Provjerite username i lozinku!');
                } else {
                    //alert(JSON.stringify(ulogovaniUser));
                    alert("Dobrodosli " + ulogovaniUser.username + " " + ulogovaniUser.lastname);
                    postLoginState();
                }
            },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        	event.preventDefault();

    });
    
    
    //Register
    
    
    $('#registerform').submit(function( event ) {
        var user = JSON.stringify($(this).serializeObject());
        $.ajax({
            method: "POST",
            url: "RegisterController",
            dataType: "json",
            data: {'registeredUser' : user},
            success: function (response){
                var registrovaniUser = response;
                if ($.isEmptyObject(registrovaniUser)){
                    alert('Provjerite unesene podatke!');
                } else {
                    alert("Dobrodosli " + registrovaniUser.username + " " + registrovaniUser.lastname);   
                    postLoginState();
                }
            },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        	event.preventDefault();
    });
    
    
    //--
}



$(document).ready(ajaxCalls);