#!/bin/bash

echo 0 > /sys/kernel/debug/tracing/tracing_on
sleep 1
echo "tracing_off"

echo 0 > /sys/kernel/debug/tracing/events/enable
sleep 1
echo "events disabled"

echo function > /sys/kernel/debug/tracing/current_tracer
sleep 1
echo "function tracer enabled"

{
  grep "sys_clone" /sys/kernel/debug/tracing/available_filter_functions
  echo do_exit
  echo _do_fork
  echo copy_process
} >> /sys/kernel/debug/tracing/set_ftrace_filter
sleep 1
echo "set_ftrace_filter enabled"

echo 1 > /sys/kernel/debug/tracing/events/sched/sched_wakeup/enable
echo 1 > /sys/kernel/debug/tracing/events/sched/sched_switch/enable
echo 1 > /sys/kernel/debug/tracing/events/sched/sched_process_fork/enable
echo 1 > /sys/kernel/debug/tracing/events/sched/sched_process_exit/enable
echo 1 > /sys/kernel/debug/tracing/events/task/task_rename

echo 1 > /sys/kernel/debug/tracing/events/signal/enable
sleep 1
echo "event enabled"

echo 1 > /sys/kernel/debug/tracing/options/func_stack_trace
echo 1 > /sys/kernel/debug/tracing/options/sym-offset
sleep 1
echo "function stack trace enabled"

echo 1 > /sys/kernel/debug/tracing/tracing_on
sleep 1
echo "tracing_on"
