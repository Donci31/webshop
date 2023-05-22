#include "loginmanager.h"

LoginManager::LoginManager(QObject *parent) : QObject(parent)
{
}

void LoginManager::storeToken(const QString &token)
{
    jwtToken = token;
}

QString LoginManager::getToken()
{
    return jwtToken;
}

void LoginManager::deleteToken()
{
    jwtToken = "-";
}
