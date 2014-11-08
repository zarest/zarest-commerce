if (window.console) {
    console.log("Welcome to zarest Application");
}
// The whole concept of jquery is : $(selector).action()
// selectors from DOM or css style. Actions are like click mouse movement, ...

$(document).ready(function () {

    $('.nav').find('a[href="' + location.pathname + '"]').parents('li').addClass('active');

    $("#language").change(function () {
        $(this).closest('form').trigger('submit');
    });
    var lang = $("#language").val();
    if (lang == "fa") {
        $('link[href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-rtl/3.2.0-rc2/css/bootstrap-rtl.min.css"]')
            .prop("disabled", false);
        $('form').removeClass('navbar-right').addClass('navbar-left');
        $('.nav').attr('style', 'padding-right: 100px');
        $('.dropdown-submenu').toggleClass('rightSub');

    } else {
        $('link[href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-rtl/3.2.0-rc2/css/bootstrap-rtl.min.css"]')
            .prop("disabled", true);
        $('.dropdown-submenu').toggleClass('leftSub');
        $('.nav').attr('style', 'padding-left: 100px');
    }

});

$(function () { // document ready

    if (!!$('.sticky').offset()) { // make sure ".sticky" element exists

        var stickyTop = $('.sticky').offset().top; // returns number

        $(window).scroll(function () { // scroll event
            var windowTop = $(window).scrollTop(); // returns number
            var top = 0;
            if (stickyTop < windowTop) {
                $('.sticky').each(function () {
                    $(this).css({ position: 'fixed', top: top});
                    top = top + 36;
                });
            }
            else {
                $('.sticky').each(function () {
                    $(this).css('position', 'static');
                });
            }
        });

    }

});