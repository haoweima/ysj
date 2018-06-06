var url = "http://localhost:8080/Mana/store/getStoreIndex";
var sale = new Array();
var come = new Array();
var retu = new Array();
var change = new Array();
var today = new Array();

$(document).ready(function () {
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        error:function(){alert("出错啦")},
        success: function(data){
            var list = data;
            $.each(list, function (index, value) {
                sale[index] = (value.saleMoney);
                come[index] = (value.comeMoney);
                retu[index] = (value.returnMoney);
                change[index] = (value.changeMoney);
                today[index] = (value.selectDate);
            })
            draw1();
            // draw2(come,today);
            // draw3(retu,today);
            // draw4(change,today);
        }
    });
})

function draw1() {
    var saleMoney = $("#saleMoney");
    var barChart = new Chart(saleMoney, {
        type: 'line',
        data: {
            labels: today,
            datasets: [
                {
                    label: "销售",
                    fill: false,  //是否要显示数据部分阴影面积块  false:不显示
                    borderColor: "rgba(255, 99, 132, 1)",//数据曲线颜色
                    pointBackgroundColor: "#fff", //数据点的颜色
                    data : sale
                },
                {
                    label: "进货",
                    fill: false,  //是否要显示数据部分阴影面积块  false:不显示
                    borderColor: "rgba(54, 162, 235, 1)",//数据曲线颜色
                    pointBackgroundColor: "#fff", //数据点的颜色
                    data : come
                },
                {
                    label: "退货",
                    fill: false,  //是否要显示数据部分阴影面积块  false:不显示
                    borderColor: "rgba(255, 206, 86, 1)",//数据曲线颜色
                    pointBackgroundColor: "#fff", //数据点的颜色
                    data : retu
                },
                {
                    label: "换货",
                    fill: false,  //是否要显示数据部分阴影面积块  false:不显示
                    borderColor: "rgba(75, 192, 192, 1)",//数据曲线颜色
                    pointBackgroundColor: "#fff", //数据点的颜色
                    data : change
                }
            ]
        },
        options: {
            showScale: true,
            responsive: true
        }
    });
}

// function draw2(data, day) {
//     var comeMoney = $("#comeMoney");
//     var barChart = new Chart(comeMoney, {
//         type: 'bar',
//         data: {
//             labels: day,
//             datasets: [{
//                 label: '进货报表',
//                 data: data,
//                 backgroundColor: [
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(153, 102, 255, 0.6)',
//                     'rgba(255, 159, 64, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(153, 102, 255, 0.6)',
//                     'rgba(255, 159, 64, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(153, 102, 255, 0.6)',
//                     'rgba(255, 159, 64, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)'
//                 ]
//             }]
//         }
//     });
// }
//
// function draw3(data, day) {
//     var returnMoney = $("#returnMoney");
//     var barChart = new Chart(returnMoney, {
//         type: 'bar',
//         data: {
//             labels: day,
//             datasets: [{
//                 label: '退货报表',
//                 data: data,
//                 backgroundColor: [
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(153, 102, 255, 0.6)',
//                     'rgba(255, 159, 64, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(153, 102, 255, 0.6)',
//                     'rgba(255, 159, 64, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(153, 102, 255, 0.6)',
//                     'rgba(255, 159, 64, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)'
//                 ]
//             }]
//         }
//     });
// }
// function draw4(data, day) {
//     var changeMoney = $("#changeMoney");
//     var barChart = new Chart(changeMoney, {
//         type: 'bar',
//         data: {
//             labels: day,
//             datasets: [{
//                 label: '换货报表',
//                 data: data,
//                 backgroundColor: [
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(153, 102, 255, 0.6)',
//                     'rgba(255, 159, 64, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(153, 102, 255, 0.6)',
//                     'rgba(255, 159, 64, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)',
//                     'rgba(153, 102, 255, 0.6)',
//                     'rgba(255, 159, 64, 0.6)',
//                     'rgba(255, 99, 132, 0.6)',
//                     'rgba(54, 162, 235, 0.6)',
//                     'rgba(255, 206, 86, 0.6)',
//                     'rgba(75, 192, 192, 0.6)'
//                 ]
//             }]
//         }
//     });
// }