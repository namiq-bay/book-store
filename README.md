# book-store

## How to use
### Pre-requests
* Just execute docker-compose file 
```dockerfile
docker-compose -f src/main/resources/docker-compose.yml up -d
```

### Run
* Then run the application by Spring boot maven plugin directly.
```bash
mvn spring-boot:run
```

### Test

* For unit test phase, you can run:

```bash
mvn test
```
### Default Users

|username|password|
|------|------|
|publisher|qfrxxxadygrlwtfz|
|user|iyrgyamflafupzkn|
## Request URLs and Examples
* All requests contains HTTP Basic Auth.
### Details of a book by ID
* GET - http://localhost:8081/books/bc633c81-4f03-4dae-9c33-3935a1226f52

```json
{
    "id": "bc633c81-4f03-4dae-9c33-3935a1226f52",
    "bookName": "Spring Boot in Action",
    "author": "Craig Walls",
    "publisher": "publisher"
}
```

### List all books
* GET - http://localhost:8081/books/all

```json
[
    {
        "id": "bc633c81-4f03-4dae-9c33-3935a1226f52",
        "bookName": "Spring Boot in Action",
        "author": "Craig Walls",
        "publisher": "publisher"
    },
    {
        "id": "1dda15e2-9eb9-4c2c-8b82-2e274c1403a7",
        "bookName": "Head First Java",
        "author": "Kathy Sierra & Bert Bates",
        "publisher": "publisher"
    },
    {
        "id": "ac3c6dd0-2ef3-4d47-b8ca-62f2e7210433",
        "bookName": "Spring Microservices in Action",
        "author": "John Carnell",
        "publisher": "publisher"
    },
    {
        "id": "65a8f5ec-a2f3-40f4-88af-f0ecc1c937ea",
        "bookName": "Algorithms",
        "author": "Thomas H. Cormen",
        "publisher": "publisher"
    }
]
```

### List all books published by specific publisher
* GET - http://localhost:8081/books/publisher?publisherName=publisher

```json
[
    {
        "id": "bc633c81-4f03-4dae-9c33-3935a1226f52",
        "bookName": "Spring Boot in Action",
        "author": "Craig Walls",
        "publisher": "publisher"
    },
    {
        "id": "1dda15e2-9eb9-4c2c-8b82-2e274c1403a7",
        "bookName": "Head First Java",
        "author": "Kathy Sierra & Bert Bates",
        "publisher": "publisher"
    },
    {
        "id": "ac3c6dd0-2ef3-4d47-b8ca-62f2e7210433",
        "bookName": "Spring Microservices in Action",
        "author": "John Carnell",
        "publisher": "publisher"
    },
    {
        "id": "65a8f5ec-a2f3-40f4-88af-f0ecc1c937ea",
        "bookName": "Algorithms",
        "author": "Thomas H. Cormen",
        "publisher": "publisher"
    }
]
```
### Search specific books (pagination support)
* GET - http://localhost:8081/books?bookName=Java&pageNo=0&pageSize=1

```json
{
    "content": [
        {
            "id": "1dda15e2-9eb9-4c2c-8b82-2e274c1403a7",
            "bookName": "Head First Java",
            "author": "Kathy Sierra & Bert Bates",
            "publisher": "publisher"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 1,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalElements": 1,
    "last": true,
    "totalPages": 1,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "numberOfElements": 1,
    "first": true,
    "size": 1,
    "number": 0,
    "empty": false
}
```
### Add a new book (Publisher specific)
* POST - http://localhost:8081/books/create

#### Request Body
```json
{
    "bookName" : "Algorithms",
    "author": "Thomas H. Cormen"
}
```
> This request returns the 201 HTTP Created status code and the URI of the added book as the location in the Header.

### Update existing book by ID (Publisher specific)
* PUT - http://localhost:8081/books/update/65a8f5ec-a2f3-40f4-88af-f0ecc1c937ea
#### Request Body
```json
{
    "bookName" : "Introduction to Algorithms",
    "author": "Thomas H. Cormen"
}
```
#### Response body
```json
{
    "id": "65a8f5ec-a2f3-40f4-88af-f0ecc1c937ea",
    "bookName": "Introduction to Algorithms",
    "author": "Thomas H. Cormen",
    "publisher": "publisher"
}
```

### Delete existing book by ID (Publisher specific)
* DELETE - http://localhost:8081/books/delete/65a8f5ec-a2f3-40f4-88af-f0ecc1c937ea
  > This request returns the 202 HTTP Accepted status code.
