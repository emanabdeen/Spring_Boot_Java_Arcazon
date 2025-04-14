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


# Order API Documentation

This document describes the API endpoints for managing orders. The API is built using Spring Boot and exposes endpoints under the `/api/orders` base path.

## Base URL

`/api/orders`

## Endpoints

### 1. Get All Orders

* **Endpoint:** `GET /api/orders/all`
* **Description:** Retrieves a list of all orders.
* **HTTP Method:** `GET`
* **Request Body:** None
* **Response Codes:**
  * `200 OK`: Successfully retrieved the list of orders. Returns a JSON array of `OrderResponse` objects.
  * `404 Not Found`: No orders were found. Returns a JSON object with a `message`.
  * `500 Internal Server Error`: An error occurred while retrieving the orders. Returns a JSON object with `error` and `details`.
* **Response Body (Success - 200 OK):**
    ```json
    [
      {
        "id": 1,
        "customer_id": 1,
        "orderDate": "2025-01-05T10:15:00Z",
        "totalAmount": 239.96,
        "createdAt": "2025-04-11T15:14:49Z",
        "updatedAt": "2025-04-11T15:14:49Z"
      },
      {
        "id": 2,
        "customer_id": 2,
        "orderDate": "2025-01-07T14:22:00Z",
        "totalAmount": 59.99,
        "createdAt": "2025-04-11T15:14:49Z",
        "updatedAt": "2025-04-11T15:14:49Z"
      }
    ]
    ```
* **Response Body (Not Found - 404 Not Found):**
    ```json
    {
      "message": "No orders found"
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to retrieve orders",
      "details": "Error message details..."
    }
    ```

### 2. Get Order by ID

* **Endpoint:** `GET /api/orders/{id}`
* **Description:** Retrieves a specific order by its ID.
* **HTTP Method:** `GET`
* **Path Parameter:**
  * `id` (required): The unique identifier of the order.
* **Request Body:** None
* **Response Codes:**
  * `200 OK`: Successfully retrieved the order. Returns a JSON object of `OrderResponse`.
  * `404 Not Found`: Order not found with the given ID. Returns a JSON object with a `message`.
  * `500 Internal Server Error`: An error occurred while retrieving the order. Returns a JSON object with `error` and `details`.
* **Response Body (Success - 200 OK):**
    ```json
    {
      "id": 25,
      "customer_id": 21,
      "orderDate": "2025-04-14T00:01:53Z",
      "totalAmount": 602.00,
      "createdAt": "2025-04-14T00:01:53Z",
      "updatedAt": "2025-04-14T00:19:25Z"
    }
    ```
* **Response Body (Not Found - 404 Not Found):**
    ```json
    {
      "message": "Order not found with id: 25"
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to retrieve order",
      "details": "Error message details..."
    }
    ```

### 3. Get All Orders by Customer ID

* **Endpoint:** `GET /api/orders/customer/{id}`
* **Description:** Retrieves a list of all orders placed by a specific customer ID.
* **HTTP Method:** `GET`
* **Path Parameter:**
  * `id` (required): The unique identifier of the customer.
* **Request Body:** None
* **Response Codes:**
  * `200 OK`: Successfully retrieved the list of orders for the customer. Returns a JSON array of `OrderResponse` objects.
  * `404 Not Found`: No orders were found for the given customer ID. Returns a JSON object with a `message`.
  * `500 Internal Server Error`: An error occurred while retrieving the orders. Returns a JSON object with `error` and `details`.
* **Response Body (Success - 200 OK):**
    ```json
    [
      {
        "id": 21,
        "customer_id": 16,
        "orderDate": "2025-04-13T21:47:44Z",
        "totalAmount": 37.99,
        "createdAt": "2025-04-13T21:47:44Z",
        "updatedAt": "2025-04-13T21:47:44Z"
      },
      {
        "id": 22,
        "customer_id": 16,
        "orderDate": "2025-04-13T21:51:47Z",
        "totalAmount": 45.99,
        "createdAt": "2025-04-13T21:51:47Z",
        "updatedAt": "2025-04-13T21:51:47Z"
      }
      
    ]
    ```
* **Response Body (Not Found - 404 Not Found):**
    ```json
    {
      "message": "No orders found for customer id: 21"
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to retrieve orders for customer id: 101",
      "details": "Error message details..."
    }
    ```

### 4. Create a New Order

* **Endpoint:** `POST /api/orders`
* **Description:** Creates a new order. This operation also validates product stock and updates it upon successful order creation.
* **HTTP Method:** `POST`
* **Request Body:** A JSON object representing the order to be created. The body should conform to the `OrderRequest` structure.
    ```json
    {
      "customer_id": 101,
      "totalAmount": 75.50,
      "orderItems": [
        {
          "productId": 1,
          "quantity": 2
        },
        {
          "productId": 2,
          "quantity": 1
        }
      ]
    }
    ```
