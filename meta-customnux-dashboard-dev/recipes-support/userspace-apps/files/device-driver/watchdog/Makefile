CC = gcc
CFLAGS = -Wall -I/usr/include/linux-uapi-headers

TARGETS = watchdog-userspace

all: $(TARGETS)

watchdog-userspace: watchdog-userspace.c
	$(CC) $(CFLAGS) -o $@ $<

clean:
	rm -f $(TARGETS)
