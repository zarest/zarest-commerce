if (window.console) {
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
        $('.menu').toggleClass('right');
        $('.top_menu').toggleClass('top_right');
        $('#left_side').hide();
        //$("#leftSide").css({float: 'right'});
    } else {
        $(document.body).attr('style', 'direction: ltr');
        $(".change-locale").attr('style', 'float: right');
        $('.menu').toggleClass('left');
        $('.top_menu').toggleClass('top_left');
        $('#right_side').hide();
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
                    $(this).css({ position: 'fixed', top:  top});
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