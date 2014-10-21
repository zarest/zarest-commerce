if(window.console) {
    console.log("Welcome to zarest Application");
}
// The whole concept of jquery is : $(selector).action()
// selectors from DOM or css style. Actions are like click mouse movement, ...

$(document).ready(function () {
    $("#language").change(function () {
        $(this).closest('form').trigger('submit');

    });
    var lang = $("#language").val();
    if (lang == "fa") {
        $(document.body).attr('style', 'direction: rtl');
        $(".change-locale").attr('style', 'float: left');
    } else {
        $(document.body).attr('style', 'direction: ltr');
        $(".change-locale").attr('style', 'float: right');
    }

});