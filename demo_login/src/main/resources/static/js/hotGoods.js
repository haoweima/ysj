/**
 * Created by marnon on 2018/6/6.
 */

$(document).ready(function () {
    var url = "http://localhost:8080/Mana/store/getHotGoods";
    var line = "<tr>" +
        "<td>商品名称</td>" +
        "<td>售价</td>" +
        "<td>进价</td>" +
        "</tr>"

    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        error:function(){alert("抱歉，这个月您的销售额为0。")},
        success: function (data) {
            var list = data;
            var tbody = $("tbody");
            tbody.empty();
            tbody.append(line);
            $.each(list, function (index, value) {
                var lt = "<tr>" +
                    "<td>" + value.name + "</td>" +
                    "<td>" + value.come_price + "</td>" +
                    "<td>" + value.sale_price + "</td>" +
                    "</tr>"
                tbody.append(lt);
            })
        }
    })
})
