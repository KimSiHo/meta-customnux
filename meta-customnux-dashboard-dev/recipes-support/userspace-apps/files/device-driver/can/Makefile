CC = gcc
CFLAGS = -Wall -I/usr/include/linux-uapi-headers

TARGETS = can-raw can-raw-stats

all: $(TARGETS)

can-raw: can-raw.c
	$(CC) $(CFLAGS) -o $@ $<

can-raw-stats: can-raw-stats.c
	$(CC) $(CFLAGS) -o $@ $<

clean:
	rm -f $(TARGETS)
