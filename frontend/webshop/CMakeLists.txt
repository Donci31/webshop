cmake_minimum_required(VERSION 3.16)

project(webshop VERSION 0.1 LANGUAGES CXX)

set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(Qt6 6.4 REQUIRED COMPONENTS Quick Quick Charts)

qt_standard_project_setup()

qt_add_executable(appwebshop
    main.cpp
    loginmanager.h
    loginmanager.cpp
)

qt_add_qml_module(appwebshop
    URI webshop
    VERSION 1.0
    QML_FILES Main.qml NewWindow.qml
)

set_target_properties(appwebshop PROPERTIES
    MACOSX_BUNDLE_GUI_IDENTIFIER my.example.com
    MACOSX_BUNDLE_BUNDLE_VERSION ${PROJECT_VERSION}
    MACOSX_BUNDLE_SHORT_VERSION_STRING ${PROJECT_VERSION_MAJOR}.${PROJECT_VERSION_MINOR}
    MACOSX_BUNDLE TRUE
    WIN32_EXECUTABLE TRUE
)

target_link_libraries(appwebshop
    PRIVATE Qt6::Quick
    Qt6::Charts
)

install(TARGETS appwebshop
    BUNDLE DESTINATION .
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})
