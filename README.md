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
      // ... more products
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
