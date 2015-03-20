$(document).ready(function(){
	// $(".alert").addClass("in").fadeOut(4500);

	/* swap open/close side menu icons */
	$('[data-toggle=collapse]').click(function(){
	  	// toggle icon
	  	$(this).find("i").toggleClass("glyphicon-chevron-right glyphicon-chevron-down");
	});

	/* Datepicker */
	$(".datepicker").datepicker(
		{
			format: "dd-mm-yyyy"
		}
	)

	/* Delete User */
	$(document).on("click", "#delete-user", function(elem) {
		elem.preventDefault();
		if (confirm("Are you sure you want to delete this user ?")) {
			var form = document.createElement("form");
			form.setAttribute("method", "POST");
			form.setAttribute("action", $(this).attr("href"));

			var userId = document.createElement("input");
			userId.setAttribute("type", "hidden");
			userId.setAttribute("name", "user_id");
			userId.setAttribute("value", $(this).data('user'));

			form.appendChild(userId);
			document.body.appendChild(form);
			form.submit();
		}
	});

	/* Delete Activity */
	$(document).on("click", "#delete-activity", function(elem) {
		elem.preventDefault();
		if (confirm("Are you sure you want to delete this activity ?")) {
			var form = document.createElement("form");
			form.setAttribute("method", "POST");
			form.setAttribute("action", $(this).attr("href"));

			var userId = document.createElement("input");
			userId.setAttribute("type", "hidden");
			userId.setAttribute("name", "activity_id");
			userId.setAttribute("value", $(this).data('activity'));

			form.appendChild(userId);
			document.body.appendChild(form);
			form.submit();
		}
	});
});