# Backend Code Review Report

## Unused Imports

### ClientService.java

- `import com.trackmyfix.trackmyfix.aspects.annotations.UserChangeNotify;` - Not used in the code.

### AdminService.java

- `import java.sql.SQLException;` - Not used.

### TechnicianService.java

- `import com.trackmyfix.trackmyfix.aspects.annotations.UserChangeNotify;` - Not used.
- `import com.trackmyfix.trackmyfix.entity.Client;` - Not used.

## Code Inconsistencies

### Delete Logic in User Services

In ClientService, AdminService, and TechnicianService, the `delete` method has inconsistent behavior:

- If `active` is true, it calls `repository.deleteById(id)` (hard delete) but returns message "User id: X marked as INACTIVE success".
- If `active` is false, it sets `active = true` and saves, returning "User id: X marked as ACTIVE success".

This is confusing and likely a bug. Probably intended as soft delete by setting `active = false`.

### Unused Method

In AdminService, `createUserChange` method is defined but never called.

## Proposed Improvements

### Remove Unused Imports

Remove the listed unused imports to clean up the code.

### Fix Delete Logic

Refactor the delete method to perform soft delete:

- Always set `active = false` and save.
- Return appropriate message.

### Add Comments

Add JavaDoc comments to classes and methods for better readability.

### Best Practices

- Use consistent naming (e.g., camelCase).
- Ensure all exceptions are properly handled.

## Justification

- Removing unused imports reduces clutter and potential confusion.
- Fixing delete logic ensures correct behavior and prevents data loss from hard deletes.
- Comments improve maintainability.
- Following best practices enhances code quality and team collaboration.
