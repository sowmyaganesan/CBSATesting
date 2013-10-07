setlocal
set JDK17_HOME=C:\Program Files (x86)\Java\jdk1.7.0_09
del DOHRobot*.class
%JDK17_HOME%\bin\javac -target 1.7 -classpath %JDK17_HOME%\jre\lib\plugin.jar DOHRobot.java
rem del DOHRobot.jar
%JDK17_HOME%\bin\jar xvf DOHRobot.jar META-INF
%JDK17_HOME%\bin\jar cvf DOHRobot.jar DOHRobot*.class META-INF
rem %JDK14_HOME%\bin\jarsigner -keystore ./dohrobot DOHRobot.jar dojo <key
del DOHRobot*.class
endlocal
