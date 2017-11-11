$(document ).ready(function() {
	 $('#signIn').addClass('active');
    
    $('#signIn').click(function(e) {
		$("#LogInForma").delay(100).fadeIn(100);
 		$("#RegistracijaForma").fadeOut(100);
		$('.tab SignUp').removeClass('active');
		$(this).addClass('active');
	
	});
	$('#signUp').click(function(e) {
		$("#RegistracijaForma").delay(100).fadeIn(100);
 		$("#LogInForma").fadeOut(100);
		$('.tab SignIn').removeClass('active');
		$(this).addClass('active');
	});

});




