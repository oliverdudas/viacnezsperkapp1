- the gmultipart maven dependency must by installed manually into the local repository:

mvn install:install-file -Dfile=${PATH_TO_PROJECT}\viacnezsperkapp1\src\main\webapp\WEB-INF\lib\gmultipart-0.4.jar -DgroupId=gmultipart -DartifactId=gmultipart -Dversion=0.4 -Dpackaging=jar