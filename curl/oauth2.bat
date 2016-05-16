get grant code
--------------------------------------------
http://localhost:8098/oauth/authorize?client_id=oath2demo&response_type=code


curl -i -X POST -H "Content-Type: application/json" -d "{\"userName\":\"kevin\"}" localhost:8090/user/create

http://localhost:8098/rest/api/v1/greeting