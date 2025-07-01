from PyQt5.QtWidgets import (
    QWidget, QVBoxLayout, QLabel, QPushButton, QTextEdit, QHBoxLayout
)
from PyQt5.QtCore import Qt, QProcess


class PeripheralDevicesPage(QWidget):
    def __init__(self, back_to_home_callback=None):
        super().__init__()

        self.output_box = QTextEdit(self)
        self.output_box.setReadOnly(True)
        self.output_box.setStyleSheet("font-size: 14px;")

        title = QLabel("주변 장치 테스트")
        title.setAlignment(Qt.AlignCenter)
        title.setStyleSheet("font-size: 24px; font-weight: bold;")

        # 거리 센서 실행/중단 버튼
        self.distance_process = None
        self.distance_btn = QPushButton("거리 센서 실행")
        self.distance_btn.setStyleSheet("font-size: 18px; padding: 10px;")
        self.distance_btn.clicked.connect(self.run_distance_sensor)

        self.distance_stop_btn = QPushButton("중단")
        self.distance_stop_btn.setStyleSheet("font-size: 18px; padding: 10px;")
        self.distance_stop_btn.clicked.connect(self.stop_distance_sensor)

        distance_btn_group = QHBoxLayout()
        distance_btn_group.addWidget(self.distance_btn)
        distance_btn_group.addWidget(self.distance_stop_btn)

        # 사운드 재생 실행/중단 버튼
        self.sound_process = None
        self.sound_btn = QPushButton("사운드 재생")
        self.sound_btn.setStyleSheet("font-size: 18px; padding: 10px;")
        self.sound_btn.clicked.connect(self.run_sound_player)

        self.sound_stop_btn = QPushButton("중단")
        self.sound_stop_btn.setStyleSheet("font-size: 18px; padding: 10px;")
        self.sound_stop_btn.clicked.connect(self.stop_sound_player)

        sound_btn_group = QHBoxLayout()
        sound_btn_group.addWidget(self.sound_btn)
        sound_btn_group.addWidget(self.sound_stop_btn)

        layout = QVBoxLayout()
        layout.setSpacing(30)
        layout.setContentsMargins(30, 30, 30, 30)
        layout.addWidget(title)
        layout.addWidget(self.output_box)
        layout.addLayout(distance_btn_group)
        layout.addLayout(sound_btn_group)

        if back_to_home_callback:
            back_btn = QPushButton("← 홈으로")
            back_btn.setStyleSheet("font-size: 18px; padding: 10px;")
            back_btn.clicked.connect(back_to_home_callback)
            layout.addWidget(back_btn, alignment=Qt.AlignCenter)

        self.setLayout(layout)

    def run_distance_sensor(self):
        if self.distance_process:
            self.distance_process.kill()
        self.distance_process = QProcess(self)
        self.distance_process.setProcessChannelMode(QProcess.MergedChannels)
        self.distance_process.readyReadStandardOutput.connect(
            lambda: self._read_output(self.distance_process))
        self.distance_process.start("python3", ["-u", "/opt/dashboard/peripheral_devices/distance_sensor.py"])

    def stop_distance_sensor(self):
        if self.distance_process:
            self.distance_process.kill()
            self.output_box.append("[거리 센서] 중단됨")

    def run_sound_player(self):
        if self.sound_process:
            self.sound_process.kill()
        self.sound_process = QProcess(self)
        self.sound_process.setProcessChannelMode(QProcess.MergedChannels)
        self.sound_process.readyReadStandardOutput.connect(
            lambda: self._read_output(self.sound_process))
        self.sound_process.start("python3", ["-u", "/opt/dashboard/peripheral_devices/sound_player.py"])

    def stop_sound_player(self):
        if self.sound_process:
            self.sound_process.kill()
            self.output_box.append("[사운드 재생] 중단됨")

    def _read_output(self, process):
        output = process.readAllStandardOutput().data().decode()
        self.output_box.append(output)
