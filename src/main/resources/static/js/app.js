$(document).ready(function() {
	$('[data-toggle=offcanvas]').click(function() {
		$('.row-offcanvas').toggleClass('active');
	});
});

function showLecture(lecture) {
	document.getElementById("lecture").innerHTML = lecture;
}