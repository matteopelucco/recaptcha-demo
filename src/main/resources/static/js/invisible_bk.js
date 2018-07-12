var formId = "#signup-form";

$(function() {
	getUsers();
	$(formId).validate({
		errorClass : "is-invalid",
		validClass : "is-valid"
	});

	$(formId + " button").on("click", function(e){
		toastr.info("validating form..");
		if ($(formId).valid()){
			toastr.success("form succesfully validated!");
			toastr.info("executing ReCaptcha..")
			grecaptcha.execute();
		} else {
			toastr.error("please correct your form first..");
			grecaptcha.reset();
		}
	});
	
});

function getUsers(){
	$.getJSON("/api/users", function(data){
		$("#users-list").html(Mustache.to_html($("#users-list-template").html(), data));
	});
}

function readyToSubmit(){
	toastr.info("ReCaptcha finished, ready to submit the form..");
	
	$.ajax({
        type: "POST",
        url: $(formId).attr("action"),
        data: $(formId).serialize(),
        dataType: "json",
        beforeSend: function(){
        	toastr.info("submitting the form..");
        },
        success: function(response){
        	toastr.info("success, updating users and resetting ReCaptcha..");
            getUsers();
            grecaptcha.reset();
        },
        error: function(){
            toastr.error("some error occurred..");
            grecaptcha.reset();
        }
    });
	
}