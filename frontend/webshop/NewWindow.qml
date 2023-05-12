import QtQuick 2.15
import QtQuick.Window 2.15
import QtQuick.Controls 2.15
import "."

Window {
    width: 640
    height: 480
    visible: true
    title: qsTr("Új ablak")

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
                } else {
                    console.log("Sikertelen kérés:", xhr.status, xhr.statusText);
                }
            }
        }

        xhr.open("GET", url, true);
        xhr.setRequestHeader("Authorization", "Bearer " + loginManager.getToken());
        xhr.send();
    }

    Button {
        text: qsTr("Küldj hitelesített kérést")
        anchors.centerIn: parent
        onClicked: {
            sendAuthenticatedRequest();
        }
    }
}
