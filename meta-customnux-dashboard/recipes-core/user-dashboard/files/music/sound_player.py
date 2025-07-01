import subprocess
import os
import time

SOUND_DIR = "/var/lib/media/music"

def _play_wav_pulse(filename, duration):
    filepath = os.path.join(SOUND_DIR, filename)
    if not os.path.isfile(filepath):
        print(f"[Error] File not found: {filepath}")
        return

    try:
        proc = subprocess.Popen(["paplay", filepath])
        time.sleep(duration)
        proc.terminate()
    except Exception as e:
        print(f"[Error] Failed to play sound: {e}")


def sound_emergency(duration):
    _play_wav_pulse("emergency.wav", duration)

def sound_alert(duration):
    _play_wav_pulse("alert.wav", duration)

def sound_notification(duration):
    _play_wav_pulse("notification.wav", duration)


if __name__ == "__main__":
    print("▶ Emergency")
    sound_emergency(5)

    print("▶ Alert")
    sound_alert(3)

    print("▶ Notification")
    sound_notification(2)
