$(window).on('load', function() {
    if ($(".toast-body").text().trim() != "") {
        $('.toast').toast('show');
    }
});