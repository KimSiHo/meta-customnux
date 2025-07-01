from PyQt5.QtWidgets import QWidget, QVBoxLayout, QLabel, QPushButton
from PyQt5.QtCore import Qt
from peripheral_devices.distance_sensor import measure_distance
from peripheral_devices.sound_player import sound_alert

class SystemPage(QWidget):
    def __init__(self, back_to_home_callback=None):
        super().__init__()

        title = QLabel("System")
        title.setAlignment(Qt.AlignCenter)
        title.setStyleSheet("font-size: 24px; font-weight: bold;")

        layout = QVBoxLayout()
        layout.setSpacing(30)
        layout.setContentsMargins(30, 30, 30, 30)
        layout.addWidget(title)

        if back_to_home_callback:
            self.back_btn = QPushButton("← 홈으로")
            self.back_btn.setStyleSheet("font-size: 18px; padding: 10px;")
            self.back_btn.clicked.connect(back_to_home_callback)
            layout.addWidget(self.back_btn, alignment=Qt.AlignCenter)

        self.setLayout(layout)

    def check_distance_and_alert(self):
        distance = measure_distance()
        print(f"측정된 거리: {distance}cm")
        if distance <= 20:
            print("거리가 20 이하입니다. 알람을 발생시킵니다.")
            sound_alert()
        else:
            print("거리가 안전 거리보다 큽니다.")


if __name__ == "__main__":
    import time

    system_page = SystemPage()

    while True:
        system_page.check_distance_and_alert()
        time.sleep(1)
