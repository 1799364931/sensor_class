const apiBase = "http://localhost:5000"; // 根据部署端口修改
let autoRefreshTimer = null;


function fetchAndRenderLogs() {
    // 固定查询参数（获取全部数据）
    const payload = {
        startDate: "1970-01-01T00:00:00", // 时间起点（UNIX纪元）
        endDate: "2100-01-01T00:00:00",   // 时间终点（未来时间）
        temperatureMin: -10000,           // 温度下限（极低值）
        temperatureMax: 10000,            // 温度上限（极高值）
        humidityMin: 0,                   // 湿度下限
        humidityMax: 100,                 // 湿度上限
        isAlert: 2                        // 2表示显示全部日志
    };

    // 发起数据请求
    fetch(`${apiBase}/api/log_batch`, {
        method: "POST",
        headers: { "Content-Type": "application/json" }, // JSON格式请求
        body: JSON.stringify(payload)                   // 序列化查询参数
    })
        .then(res => res.json())  // 解析响应为JSON
        .then(res => {
            // 响应状态码处理
            if (res.code === 200) { // 成功响应
                renderTable(res.data); // 渲染表格数据
            } else {                // 业务逻辑错误
                console.warn("后端返回失败", res.message); // 输出错误信息
            }
        })
        .catch(err => {
            console.error("获取日志失败", err); // 网络请求错误处理
        });
}

function renderTable(logs) {
    const tbody = document.querySelector("#logTable tbody");// 定位表格主体
    tbody.innerHTML = ""; // 清空旧数据

    // 动态生成表格行
    logs.forEach(log => {
        const tr = document.createElement("tr");
        let logId = log.logId.toString().padStart(6, '0'); // 将日志ID格式化为6位字符串（前补0）
        // 使用模板字符串构建表格内容
        tr.innerHTML = `
      <td><input type="checkbox" class="log-checkbox" data-id="${log.logId}"></td>
      <td>${logId}</td>
      <td>${new Date(log.logTime).toLocaleString()}</td>
      <td>${log.temperature}</td>
      <td>${log.humidity}</td>
      <td>${log.alert ? "⚠️ 是" : "否"}</td>
    `;

        tbody.appendChild(tr);// 插入表格主体
    });
}


// 初始化全选状态
let isAllSelected = false;

// 添加全选/取消全选按钮点击事件
document.getElementById('selectAllBtn').addEventListener('click', () => {
    const checkboxes = document.querySelectorAll('.log-checkbox'); // 获取所有复选框
    checkboxes.forEach(checkbox => {
        checkbox.checked = !isAllSelected; // 根据当前状态切换选中状态
    });

    // 切换状态
    isAllSelected = !isAllSelected;
    if(isAllSelected){
        document.getElementById('selectAllBtn').style.color = 'lightblue'; // 全选时按钮变色
    }
    else{
        document.getElementById('selectAllBtn').style.color = ''; // 取消全选时恢复默认颜色
    }
});

// 绑定按钮点击事件
document.getElementById('fetchLogsButton').addEventListener('click', () => {
    // ✅ 数据加载成功后再显示容器和关闭按钮
    document.getElementById('logContainer').style.display = 'block';
    document.getElementById('closeLogBtn').style.display = 'block';
    // 触发获取并渲染数据
    fetchAndRenderLogs();
    // 启动自动刷新逻辑
    if (!autoRefreshTimer) {
        autoRefreshTimer = setInterval(() => {
            const logContainer = document.getElementById("logContainer");
            if (logContainer && logContainer.style.display === "block") {
                fetchAndRenderLogs();
            }
        }, 5000); // 每 5 秒刷新
    }
});

document.getElementById('closeLogBtn').addEventListener('click', () => {
    document.getElementById('logContainer').style.display = 'none';
    document.getElementById('closeLogBtn').style.display = 'none';
    document.querySelector('#logTable tbody').innerHTML = '';
    // 停止自动刷新
    if (autoRefreshTimer) {
        clearInterval(autoRefreshTimer);
        autoRefreshTimer = null;
    }
});

