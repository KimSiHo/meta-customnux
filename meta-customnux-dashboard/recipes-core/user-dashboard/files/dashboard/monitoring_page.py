import psutil
from PyQt5.QtWidgets import QWidget, QLabel, QVBoxLayout, QPushButton
from PyQt5.QtCore import Qt, QTimer

class MonitoringPage(QWidget):
    def __init__(self, back_to_home_callback=None):
        super().__init__()

        self.cpu_label = QLabel("CPU usage: ", self)
        self.cpu_label.setAlignment(Qt.AlignCenter)
        self.cpu_label.setStyleSheet("font-size: 24px; font-weight: bold;")

        self.mem_label = QLabel("MEMORY usage: ", self)
        self.mem_label.setAlignment(Qt.AlignCenter)
        self.mem_label.setStyleSheet("font-size: 24px; font-weight: bold;")

        layout = QVBoxLayout()
        layout.setSpacing(30)
        layout.setContentsMargins(30, 30, 30, 30)
        layout.addWidget(self.cpu_label)
        layout.addWidget(self.mem_label)

        if back_to_home_callback:
            self.back_btn = QPushButton("← 홈으로", self)
            self.back_btn.setStyleSheet("font-size: 18px; padding: 10px;")
            self.back_btn.clicked.connect(back_to_home_callback)
            layout.addWidget(self.back_btn, alignment=Qt.AlignCenter)

        self.setLayout(layout)

        self.timer = QTimer()
        self.timer.timeout.connect(self.update_stats)
        self.timer.start(1000)
        self.update_stats()

    def update_stats(self):
        cpu = psutil.cpu_percent()
        mem = psutil.virtual_memory().percent
        self.cpu_label.setText(f"CPU usage: {cpu}%")
        self.mem_label.setText(f"MEMORY usage: {mem}%")
