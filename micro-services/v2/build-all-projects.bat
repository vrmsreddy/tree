echo Building config-server
call gradle -q -p config-server clean build

echo Building webservice-registry
call gradle -q -p webservice-registry clean build

echo Building api-gateway
call gradle -q -p api-gateway clean build
echo Building auth-server
call gradle -q -p auth-server clean build

echo Building comments-webservice
call gradle -q -p comments-webservice clean build
echo Building task-webservice
call gradle -q -p task-webservice clean build
echo Building user-webservice
call gradle -q -p user-webservice  clean build
echo Building web-portal
call gradle -q -p web-portal clean build

echo Building zipkin-server
call gradle -q -p zipkin-server clean build
