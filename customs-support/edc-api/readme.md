# Special Notes

This module is not included in the build process. It could be helpful to understand the management API 
and what must be done to implement the REST API manually.

## Maven Compiler Plugin and Generator

It looks like that no all combinations of the maven-compiler-plugin and the generator work together.
The following combinations seems to work:

maven-compiler-plugin 3.8.1 and generator 7.12.0
maven-compiler-plugin 3.11.0 and generator 7.12.0

Tested maven-compiler-plugin versions 3.12.0,3.13.1 und 3.14.0 are not compatible. 

## JsonValue Type

The EDC OpenAPI defines the type __JsonValue__ which provides problems with the __@JsonValue__ annotation.
To solve the problem the type __JsonValue__ is renamed to __JsonValueType.__ 

This can cause problems when calling the API.

## Retirement Example

The used name 'Retirement Example' is not valid. It does not fit the expected regular expression for names.
(The ' ' is not an allowed character for names). The name can be changed to e.g. 'Retirement_Example'

This can cause problems when calling the API.