# LouisPrinter
Louis is a open source braille printer made with Java and Arduino, powered by [LibLouis]

# Instalation
+ Windows: Just copy the dll that matches your architecture (from the lib/dll folder) and paste it into your windows / system32 folder
+ Linux: Yeah..(sigh) First of all you should download and compile(remember root!) [LibLouis]. Then copy some files: lib/Linux/RXTXcomm.jar goes in /jre/lib/ext (under java);
lib/Linux/[machine type]/librxtxSerial.so goes in /jre/lib/[machine type] (i386 for instance)
Make sure the user is in group lock or uucp so lockfiles work.
An example location of jre its in /usr/lib/jvm/java-8-oracle/jre/


The arduino source code is in [LouisPrinterArduino] repo

[LibLouis]: <http://liblouis.org>
[LouisPrinterArduino]: <https://github.com/franquitt/LouisPrinterArduino>