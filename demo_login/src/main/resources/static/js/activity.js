/**
 * Created by marnon on 2018/6/6.
 */

$(document).ready(function () {
    var url = "http://localhost:8080/Mana/store/getActivity";
    var line = "<tr>" +
        "<td>热门商品名称</td>" +
        "<td>热门商品原售价</td>" +
        "<td>冷门商品名称</td>" +
        "<td>冷门商品原售价</td>" +
        "<td>组合售价</td>" +
        "<td>利润</td>" +
        "</tr>"

    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        error:function(){alert("抱歉，这个月您的销售额为0，无法分析热门商品")},
        success: function (data) {
            var list = data;
            var tbody = $("tbody");
            tbody.empty();
            tbody.append(line);
            $.each(list, function (index, value) {
                var lt = "<tr>" +
                    "<td>" + value.nameFirst + "</td>" +
                    "<td>" + value.saleOld + "</td>" +
                    "<td>" + value.nameSecond + "</td>" +
                    "<td>" + value.saleNew + "</td>" +
                    "<td>" + value.saleMoney + "</td>" +
                    "<td>" + value.profit + "</td>" +
                    "</tr>"
                tbody.append(lt);
            })
        }
    })
})
