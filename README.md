# Spring_Boot_Java_Arcazon

# Product API Documentation

This document describes the API endpoints for managing products. The API is built using Spring Boot and exposes endpoints under the `/api/products` base path.

## Base URL

`/api/products`

## Endpoints

### 1. Get All Products

* **Endpoint:** `GET /api/products/all`
* **Description:** Retrieves a list of all products.
* **HTTP Method:** `GET`
* **Request Body:** None
* **Response Codes:**
    * `200 OK`: Successfully retrieved the list of products. Returns a JSON array of `ProductResponse` objects.
    * `404 Not Found`: No products were found. Returns a JSON object with a `message`.
    * `500 Internal Server Error`: An error occurred while retrieving the products. Returns a JSON object with `error` and `details`.
* **Response Body (Success - 200 OK):**
    ```json
    [
      {
        "id": 1,
        "name": "Blue Large Tee",
        "description": "100% organic cotton",
        "price": 29.99,
        "stock": 120,
        "categoryId": 1
    },
    {
        "id": 2,
        "name": "Red Large Tee",
        "description": "Breathable fabric, crew neck",
        "price": 27.5,
        "stock": 80,
        "categoryId": 1
    }
    ]
    ```
* **Response Body (Not Found - 404 Not Found):**
    ```json
    {
      "message": "No products found"
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to retrieve products",
      "details": "Error message details..."
    }
    ```

### 2. Get Product by ID

* **Endpoint:** `GET /api/products/{id}`
* **Description:** Retrieves a specific product by its ID.
* **HTTP Method:** `GET`
* **Path Parameter:**
    * `id` (required): The unique identifier of the product.
* **Request Body:** None
* **Response Codes:**
    * `200 OK`: Successfully retrieved the product. Returns a JSON object of `ProductResponse`.
    * `404 Not Found`: Product not found with the given ID. Returns a JSON object with a `message`.
    * `500 Internal Server Error`: An error occurred while retrieving the product. Returns a JSON object with `error` and `details`.
* **Response Body (Success - 200 OK):**
    ```json
    {
    "id": 1,
    "name": "Blue Large Tee",
    "description": "100% organic cotton",
    "price": 29.99,
    "stock": 120,
    "categoryId": 1
}
    ```
* **Response Body (Not Found - 404 Not Found):**
    ```json
    {
      "message": "Product not found with id: 1"
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to retrieve product",
      "details": "Error message details..."
    }
    ```

### 3. Create a New Product

* **Endpoint:** `POST /api/products`
* **Description:** Creates a new product.
* **HTTP Method:** `POST`
* **Request Body:** A JSON object representing the product to be created. The body should conform to the `ProductRequest` structure.
    ```json
    {
      "name": "New Product",
      "description": "Description of the new product",
      "price": 19.99,
      "stock": 200,
      "categoryId": 102
    }
    ```
* **Request Content Type:** `application/json`
* **Response Codes:**
    * `201 Created`: Successfully created the new product. Returns a JSON object of the newly created `ProductResponse`.
    * `400 Bad Request`: The request body is invalid or the specified category ID does not exist. Returns a JSON object with an `error` message.
    * `500 Internal Server Error`: An error occurred while creating the product. Returns a JSON object with `error` and `details`.
* **Response Body (Created - 201 Created):**
    ```json
    {
      "id": 3,
      "name": "New Product",
      "description": "Description of the new product",
      "price": 19.99,
      "stock": 200,
      "categoryId": 102
    }
    ```
* **Response Body (Bad Request - 400 Bad Request - Null Request):**
    ```json
    {
      "error": "Product request cannot be null"
    }
    ```
* **Response Body (Bad Request - 400 Bad Request - Category Not Found):**
    ```json
    {
      "error": "Category not found with id: 102"
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to create product",
      "details": "Error message details..."
    }
    ```

### 4. Update an Existing Product

* **Endpoint:** `PUT /api/products/{id}`
* **Description:** Updates an existing product with the specified ID.
* **HTTP Method:** `PUT`
* **Path Parameter:**
    * `id` (required): The unique identifier of the product to be updated.
* **Request Body:** A JSON object containing the updated product information. The body should conform to the `ProductRequest` structure.
    ```json
    {
      "name": "Updated Product A",
      "description": "Updated description of Product A",
      "price": 29.99,
      "stock": 150,
      "categoryId": 101
    }
    ```
* **Request Content Type:** `application/json`
* **Response Codes:**
    * `200 OK`: Successfully updated the product. Returns a JSON object indicating success and the updated `ProductResponse`.
    * `400 Bad Request`: The request body is invalid or the specified category ID does not exist. Returns a JSON object with an `error` message.
    * `404 Not Found`: Product not found with the given ID. Returns an empty response body with a `404` status.
    * `500 Internal Server Error`: An error occurred while updating the product. Returns a JSON object with `error` and `details`.
