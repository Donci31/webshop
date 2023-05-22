
#include <QApplication>
#include <QQmlApplicationEngine>
#include <QQmlContext>
#include "loginmanager.h"

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);
    QQmlApplicationEngine engine;

    LoginManager loginManager;
    engine.rootContext()->setContextProperty("loginManager", &loginManager);

    const QUrl url(u"qrc:/webshop/Main.qml"_qs);
    QObject::connect(&engine, &QQmlApplicationEngine::objectCreationFailed,
        &app, []() { QCoreApplication::exit(-1); },
        Qt::QueuedConnection);
    engine.load(url);

    return app.exec();
}
