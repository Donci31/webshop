#ifndef LOGINMANAGER_H
#define LOGINMANAGER_H

#include <QObject>
#include <QString>

class LoginManager : public QObject
{
    Q_OBJECT

public:
    explicit LoginManager(QObject *parent = nullptr);

    // Q_INVOKABLE azt jelenti, hogy ezt a függvényt meghívhatjuk QML-ből.
    Q_INVOKABLE void storeToken(const QString &token);

    Q_INVOKABLE QString getToken();
    Q_INVOKABLE void deleteToken();

private:
    QString jwtToken;
};

#endif
