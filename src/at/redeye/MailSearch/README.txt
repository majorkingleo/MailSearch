
== in die .profile.user ==

Java Pfad hinzufügen

export PATH="~/bin/:${PATH}:`winpath2unix 'C:\Program Files (x86)\Java\jre6\bin'`"

function jsearch () {
     java.exe -jar 'c:\Program Files (x86)\SupaGrep\JavaSearch.jar' -cwd `pwd` -interix $*
}

== in die .bashrc ==

alias gc='jsearch -ext "*.c,*.cc,*.cpp" -expr'
alias gh='jsearch -ext "*.h,*.hh" -expr'
alias gch='jsearch -ext "*.c,*.cc,*.cpp,*.h,*.hh" -expr'
alias gpdl='jsearch -ext "*.pdl,*.pds" -expr'
alias gj='jsearch -ext "*.java" -expr'
