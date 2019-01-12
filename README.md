# Oauth2SpringBoot

##usage:
GET request:
```
URI:  http://localhost:8080/oauth/authorize?client_id=test&response_type=code&scope=read
```

Response:
Login page. After login auto redirect to: http://localhost:8090?code=XXXXXX


POST request:
```
header: Authorization Basic client_id:client_secrect
URI:    http://localhost:8080/oauth/token?grant_type=authorization_code&code=XXXXXX&redirect_uri=http://localhost:8090/
```

Response:
JWT token