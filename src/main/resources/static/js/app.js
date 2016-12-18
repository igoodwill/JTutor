$(document).ready(function() {
	$('[data-toggle=offcanvas]').click(function() {
		$('.row-offcanvas').toggleClass('active');
	});
});

function getWordAnswers(count, lang) {
	count = count.toString();

	if (lang == "ua") {
		if (count.endsWith("11")) {
			return "відповідей";
		}

		if (count.endsWith("12")) {
			return "відповідей";
		}

		if (count.endsWith("13")) {
			return "відповідей";
		}

		if (count.endsWith("14")) {
			return "відповідей";
		}

		if (count.endsWith("1")) {
			return "відповідь";
		}

		if (count.endsWith("2")) {
			return "відповіді";
		}

		if (count.endsWith("3")) {
			return "відповіді";
		}

		if (count.endsWith("4")) {
			return "відповіді";
		}

		return "відповідей";
	} else {
		if (count == "1")
			return "answer";

		return "answers";
	}
}