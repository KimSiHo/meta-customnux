export JAVA_HOME=/usr/lib/jvm/openjdk-8
export PATH=$JAVA_HOME/bin:$PATH
export LD_LIBRARY_PATH=/usr/lib:$LD_LIBRARY_PATH
export LANG=ko_KR.utf8

# alias settings
alias ls='ls --color=auto'
alias python='python3'
alias clr='clear'
alias lsblk='lsblk -f'
alias dmesge='dmesg --level=err,crit,alert,emerg'

alias tmate-share='tmate show-messages | grep ssh > /mnt/share/tmate.txt'
alias tmux-split='tmux new-session \; split-window -v'

alias jboot='journalctl -b'
alias junit='journalctl -u'
alias jfunit='journalctl -f -u'
alias jdetail='journalctl -xe -u'

alias sc='systemctl'
alias sca='systemctl status'
alias sce='systemctl enable'
alias scd='systemctl disable'
alias scr='systemctl restart'
alias sclu='systemctl list-units'
alias scluf='systemctl list-unit-files'
alias scp='systemctl start'
alias scs='systemctl stop'

# pyqt5 settings
export QT_QPA_FONTDIR=/usr/share/fonts/truetype
export XDG_RUNTIME_DIR=/tmp/xdg
mkdir -p $XDG_RUNTIME_DIR
chmod 700 $XDG_RUNTIME_DIR

# function settings
fbgrab() {
    /usr/bin/fbgrab /mnt/share/capture_$(date +%s).png
}

# common settings
numlockx on
