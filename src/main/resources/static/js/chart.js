function paintChart(element, title, type, dataSet) {
    var chart = echarts.init(document.getElementById(element));
    var option = {
        title: {
            text: title
        },
        tooltip: {
            show: true
        },
        xAxis: {
            data: dataSet.map(function (data) {
                return data.x;
            })
        },
        yAxis: {
            name: "流量/MB"
        },
        series: [{
            type: type,
            data: dataSet.map(function (data) {
                return data.y;
            }),
            tooltip: {
                formatter: '{c}MB'
            }
        }]
    };
    chart.setOption(option);
}