// 绘制温湿度折线图函数
function plotTemperatureHumidityChart(selectedLogs) {
    // 按时间排序
    selectedLogs.sort((a, b) => new Date(a.logTime) - new Date(b.logTime));

    // 准备图表数据
    const labels = selectedLogs.map(log =>
        new Date(log.logTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    );

    const temperatureData = selectedLogs.map(log => log.temperature);
    const humidityData = selectedLogs.map(log => log.humidity);

    // 销毁现有图表
    if (window.myChart) {
        window.myChart.destroy();
    }

    // 显示图表容器
    const chartContainer = document.getElementById("chartContainer");
    chartContainer.style.display = 'block';
    document.getElementById("closeChartBtn").style.display = 'block';

    // 创建Canvas元素
    const canvas = document.getElementById("tempHumidityChart");
    canvas.width = chartContainer.clientWidth - 20;
    canvas.height = chartContainer.clientHeight - 20;

    // 绘制图表
    const ctx = canvas.getContext('2d');
    window.myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: '温度 (°C)',
                    data: temperatureData,
                    borderColor: 'rgb(255, 99, 132)', // 红色
                    backgroundColor: 'rgba(255, 99, 132, 0.1)',
                    borderWidth: 3, // 加粗温度线
                    pointRadius: 4,
                    yAxisID: 'y',
                    tension: 0.2
                },
                {
                    label: '湿度 (%)',
                    data: humidityData,
                    borderColor: 'rgb(54, 162, 235)', // 蓝色
                    backgroundColor: 'rgba(54, 162, 235, 0.1)',
                    borderWidth: 2,
                    pointRadius: 3,
                    yAxisID: 'y1',
                    tension: 0.2,
                    borderDash: [5, 3] // 添加虚线样式区分
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            interaction: {
                mode: 'index',
                intersect: false
            },
            scales: {
                y: {
                    type: 'linear',
                    display: true,
                    position: 'left',
                    title: {
                        display: true,
                        text: '温度 (°C)'
                    },
                    min: Math.min(...temperatureData) - 1,
                    max: Math.max(...temperatureData) + 1
                },
                y1: {
                    type: 'linear',
                    display: true,
                    position: 'right',
                    title: {
                        display: true,
                        text: '湿度 (%)'
                    },
                    min: Math.min(...humidityData) - 1,
                    max: Math.max(...humidityData) + 5,
                    grid: {
                        drawOnChartArea: false
                    }
                }
            },
            plugins: {
                title: {
                    display: true,
                    text: '温湿度变化趋势图',
                    font: {
                        size: 14
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            let label = context.dataset.label || '';
                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed.y !== null) {
                                label += context.parsed.y.toFixed(1);
                            }
                            return label;
                        },
                        afterLabel: function(context) {
                            const log = selectedLogs[context.dataIndex];
                            return `时间: ${new Date(log.logTime).toLocaleString()}\n告警: ${log.alert ? '是' : '否'}`;
                        }
                    }
                }
            }
        }
    });
}



document.getElementById('confirmSelectedBtn').addEventListener('click', () => {
    const checkboxes = document.querySelectorAll('.log-checkbox:checked');
    const selectedIds = Array.from(checkboxes).map(cb => parseInt(cb.dataset.id));

    if (selectedIds.length === 0) {
        alert("请先勾选需要统计的日志");
        return;
    }

    const payload = { ids: selectedIds };
    //console.log("构造的请求体为:", JSON.stringify(payload));

    fetch(`${apiBase}/api/log_batch/statistic`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    })
        .then(res => res.json())
        .then(res => {
            if (res.code === 200) {
                const data = res.data;
                const tbody = document.querySelector('#statisticTable tbody');
                tbody.innerHTML = `
                    <tr>
                        <td>${data.averageTemperature.toFixed(2)}</td>
                        <td>${data.averageHumidity.toFixed(2)}</td>
                        <td>${data.maxTemperature}</td>
                        <td>${data.minTemperature}</td>
                        <td>${data.maxHumidity}</td>
                        <td>${data.minHumidity}</td>
                        <td>${data.alertCount}</td>
                    </tr>
                `;
                document.getElementById('statisticTable').style.display = 'table';
                document.getElementById('closeStatisticBtn').style.display = 'block';

                // 显示在 logContainer 下的内嵌统计结果
                const inlineStatsBody = document.getElementById("logStatsBody");
                inlineStatsBody.innerHTML = `
                    <tr>
                        <td>${data.averageTemperature.toFixed(2)}</td>
                        <td>${data.averageHumidity.toFixed(2)}</td>
                        <td>${data.maxTemperature}</td>
                        <td>${data.minTemperature}</td>
                        <td>${data.maxHumidity}</td>
                        <td>${data.minHumidity}</td>
                        <td>${data.alertCount}</td>
                    </tr>
                `;
                document.getElementById("logStatsInline").style.display = "block";
                //add
                // 获取选中的日志数据
                //const selectedLogs = data.filter(log => selectedIds.includes(log.logId));

                fetch(`${apiBase}/api/log_batch/by-id`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(payload)
                }).then(res => res.json())
                    .then(data => {
                        if (data.code !== 200) {
                            alert("获取选中日志失败: " + data.message);
                        }
                        else{
                            plotTemperatureHumidityChart(data.data);
                            // 滚动到图表位置
                            document.getElementById("chartContainer").scrollIntoView({ behavior: "smooth" });
                        }
                    })

                // // 绘制折线图
                // plotTemperatureHumidityChart(selectedLogs);




                //document.getElementById("logStatsInline").scrollIntoView({ behavior: "smooth" });
            } else {
                alert("统计失败: " + res.message);
            }
        })
        .catch(err => {
            console.error("统计请求出错", err);
            alert("网络错误，请查看控制台日志");
        });
});



