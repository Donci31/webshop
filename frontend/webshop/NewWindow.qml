import QtQuick 2.15
import QtQuick.Window 2.15
import QtQuick.Controls 2.15
import QtCharts 2.0

Window {
    width: 800
    height: 600
    visible: true
    title: qsTr("AlfWebshop @admin")

    ChartView {
        id: chartView
        width: 400
        height: 300
        anchors.centerIn: parent
        PieSeries {
            id: pieSeries
            PieSlice { label: "Placeholder" }
        }
    }

    function sendAuthenticatedRequest() {
        if (loginManager.getToken() === "") {
            console.log("A JWT token nincs beállítva.");
            return;
        }

        var xhr = new XMLHttpRequest();
        var url = "http://localhost:8080/api/products";

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    console.log("Sikeres kérés:", xhr.responseText);

                    // Clear the previous data
                    pieSeries.clear();

                    var productData = JSON.parse(xhr.responseText);
                    var categories = {};

                    // Tally up the categories
                    for (var i = 0; i < productData.length; i++) {
                        var category = productData[i].category.name;
                        if (categories[category]) {
                            categories[category]++;
                        } else {
                            categories[category] = 1;
                        }
                    }

                    // Add the new data to the pie chart
                    for (var cat in categories) {
                        pieSeries.append(cat, categories[cat]);
                        console.log("Category:", cat);
                    }
                } else {
                    console.log("Sikertelen kérés:", xhr.status, xhr.statusText);
                }
            }
        }

        xhr.open("GET", url, true);
        xhr.setRequestHeader("Authorization", "Bearer " + loginManager.getToken());
        xhr.send();
    }

    MenuBar {
        width: parent.width
        Menu {
            title: "Menü"
            MenuItem {
                text: "Termék kategóriák lekérése"
                onTriggered: sendAuthenticatedRequest()
            }
        }
    }
}
