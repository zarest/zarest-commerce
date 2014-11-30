if (window.console) {
    console.log("Welcome to zarest Application");
}
// The whole concept of jquery is : $(selector).action()
// selectors from DOM or css style. Actions are like click mouse movement, ...

$(document).ready(function () {

    $('#viewOption a').click(function() {
        $(this).siblings('.active').removeClass('active');
        if(!$(this).hasClass('active')) {
            $(this).addClass('active');
        }
    });

    $('[data-toggle="offcanvas"]').click(function () {
        $('.row-offcanvas').toggleClass('active')
    });

    $('.dropdown-toggle').click(function () {
        $(this).toggleClass('disabled');
    });
    $(".dropdown").hover(
        function() {
            $('#rootDropdown', this).stop( true, true ).slideDown("fast");
            $(this).toggleClass('open');
        },
        function() {
            $('#rootDropdown', this).stop( true, true ).slideUp("fast");
            $(this).toggleClass('open');
        }
    );

    $('.nav').find('a[href="' + location.pathname + '"]').parents('li').addClass('active');
    var path = location.pathname.substring(1, location.pathname.length);
    var split = path.split("/");
    $.each(split, function (i) {
        var position= location.pathname.search(split[i]) + split[i].length;
        $('.breadcrumb').append(
            $('<li>').append(
                $('<a>').attr('href',location.pathname.slice(0,position)).append(
                    $('<span>').attr('class', 'tab').append(Messages(path == "" ? 'home' : split[i]))
                )));
    })

    $("#language").change(function (e) {
        e.preventDefault();
        $(this).closest('form').trigger('submit');
        return false;
    });
    var lang = $("#language").val();
    if (lang == "fa") {
        $('link[href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-rtl/3.2.0-rc2/css/bootstrap-rtl.min.css"]')
            .prop("disabled", false);
        $('form').removeClass('navbar-right').addClass('navbar-left');
        //$('#myNavbar').attr('style', 'padding-right: 100px');
        $('.dropdown-submenu').toggleClass('rightSub');
        $('#sideToggle').parent().removeClass('pull-left').addClass('pull-right');
        $('.row-offcanvas').toggleClass('row-offcanvas-right');
        $('.has-feedback').toggleClass('has-feedback-right');
        $('.slide-footer' ).toggleClass('right');
        $('.slide-content').toggleClass('right');
        $('.slide-carousel').toggleClass('right');
        $('.buttons').removeClass('pull-right').addClass('pull-left');

    } else {
        $('link[href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-rtl/3.2.0-rc2/css/bootstrap-rtl.min.css"]')
            .prop("disabled", true);
        $('.dropdown-submenu').toggleClass('leftSub');
        //$('#myNavbar').attr('style', 'padding-left: 100px');
        $('.has-feedback').toggleClass('has-feedback-left');
        $('.row-offcanvas').toggleClass('row-offcanvas-left');
        $('.slide-footer' ).toggleClass('left');
        $('.slide-content').toggleClass('left');
        $('.slide-carousel').toggleClass('left');
        $('.buttons').removeClass('pull-left').addClass('pull-right');

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
                    $('body').css({paddingTop: 60})
                    //top = top + 36;
                });
            }
            else {
                $('.sticky').each(function () {
                    $(this).css('top', '25');
                    $('body').css({paddingTop: 85})
                });
            }
        });

    }

});

