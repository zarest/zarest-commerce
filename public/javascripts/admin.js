/**
 * Created by meysamabl on 11/1/14.
 */

$(document).ready(function () {
//    $('#picture').change(function () {
//        $("#catForm").ajaxForm({ target: "#image_view" }).submit();
//        return false;
//    });

    $("#level2Parent").hide();
    $("#level1").change(function (e) {
        e.preventDefault();
        var form = $(this).closest('form');
        var url = form.attr("action");
        if ($("#level1").val() != "") {
            $("#level2Parent").show();
//            form.submit();
//            return false;
//            form.ajaxForm({ target: "#level2" }).submit();
//            return false;

        } else {
            $("#level2Parent").hide();
            $("#level2").val("");
        }
    })
});
