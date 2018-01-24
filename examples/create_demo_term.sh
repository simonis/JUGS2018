#!/bin/bash

XDG_CONFIG_HOME=~/.config_presentation xfce4-terminal --maximize --hide-menubar --disable-server -T "Historical" -e "`pwd`/create_demo_tab.sh historical" --tab -T "Debug" -e "`pwd`/create_demo_tab.sh debug"  --tab -T "Hello" -e "`pwd`/create_demo_tab.sh hello" --tab -T "JShell" -e "`pwd`/create_demo_tab.sh jshell" &

