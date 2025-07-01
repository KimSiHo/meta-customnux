import gpiod
import time


TRIG_LINE = 17
ECHO_LINE = 27
CHIP = "gpiochip0"


def _measure_distance(trig, echo):
    trig.set_value(0)
    time.sleep(0.05)

    trig.set_value(1)
    time.sleep(0.00001)
    trig.set_value(0)

    while echo.get_value() == 0:
        pulse_start = time.time()

    while echo.get_value() == 1:
        pulse_end = time.time()

    pulse_duration = pulse_end - pulse_start
    distance = pulse_duration * 17150

    return round(distance, 2)


def _prepare_gpio():
    chip = gpiod.Chip(CHIP)

    trig = chip.get_line(TRIG_LINE)
    echo = chip.get_line(ECHO_LINE)

    trig.request(consumer="hc-sr04", type=gpiod.LINE_REQ_DIR_OUT)
    echo.request(consumer="hc-sr04", type=gpiod.LINE_REQ_DIR_IN)

    return trig, echo


def measure_distance_test():
    trig, echo = _prepare_gpio()

    try:
        while True:
            dist = _measure_distance(trig, echo)
            print(f"Distance: {dist} cm")
            time.sleep(1)
    except KeyboardInterrupt:
        print("Stopped")
    finally:
        trig.release()
        echo.release()


def measure_distance():
    trig, echo = _prepare_gpio()

    try:
        dist = _measure_distance(trig, echo)
        return dist
    except Exception as e:
        print(f"거리 측정 중 오류 발생: {e}")
        raise
    finally:
        trig.release()
        echo.release()


if __name__ == "__main__":
    measure_distance_test()
