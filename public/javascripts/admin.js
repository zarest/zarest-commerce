/**
 * Created by meysamabl on 11/1/14.
 */

$(document).ready(function () {
//    $('#picture').change(function () {
//        $("#catForm").ajaxForm({ target: "#image_view" }).submit();
//        return false;
//    });

    $("#level2Parent").hide();
    $("#level3Parent").hide();
    $("#level1").change(function (e) {
        e.preventDefault();
        var id = $("#level1").val();
        if (id != "") {
            $("#level2Parent").show();
            jsRoutes.controllers.Administration.getSubCategories(id).ajax({
                dataType: 'json',
                success: function (data) {
                    $('#level2').empty();
                    $('#level2').append($('<option>').text(Messages('chooseCategory')).attr('value', ""));
                    $.each(data, function (i, value) {
                        $('#level2').append($('<option>').text(value).attr('value', i));
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
    $("#level2").change(function () {
        var id = $("#level2").val();
        if (id != "") {
            $("#level1").removeAttr("name");
            $("#level3Parent").show();
            jsRoutes.controllers.Administration.getSubCategories(id).ajax({
                dataType: 'json',
                success: function (data) {
                    $('#level3').empty();
                    $('#level3').append($('<option>').text(Messages('chooseCategory')).attr('value', ""));
                    $.each(data, function (i, value) {
                        $('#level3').append($('<option>').text(value).attr('value', i));
                    });

                },
                error: function () {
                    alert("Error!")
                }
            })
        } else {
            $("#level1").attr('name', 'parentCategory');
            $("#level3Parent").hide();
            $("#level3").val("");
        }
    })
    $("#level3").change(function () {
        var id = $("#level3").val();
        if (id != "") {
            $("#level2").removeAttr("name");
        } else {
            $("#level2").attr('name', 'parentCategory');
        }
    })
});
