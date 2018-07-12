var formId = "#signup-form";

$(function() {
	getUsers();
	$(formId).validate({
		errorClass : "is-invalid",
		validClass : "is-valid"
	});

});

function getUsers(){
	$.getJSON("/api/users", function(data){
		$("#users-list").html(Mustache.to_html($("#users-list-template").html(), data));
	});
}

function ajaxFormSubmit(reCaptchaToken) {
	
	var formData = $(formId).serialize();
	formData.reCaptchaToken = reCaptchaToken;
	
	$.ajax({
        type: "POST",
        url: $(formId).attr("action"),
        data: formData,
        // dataType: "json",
        beforeSend: function(){
            // $("#statutConnexion").html("Traitement de votre requête d'authentification en cours...");
        },
        success: function(response){
        	toastr.success("Signup completed successfully");
			getUsers();
			grecaptcha.reset();
			$(formId).reset();
        },
        error: function(response){
        	if (response.responseJSON){
				toastr.error(response.responseJSON.message);
			} else {
				toastr.error(response.responseText);
			}
			
			grecaptcha.reset();
        }
    });
	
}