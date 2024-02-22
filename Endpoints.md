Endpoints
======

## Authentication

### Post
`/api/auth/registration` - registers new user, accessible without any role.

Exemplary link:
`http://localhost:8080/api/auth/registration`

Exemplary request:
```json
{
    "email": "bob@example.com",
    "password": "1234",
    "repeatPassword": "1234",
    "firstName": "Name",
    "lastName": "Surname",
    "shippingAddress": "address"
}
```
All parameters, except for shippingAddress, must not be blank.

**Response status code**: 201

Exemplary response:
```json
{
    "id": 1,
    "email": "bob@example.com",
    "firstName": "Name",
    "lastName": "Surname",
    "shippingAddress": "address"
}
```
---

`/api/auth/login` - log in with registered user, return the JWT token, accessible without any role.

Exemplary link:
`http://localhost:8080/api/auth/login`

Exemplary request:
```json
{
    "email": "bob@example.com",
    "password": "1234"
}
```
All parameters must not be blank.

**Response status**: 202
Exemplary response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2JAZXhhbXBsZS5jb20iLCJpYXQiOjE3MDg1NTA3OTEsImV4cCI6MTcwODU1MTA5MX0.FQOptkrB5WwoFdEU7B7hi9S_AZEE5Kk927xUSBhJu5I"
}
```

## Book

### Get
`/api/books` - returns a list of all stored books as BookDto, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/books`

**Response status code**: 200

Exemplary response:
```json
{
    "id": 1,
    "title": "Colony",
    "author": "Max Kidruk",
    "isbn": "978-0-545-01022-1",
    "price": 15.50,
    "description": "First book in 'Dark ages' trilogy",
    "coverImage": "http://example.com/colony.jpg",
    "categoryIds": [
      1
    ]
}
```
---

`/api/books/{id}` - returns book by the specified id value as a BookDto, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/books/1`

**Response status code**: 200

Exemplary response:
```json
{
    "id": 1,
    "title": "Colony",
    "author": "Max Kidruk",
    "isbn": "978-0-545-01022-1",
    "price": 15.50,
    "description": "First book in 'Dark ages' trilogy",
    "coverImage": "http://example.com/colony.jpg",
    "categoryIds": [
      1
    ]
}
```
---

`/api/books/search` - searches for books using specified parameters and returns a list of BookDto, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/books/search?titles=Colony&author=Max Kidruk`

**Response status code**: 200

Exemplary response:
```json
{
    "id": 1,
    "title": "Colony",
    "author": "Max Kidruk",
    "isbn": "978-0-545-01022-1",
    "price": 15.50,
    "description": "First book in 'Dark ages' trilogy",
    "coverImage": "http://example.com/colony.jpg",
    "categoryIds": [
      1
    ]
}
```

### Post
`/api/books` - creates a new book in the database and returns a BookDto, accessible for role Admin.

Exemplary request:
```json
{
    "title": "Another book",
    "author": "Me",
    "isbn": "978-92-95055-02-5",
    "price": 29.99,
    "description": "Desc",
    "coverImage": "https://example.com/updated-cover-image.jpg"
}
```
Parameters title, author must not be blank, isbn must comply with ISBN code style (10 or 13 symbols), price must be more than 0. Other parameters may be null or blank.

**Response status code**: 201

Exemplary response:
```json
{
    "id": 6,
    "title": "Another book",
    "author": "Me",
    "isbn": "978-92-95055-02-5",
    "price": 29.99,
    "description": "Desc",
    "coverImage": "https://example.com/updated-cover-image.jpg",
    "categoryIds": []
}
```

### Put
`/api/books/{id}` - updates the books with specified id, accessible for role Admin. Takes the request and sets the specified id to it, so basically this operation overwrites the database record. 

Exemplary link:
`http://localhost:8080/api/books/1`

**Response status code**: 202

Exemplary request:
```json
{
    "title": "Godfather",
    "author": "Mario Puso",
    "isbn": "978-1-4028-9462-6",
    "price": 21.99,
    "description": "Mafia",
    "coverImage": "https://example.com/godfather-cover-image.jpg"
}
```

### Delete
`/api/books/{id}` - soft-deletes from database a record with the specified id, accessible for role Admin.

Exemplary link:
`http://localhost:8080/api/books/1`

**Response status code**: 200


## Category mappings
### Get
`/api/categories` - returns a list of all categories stored in the database, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/categories`

**Response status code**: 200

Exemplary response:
```json
[
    {
        "id": 1,
        "name": "sci-fi",
        "description": "Science fiction"
    },
    {
        "id": 2,
        "name": "fantasy",
        "description": "Classic Tolkien fantasy genre"
    }
]
```
---

`/api/categories/{id}` - returns category that has specified id value, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/categories/1`

**Response status code**: 200

Exemplary response:
```json
{
    "id": 1,
    "name": "sci-fi",
    "description": "Science fiction"
}
```
---

`/api/categories/{id}/books` - returns a list of books, that have specified category

Exemplary link:
`http://localhost:8080/api/categories/1/books`

**Response status code**: 200