* **Response Body (Success - 200 OK):**
    ```json
    {
      "message": "Product updated successfully",
      "success": true,
      "product": {
          "id": 3,
          "name": "Updated Product A",
        "description": "Updated description of Product A",
        "price": 29.99,
        "stock": 150,
        "categoryId": 101
      }
    }
    ```
* **Response Body (Bad Request - 400 Bad Request - Null Request):**
    ```json
    {
      "error": "Product data cannot be null"
    }
    ```
* **Response Body (Bad Request - 400 Bad Request - Category Not Found):**
    ```json
    {
      "error": "Category not found"
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to update product",
      "details": "Error message details..."
    }
    ```

### 5. Delete a Product

* **Endpoint:** `DELETE /api/products/{id}`
* **Description:** Deletes the product with the specified ID.
* **HTTP Method:** `DELETE`
* **Path Parameter:**
    * `id` (required): The unique identifier of the product to be deleted.
* **Request Body:** None
* **Response Codes:**
    * `200 OK`: Successfully deleted the product. Returns a JSON object indicating success and the ID of the deleted product.
    * `404 Not Found`: Product not found with the given ID. Returns an empty response body with a `404` status.
    * `500 Internal Server Error`: An error occurred while deleting the product. Returns a JSON object with `error` and `details`.
* **Response Body (Success - 200 OK):**
    ```json
    {
      "success": true,
      "message": "Product deleted successfully",
      "deletedId": 1
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to delete product",
      "details": "Error message details...",
      "productId": 1
    }
    ```

# Customer API 

This section describes the API endpoints for managing customers. 

## Base URL

`/api/customers`

## Endpoints

### 1. Get All Customers

* **Endpoint:** `GET /api/customers`
* **Description:** Retrieves a list of all customers.
* **HTTP Method:** `GET`
* **Request Body:** None
* **Response Codes:**
    * `200 OK`: Successfully retrieved the list of customers.
    * `500 Internal Server Error`: An error occurred while retrieving the customers. Returns a JSON object with `error`.
* **Response Body (Success - 200 OK):**
    ```json
    [
      {
        "id": 1,
        "firstName": "Alice",
        "lastName": "Nguyen",
        "email": "alice.nguyen@myinstamail.com",
        "phone": "519‑555‑0101",
        "addressLine1": "123 Elm St",
        "city": "Kitchener",
        "province": "ON",
        "postalCode": "N2A 1A1",
        "createdAt": "2025-04-12T04:11:11Z",
        "updatedAt": "2025-04-12T04:11:11Z"
    },
    {
         "id": 2,
        "firstName": "Brandon",
        "lastName": "Smith",
        "email": "brandon.smith@myinstamail.com",
        "phone": "519‑555‑0102",
        "addressLine1": "456 Maple Ave",
        "city": "Waterloo",
        "province": "ON",
        "postalCode": "N2L 3C3",
        "createdAt": "2025-04-12T04:11:11Z",
        "updatedAt": "2025-04-12T04:11:11Z"
    }
    ]
    ```