//************************************************************
document.getElementById('fetchAlertSettingsBtn').addEventListener('click', () => {
    fetch(`${apiBase}/api/log_alert`, {
        method: "GET",
        headers: { "Content-Type": "application/json" }
    })
        .then(res => res.json())
        .then(res => {
            if (res.code === 200) {
                renderAlertSettings(res.data);
            } else {
                alert("获取失败: " + res.message);
            }
        })
        .catch(err => {
            console.error("获取告警失败", err);
            alert("获取失败，请检查控制台");
        });
});

document.getElementById('submitAlertSettingsBtn').addEventListener('click', () => {
    const payload = {
        temperatureMin: parseFloat(document.getElementById('tempMin').value),
        temperatureMax: parseFloat(document.getElementById('tempMax').value),
        humidityMin: parseFloat(document.getElementById('humMin').value),
        humidityMax: parseFloat(document.getElementById('humMax').value)
    };

    fetch(`${apiBase}/api/log_alert`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    })
        .then(res => res.json())
        .then(res => {
            if (res.code === 200) {
                renderAlertSettings(res.data);
            } else {
                alert("设置失败: " + res.message);
            }
        })
        .catch(err => {
            console.error("提交告警失败", err);
            alert("设置失败，请检查控制台");
        });
});

function renderAlertSettings(data) {
    const tbody = document.querySelector("#alertTable tbody");
    tbody.innerHTML = "";

    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${data.temperatureMin}</td>
        <td>${data.temperatureMax}</td>
        <td>${data.humidityMin}</td>
        <td>${data.humidityMax}</td>
        <td>${new Date(data.setTime).toLocaleString()}</td>
    `;
    tbody.appendChild(row);

    document.getElementById("alertContainer").style.display = "block";
    document.getElementById("closeAlertBtn").style.display = "block";
}

document.getElementById('closeAlertBtn').addEventListener('click', () => {
    document.getElementById('alertContainer').style.display = 'none';
    document.getElementById('closeAlertBtn').style.display = 'none';
    document.querySelector('#alertTable tbody').innerHTML = '';
});


//******************************************************
document.getElementById('fetchStatisticsBtn').addEventListener('click', () => {
    const input = document.getElementById('statIdsInput').value.trim();
    if (!input) {
        alert("请输入日志 ID，例如: 1,2,3");
        return;
    }

    const ids = input.split(',').map(id => parseInt(id.trim())).filter(id => !isNaN(id));
    if (ids.length === 0) {
        alert("格式有误，确保输入如 1,2,3");
        return;
    }

    const payload = { ids };

    fetch(`${apiBase}/api/log_batch/statistic`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    })
        .then(res => res.json())
        .then(res => {
            if (res.code === 200) {
                const data = res.data;
                const tbody = document.querySelector('#statisticTable tbody');
                tbody.innerHTML = `
                    <tr>
                        <td>${data.averageTemperature.toFixed(2)}</td>
                        <td>${data.averageHumidity.toFixed(2)}</td>
                        <td>${data.maxTemperature}</td>
                        <td>${data.minTemperature}</td>
                        <td>${data.maxHumidity}</td>
                        <td>${data.minHumidity}</td>
                        <td>${data.alertCount}</td>
                    </tr>
                `;

                document.getElementById('statisticTable').style.display = 'table';
                document.getElementById('closeStatisticBtn').style.display = 'block';
            } else {
                alert("统计失败: " + res.message);
            }
        })
        .catch(err => {
            console.error("统计请求出错", err);
            alert("网络错误，请查看控制台日志");
        });
});

// 点击“×”关闭统计表格
document.getElementById('closeStatisticBtn').addEventListener('click', () => {
    document.getElementById('statisticTable').style.display = 'none';
    document.getElementById('closeStatisticBtn').style.display = 'none';
    document.querySelector('#statisticTable tbody').innerHTML = '';
});

// ======================= 筛选逻辑 ==========================
window.addEventListener('DOMContentLoaded', () => {
    const yearSel = document.getElementById("yearSelect");
    const monthSel = document.getElementById("monthSelect");
    const daySel = document.getElementById("daySelect");
    const hourSel = document.getElementById("hourSelect");
    const filterBtn = document.getElementById("unifiedFilterBtn");

    if (!yearSel || !monthSel || !daySel || !hourSel || !filterBtn) return;

    yearSel.innerHTML = "<option>选择年份</option>";
    const now = new Date();
    for (let y = 2000; y <= now.getFullYear(); y++) {
        yearSel.appendChild(new Option(`${y}年`, y));
    }

    yearSel.addEventListener("change", () => {
        monthSel.disabled = false;
        monthSel.innerHTML = "<option>选择月份</option>";
        for (let m = 1; m <= 12; m++) {
            monthSel.appendChild(new Option(`${m}月`, m.toString().padStart(2, "0")));
        }
        daySel.disabled = true;
        hourSel.disabled = true;
    });

    monthSel.addEventListener("change", () => {
        const year = yearSel.value;
        const month = monthSel.value;
        const days = new Date(year, month, 0).getDate();
        daySel.disabled = false;
        daySel.innerHTML = "<option>选择日期</option>";
        for (let d = 1; d <= days; d++) {
            daySel.appendChild(new Option(`${d}日`, d.toString().padStart(2, "0")));
        }
        hourSel.disabled = true;
    });

    daySel.addEventListener("change", () => {
        hourSel.disabled = false;
        hourSel.innerHTML = "<option>选择小时</option>";
        for (let h = 0; h < 24; h++) {
            hourSel.appendChild(new Option(`${h}时`, h.toString().padStart(2, "0")));
        }
    });

    // hourSel.addEventListener("change", () => {
    //     filterBtn.disabled = false;
    // });

    filterBtn.addEventListener("click", () => {
        const logContainer = document.getElementById("logContainer");
        if (!logContainer || logContainer.style.display !== "block") {
            alert("请先切换到『日志实时监测』页面后再使用时间筛选功能");
            return;
        }

        const year = yearSel.value;
        const month = monthSel.value;
        const day = daySel.value;
        const hour = hourSel.value;
        const tempMin = parseFloat(document.getElementById("tempMinFilter")?.value);
        const tempMax = parseFloat(document.getElementById("tempMaxFilter")?.value);
        const humMin = parseFloat(document.getElementById("humMinFilter")?.value);
        const humMax = parseFloat(document.getElementById("humMaxFilter")?.value);
        //let start = undefined;
        //let end = undefined;
        let start = "1970-01-01T00:00:00";
        let end = "2100-01-01T00:00:00";
        if(year === "选择年份" || year == null){
            start = "1970-01-01T00:00:00";
            end = "2100-01-01T00:00:00";
        }
        else if (month === "选择月份" || month == null) {
            start = `${year}-01-01T00:00:00`;
            end = `${year}-12-31T23:59:59`;
        } else if (day === "选择日期" || day == null) {
            start = `${year}-${month}-01T00:00:00`;
            end = `${year}-${month}-31T23:59:59`;
        } else if (hour === "选择小时" || hour == null) {
            start = `${year}-${month}-${day}T00:00:00`;
            end = `${year}-${month}-${day}T23:59:59`;
        } else {
            start = `${year}-${month}-${day}T${hour}:00:00`;
            end = `${year}-${month}-${day}T${hour}:59:59`;
        }

        const payload = {
            startDate: start,
            endDate: end,
            temperatureMin: isNaN(tempMin) ? -10000 : tempMin,
            temperatureMax: isNaN(tempMax) ? 10000 : tempMax,
            humidityMin: isNaN(humMin) ? 0 : humMin,
            humidityMax: isNaN(humMax) ? 100 : humMax,
            isAlert: 2
        };

        fetch(`${apiBase}/api/log_batch`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        })
            .then(res => res.json())
            .then(res => {
                if (res.code === 200) {
                    document.getElementById('logContainer').style.display = 'block';
                    document.getElementById('closeLogBtn').style.display = 'block';
                    renderTable(res.data);
                } else {
                    alert("筛选失败: " + res.message);
                }
            })
            .catch(err => {
                console.error("筛选请求出错", err);
                alert("网络错误");
            });
    });
});

document.getElementById('exportBtn').addEventListener('click', function () {


    let table = document.getElementById('logTable');
    let rowCount = table.rows.length; // 获取表格的行数

    let data = XLSX.utils.sheet_to_json(XLSX.utils.table_to_sheet(table), { header: 1 });

    data = data.map(row => row.slice(1));

    let worksheet = XLSX.utils.aoa_to_sheet(data);

    for (let row = 2; row <= rowCount; row++) {
        let cellRef = `B${row}`;
        if (worksheet[cellRef]) {
            worksheet[cellRef].z = "yyyy-mm-dd hh:mm:ss"; // 设置时间格式
        }
    }

    for (let row = 2; row <= rowCount; row++) {
        let cellRef = `E${row}`;
        if (worksheet[cellRef] && worksheet[cellRef].v.includes("是")) {
            worksheet[`E${row}`].v = "是";
        }
    }

    let workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

    XLSX.writeFile(workbook, "log-data.xlsx",{ cellStyles: true });

});
