
export PS1="\[\e]0;\w\a\]\n\[\e[32m\]\u@\h \[\e[33m\]\w\[\e[0m\]\n\$ "
export TERM=xterm-256color

CWD=`pwd`

#export PATH=/share/output-jdk9-hs-comp-dbg/images/jdk/bin:$PATH
#export PATH=/share/output-jdk9-dev-opt/images/jdk/bin:$PATH

if [ "$1" == "historical" ]; then
export PATH=/share/output-jdk-hs-opt/images/jdk/bin:$PATH
export CLASSPATH=$CWD/jdk113/demo/DrawTest/1.0.2:$CWD/jdk113/demo/MoleculeViewer:$CWD/jdk113/demo/WireFrame
# export _JAVA_OPTIONS="-Djava.security.policy=java.policy"
# cd jdk113/
# cd /share/software/Java/jdk1.1.3
else
if [ "$1" == "debug" ]; then
export PATH=/share/output-jdk-hs-dbg/images/jdk/bin:$PATH
export CLASSPATH=$CWD/bin
else
if [ "$1" == "hello" ]; then
export PATH=/share/output-jdk-hs-dbg/images/jdk/bin:$PATH
export CLASSPATH=$CWD/bin
else
if [ "$1" == "jshell" ]; then
export CLASSPATH=$CWD/bin
fi
fi
fi
fi

export LD_LIBRARY_PATH=/share/OpenJDK/hsdis
ulimit -c unlimited

alias la='ls -la'
#alias emacs="emacs -nw --eval \"(load-theme 'manoj-dark t)\""
alias emacs="emacs -nw --eval \"(load-theme 'tango-dark t)\""

set -o history
unset HISTFILE
history -c
history -r $CWD/.history_$1
