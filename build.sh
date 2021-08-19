mvn -P dev clean package -Dmaven.test.skip=true
cp libsqlitejdbc.so ~/electric-dash/.
docker build -t isahl/meter -f Dockerfile ~/electric-dash