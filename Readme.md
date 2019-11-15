# Hibernate One to Many - Demo Application

# REST API:

POST a message:
```
curl -X POST \
  http://localhost:8080/posts/ \
  -H 'Content-Type: application/json' \
  -d '{
	"title": "Post 1",
	"description": "bla bla",
	"content": "content"
}'
```

POST comments:
```
curl -X POST \
  http://localhost:8080/posts/1/comments \
  -H 'Content-Type: application/json' \
  -d '[
	{
	"message": "superb!"
	},
	{
	"message": "Awesome..."
	}
]
```

GET all posts with comments:
```
curl -X GET \
  http://localhost:8080/posts/ 
```