* **Request Content Type:** `application/json`
* **Response Codes:**
  * `201 Created`: Successfully created the new order. Returns a JSON object of the newly created `OrderResponse`.
  * `400 Bad Request`: The request body is invalid (null, missing customer\_id, missing totalAmount, missing orderItems, empty orderItems) or if one or more products in the order do not have enough stock. Returns a JSON object with an `error` message.
  * `500 Internal Server Error`: An error occurred while creating the order. Returns a JSON object with `error` and `details`.
* **Response Body (Created - 201 Created):**
    ```json
    {
      "id": 25,
      "customer_id": 101,
      "orderDate": "2025-04-14T00:01:52.986690500Z",
      "totalAmount": 600,
      "createdAt": "2025-04-14T00:01:52.986690500Z",
      "updatedAt": "2025-04-14T00:01:52.986690500Z"
    }
    ```
* **Response Body (Bad Request - 400 Bad Request - Null Request):**
    ```json
    {
      "error": "Order request cannot be null"
    }
    ```
* **Response Body (Bad Request - 400 Bad Request - Missing customer_id):**
    ```json
    {
      "error": "customer_id is missing"
    }
    ```
* **Response Body (Bad Request - 400 Bad Request - Missing totalAmount):**
    ```json
    {
      "error": "totalAmount is missing"
    }
    ```
* **Response Body (Bad Request - 400 Bad Request - Missing order-items):**
    ```json
    {
      "error": "order-items are missing"
    }
    ```
* **Response Body (Bad Request - 400 Bad Request - Not enough stock):**
    ```json
    {
      "error": "one or more products have not enough stock"
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to create order",
      "details": "Error message details..."
    }
    ```

### 5. Update an Existing Order

* **Endpoint:** `PUT /api/orders/{id}`
* **Description:** Updates an existing order with the specified ID.
* **HTTP Method:** `PUT`
* **Path Parameter:**
  * `id` (required): The unique identifier of the order to be updated.
* **Request Body:** A JSON object containing the updated order information. The body should conform to the `OrderRequest` structure.
    ```json
    {
      "customer_id": 102,
      "totalAmount": 150.00
    }
    ```
* **Request Content Type:** `application/json`
* **Response Codes:**
  * `200 OK`: Successfully updated the order. Returns a JSON object indicating success and the updated `Order` object (note: the response structure in the code includes a message and success flag).
  * `400 Bad Request`: The request body is invalid (null). Returns a JSON object with an `error` message.
  * `404 Not Found`: Order not found with the given ID. Returns an empty response body with a `404` status.
  * `500 Internal Server Error`: An error occurred while updating the order. Returns a JSON object with `error` and `details`.
* **Response Body (Success - 200 OK):**
    ```json
    {
      "order": {
        "id": 25,
        "orderDate": "2025-04-14T00:01:53Z",
        "totalAmount": 602,
        "createdAt": "2025-04-14T00:01:53Z",
        "updatedAt": "2025-04-14T00:19:24.502106Z",
        "orderItems": [
            {
                "id": {
                    "orderId": 25,
                    "productId": 55
                },
                "quantity": 2,
                "unitPrice": 300.00,
                "createdAt": "2025-04-14T00:01:53Z",
                "updatedAt": "2025-04-14T00:01:53Z"
            }
        ]
    },
    "success": true,
    "message": "Order updated successfully"
    }
    ```
* **Response Body (Bad Request - 400 Bad Request - Null Request):**
    ```json
    {
      "error": "Order data cannot be null"
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to update order",
      "details": "Error message details..."
    }
    ```

### 6. Delete an Order

* **Endpoint:** `DELETE /api/orders/{id}`
* **Description:** Deletes the order with the specified ID.
* **HTTP Method:** `DELETE`
* **Path Parameter:**
  * `id` (required): The unique identifier of the order to be deleted.
* **Request Body:** None
* **Response Codes:**
  * `200 OK`: Successfully deleted the order. Returns a JSON object indicating success and the ID of the deleted order.
  * `404 Not Found`: Order not found with the given ID. Returns an empty response body with a `404` status.
  * `500 Internal Server Error`: An error occurred while deleting the order. Returns a JSON object with `error` and `details`.
* **Response Body (Success - 200 OK):**
    ```json
    {
      "success": true,
      "message": "Order deleted successfully",
      "deletedId": 1
    }
    ```
* **Response Body (Internal Server Error - 500 Internal Server Error):**
    ```json
    {
      "error": "Failed to delete order",
      "details": "Error message details...",
      "productId": 1
    }
    ```

