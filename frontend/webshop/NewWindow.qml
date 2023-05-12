import QtQuick 2.15
import QtQuick.Window 2.15
import QtQuick.Controls 2.15

Window {
    width: 800
    height: 600
    visible: true
    title: qsTr("AlfWebshop @admin")

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

    MenuBar {
        width: parent.width
            Menu {
                title: "Menü"
                MenuItem {
                    text: "Profil"
                    onTriggered: console.log("Profil")
                }
                MenuItem {
                    text: "Kijelentkezés"
                    onTriggered: console.log("Kijelentkezés")
                }
            }
        }
}