Exemplary response:
```json
[
    {
        "id": 1,
        "title": "Colony",
        "author": "Max Kidruk",
        "isbn": "978-0-545-01022-1",
        "price": 15.50,
        "description": "First book in 'Dark ages' trilogy",
        "coverImage": "http://example.com/colony.jpg"
    },
    {
        "id": 5,
        "title": "Hyperion",
        "author": "Dan Simmons",
        "isbn": "978-1-4028-9462-6",
        "price": 24.99,
        "description": "First book in series of science fiction novels",
        "coverImage": "http://example.com/hyperion.jpg"
    }
]
```

### Post
`/api/categories` - creates a new category, accessible for role Admin.

Exemplary link:
`http://localhost:8080/api/categories`

Exemplary request:
```json
{
    "name": "category",
    "description": "some description"
}
```
Name must not be blank, description may be null or empty.

**Response status code**: 201

Exemplary response:
```json
{
    "id": "1",
    "name": "category",
    "description": "some description"
}
```

### Put
`http://localhost:8080/api/categories/{id}` - updates category with specified id, accessible for role Admin. Overwrites the object with specified id by setting this id to the object from request.

Exemplary link:
`http://localhost:8080/api/categories/1`

Exemplary request:
```json
{
    "name": "updated category",
    "description": "updated description"
}
```
Name must not be blank, description may be null or empty.

**Response status code**: 202

Exemplary response:
```json
{
    "id": "1",
    "name": "updated category",
    "description": "updated description"
}
```

### Delete
`http://localhost:8080/api/categories/{id}` - soft-deletes category with specified id, accessible for role Admin.

Exemplary link:
`http://localhost:8080/api/categories/1`

**Response status code**: 200

## Order mappings
### Get
`/api/orders` - returns list of OrderDto, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/orders`

**Response status code**: 200

Exemplary response:
```json
[
    {
        "id": 1,
        "userId": 3,
        "orderItemResponseDtos": [
            {
                "id": 1,
                "bookId": 2,
                "quantity": 4
            },
            {
                "id": 2,
                "bookId": 5,
                "quantity": 1
            }
        ],
        "orderDate": "2024-02-22T00:06:04",
        "total": 54.98,
        "status": "PENDING"
    }
]
```
---

`/api/orders/{orderId}/items` - returns list of OrderItemResponseDto from specified order, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/orders/1/items`

**Response status code**: 200

Exemplary response:
```json
[
    {
        "id": 1,
        "bookId": 2,
        "quantity": 4
    },
    {
        "id": 2,
        "bookId": 5,
        "quantity": 1
    }
]
```
---

`/api/orders/{orderId}/items/{itemId}` - returns OrderItemResponseDto with itemId from order with orderId, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/orders/1/items/2`

**Response status code**: 200

Exemplary response:
```json
{
    "id": 1,
    "bookId": 2,
    "quantity": 4
}
```

### Post
`/api/orders` - creates new order, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/orders`

Exemplary request:
```json
{
  "shippingAddress": "some address"
}
```
Shipping address must not be blank.

**Response status code**: 201

Exemplary response:
```json
{
    "id": 1,
    "userId": 3,
    "orderItemResponseDtos": [
        {
            "id": 2,
            "bookId": 5,
            "quantity": 1
        },
        {
            "id": 1,
            "bookId": 2,
            "quantity": 4
        }
    ],
    "orderDate": "2024-02-22T00:06:03.9277497",
    "total": 54.98,
    "status": "PENDING"
}
```

### Patch
`/api/orders/{id}` - updates order status, accessible for role Admin.

Exemplary link:
`http://localhost:8080/api/orders/1`

Exemplary request:
```json
{
  "status": 2
}
```

**Response status code**: 202

Exemplary response:
```json
{
    "id": 1,
    "userId": 3,
    "orderItemResponseDtos": [
        {
            "id": 2,
            "bookId": 5,
            "quantity": 1
        },
        {
            "id": 1,
            "bookId": 2,
            "quantity": 4
        }
    ],
    "orderDate": "2024-02-22T00:06:04",
    "total": 54.98,
    "status": "DELIVERED"
}
```


## ShoppingCart mappings

### Get
`/api/cart` - returns ShoppingCartDto of logged-in user, user info is taken from SecurityContext. Accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/cart`

**Response status code**: 200

Exemplary response:
```json
{
    "id": 3,
    "userId": 3,
    "cartItemsIds": [
        5,
        6,
        7
    ]
}
```

### Post
`/api/cart` - adds book to the shopping cart of logged in user, returns ShoppingCartDto, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/cart/`

Exemplary request:
```json
{
    "bookId": 4,
    "quantity": 1
}
```
Name must not be blank, description may be null or empty.

**Response status code**: 201

Exemplary response:
```json
{
    "id": 3,
    "userId": 3,
    "cartItemsIds": [
        5,
        6,
        7,
        9,
        10,
        11
    ]
}
```

### Put
`/api/cart/cart-items/{id}` - changes quantity field value of the cart item with specified id in the logged-in user’s shopping cart, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/cart/cart-items/11`

Exemplary request:
```json
{
  "quantity": 2
}
```
**Response status code**: 202
Exemplary response:
```json
{
    "bookId": 4,
    "quantity": 2
}
```

### Delete
`/api/cart/cart-items/{id}` - soft-deletes cart item with specified id from the logged-in user’s shopping cart, accessible for roles User, Admin.

Exemplary link:
`http://localhost:8080/api/cart/cart-items/11`

**Response status code**: 200

