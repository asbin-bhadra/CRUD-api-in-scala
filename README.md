## About
I have implemented CRUD api for creating user of type customer and admin in scala language with SBT.

## CRUD Features
1. addUser()- Add user in a list
1. getUsers() - Return list of users
1. getUserById() - Returns a particular user by ID
1. updateUserName() - Update username of particular user
1. deleteUserById() - Delete user by ID
1. deleteAllUsers() - Delete All users


## Compile Code
```
sbt compile
```
## Run Code
```
sbt run
```

## Generate scalastyle config file ( Already created in this project )
```
sbt scalastyleGenerateConfig
```

## Check scalastyle for code
```
sbt scalastyle