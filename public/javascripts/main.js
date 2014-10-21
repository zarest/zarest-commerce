$(document).ready(function () {
    $("#language").change(function () {
        $(this).closest('form').trigger('submit');

    });
    var lang = $("#language").val();
    if (lang == "fa") {
        $(document.body).attr('style', 'direction: rtl');
    } else {
        $(document.body).attr('style', 'direction: ltr');
    }

});