from PyQt5.QtWidgets import QWidget, QVBoxLayout, QLabel, QPushButton
from PyQt5.QtCore import Qt

class UndecidedPage(QWidget):
    def __init__(self, back_to_home_callback=None):
        super().__init__()

        title = QLabel("미정")
        title.setAlignment(Qt.AlignCenter)
        title.setStyleSheet("font-size: 24px; font-weight: bold;")

        layout = QVBoxLayout()
        layout.setSpacing(30)
        layout.setContentsMargins(30, 30, 30, 30)
        layout.addWidget(title)

        if back_to_home_callback:
            back_btn = QPushButton("← 홈으로")
            back_btn.setStyleSheet("font-size: 18px; padding: 10px;")
            back_btn.clicked.connect(back_to_home_callback)
            layout.addWidget(back_btn, alignment=Qt.AlignCenter)

        self.setLayout(layout)
