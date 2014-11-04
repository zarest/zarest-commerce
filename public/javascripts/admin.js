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
        var id = $("#level1").val();
        if ($("#level1").val() != "") {
            $("#level2Parent").show();
            jsRoutes.controllers.Administration.getSubCategories(id).ajax({
                dataType: 'json',
                success: function (data) {
                    $('#level2').empty();
                    $('#level2').append($('<option>').text(Messages('chooseCategory')).attr('value', null));
                    console.info(data);
                    $.each(data, function(i, value) {
                        $('#level2').append($('<option>').text(value).attr('value', value));
                    });

                },
                error: function () {
                    alert("Error!")
                }
            })

        } else {
            $("#level2Parent").hide();
            $("#level2").val("");
        }
    })
});
