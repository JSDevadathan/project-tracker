# Project Tracker 

The Project Tracker Application is a versatile tool designed to maintain project tickets



## Features
- **Create Category:** Easily create category.
- **Create Tickets:** Easily create tickets within category.
- **Create Comments:** Easily create comments within ticket.
- **Manage Multiple Tickets:** Add and organize multiple tickets.


## Endpoints (API)

### Create ticket
- **Endpoint:** `/tickets/create`
- **Method:** POST
- **Description:** Create a new ticket .

### List Tickets
- **Endpoint:** `/tickets/view`
- **Method:** GET
- **Description:** Retrieve a list of all tickets.

### Get tickets by categoryId
- **Endpoint:** `/tickets/categories/{categoryId}`
- **Method:** GET
- **Description:** Retrieve a specific tickets's details by its categoryId.

### Update Tickets
- **Endpoint:** `/tickets/{id}`
- **Method:** PUT
- **Description:** Update an existing ticket's details.

### Get tickets by categoryName
- **Endpoint:** `/tickets/categories/{categoryName}`
- **Method:** GET
- **Description:** Retrieve a specific tickets's details by its categoryName.

### Delete Ticket
- **Endpoint:** `/tickets/{id}`
- **Method:** DELETE
- **Description:** Delete a specific tickets by its ID.

### Search Ticket Details with Pagination
- **Endpoint:** `/tickets/search`
- **Method:** GET
- **Description:** Search ticket details with Pagination.

### Create category
- **Endpoint:** `/category/create`
- **Method:** POST
- **Description:** Create a new category.

### List category
- **Endpoint:** `/category/view`
- **Method:** GET
- **Description:** Retrieve a list of all category.


## Installation

1. Clone the repository to your local machine:https://github.com/JSDevadathan/project-tracker.git.
2. Open the project.
3. Ensure you have Java and Maven installed.