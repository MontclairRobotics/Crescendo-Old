# Styleguide
## Basic Principles
- Naming Schemes
    - Constants & Enums in SHOUTING_CASE
    - Variables in camelCase
    - Classes in UpperCamelCase
    - Do not use single letter variables
        - Exception when using for loops
- Functions / Blocks
    - Opening bracket on same line as function declaration
    - Switch statements are allowed
    - (simple) Ternary operators are allowed (no nesting, etc)
    - When calling methods with several parameters, wrap each parameter to a new line.
        - Exception for extremely short method calls
- OOP
    - Private unless explicity necessary.
- Comments
    - Comments should be on an otherwise empty line
    - Use TODOs for uncompleted or buggy code
- Misc
    - Imports
        - Should be at the very start of the file
        - Imports referencing internal code should be closer to the start
    - Commands with the .withName() method should mention the subsystem then the action
        - Examples: "Shooter Stop" "Intake In"

