import QtQuick 2.15
import QtQuick.Window 2.15
import QtQuick.Controls 2.15


Window {
    id: mainWindow
    width: 400
    height: 300
    visible: true
    title: qsTr("Bejelentkezés")



    function openNewWindowAndCloseCurrent() {
            var component = Qt.createComponent("NewWindow.qml");
            if (component.status === Component.Ready) {
                var window = component.createObject(mainWindow);
                window.show();
            } else {
                console.log("Error creating object:", component.errorString());
            }

            // Bezárja az aktuális ablakot
            mainWindow.visible = false;
        }

    function handleLogin() {
        var email = emailField.text;
        var password = passwordField.text;

        var xhr = new XMLHttpRequest();
        var url = "http://localhost:8080/api/login";

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    console.log("Sikeres bejelentkezés:", xhr.responseText);
                    var response = JSON.parse(xhr.responseText);
                    if (response.token) {
                        loginManager.storeToken(response.token);
                        console.log(loginManager.getToken());
                    } else {
                        console.log("A válaszban nem található JWT token.");
                    }
                    openNewWindowAndCloseCurrent();
                } else {
                    console.log("Sikertelen bejelentkezés:", xhr.status, xhr.statusText);
                }
            }
        }

        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.send(JSON.stringify({ email: email, password: password }));
    }


    Column {
        anchors.centerIn: parent
        spacing: 20

        Label {
            id: emailLabel
            text: qsTr("Email:")
            horizontalAlignment: Text.AlignRight
        }

        TextField {
            id: emailField
            width: 250
            placeholderText: qsTr("test@test.com")
            text: "admin@test.com"
            inputMethodHints: Qt.ImhEmailCharactersOnly
            anchors.horizontalCenter: parent.horizontalCenter
        }

        Label {
            id: passwordLabel
            text: qsTr("Jelszó:")
            horizontalAlignment: Text.AlignRight
        }

        TextField {
            id: passwordField
            width: 250
            placeholderText: qsTr("password")
            text: "admin"
            echoMode: TextInput.Password            
            inputMethodHints: Qt.ImhHiddenText | Qt.ImhSensitiveData | Qt.ImhNoAutoUppercase
            anchors.horizontalCenter: parent.horizontalCenter

            Keys.onReturnPressed: {
                handleLogin()
            }
        }

        Button {
            id: loginButton
            text: qsTr("Bejelentkezés")
            width: 250
            anchors.horizontalCenter: parent.horizontalCenter
            onClicked: {
                handleLogin()
            }

        }
    }
}
