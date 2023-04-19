
#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QMessageBox>



MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    setWindowTitle("Webshop");
}

MainWindow::~MainWindow()
{
    delete ui;
}



void MainWindow::on_pushButton_login_clicked()
{
    QString UserName = ui->lineEdit_username->text();
    QString Password = ui->lineEdit_password->text();

    if (UserName == "admin" && Password == "admin")
    {
        QMessageBox::information(this, "Webshop", "Login successful");
    }
    else
    {
        QMessageBox::information(this, "Webshop", "Please enter a valid username or password");
    }
}


void MainWindow::on_pushButton_cancel_clicked()
{
    QMessageBox::StandardButton reply;
    reply =  QMessageBox::question(this, "Webshop", "Are you sure to close the application?", QMessageBox::Yes | QMessageBox::No);
    if (reply == QMessageBox::Yes)
    {
        QApplication::quit();
    }
}