* **Response Body (500 Internal Server Error):**
    ```json
    {
      "error": "Failed to retrieve customer"
    }
    

### 2. Get Customer by ID

* **Endpoint:** `GET /api/customers/{id}`
* **Description:** Retrieves a specific customer by its ID.
* **HTTP Method:** `GET`
* **Path Parameter:**
    * `id` (required): The unique identifier of the customer.
* **Request Body:** None
* **Response Codes:**
    * `200 OK`: Successfully retrieved the customer.
    * `204 No Content`: Successfully processed the logic but no record was found. Returns an empty JSON object.
    * `500 Internal Server Error`: An error occurred while retrieving the customer. Returns a JSON object with `error`.
* **Response Body (Success - 200 OK):**
```json
  {
    "id": 1,
    "firstName": "Alice",
    "lastName": "Nguyen",
    "email": "alice.nguyen@myinstamail.com",
    "phone": "519‑555‑0101",
    "addressLine1": "123 Elm St",
    "city": "Kitchener",
    "province": "ON",
    "postalCode": "N2A 1A1",
    "createdAt": "2025-04-12T04:11:11Z",
    "updatedAt": "2025-04-12T04:11:11Z",
    "orders": [
        {
            "id": 1,
            "orderDate": "2025-01-05T10:15:00Z",
            "totalAmount": 239.96,
            "createdAt": "2025-04-12T04:11:19Z",
            "updatedAt": "2025-04-12T04:11:19Z",
            "orderItems": [
                {
                    "id": {
                        "orderId": 1,
                        "productId": 15
                    },
                    "quantity": 2,
                    "unitPrice": 14.50,
                    "createdAt": "2025-04-12T04:11:24Z",
                    "updatedAt": "2025-04-12T04:11:24Z"
                },
                {
                    "id": {
                        "orderId": 1,
                        "productId": 4
                    },
                    "quantity": 2,
                    "unitPrice": 79.99,
                    "createdAt": "2025-04-12T04:11:24Z",
                    "updatedAt": "2025-04-12T04:11:24Z"
                }
            ]
        },
        {
            "id": 16,
            "orderDate": "2025-02-14T18:05:00Z",
            "totalAmount": 104.00,
            "createdAt": "2025-04-12T04:12:58Z",
            "updatedAt": "2025-04-12T04:12:58Z",
            "orderItems": [
                {
                    "id": {
                        "orderId": 16,
                        "productId": 31
                    },
                    "quantity": 1,
                    "unitPrice": 89.00,
                    "createdAt": "2025-04-12T04:13:03Z",
                    "updatedAt": "2025-04-12T04:13:03Z"
                },
                {
                    "id": {
                        "orderId": 16,
                        "productId": 15
                    },
                    "quantity": 4,
                    "unitPrice": 14.50,
                    "createdAt": "2025-04-12T04:13:03Z",
                    "updatedAt": "2025-04-12T04:13:03Z"
                }
            ]
        }
    ],
    "customerName": "Alice Nguyen"
}
```


* **Response Body (500 Internal Server Error):**
    ```json
    {
      "error": "Failed to retrieve customer"
    }
    ```

### 3. Create a New Customer

* **Endpoint:** `POST /api/customer`
* **Description:** Creates a new customer.
* **HTTP Method:** `POST`
* **Request Body:** A JSON object representing the customer to be created.
    ```json
    {
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jdoe@test.com",
    "phone": "999-9999-9999",
    "addressLine1": "123 weber St",
    "city": "Waterloo",
    "province": "ON",
    "postalCode": "A2A 2A2"
}
    ```
* **Request Content Type:** `application/json`
* **Response Codes:**
    * `201 Created`: Successfully created the new customer. Returns a JSON object of the newly created customer.
    * `400 Bad Request`: The request body is invalid. Returns a JSON object with an `error` message.
    * `500 Internal Server Error`: An error occurred while creating the customer. Returns a JSON object with `error`.
* **Response Body (Created - 201 Created):**
    ```json
  {
  "id": 31,
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jdoe@test.com",
  "phone": "999-9999-9999",
  "addressLine1": "123 weber St",
  "city": "Waterloo",
  "province": "ON",
  "postalCode": "A2A 2A2"
  "createdAt": "2025-04-13T19:11:22.234115Z",
  "updatedAt": "2025-04-13T19:11:22.234115Z"
  }
    ```
* **Response Body (422 Unprocessable Entity):**
    ```json
    {
      "error": "Email already exists"
    }
    ```
* **Response Body (500 Internal Server Error):**
    ```json
    {
      "error": "Failed to add customer"
    }
    ```

### 4. Update an Existing Customer

* **Endpoint:** `PUT /api/customers/`
* **Description:** Updates an existing customers with the specified ID.
* **HTTP Method:** `PUT`
* **Request Body:** A JSON object containing the updated customer information.
    ```json
    {
    "id": 16,
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "janedoe2@email.com",
    "phone": "999-9999-9999",
    "addressLine1": "123 weber St",
    "city": "Waterloo",
    "province": "ON",
    "postalCode": "A2A 2A2"
}
    ```
* **Request Content Type:** `application/json`
* **Response Codes:**
    * `200 OK`: Successfully updated the customer.
    * `400 Bad Request`: The request body is invalid. Returns a JSON object with an `error` message.
    * `500 Internal Server Error`: An error occurred while updating the customer. Returns a JSON object with `error`.
* **Response Body (Success - 200 OK):**
    ```json
    {
    "id": 31,
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "janedoe3@email.com",
    "phone": "999-9999-9999",
    "addressLine1": "123 weber St",
    "city": "Waterloo",
    "province": "ON",
    "postalCode": "A2A 2A2",
  "createdAt": "2025-04-13T19:11:22.234115Z",
  "updatedAt": "2025-04-13T19:58:34.037599Z"
}
    ```
* **Response Body (400 Bad Request - Null Request):**
    ```json
    {
      "error": "Invalid customer object"
    }
    ```

* **Response Body (500 Internal Server Error):**
    ```json
    {
      "error": "Failed to update customer"
    }
    ```

### 5. Delete a Customer

* **Endpoint:** `DELETE /api/customers/{id}`
* **Description:** Deletes the customer with the specified ID.
* **HTTP Method:** `DELETE`
* **Path Parameter:**
    * `id` (required): The unique identifier of the customer to be deleted.
* **Request Body:** None
* **Response Codes:**
    * `204 No Content`: Successfully deleted the customer.
    * `500 Internal Server Error`: An error occurred while deleting the customer. Returns a JSON object with `error` and `details`.

* **Response Body (500 Internal Server Error):**
    ```json
    {
      "error": "Failed to delete customer"
    }
    ```