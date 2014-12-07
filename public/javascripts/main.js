if (window.console) {
    console.log("Welcome to zarest Application");
}
// The whole concept of jquery is : $(selector).action()
// selectors from DOM or css style. Actions are like click mouse movement, ...

$(document).ready(function () {

    $("#pageSize").change(function (e) {
        e.preventDefault();
        $(this).closest('form').trigger('submit');
        return false;
    });
    $("#sortBy").change(function (e) {
        e.preventDefault();
        $(this).closest('form').trigger('submit');
        return false;
    });

    persian={0:'۰',1:'۱',2:'۲',3:'۳',4:'۴',5:'۵',6:'۶',7:'۷',8:'۸',9:'۹'};
    function traverse(el){
        if(el.nodeType==3){
            var list=el.data.match(/[0-9]/g);
            if(list!=null && list.length!=0){
                for(var i=0;i<list.length;i++)
                    el.data=el.data.replace(list[i],persian[list[i]]);
            }
        }
        for(var i=0;i<el.childNodes.length;i++){
            traverse(el.childNodes[i]);
        }
    }

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
        traverse(document.body);
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
        $('.toolbar form').toggleClass('pull-right');

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

