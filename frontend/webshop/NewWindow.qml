import QtQuick 2.15
import QtQuick.Window 2.15
import QtQuick.Controls 2.15
import QtCharts 2.0

Window {
    id: chartWindow
    width: 800
    height: 600
    visible: true
    title: qsTr("AlfWebshop @admin")

    MenuBar {
        id: menuBar
        width: parent.width
        Menu {
            title: "Menü"
            MenuItem {
                text: "Kijelentkezés"
                onTriggered: logout()
            }
            MenuItem {
                text: "Termék kategóriák lekérése"
                onTriggered: sendAuthenticatedRequest()
            }
        }
    }

    ChartView {
        id: chartView
        width: 600
        height: 300
        anchors.left: parent.left
        anchors.top: menuBar.bottom
        PieSeries {
            id: pieSeries
            PieSlice { label: "Placeholder" }
        }
    }

    ListView {
        id: listView
        width: 200
        height: 300
        anchors.right: parent.right
        anchors.top: menuBar.bottom
        model: ListModel {
            id: listModel
        }
        delegate: Text {
            text: model.name
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
                    listModel.clear();

                    var productData = JSON.parse(xhr.responseText);
                    var categories = {};

                    // Tally up the categories and add products to the list
                    for (var i = 0; i < productData.length; i++) {
                        var category = productData[i].category.name;
                        if (categories[category]) {
                            categories[category]++;
                        } else {
                            categories[category] = 1;
                        }
                        listModel.append({name: productData[i].name});
                    }

                    // Add the new data to the pie chart
                    for (var category in categories) {
                        var slice = pieSeries.append(category + " (" + categories[category] + ")", categories[category]);
                        slice.labelVisible = true;
                        console.log("Category:", category);
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

    function logout() {
            loginManager.deleteToken();
            console.log("jwtToken set to: ", loginManager.getToken());
            chartWindow.close();
            mainWindow.visible = true;
        }
}
