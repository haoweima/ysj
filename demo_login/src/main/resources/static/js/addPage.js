/**
 * Created by marnon on 2017/9/2.
 */

var num = 6;
var index = 7;
$(function() {
    $("#add").click(function() {
        var tr =
            "<tr>" +
            "<td>" + index + "</td>" +
            "<td><input class='gName' type='text' name='table[" + num + "].name' list='goodsname' autocomplete='off'></td>" +
            "<td><input type='text' name='table[" + num + "].unit' disabled='disabled'></td>" +
            "<td><input type='text' name='table[" + num + "].num' class='number'></td>" +
            "<td><input type='text' name='table[" + num + "].price' class='price'></td>" +
            "<td class='totalnul'></td>" +
            "<td><input type='text' name='table[" + num + "].other'></td>" +
            "</tr>";
        $("table").append(tr);
        num++, index++;
        $('html,body').animate({scrollTop: $('footer').offset().top},100);
    });
});


function sure(){
    if(confirm("确认保存？")){
        alert("正在保存...");
    }else return false;
}

// 计算总额
$(document).ready(function () {
    var number,price;
    var sum;
    var left, right, td;
    $("table").on("blur", ".price",function () {
        td = this.parentNode;
        left = td.previousElementSibling;
        right = td.nextElementSibling;

        price = this.value;
        number = left.firstChild.value;

        sum = (price * number).toFixed(2);

        right.innerText = sum;
    });
})

// 仓库选择
$(document).ready(function () {
    $("#ivnt").change(function () {
        $("#goodsname").empty();
        $("table input").val("");
        $("table input").attr("placeholder","");
        var url = "/Mana/sale/getNames?ssc=" + this.value;
        var gNames;
        $.getJSON(url, function (data) {
            gNames = data.gNames;
            $.each(gNames, function (i, v) {
                var option = "<option class='gNames'>" + v + "</option>"
                $("#goodsname").append(option)
            })
        });
    });
})

// 让用户不选择仓库不能使用功能
$(document).ready(function () {
    $("#ivnt").blur(function () {
        var text;
//                op = this.find("#ivnt option:selected");
        text = $("#ivnt").val();
        if(text=="请选择仓库"){
            alert("还没有选择仓库！");
            $("#ivnt").focus;
        }
    })
})

// 让用户不选择仓库不能使用功能
$(document).ready(function () {
    $("#come").blur(function () {
        var text;
//                op = this.find("#ivnt option:selected");
        text = $("#come").val();
        if(text=="请选择仓库"){
            alert("还没有选择仓库！");
            $("#come").focus;
        }
    })
})

// 选择商品后跳出信息
$(document).ready(function () {
    $("table").on("blur", ".gName",function () {
        var url = "/Mana/sale/getGoodsMess?name=" + this.value;
        var gMess;
        var td = this.parentNode;
        var next;
        $.getJSON(url, function (data) {
            gMess = data.gMess;
            next = td.nextElementSibling;
//                    next.firstChild.value = gMess.unit;
            next.firstChild.placeholder = gMess.unit;
            next = next.nextElementSibling;
            next.firstChild.placeholder = "剩余：" + gMess.num;
            next = next.nextElementSibling;
            next.firstChild.placeholder = "建议零售价："+gMess.price;
        })
    })
})
