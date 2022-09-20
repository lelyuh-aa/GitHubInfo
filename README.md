# GitHubInfo: Review of GitHub public repositories

The application provides simple access to common information about public repositories on GitHub.

You can enter any valid GitHub username on first screen and get the following information:
- list of his public repositories with name and short description
- detail information about each repository containing name, description, programming language, count of commits per every months and commits authors.

At any time, you can return to the previous screen and request data for another user or repository.

Also you can save any logins which repositories were successfully loaded in you Favorites and choose it on first screen on next attempts.
Of course you can remove logins previously added in Favorites, if you want.

## Getting started
The application is completely ready to work after the project is built and installed on the device.
Additional actions to refine the program code are not required.

## Features
The application contains several important technical features:

### Smooth animated display
The detail screen contains an animated data area at the bottom that fades in and out as you open and close the screen, respectively.
Information about the count of commits in given repository for each month is cyclical changing every 1,5 second in chronological order
and using special created custom view for these purposes.
The relative value of the current month commits to the maximum amount for the month in this repository is displayed
using a variable in height bar view.

### Caching
The application supports caching of all network requests and allows you to avoid excessive load on the server
and minimize the user's waiting time for the result.

### Release version
The application fully supports work in the release version with code obfuscation and is ready for signing and publishing

## Architecture and project structure
The application uses a modern approach to architecture using principles Clean Architecture and SOLID for layering all components
and the relationship between them.

### Data layer
Package "data". Contains classes for making requests to the network and a repository implementation for transforming data into suitable models
for domain layer.

### Domain layer
Package "domain". Contains all business logic with a set of use cases to perform all the necessary tasks for the subsequent display
of information to the user.

### Presentation layer
Package "presentation". Contains all classes for displaying information on screens and provides processing and transmission of user-entered data.
For interaction between the components of the layer, a standard approach is used with Architecture Components and the MVVM pattern.

### DI
Package "di". Provides a dependency injection implementation to avoid manually creating instances of classes supplied as dependencies
and controlling the number of such instances that are created.

### Testing
Package "test". All implementations of data and domain layer classes, as well as some classes from the presentation layer (ViewModel classes),
are completely covered by unit tests.

## 3d-party libraries
The application uses several 3d-party libraries for it work:
- [Retrofit](https://github.com/square/retrofit) - to make requests to the network
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) - to perform asynchronous tasks
- [Dagger](https://github.com/google/dagger) - framework to implement dependency injection
- [Mockk](https://mockk.io/) - for support testing on Kotlin

## Contacts
For all questions related to improving the project, fixing bugs, etc., do not hesitate to contact the author of the project
Aleksandr Leliukh (lelyuh46@gmail.com)

Enjoy work with project and don't break it! :)
