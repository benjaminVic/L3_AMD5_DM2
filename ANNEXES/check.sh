#!/bin/bash

expected="Grid.java Cellule.java Neighbor.java Laby.java Makefile"
optional="bonus.xml"

function die {
	echo "______________"
	printf "$2" >&2 && echo
	echo "Rejected."
	echo "Please have a look on tp2.pdf"
	exit $1
}
function help {
echo "-----------------------------------"
echo "Welcome to the archive checker"
echo "  This script only test the presence and the absence of expected files."
echo "  Only file listed below as 'Found' will be considered."
echo "  The submission specification is described in 'tp2.pdf', section 4."
echo "-----------------------------------"
}

#test presence of argument (error return: 1)
[ $# -lt 1 ] &&\
	echo "Usage: ./test.sh {archive.tar}" >&2 &&\
	exit 1
[[ "$1" == "--help" ]] || [[ "$1" == "-h" ]] &&\
	help &&\
	exit 0

echo && echo "------- Checking the archive"
ftar="$1"
#test presence of archive (error return: 2)
[ -f "$ftar" ] ||\
	die 2 "ERROR: '$ftar' file not found"

#test name of archive (error return: 3)
echo "$ftar" | egrep "^[-a-z]*_[-a-z]*.tar\$" >/dev/null
if [ $? -eq 0 ]; then
	echo "    Name '$ftar' seems to be well formed."
else
	die 3 "ERROR: the archive name is wrong.\n\texpected name: '<firstname>_<lastname>.tar'\n\twhere '<lastname>' and '<firstname>' have to be replaced by your own last and first name written in lower case without accent, space and apostrophe."
fi

#test validity of the archive (error return: 4)
fin="$(tar tf "$ftar")"
[ $? -ne 0 ] &&\
	die 4 "Error: '$ftar' is not a valid archive."

#test presence of expected files (error return: 5)
echo && echo "------- Looking for expected files..."
expflag=false
for i in $expected; do
	echo "$fin" | egrep "^$i\$" >/dev/null
	if [ $? -eq 0 ]; then
		echo "    Found: '$i'"
	else
		expflag=true
		echo "    ERROR: '$i' not found!" >&2
	fi
done

#test presence of optional files
echo && echo "------- Looking for optional files..."
optflag=false
for i in $optional; do
	echo "$fin" | egrep "^$i\$" >/dev/null
	if [ $? -eq 0 ]; then
		echo "    Found: '$i'"
	else
		optflag=true
		echo "    WARNING: '$i' not found!" >&2
	fi
done

#test presence of additional files
echo && echo "------- Looking for additional files..."
addflag=false
for i in $fin; do
	if [[ "$i" =~ *.java ]]; then
		echo "$expected" | egrep "^$i\$" >/dev/null ||\
			echo "$optional" | egrep "^$i\$" >/dev/null
		[ $? -ne 0 ] &&\
			addflag=true &&\
			echo "    Found: '$i'"
	fi
done
$addflag && echo "    That's all."
$addflag || echo "    No additional file found."

$expflag && die 5 "ERROR: Some required files are missing."
#accept archive and exit (return 0)
echo "______________"
echo "Accepted"
exit 0

