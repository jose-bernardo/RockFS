echo "###############################################"
echo "#              RockFS installer               #"
echo "###############################################"


#echo "Installing PVSS..."
#mvn install:install-file -Dfile=lib/pvss.jar -DgroupId=com.ufsc -DartifactId=pvss -Dversion=1.0-SNAPSHOT -Dpackaging=jar

DIR=`pwd`

echo "Installing JReedSolEC..."
mvn install:install-file -Dfile=lib/JavaReedSolomon.jar -DgroupId=backblaze.backblaze -DartifactId=JavaReedSolomon -Dversion=1.0-SNAPSHOT -Dpackaging=jar

echo "Downloading Depspacito"
git clone https://github.com/inesc-id/DepSpacito.git
cd DepSpacito
echo "Installing Depspacito"
sh build_args.sh 127.0.0.1 8000
mvn install
cd $DIR

echo "Installing SafeCloudFS..."
mvn -s settings.xml compile

echo "Finished installing"


