import sys
from PyQt5.QtWidgets import (
    QApplication, QMainWindow, QPushButton, QVBoxLayout, QHBoxLayout,
    QWidget, QStackedWidget, QGridLayout, QSizePolicy
)
from monitoring_page import MonitoringPage
from system_page import SystemPage
from undecided_page import UndecidedPage
from peripheral_devices_page import PeripheralDevicesPage


class HomePage(QWidget):
    def __init__(self, switch_callback):
        super().__init__()
        layout = QGridLayout()
        layout.setContentsMargins(10, 10, 10, 10)
        layout.setSpacing(15)
        self.setLayout(layout)

        buttons = [
            ("시스템", 0),
            ("주변 장치", 1),
            ("모니터링", 2),
            ("미정", 3),
        ]

        for idx, (label, page_index) in enumerate(buttons):
            btn = QPushButton(label)
            btn.setMinimumSize(0, 0)
            btn.setSizePolicy(QSizePolicy.Expanding, QSizePolicy.Expanding)
            btn.setStyleSheet("font-size: 20px; padding: 20px;")
            btn.clicked.connect(lambda _, i=page_index: switch_callback(i + 1))
            layout.addWidget(btn, idx // 2, idx % 2)


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("System Dashboard")
        self.resize(800, 600)

        self.stack = QStackedWidget()

        self.home_page = HomePage(self.switch_page)
        self.system_page = SystemPage(self.go_home)
        self.peripheral_devices_page = PeripheralDevicesPage(self.go_home)
        self.monitoring_page = MonitoringPage(self.go_home)
        self.undecided_page = UndecidedPage(self.go_home)

        self.stack.addWidget(self.home_page)
        self.stack.addWidget(self.system_page)
        self.stack.addWidget(self.peripheral_devices_page)
        self.stack.addWidget(self.monitoring_page)
        self.stack.addWidget(self.undecided_page)
        self.setCentralWidget(self.stack)

    def switch_page(self, index):
        self.stack.setCurrentIndex(index)

    def go_home(self):
        self.stack.setCurrentIndex(0)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())
