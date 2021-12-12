# Phonebook
A client web application that creates and manages contact information using Spring framework. It uses role-based access control as the model for configuring the necessary level of control a user should have. Only those with admin or member roles are allowed to perform CRUD operations; they can add, view, edit, and delete contacts in the phonebook which is stored in an H2 database. However, if they have a guest role, they are restricted access.

# Dependencies
- H2 Database
- Lombok
- Spring Data JDBC
- Spring Security
- Spring Web
- Thymeleaf
